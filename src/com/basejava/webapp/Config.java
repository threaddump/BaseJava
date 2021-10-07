package com.basejava.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    // keep in mind the order of initialization
    private static final File CONFIG_FILE = new File("./config/resumes.properties");
    private static final Config INSTANCE = new Config();

    private final Properties props = new Properties();
    private final File storageDir;

    private Config() {
        try (InputStream is = new FileInputStream(CONFIG_FILE)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + CONFIG_FILE.getAbsolutePath());
        }
    }

    public static Config get() { return INSTANCE; }

    public File getStorageDir() { return storageDir; }

    public String getDbUrl() { return props.getProperty("db.url"); }

    public String getDbUser() { return props.getProperty("db.user"); }

    public String getDbPassword() { return props.getProperty("db.password"); }
}
