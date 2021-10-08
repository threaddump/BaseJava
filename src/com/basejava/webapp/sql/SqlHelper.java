package com.basejava.webapp.sql;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    // https://www.postgresql.org/docs/14/errcodes-appendix.html
    private static final String PSQL_UNIQUE_VIOLATION = "23505";

    private final ConnectionFactory connFactory;

    public SqlHelper(ConnectionFactory connFactory) {
        this.connFactory = connFactory;
    }

    public void execChecked(String sql) {
        execChecked(sql, PreparedStatement::execute);
    }

    public <T> T execChecked(String sql, SqlExecutor<T> executor) {
        try (
                Connection conn = connFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw convertSqlException(e);
        }
    }

    public void execUnchecked(Connection conn, String sql) throws SQLException {
        execUnchecked(conn, sql, PreparedStatement::execute);
    }

    public <T> T execUnchecked(Connection conn, String sql, SqlExecutor<T> executor) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        }
    }

    // transactional wrapper for use with execUnchecked(...)
    public <T> T execTransaction(SqlTransaction<T> transaction) {
        try (Connection conn = connFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T result = transaction.execute(conn);
                conn.commit();
                return result;
            } catch (SQLException e) {
                conn.rollback();
                throw convertSqlException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private RuntimeException convertSqlException(SQLException e) {
        if (e instanceof PSQLException) {
            final String sqlState = e.getSQLState();
            if (sqlState.equals(PSQL_UNIQUE_VIOLATION)) {
                return new ExistStorageException(e);
            }
        }

        return new StorageException(e);
    }
}
