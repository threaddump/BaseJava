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

    public <T> T execute(String sql, SqlExecutor<T> executor) {
        try (
                Connection conn = connFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw processSqlException(e);
        }
    }

    public void execute(String sql) {
        execute(sql, PreparedStatement::execute);
    }

    private RuntimeException processSqlException(SQLException e) {
        if (e instanceof PSQLException) {
            final String sqlState = e.getSQLState();
            if (sqlState.equals(PSQL_UNIQUE_VIOLATION)) {
                return new ExistStorageException(e);
            }
        }

        return new StorageException(e);
    }
}
