package com.basejava.webapp.storage.strategy;

import com.basejava.webapp.model.*;
import com.basejava.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {

    private final XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(
                Resume.class,
                ContactType.class,
                SectionType.class,
                Section.class,
                TextSection.class,
                ListSection.class,
                OrgSection.class,
                Organization.class,
                Link.class,
                Position.class,
                TimeSpan.class
        );
    }

    @Override
    public void writeResume(OutputStream os, Resume resume) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(w, resume);
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}
