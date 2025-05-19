package top.yangsc.tools;


import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimestampUtil {

    private static final DateTimeFormatter DEFAULT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 将时间戳转换为默认格式的字符串 (yyyy-MM-dd HH:mm:ss)
     * @param timestamp 毫秒时间戳
     * @return 格式化后的时间字符串
     */
    public static String format(long timestamp) {
        return format(timestamp, DEFAULT_FORMATTER);
    }

    public static String format(Timestamp timestamp) {
        return format( timestamp.getTime(), DEFAULT_FORMATTER);
    }

    /**
     * 将时间戳转换为指定格式的字符串
     * @param timestamp 毫秒时间戳
     * @param pattern 时间格式模式
     * @return 格式化后的时间字符串
     */
    public static String format(long timestamp, String pattern) {
        return format(timestamp, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将时间戳转换为指定格式器的字符串
     * @param timestamp 毫秒时间戳
     * @param formatter 时间格式器
     * @return 格式化后的时间字符串
     */
    public static String format(long timestamp, DateTimeFormatter formatter) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .format(formatter);
    }

    /**
     * 将时间字符串(默认格式)转换为时间戳
     * @param dateTime 时间字符串 (yyyy-MM-dd HH:mm:ss)
     * @return 毫秒时间戳
     */
    public static long parse(String dateTime) {
        return parse(dateTime, DEFAULT_FORMATTER);
    }

    /**
     * 将时间字符串(指定格式)转换为时间戳
     * @param dateTime 时间字符串
     * @param pattern 时间格式模式
     * @return 毫秒时间戳
     */
    public static long parse(String dateTime, String pattern) {
        return parse(dateTime, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将时间字符串(指定格式器)转换为时间戳
     * @param dateTime 时间字符串
     * @param formatter 时间格式器
     * @return 毫秒时间戳
     */
    public static long parse(String dateTime, DateTimeFormatter formatter) {
        return LocalDateTime.parse(dateTime, formatter)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    /**
     * 获取当前时间戳
     * @return 当前毫秒时间戳
     */
    public static long current() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间字符串(默认格式)
     * @return 格式化后的当前时间字符串
     */
    public static String currentAsString() {
        return format(current());
    }

    /**
     * 将Date对象转换为时间戳
     * @param date Date对象
     * @return 毫秒时间戳
     */
    public static long toTimestamp(Date date) {
        return date.getTime();
    }

    /**
     * 将时间戳转换为Date对象
     * @param timestamp 毫秒时间戳
     * @return Date对象
     */
    public static Date toDate(long timestamp) {
        return new Date(timestamp);
    }
}