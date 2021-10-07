package com.basejava.webapp.storage.factory;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.*;
import com.basejava.webapp.storage.strategy.*;

import java.io.File;

public class StorageFactory {

    private StorageFactory() {
    }

    public static Storage getStorage() {
        return getStorage(Config.get().getStorageConfig());
    }

    public static Storage getStorage(StorageConfig cfg) {
        return getStorage(cfg.getStorageType(), cfg);
    }

    public static Storage getStorage(StorageType storageType, StorageConfig cfg) {
        switch (storageType) {
            case ARRAY:
                return new ArrayStorage();
            case SORTED_ARRAY:
                return new SortedArrayStorage();
            case LIST:
                return new ListStorage();
            case MAP_UUID:
                return new MapUuidStorage();
            case MAP_RESUME:
                return new MapResumeStorage();
            case FILE:
                return new FileStorage(
                        new File(cfg.getStorageDir()),
                        getStreamSerializer(cfg)
                );
            case PATH:
                return new PathStorage(
                        cfg.getStorageDir(),
                        getStreamSerializer(cfg)
                );
            case SQL:
                return new SqlStorage(
                        cfg.getDbUrl(),
                        cfg.getDbUsername(),
                        cfg.getDbPassword()
                );
            default:
                throw new IllegalStateException("Unknown storageType = " + storageType);
        }
    }

    private static StreamSerializer getStreamSerializer(StorageConfig cfg) {
        return getStreamSerializer(cfg.getStreamSerializerType());
    }

    private static StreamSerializer getStreamSerializer(StreamSerializerType serializerType) {
        switch (serializerType) {
            case OBJECT_STREAM:
                return new ObjectStreamSerializer();
            case XML:
                return new XmlStreamSerializer();
            case JSON:
                return new JsonStreamSerializer();
            case DATA_STREAM:
                return new DataStreamSerializer();
            default:
                throw new IllegalStateException("Unknown serializerType = " + serializerType);
        }
    }
}
