package com.basejava.webapp.storage.factory;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.*;
import com.basejava.webapp.storage.strategy.*;

import java.io.File;
import java.util.Properties;

public class StorageFactory {

    private StorageFactory() {
    }

    public static Storage getStorage() {
        return getStorage(Config.get().getProps());
    }

    public static Storage getStorage(Properties props) {
        return getStorage(props.getProperty("storage.type"), props);
    }

    public static Storage getStorage(String storageType, Properties props) {
        return getStorage(StorageType.valueOf(storageType), props);
    }

    public static Storage getStorage(StorageType storageType, Properties props) {
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
                        new File((String) props.get("storage.dir")),
                        getStreamSerializer((String) props.get("serializer.type"))
                );
            case PATH:
                return new PathStorage(
                        (String) props.get("storage.dir"),
                        getStreamSerializer((String) props.get("serializer.type"))
                );
            case SQL:
                return new SqlStorage(
                        (String) props.get("db.url"),
                        (String) props.get("db.user"),
                        (String) props.get("db.password")
                );
            default:
                throw new IllegalStateException("Unknown storageType = " + storageType);
        }
    }

    private static StreamSerializer getStreamSerializer(String serializerType) {
        return getStreamSerializer(StreamSerializerType.valueOf(serializerType));
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
