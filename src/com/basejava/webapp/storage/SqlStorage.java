package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {

    private static final Logger LOGGER = Logger.getLogger(SqlStorage.class.getName());

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> {
            // very expensive operation; TODO: add caching?
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        });
    }

    @Override
    public void clear() {
        LOGGER.info("clear()");
        sqlHelper.execChecked("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        LOGGER.info("update(): r=" + r);
        sqlHelper.execChecked(
                "UPDATE resume SET full_name = ? WHERE uuid = ?",
                ps -> {
                    ps.setString(1, r.getFullName());
                    final String uuid = r.getUuid();
                    ps.setString(2, uuid);

                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        LOGGER.info("save(): r=" + r);
        final String uuid = r.getUuid();
        sqlHelper.execTransaction(conn -> {
            sqlHelper.execUnchecked(conn, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
                ps.setString(1, uuid);
                ps.setString(2, r.getFullName());
                ps.execute();
                return null;
            });
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                sqlHelper.execUnchecked(conn, "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)", ps -> {
                    ps.setString(1, uuid);
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.execute();
                    return null;
                });
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOGGER.info("get(): uuid=" + uuid);
        return sqlHelper.execChecked(
                "SELECT * FROM resume r WHERE r.uuid = ?",
                //"SELECT * FROM resume r JOIN contact c ON r.uuid = c.resume_uuid WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    final ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                }
        );
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("delete(): uuid=" + uuid);
        sqlHelper.execChecked(
                "DELETE FROM resume r WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        LOGGER.info("getAllSorted()");
        return sqlHelper.execChecked(
                "SELECT * FROM resume ORDER BY full_name, uuid",
                ps -> {
                    final ResultSet rs = ps.executeQuery();
                    final List<Resume> resumes = new ArrayList<Resume>();
                    while (rs.next()) {
                        resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                    }
                    return resumes;
                }
        );
    }

    @Override
    public int size() {
        LOGGER.info("size()");
        return sqlHelper.execChecked(
                "SELECT COUNT(*) FROM resume",
                ps -> {
                    final ResultSet rs = ps.executeQuery();
                    return rs.next() ? rs.getInt(1) : 0;
                }
        );
    }
}
