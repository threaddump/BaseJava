package com.basejava.webapp;

import com.basejava.webapp.storage.factory.StorageConfig;

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

    private Config() {
        try (InputStream is = new FileInputStream(CONFIG_FILE)) {
            props.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + CONFIG_FILE.getAbsolutePath());
        }
    }

    public static Config get() { return INSTANCE; }

    public StorageConfig getStorageConfig() {
        return new StorageConfig(
                props.getProperty("storage.type"),
                props.getProperty("storage.dir"),
                props.getProperty("serializer.type"),
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
                );
    }
}
