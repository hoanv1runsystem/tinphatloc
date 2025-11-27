package com.vn.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class DateUtils {
    // ---- Định dạng chung ----
    public static final String DATE_DD_MM_YYYY = "dd/MM/yyyy";
    public static final String DATE_DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";

    public static final String DDMMYYYY = "ddMMyyyy";

    public static final String DD_MM_YYYY = "dd-MM-yyyy";
    public static final String DD_MM_YYYY_HH_MM = "dd-MM-yyyy HH:mm";

    // =============================
    // ==== Format LocalDate =======
    // =============================

    /** Format LocalDate sang chuỗi dd/MM/yyyy */
    public static String formatDate(LocalDate date, String strFormat) {
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(strFormat);

        return Optional.ofNullable(date)
            .map(DATE_FORMATTER::format)
            .orElse("");
    }

    /** Parse chuỗi dd/MM/yyyy sang LocalDate */
    /*public static LocalDate parseDate(String text) {
        if (text == null || text.isBlank()) return null;
        try {
            return LocalDate.parse(text, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Không thể parse ngày: " + text);
            return null;
        }
    }*/

    // =============================
    // ==== Format LocalDateTime ===
    // =============================

    /** Format LocalDateTime sang chuỗi dd/MM/yyyy HH:mm */
    public static String formatDateTime(LocalDateTime dateTime, String strFormat) {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(strFormat);
        return Optional.ofNullable(dateTime)
            .map(DATE_TIME_FORMATTER::format)
            .orElse("");
    }

    /** Parse chuỗi dd/MM/yyyy HH:mm sang LocalDateTime */
    /*public static LocalDateTime parseDateTime(String text) {
        if (text == null || text.isBlank()) return null;
        try {
            return LocalDateTime.parse(text, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Không thể parse ngày giờ: " + text);
            return null;
        }
    }*/

    // =============================
    // ==== Một số tiện ích thêm ===
    // =============================

    /** Lấy ngày hiện tại dạng chuỗi dd/MM/yyyy */
    /*public static String todayAsString() {
        return formatDate(LocalDate.now());
    }*/

    /** Lấy thời gian hiện tại dạng chuỗi dd/MM/yyyy HH:mm */
    /*public static String nowAsString() {
        return formatDateTime(LocalDateTime.now());
    }*/
}
