package com.basejava.webapp.model;

import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.storage.factory.StorageFactory;
import com.basejava.webapp.util.DateUtils;

import java.time.Month;
import java.util.Scanner;
import java.util.UUID;

public class ResumeTestData {
    private static boolean SET_CONTACTS = true;
    private static boolean SET_SECTIONS = true;

    private static boolean SET_TEXT_SECTIONS = true;
    private static boolean SET_LIST_SECTIONS = true;
    private static boolean SET_ORG_SECTIONS = true;

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
        resume.setContact(ContactType.HOME_PAGE, "http://example.com/");
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
                                    new Position(new TimeSpan(DateUtils.of(2013, Month.OCTOBER), DateUtils.NOW), "Role 1", "Description 1")
                            ),
                            new Organization(
                                    new Link("Title 2", "http://link2.example.com/"),
                                    new Position(new TimeSpan(DateUtils.of(2014, Month.OCTOBER), DateUtils.of(2016, Month.JANUARY)), "Role 2", "Description 2")
                            ),
                            new Organization(
                                    new Link("Title 3", null),
                                    new Position(new TimeSpan(DateUtils.of(2012, Month.APRIL), DateUtils.of(2014, Month.OCTOBER)), "Role 3", "Description 3")
                            ),
                            new Organization(
                                    new Link("Title 4", "http://link4.example.com/"),
                                    new Position(new TimeSpan(DateUtils.of(2010, Month.DECEMBER), DateUtils.of(2012, Month.APRIL)), "Role 4", "Description 4")
                            ),
                            new Organization(
                                    new Link("Title 5", "http://link5.example.com/"),
                                    new Position(new TimeSpan(DateUtils.of(2008, Month.JUNE), DateUtils.of(2010, Month.DECEMBER)), "Role 5", "Description 5")
                            ),
                            new Organization(
                                    new Link("Title 6", "http://link6.example.com/"),
                                    new Position(new TimeSpan(DateUtils.of(2007, Month.MARCH), DateUtils.of(2008, Month.JUNE)), "Role 6", "Description 6")
                            ),
                            new Organization(
                                    new Link("Title 7", "http://link7.example.com/"),
                                    new Position(new TimeSpan(DateUtils.of(2005, Month.JANUARY), DateUtils.of(2007, Month.FEBRUARY)), "Role 7", "Description 7")
                            ),
                            new Organization(
                                    new Link("Title 8", "http://link8.example.com/"),
                                    new Position(new TimeSpan(DateUtils.of(1997, Month.SEPTEMBER), DateUtils.of(2005, Month.JANUARY)), "Role 8", "Description 8")
                            )
                    )
            );
            resume.setSection(
                    SectionType.EDUCATION,
                    new OrgSection(
                            new Organization(
                                    new Link("Title 1", "http://link1.example.com/"),
                                    new Position(new TimeSpan(DateUtils.of(2013, Month.MARCH), DateUtils.of(2013, Month.MAY)), "Role 1", null)
                            ),
                            new Organization(
                                    new Link("Title 2", "http://link2.example.com/"),
                                    new Position(new TimeSpan(DateUtils.of(2011, Month.MARCH), DateUtils.of(2011, Month.APRIL)),"Role 2", null)
                            ),
                            new Organization(
                                    new Link("Title 3", "http://link3.example.com/"),
                                    new Position(new TimeSpan(DateUtils.of(2005, Month.JANUARY), DateUtils.of(2005, Month.APRIL)),"Role 3", null)
                            ),
                            new Organization(
                                    new Link("Title 4", "http://link4.example.com/"),
                                    new Position(new TimeSpan(DateUtils.of(1997, Month.SEPTEMBER), DateUtils.of(1998, Month.MARCH)),"Role 4", null)
                            ),
                            new Organization(
                                    new Link("Title 5", "http://link5.example.com/"),
                                    new Position(new TimeSpan(DateUtils.of(1993, Month.SEPTEMBER), DateUtils.of(1996, Month.JULY)),"Role 5", null),
                                    new Position(new TimeSpan(DateUtils.of(1987, Month.SEPTEMBER), DateUtils.of(1993, Month.JULY)),"Role 6", null)
                            ),
                            new Organization(
                                    new Link("Title 6", "http://link6.example.com/"),
                                    new Position(new TimeSpan(DateUtils.of(1984, Month.SEPTEMBER), DateUtils.of(1987, Month.JUNE)),"Role 7", null)
                            )
                    )
            );
        }
    }

    public static void main(String[] args) {
        System.out.println("???????????????????? ???????????????? ???????????? ?? ????????");

        Storage storage = StorageFactory.getStorage();

        Scanner scanner = new Scanner(System.in);
        System.out.print("?????????????? ??????????????????? ");
        int count = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < count; i++) {
            storage.save(makeResume(UUID.randomUUID().toString(), "John Doe"));
        }

        System.out.println("????????????");
        scanner.nextLine();
    }
}
