package com.app20222.app20222_backend.utils;

import java.time.Clock;
import java.time.Duration;
import java.time.ZoneId;

public class DateUtils {

    public static final String FORMAT_DATE_DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
    public static final String FORMAT_DATE_DD_MM_YY_HH_MM_SS = "dd/MM/yy HH:mm:ss";
    public static final String FORMAT_DATE_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
    public static final String FORMAT_DATE_YYYY_MMDD_HHMM = "yyyyMMddHHmm";
    public static final String FORMAT_DATE_YYYY_MMDD_HHMMSS = "yyyyMMddHHmmss";
    public static final String FORMAT_DATE_DD_MM_YYYY_HH_MM_A = "dd/MM/yyyy hh:mma";
    public static final ZoneId ZONE_ID = ZoneId.of("UTC");
    public static final ZoneId ICT_ZONE_ID = ZoneId.of("Asia/Jakarta");
    public static final ZoneId SYS_ZONE_ID = ZoneId.systemDefault();
    public static final Clock offsetClockICT = Clock.offset(Clock.systemUTC(), Duration.ofHours(+7));
    public static final String FORMAT_DATE_DD_MM_YYYY = "ddMMyyyy";
    public static final String FORMAT_DATE_YYYY_MM_DD = "yyyyMMdd";
    public static final String FORMAT_DATE_DD_MM_YYYY_SLASH="dd/MM/yyyy";
    public static final String FORMAT_DATE_DD_MM_SLASH = "dd/MM";
    public static final String TIME_ZONE = "Asia/Ho_Chi_Minh";
    public static final String FORMAT_DATE_YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FORMAT_DATE_YYYY_MM_DD_T_HH_MM_SS_SSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static final String FORMAT_DATE_FILE_DD_MM_YYYY_HH_MM_SS = "ddMMyyyyHHmmss";

    public static final String FORMAT_DD_MM_YYYY= "dd-MM-yyyy";
    public static final String FORMAT_YYYY_MM_DD= "yyyy-MM-dd";
    public static final String SEARCH_START_DATE_SUFFIX = " 00:00:00";
    public static final String SEARCH_END_DATE_SUFFIX= " 23:59:59";
    public static final int DATE_OF_WEEK = 7;
    public static final String FORMAT_DATE_MM_YYYY= "MM/uuuu";
    public static final String FORMAT_DATE_YYYY_MM_DD_2= "YYYY/MM/dd";
}
