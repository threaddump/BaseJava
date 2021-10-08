package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;
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
        final String uuid = r.getUuid();
        sqlHelper.execTransaction(conn -> {
            sqlHelper.execUnchecked(conn, "UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
                ps.setString(1, r.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
                return null;
            });

            sqlHelper.execUnchecked(conn, "DELETE FROM contact WHERE resume_uuid = ?", ps -> {
                ps.setString(1, uuid);
                ps.execute();
                return null;
            });

            sqlHelper.execUnchecked(conn, "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)", ps -> {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    ps.setString(1, uuid);
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
                return null;
            });

            return null;
        });
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

            sqlHelper.execUnchecked(conn, "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)", ps -> {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    ps.setString(1, uuid);
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
                return null;
            });

            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOGGER.info("get(): uuid=" + uuid);
        return sqlHelper.execTransaction(conn -> {
            final Resume r = sqlHelper.execUnchecked(conn, "SELECT * FROM resume WHERE uuid = ?", ps -> {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                return new Resume(uuid, rs.getString("full_name"));
            });

            sqlHelper.execUnchecked(conn, "SELECT * FROM contact WHERE resume_uuid = ?", ps -> {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    r.setContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                }
                return null;
            });

            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("delete(): uuid=" + uuid);
        sqlHelper.execChecked("DELETE FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOGGER.info("getAllSorted()");
        return sqlHelper.execTransaction(conn -> {
            final Map<String, Resume> resumeMap = new HashMap<>();
            sqlHelper.execUnchecked(conn, "SELECT * FROM resume", ps -> {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumeMap.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
                return null;
            });

            sqlHelper.execUnchecked(conn, "SELECT * FROM contact", ps -> {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = resumeMap.get(rs.getString("resume_uuid"));
                    r.setContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                }
                return null;
            });

            final List<Resume> resumes = new ArrayList<>(resumeMap.values());
            Collections.sort(resumes);
            return resumes;
        });
    }

    @Override
    public int size() {
        LOGGER.info("size()");
        return sqlHelper.execChecked("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
