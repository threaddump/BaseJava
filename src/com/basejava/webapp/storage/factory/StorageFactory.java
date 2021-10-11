package com.basejava.webapp.storage.factory;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.*;
import com.basejava.webapp.storage.strategy.*;

import java.io.File;

public class StorageFactory {

    private StorageFactory() {
    }

    public static Storage getStorage() {
        final StorageConfig cfg = Config.get().getStorageConfig();
        return getStorage(cfg);
    }

    public static Storage getStorage(StorageConfig cfg) {
        return getStorage(cfg.getStorageType(), cfg);
    }

    private static Storage getStorage(StorageType storageType, StorageConfig cfg) {
        Storage storage = null;
        switch (storageType) {
            case ARRAY:
                storage = new ArrayStorage();
                break;
            case SORTED_ARRAY:
                storage = new SortedArrayStorage();
                break;
            case LIST:
                storage = new ListStorage();
                break;
            case MAP_UUID:
                storage = new MapUuidStorage();
                break;
            case MAP_RESUME:
                storage = new MapResumeStorage();
                break;
            case FILE:
                storage = new FileStorage(
                        new File(cfg.getStorageDir()),
                        getStreamSerializer(cfg)
                );
                break;
            case PATH:
                storage = new PathStorage(
                        cfg.getStorageDir(),
                        getStreamSerializer(cfg)
                );
                break;
            case SQL:
                storage = new SqlStorage(
                        cfg.getDbUrl(),
                        cfg.getDbUsername(),
                        cfg.getDbPassword()
                );
                break;
            default:
                throw new IllegalStateException("Unknown storageType = " + storageType);
        }

        if (cfg.isReadOnly()) {
            storage = new ReadOnlyStorageDecorator(storage);
        }
        return storage;
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
