package com.basejava.webapp.drafts;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainDate {
    public static void main(String[] args) {
        Date date = new Date(); // mutable!
        System.out.println(date);

        System.out.println(System.currentTimeMillis() - date.getTime());

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        System.out.println(cal.getTime()); // still in current timezone

        // non-thread-safe
        SimpleDateFormat sdf = new SimpleDateFormat("YY/MM/dd");
        System.out.println(sdf.format(new Date()));

        // SimpleDateFormat may be used to convert timezones

        // big projects prefer Joda Time library
        // since Java 8 users are advised to migrate to java.time.*

        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ld);
        System.out.println(lt);
        System.out.println(ldt);
        ldt = LocalDateTime.of(ld, lt);
        System.out.println(ldt);

        DateTimeFormatter dtf = DateTimeFormatter
                .ofLocalizedTime(FormatStyle.SHORT)
                .withLocale(Locale.GERMAN);
        LocalTime leetTime = LocalTime.parse("13:37", dtf);
        System.out.println(leetTime);
    }
}
