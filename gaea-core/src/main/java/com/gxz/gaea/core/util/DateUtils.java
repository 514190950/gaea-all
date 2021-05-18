package com.gxz.gaea.core.util;

import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/***
 *
 * @author gxz
 * @date 2019/12/10
 **/
public class DateUtils {

    public final static ZoneId DEFAULT_ZONE = ZoneId.systemDefault();
    public final static Map<String, DateTimeFormatter> FORMATTER_MAP = new HashMap<>();
    public final static String DEFAULT_REX = "yyyy-MM-dd HH:mm:ss";
    public final static DateTimeFormatter DEFAULT_DATE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter PATTERN_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter CN_DATE = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
    public final static DateTimeFormatter HOUR_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
    public final static DateTimeFormatter LINK_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    public final static int SECOND = 1000;
    public final static int MINUTE = 60 * SECOND;
    public final static int HOUR = 60 * MINUTE;
    public final static long DAY = 24L * HOUR;
    public final static long WEEK = 7 * DAY;

    static {
        FORMATTER_MAP.put("yyyy-MM-dd HH:mm:ss", DEFAULT_DATE_PATTERN);
        FORMATTER_MAP.put("yyyyMMdd", PATTERN_YYYYMMDD);
        FORMATTER_MAP.put("yyyy年MM月dd日", CN_DATE);
        FORMATTER_MAP.put("yyyy-MM-dd HH", HOUR_FORMATTER);
        FORMATTER_MAP.put("yyyyMMddHHmm", LINK_FORMATTER);
    }



    private DateUtils() {
        throw new RuntimeException("禁止创建实体");
    }


    /**
     * 返回两个时间中靠后的时间
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @param big   true返回靠后的  false 返回靠前的
     */
    public static Instant swap(Instant time1, Instant time2, boolean big) {
        if (time1 == null) {
            return time2;
        }
        if (time2 == null) {
            return time1;
        }
        int i = time1.compareTo(time2);
        if (i > 0) {
            return big ? time1 : time2;
        } else {
            return big ? time2 : time1;
        }
    }

    public static Instant max(Instant time1, Instant time2) {
        return swap(time1, time2, true);
    }

    public static Instant min(Instant time1, Instant time2) {
        return swap(time1, time2, false);
    }

    public static String format(long timeStamp, String formatRex) {
        DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.getOrDefault(formatRex,
                DateTimeFormatter.ofPattern(formatRex));
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), DEFAULT_ZONE).format(dateTimeFormatter);
    }

    public static String format(long timeStamp) {
        return format(timeStamp,DEFAULT_REX);
    }

    public static String format(Instant instant){
        return format(instant.toEpochMilli());
    }

    public static String format(Instant instant,String formatRex){
        return format(instant.toEpochMilli(),formatRex);
    }

    public static String format(LocalDateTime localDateTime){
        return DEFAULT_DATE_PATTERN.format(localDateTime);
    }


    public static Instant parse(String time){
        return LocalDateTime.parse(time, DateUtils.DEFAULT_DATE_PATTERN).toInstant(ZoneOffset.of("+8"));
    }

    public static Instant parse(String time, String formatRex) {
        DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.getOrDefault(formatRex,
                DateTimeFormatter.ofPattern(formatRex));
        return LocalDateTime.parse(time, dateTimeFormatter).toInstant(ZoneOffset.of("+8"));
    }

    public static long duration(long startTime) {
        return Instant.now().toEpochMilli() - startTime;
    }

    public static Instant roundNumber(Instant instant, long roundNumber) {
        long l = instant.toEpochMilli();
        l = l / roundNumber * roundNumber;
        return Instant.ofEpochMilli(l);
    }

    public static Instant offsetTime(int offsetFlag){
        LocalDateTime now = LocalDateTime.now();
        switch (offsetFlag) {
            case 1:
                return now.minusDays(1).toInstant(ZoneOffset.of("+8"));
            case 2:
                return now.minusWeeks(1).toInstant(ZoneOffset.of("+8"));
            case 3:
                return now.minusMonths(1).toInstant(ZoneOffset.of("+8"));
            default:
                throw new RuntimeException("参数异常");
        }
    }

    public static Instant date2Instant(Date date) {
        return date.toInstant();
    }

    public static Instant long2Instant(Long timeStamp) {
        return date2Instant(new Date(timeStamp));
    }

    public static long instant2Long(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static Date instant2Date(Instant instant) {
        return Date.from(instant.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Instant getDayTime(long timeStamp) {
        return long2Instant(timeStamp / DateUtils.DAY * DateUtils.DAY);
    }

    public static Instant getHourTime(long timeStamp) {
        return long2Instant(timeStamp / DateUtils.HOUR * DateUtils.HOUR);
    }

    public static Instant getMinuteTime(long timeStamp) {
        return long2Instant(timeStamp / DateUtils.MINUTE * DateUtils.MINUTE);
    }

    public static boolean between(Instant target, Instant start ,Instant end){
        return target.isAfter(start) && target.isBefore(end);
    }


    /**
     * @param dateStr     时间字符串
     * @param datePattern 时间字符串格式，若输入为null或不输入,则默认为"yyyy-MM-dd HH:mm:ss"
     * @return 返回long型时间戳默认为毫秒
     * 若转换错误则返回 0
     * @author gxz
     * @see Date & TimeStamp
     */


    public static long date2Stamp(String dateStr, String datePattern) {
        try {
            if (StringUtils.isEmpty(datePattern)) {
                return LocalDateTime.parse(dateStr, DEFAULT_DATE_PATTERN).atZone(DEFAULT_ZONE).toInstant().toEpochMilli();
            }
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
            return LocalDateTime.parse(dateStr, dateTimeFormatter).atZone(DEFAULT_ZONE).toInstant().toEpochMilli();
        } catch (DateTimeParseException e) {
            return 0;
        }
    }

    public static Long date2Stamp(String dateStr) {
        return date2Stamp(dateStr, null);
    }
    public static Long date2Stamp(Instant instant) {
        return instant.atZone(DEFAULT_ZONE).toInstant().toEpochMilli();
    }
    public static Long date2Stamp(LocalDate localDate) {
        return localDate.atStartOfDay().atZone(DEFAULT_ZONE).toInstant().toEpochMilli();
    }
    /**
     * @param ts          毫秒时间戳
     * @param datePattern 时间字符串格式，若输入为null或不输入,则默认为"yyyy-MM-dd HH:mm:ss"
     * @return 返回long型时间戳默认为毫秒
     * 若转换错误则返回null
     * @author gxz
     * @see Date & TimeStamp
     */
    public static String stamp2String(long ts, String datePattern) {
        try {
            if (StringUtils.isEmpty(datePattern)) {
                return DEFAULT_DATE_PATTERN.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(ts),DEFAULT_ZONE));
            }
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
            return dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(ts),DEFAULT_ZONE));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


}
