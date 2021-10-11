package com.basejava.webapp.storage.factory;

public class StorageConfig {
    private final String storageType;
    private final String storageDir;
    private final String streamSerializerType;
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;
    private final String readOnly;

    public StorageConfig(String storageType, String storageDir, String streamSerializerType,
                         String dbUrl, String dbUsername, String dbPassword, String readOnly) {
        this.storageType = storageType;
        this.storageDir = storageDir;
        this.streamSerializerType = streamSerializerType;
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.readOnly = readOnly;
    }

    public StorageType getStorageType() { return StorageType.valueOf(storageType); }

    public String getStorageDir() { return storageDir; }

    public StreamSerializerType getStreamSerializerType() { return StreamSerializerType.valueOf(streamSerializerType); }

    public String getDbUrl() { return dbUrl; }

    public String getDbUsername() { return dbUsername; }

    public String getDbPassword() { return dbPassword; }

    public boolean isReadOnly() { return Boolean.parseBoolean(readOnly); }
}
