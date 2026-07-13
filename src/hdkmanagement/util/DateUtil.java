// util/DateUtil.java
package hdkmanagement.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private static final String SQL_DATE_FORMAT = "yyyy-MM-dd";
    
    // Format Date to String
    public static String formatDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }
    
    public static String formatDate(java.util.Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }
    
    public static String formatDateTime(java.util.Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        return sdf.format(date);
    }
    
    // Parse String to Date
    public static java.util.Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
    
    public static Date parseSQLDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            java.util.Date date = sdf.parse(dateStr);
            return new Date(date.getTime());
        } catch (ParseException e) {
            return null;
        }
    }
    
    // Lấy ngày hiện tại
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
    
    public static java.util.Date getCurrentUtilDate() {
        return new java.util.Date();
    }
    
    // Lấy ngày hiện tại theo format
    public static String getCurrentDateString() {
        return formatDate(getCurrentDate());
    }
    
    // So sánh ngày
    public static int compareDate(Date d1, Date d2) {
        return d1.compareTo(d2);
    }
    
    // Tính số ngày giữa 2 ngày
    public static long daysBetween(Date d1, Date d2) {
        LocalDate ld1 = d1.toLocalDate();
        LocalDate ld2 = d2.toLocalDate();
        return ChronoUnit.DAYS.between(ld1, ld2);
    }
    
    // Cộng thêm ngày
    public static Date addDays(Date date, int days) {
        LocalDate ld = date.toLocalDate();
        ld = ld.plusDays(days);
        return Date.valueOf(ld);
    }
    
    // Kiểm tra ngày hợp lệ
    public static boolean isValidDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            sdf.setLenient(false);
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}