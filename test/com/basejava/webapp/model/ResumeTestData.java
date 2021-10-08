package com.basejava.webapp.model;

import com.basejava.webapp.util.DateUtil;

import java.time.Month;

public class ResumeTestData {
    private static boolean SET_CONTACTS = true;
    private static boolean SET_SECTIONS = true;

    private static boolean SET_TEXT_SECTIONS = true;
    private static boolean SET_LIST_SECTIONS = true;
    private static boolean SET_ORG_SECTIONS = false;

    public static Resume makeResume(String uuid, String fullName) {
        final Resume resume = new Resume(uuid, fullName);
        if (SET_CONTACTS) {
            setContacts(resume);
        }
        if (SET_SECTIONS) {
            setSections(resume);
        }
        return resume;
    }

    private static void setContacts(Resume resume) {
        resume.setContact(ContactType.PHONE, "8(800)555-35-35");
        resume.setContact(ContactType.SKYPE, "abcabcabc");
        resume.setContact(ContactType.EMAIL, "noreply@nomail.no");
        resume.setContact(ContactType.LINKEDIN, "https://linkedin.com/");
        resume.setContact(ContactType.GITHUB, "https://github.com/");
        resume.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/");
        resume.setContact(ContactType.HOMEPAGE, "http://example.com/");
    }

    private static void setSections(Resume resume) {
        if (SET_TEXT_SECTIONS) {
            resume.setSection(SectionType.OBJECTIVE, new TextSection("objective section content"));
            resume.setSection(SectionType.PERSONAL, new TextSection("personal section content"));
        }

        if (SET_LIST_SECTIONS) {
            resume.setSection(
                    SectionType.ACHIEVEMENT,
                    new ListSection(
                            "achievement section content, line 1",
                            "achievement section content, line 2",
                            "achievement section content, line 3"
                    )
            );
            resume.setSection(
                    SectionType.QUALIFICATIONS,
                    new ListSection(
                            "qualifications section content, line 1",
                            "qualifications section content, line 2",
                            "qualifications section content, line 3"
                    )
            );
        }

        if (SET_ORG_SECTIONS) {
            resume.setSection(
                    SectionType.EXPERIENCE,
                    new OrgSection(
                            new Organization(
                                    new Link("Title 1", "http://link1.example.com/"),
                                    new Position(new TimeSpan(DateUtil.of(2013, Month.OCTOBER), DateUtil.NOW), "Role 1", "Description 1")
                            ),
                            new Organization(
                                    new Link("Title 2", "http://link2.example.com/"),
                                    new Position(new TimeSpan(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY)), "Role 2", "Description 2")
                            ),
                            new Organization(
                                    new Link("Title 3", null),
                                    new Position(new TimeSpan(DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER)), "Role 3", "Description 3")
                            ),
                            new Organization(
                                    new Link("Title 4", "http://link4.example.com/"),
                                    new Position(new TimeSpan(DateUtil.of(2010, Month.DECEMBER), DateUtil.of(2012, Month.APRIL)), "Role 4", "Description 4")
                            ),
                            new Organization(
                                    new Link("Title 5", "http://link5.example.com/"),
                                    new Position(new TimeSpan(DateUtil.of(2008, Month.JUNE), DateUtil.of(2010, Month.DECEMBER)), "Role 5", "Description 5")
                            ),
                            new Organization(
                                    new Link("Title 6", "http://link6.example.com/"),
                                    new Position(new TimeSpan(DateUtil.of(2007, Month.MARCH), DateUtil.of(2008, Month.JUNE)), "Role 6", "Description 6")
                            ),
                            new Organization(
                                    new Link("Title 7", "http://link7.example.com/"),
                                    new Position(new TimeSpan(DateUtil.of(2005, Month.JANUARY), DateUtil.of(2007, Month.FEBRUARY)), "Role 7", "Description 7")
                            ),
                            new Organization(
                                    new Link("Title 8", "http://link8.example.com/"),
                                    new Position(new TimeSpan(DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(2005, Month.JANUARY)), "Role 8", "Description 8")
                            )
                    )
            );
            resume.setSection(
                    SectionType.EDUCATION,
                    new OrgSection(
                            new Organization(
                                    new Link("Title 1", "http://link1.example.com/"),
                                    new Position(new TimeSpan(DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY)), "Role 1", null)
                            ),
                            new Organization(
                                    new Link("Title 2", "http://link2.example.com/"),
                                    new Position(new TimeSpan(DateUtil.of(2011, Month.MARCH), DateUtil.of(2011, Month.APRIL)),"Role 2", null)
                            ),
                            new Organization(
                                    new Link("Title 3", "http://link3.example.com/"),
                                    new Position(new TimeSpan(DateUtil.of(2005, Month.JANUARY), DateUtil.of(2005, Month.APRIL)),"Role 3", null)
                            ),
                            new Organization(
                                    new Link("Title 4", "http://link4.example.com/"),
                                    new Position(new TimeSpan(DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(1998, Month.MARCH)),"Role 4", null)
                            ),
                            new Organization(
                                    new Link("Title 5", "http://link5.example.com/"),
                                    new Position(new TimeSpan(DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY)),"Role 5", null),
                                    new Position(new TimeSpan(DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY)),"Role 6", null)
                            ),
                            new Organization(
                                    new Link("Title 6", "http://link6.example.com/"),
                                    new Position(new TimeSpan(DateUtil.of(1984, Month.SEPTEMBER), DateUtil.of(1987, Month.JUNE)),"Role 7", null)
                            )
                    )
            );
        }
    }
}
