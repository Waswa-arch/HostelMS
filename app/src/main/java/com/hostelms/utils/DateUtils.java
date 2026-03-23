package com.hostelms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private static final SimpleDateFormat DATETIME_FMT = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());

    public static String formatDate(long timestamp) {
        return DATE_FMT.format(new Date(timestamp));
    }
    public static String formatDateTime(long timestamp) {
        return DATETIME_FMT.format(new Date(timestamp));
    }
}
