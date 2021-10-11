package com.basejava.webapp.util;

import com.basejava.webapp.model.TimeSpan;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final LocalDate NOW = LocalDate.MAX;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate ld) {
        return ld.equals(NOW) ? "Сейчас" : ld.format(FORMATTER);
    }

    public static String format(TimeSpan ts) {
        return format(ts.getBegin()) + " - " + format(ts.getEnd());
    }

    public static LocalDate parse(String str) {
        if (HtmlUtils.isNullOrEmpty(str) || str.equals("Сейчас")) {
            return NOW;
        }
        YearMonth ym = YearMonth.parse(str, FORMATTER);
        return DateUtils.of(ym.getYear(), ym.getMonth());
    }
}
