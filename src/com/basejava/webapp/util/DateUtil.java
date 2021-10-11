package com.basejava.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

// TODO: refactor
public class DateUtil {
    public static final LocalDate NOW = LocalDate.MAX;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate ld) {
        return ld.equals(NOW) ? "Сейчас" : ld.format(FORMATTER);
    }
}
