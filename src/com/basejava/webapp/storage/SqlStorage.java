package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.Section;
import com.basejava.webapp.model.SectionType;
import com.basejava.webapp.sql.SqlHelper;
import com.basejava.webapp.util.JsonParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SqlStorage implements Storage {

    private static final Logger LOGGER = Logger.getLogger(SqlStorage.class.getName());

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            LOGGER.info("Loading JDBC driver explicitly");
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to load JDBC driver", e);
        }

        sqlHelper = new SqlHelper(() -> {
            // very expensive operation; TODO: add caching?
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        });
    }

    @Override
    public void clear() {
        LOGGER.info("clear()");
        // tables "contact" and "section" are handled by "on delete cascade"
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
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(uuid);
                }
                return null;
            });

            deleteContacts(conn, uuid);
            saveContacts(conn, r);

            deleteSections(conn, uuid);
            saveSections(conn, r);

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

            saveContacts(conn, r);
            saveSections(conn, r);

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
                    readContact(rs, r);
                }
                return null;
            });

            sqlHelper.execUnchecked(conn, "SELECT * FROM section WHERE resume_uuid = ?", ps -> {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    readSection(rs, r);
                }
                return null;
            });

            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("delete(): uuid=" + uuid);
        // tables "contact" and "section" are handled by "on delete cascade"
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
                    readContact(rs, r);
                }
                return null;
            });

            sqlHelper.execUnchecked(conn, "SELECT * FROM section", ps -> {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = resumeMap.get(rs.getString("resume_uuid"));
                    readSection(rs, r);
                }
                return null;
            });

            return resumeMap.values().stream().sorted().collect(Collectors.toList());
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

    private void deleteContacts(Connection conn, String uuid) throws SQLException {
        sqlHelper.execUnchecked(conn, "DELETE FROM contact WHERE resume_uuid = ?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
            return null;
        });
    }

    private void saveContacts(Connection conn, Resume r) throws SQLException {
        final String uuid = r.getUuid();
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
    }

    private void readContact(ResultSet rs, Resume r) throws SQLException {
        r.setContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
    }

    private void deleteSections(Connection conn, String uuid) throws SQLException {
        sqlHelper.execUnchecked(conn, "DELETE FROM section WHERE resume_uuid = ?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
            return null;
        });
    }

    private void saveSections(Connection conn, Resume r) throws SQLException {
        final String uuid = r.getUuid();
        sqlHelper.execUnchecked(conn, "INSERT INTO section (resume_uuid, type, value) VALUES (?, ?, ?)", ps -> {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, uuid);
                ps.setString(2, e.getKey().name());
                ps.setString(3, JsonParser.toJson(e.getValue(), Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
            return null;
        });
    }

    private void readSection(ResultSet rs, Resume r) throws SQLException {
        SectionType sectionType = SectionType.valueOf(rs.getString("type"));
        Section section = JsonParser.fromJson(rs.getString("value"), Section.class);
        r.setSection(sectionType, section);
    }
}
