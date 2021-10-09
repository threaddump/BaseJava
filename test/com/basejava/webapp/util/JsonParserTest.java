package com.basejava.webapp.util;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.ResumeTestData;
import com.basejava.webapp.model.Section;
import com.basejava.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class JsonParserTest {

    @Test
    public void testResume() {
        Resume resume = ResumeTestData.makeResume(UUID.randomUUID().toString(), "John Doe");
        String json = JsonParser.toJson(resume, Resume.class);
        System.out.println(json);
        Resume resumeRestored = JsonParser.fromJson(json, Resume.class);
        Assert.assertEquals(resume, resumeRestored);
    }

    @Test
    public void testSection() {
        Section section = new TextSection("section content");
        String json = JsonParser.toJson(section, Section.class);
        System.out.println(json);
        Section sectionRestored = JsonParser.fromJson(json, Section.class);
        Assert.assertEquals(section, sectionRestored);
    }
}