package com.basejava.webapp.storage.strategy;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void writeResume(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            deepWrite(
                    dos, resume.getContacts().entrySet(), e -> {
                        dos.writeUTF(e.getKey().name());
                        dos.writeUTF(e.getValue());
                    }
            );
            deepWrite(dos, resume.getSections().entrySet(), e -> {
                        SectionType sectionType = e.getKey();
                        dos.writeUTF(sectionType.name());
                        switch (sectionType) {
                            case OBJECTIVE: case PERSONAL:
                                dos.writeUTF(((TextSection) e.getValue()).getContent());
                                break;
                            case ACHIEVEMENT: case QUALIFICATIONS:
                                deepWrite(dos, ((ListSection) e.getValue()).getItems(), dos::writeUTF);
                                break;
                            case EXPERIENCE: case EDUCATION:
                                deepWrite(dos, ((OrgSection) e.getValue()).getOrgs(), o -> {
                                    Link l = o.getLink();
                                    dos.writeUTF(l.getTitle());
                                    dos.writeUTF(l.getUrl());
                                    deepWrite(dos, o.getPositions(), p -> {
                                        writeTimeSpan(dos, p.getTimeSpan());
                                        dos.writeUTF(p.getTitle());
                                        dos.writeUTF(p.getDescription());
                                    });
                                });
                                break;
                        }
                    }
            );
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            deepRead(dis, () -> Tuple.of(ContactType.valueOf(dis.readUTF()), dis.readUTF()))
                    .forEach(t -> resume.setContact(t.getFirst(), t.getSecond()));
            deepRead(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                Section section = null;
                switch (sectionType) {
                    case OBJECTIVE: case PERSONAL:
                        section = new TextSection(dis.readUTF());
                        break;
                    case ACHIEVEMENT: case QUALIFICATIONS:
                        section = new ListSection(deepRead(dis, dis::readUTF));
                        break;
                    case EXPERIENCE: case EDUCATION:
                        section = new OrgSection(deepRead(dis, () -> new Organization(
                                new Link(dis.readUTF(), dis.readUTF()),
                                deepRead(dis, () -> new Position(readTimeSpan(dis), dis.readUTF(), dis.readUTF()))
                                )));
                        break;
                }
                return Tuple.of(sectionType, section);
            }).forEach(t -> resume.setSection(t.getFirst(), t.getSecond()));
            return resume;
        }
    }

    private interface IWriter<T> { void write(T t) throws IOException; }

    private interface IReader<T> { T read() throws IOException; }

    private <T> void deepWrite(DataOutputStream dos, Collection<T> col, IWriter<T> writer) throws IOException {
        dos.writeInt(col.size());
        for (T obj : col) {
            writer.write(obj);
        }
    }

    private <T> List<T> deepRead(DataInputStream dis, IReader<T> reader) throws IOException {
        final List<T> list = new ArrayList<>();
        final int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private static class Tuple<A, B> {
        private final A first;
        private final B second;

        private Tuple(A first, B second) {
            this.first = first;
            this.second = second;
        }

        public static <A, B> Tuple<A, B> of(A first, B second) {
            return new Tuple(first, second);
        }

        public A getFirst() { return first; }

        public B getSecond() { return second; }
    }

    private void writeTimeSpan(DataOutputStream dos, TimeSpan timeSpan) throws IOException {
        writeLocalDate(dos, timeSpan.getBegin());
        writeLocalDate(dos, timeSpan.getEnd());
    }

    private TimeSpan readTimeSpan(DataInputStream dis) throws IOException {
        return new TimeSpan(readLocalDate(dis), readLocalDate(dis));
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonthValue());
        dos.writeInt(ld.getDayOfMonth());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }
}
