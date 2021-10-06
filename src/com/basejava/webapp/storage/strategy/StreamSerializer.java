package com.basejava.webapp.storage.strategy;

import com.basejava.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {
    public abstract void writeResume(OutputStream os, Resume r) throws IOException;

    public abstract Resume readResume(InputStream is) throws IOException;
}
