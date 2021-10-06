package com.basejava.webapp.drafts;

import com.basejava.webapp.model.*;
import com.basejava.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

// Lessons 7-8 homework: test data for Resume model from https://javawebinar.github.io/
public class ResumeTestData {

    public static Resume makeResume(String uuid, String fullName) {
        final Map<ContactType, String> contacts = new HashMap<ContactType, String>();
        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "skype:grigory.kislin");
        contacts.put(ContactType.EMAIL, "mailto:gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        contacts.put(ContactType.HOMEPAGE, "http://gkislin.ru/");

        final Map<SectionType, Section> sections = new HashMap<>();
        sections.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        sections.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        final List<String> achievement = Arrays.asList(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        );
        sections.put(SectionType.ACHIEVEMENT, new ListSection(achievement));
        final List<String> qualifications = Arrays.asList(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
                "Родной русский, английский \"upper intermediate\""
        );
        sections.put(SectionType.QUALIFICATIONS, new ListSection(qualifications));

        final List<Organization> experience = Arrays.asList(
                new Organization(
                        new Link("Java Online Projects", "http://javaops.ru/"),
                        new Position(
                                new TimeSpan(DateUtil.of(2013, Month.OCTOBER), LocalDate.MAX),
                                "Автор проекта.",
                                "Создание, организация и проведение Java онлайн проектов и стажировок."
                        )
                ),
                new Organization(
                        new Link("Wrike", "https://www.wrike.com/"),
                        new Position(
                                new TimeSpan(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY)),
                                "Старший разработчик (backend)",
                                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."
                        )
                ),
                new Organization(
                        new Link("RIT Center", null),
                        new Position(
                                new TimeSpan(DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER)),
                                "Java архитектор",
                                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"
                        )
                ),
                new Organization(
                        new Link("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/"),
                        new Position(
                                new TimeSpan(DateUtil.of(2010, Month.DECEMBER), DateUtil.of(2012, Month.APRIL)),
                                "Ведущий программист",
                                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."
                        )
                ),
                new Organization(
                        new Link("Yota", "https://www.yota.ru/"),
                        new Position(
                                new TimeSpan(DateUtil.of(2008, Month.JUNE), DateUtil.of(2010, Month.DECEMBER)),
                                "Ведущий специалист",
                                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"
                        )
                ),
                new Organization(
                        new Link("Enkata", "http://enkata.com/"),
                        new Position(
                                new TimeSpan(DateUtil.of(2007, Month.MARCH), DateUtil.of(2008, Month.JUNE)),
                                "Разработчик ПО",
                                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."
                        )
                ),
                new Organization(
                        new Link("Siemens AG", "https://www.siemens.com/ru/ru/home.html"),
                        new Position(
                                new TimeSpan(DateUtil.of(2005, Month.JANUARY), DateUtil.of(2007, Month.FEBRUARY)),
                                "Разработчик ПО",
                                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."
                        )
                ),
                new Organization(
                        new Link("Alcatel", "http://www.alcatel.ru/"),
                        new Position(
                                new TimeSpan(DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(2005, Month.JANUARY)),
                                "Инженер по аппаратному и программному тестированию",
                                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."
                        )
                )
        );
        sections.put(SectionType.EXPERIENCE, new OrgSection(experience));

        final List<Organization> education = Arrays.asList(
                new Organization(
                        new Link("Coursera", "https://www.coursera.org/course/progfun"),
                        new Position(
                                new TimeSpan(DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY)),
                                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                                null
                        )
                ),
                new Organization(
                        new Link("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
                        new Position(
                                new TimeSpan(DateUtil.of(2011, Month.MARCH), DateUtil.of(2011, Month.APRIL)),
                                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                                null
                        )
                ),
                new Organization(
                        new Link("Siemens AG", "http://www.siemens.ru/"),
                        new Position(
                                new TimeSpan(DateUtil.of(2005, Month.JANUARY), DateUtil.of(2005, Month.APRIL)),
                                "3 месяца обучения мобильным IN сетям (Берлин)",
                                null
                        )
                ),
                new Organization(
                        new Link("Alcatel", "http://www.alcatel.ru/"),
                        new Position(
                                new TimeSpan(DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(1998, Month.MARCH)),
                                "6 месяцев обучения цифровым телефонным сетям (Москва)",
                                null
                        )
                ),
                new Organization(
                        new Link(
                                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                                "http://www.ifmo.ru/"
                        ),
                        new Position(
                                new TimeSpan(DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY)),
                                "Аспирантура (программист С, С++)",
                                null
                        ),
                        new Position(
                                new TimeSpan(DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY)),
                                "Инженер (программист Fortran, C)",
                                null
                        )
                ),
                new Organization(
                        new Link(
                                "Заочная физико-техническая школа при МФТИ",
                                "http://www.school.mipt.ru/"
                        ),
                        new Position(
                                new TimeSpan(DateUtil.of(1984, Month.SEPTEMBER), DateUtil.of(1987, Month.JUNE)),
                                "Закончил с отличием",
                                null
                        )
                )
        );
        sections.put(SectionType.EDUCATION, new OrgSection(education));

        final Resume resume = new Resume(uuid, fullName);
        resume.setContacts(contacts);
        resume.setSections(sections);

        return resume;
    }

    public static void main(String[] args) {
        Resume resume = makeResume(UUID.randomUUID().toString(), "Григорий Кислин");
        System.out.println(resume);
    }
}
