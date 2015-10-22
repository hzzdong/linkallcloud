package com.linkallcloud.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.nutz.lang.Strings;

import com.linkallcloud.core.exception.BaseException;

public class Dates {

    public static final String FORMAT_DATE = "DATE";
    public static final String FORMAT_DATETIME = "DATETIME";
    public static final String FORMAT_TIME = "TIME";

    public static final String FORMAT_PATTEN_DATE = "yyyy-MM-dd";
    public static final String FORMAT_PATTEN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_PATTEN_TIME = "HH:mm:ss";

    /**
     * format date ,if patten is null,use default patten yyyy-MM-dd HH:mm:ss.
     * 
     * @param date
     * @return formated date string
     */
    public static String formatDate(Date date, String patten) {
        if (date == null) {
            return IConstants.BLANK;
        }
        return new SimpleDateFormat(Strings.isBlank(patten) ? IConstants.FORMAT_PATTEN_DATETIME : patten).format(date);
    }

    /**
     * 
     * @param date
     * @return formated date string
     */
    public static String formatDate(Date date) {
        return formatDate(date, IConstants.FORMAT_PATTEN_DATETIME);
    }

    /**
     * yyyy-MM-dd
     * 
     * @param date
     * @return date
     */
    public static Date getSimpleDate(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.clear(Calendar.MILLISECOND);
            calendar.clear(Calendar.SECOND);
            calendar.clear(Calendar.MINUTE);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * 
     * @param dateStr
     * @return date
     * @throws BaseException
     */
    public static Date parseDate(String dateStr) throws BaseException {
        return parseDate(dateStr, null);
    }

    /**
     * 
     * @param dateStr
     * @param patten
     * @return Date
     * @throws BaseException
     */
    public static Date parseDate(String dateStr, String patten) throws BaseException {
        DateFormat df = guessDateFormat(dateStr, patten);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            throw new BaseException("e.dateformat.parse", "Unable to parse " + dateStr);
        }
    }

    public static DateFormat guessDateFormat(String dateStr, String patten) {
        if (null == patten || patten.length() <= 0) {
            return Dates.guessDateFormat(dateStr);
        } else {
            return Dates.getDateFormatByPatten(patten);
        }
    }

    public static DateFormat getDateFormatByPatten(String patten) {
        if (null != patten && patten.length() > 0) {
            if (FORMAT_DATE.equals(patten)) {
                return new SimpleDateFormat(IConstants.FORMAT_PATTEN_DATE);
            } else if (FORMAT_DATETIME.equals(patten)) {
                return new SimpleDateFormat(IConstants.FORMAT_PATTEN_DATETIME);
            } else if (FORMAT_TIME.equals(patten)) {
                return new SimpleDateFormat(IConstants.FORMAT_PATTEN_TIME);
            } else {
                return new SimpleDateFormat(patten);
            }
        }
        return new SimpleDateFormat(IConstants.FORMAT_PATTEN_DATETIME);
    }

    public static DateFormat guessDateFormat(String dataStr) {
        String patten = Dates.guessDateFormatPatten(dataStr);
        return new SimpleDateFormat(patten);
    }

    /**
     * @param dataStr
     * @return DateFormatPatten
     */
    public static String guessDateFormatPatten(String dataStr) {
        if (null != dataStr && !Strings.isBlank(dataStr)) {
            int idx1 = dataStr.indexOf("-");
            int idx2 = dataStr.indexOf(":");
            if (idx1 != -1 && idx2 != -1) {
                return IConstants.FORMAT_PATTEN_DATETIME;
            }
            if (idx1 != -1) {
                return IConstants.FORMAT_PATTEN_DATE;
            }
            if (idx2 != -1) {
                return IConstants.FORMAT_PATTEN_TIME;
            }
        }
        return IConstants.FORMAT_PATTEN_DATETIME;
    }

    /**
     * 
     * @param dt
     * @param years
     * @return date
     */
    public static Date addYear(Date dt, int years) {
        GregorianCalendar thisday = new GregorianCalendar();
        thisday.setTime(dt);
        thisday.add(GregorianCalendar.YEAR, years);
        return thisday.getTime();
    }

    /**
     * 
     * @param dt
     * @param m
     * @return date
     */
    public static Date addMonth(Date dt, int m) {
        GregorianCalendar thisday = new GregorianCalendar();
        thisday.setTime(dt);
        thisday.add(GregorianCalendar.MONTH, m);
        return thisday.getTime();
    }

    /**
     * 
     * @param dt
     * @param d
     * @return date
     */
    public static Date addDate(Date dt, int d) {
        GregorianCalendar thisday = new GregorianCalendar();
        thisday.setTime(dt);
        thisday.add(GregorianCalendar.DATE, d);
        return thisday.getTime();
    }

    /**
     * 
     * @param dt
     * @param h
     * @return date
     */
    public static Date addHour(Date dt, int h) {
        GregorianCalendar thisday = new GregorianCalendar();
        thisday.setTime(dt);
        thisday.add(GregorianCalendar.HOUR_OF_DAY, h);
        return thisday.getTime();
    }

    /**
     * 
     * @param dt
     * @param m
     * @return date
     */
    public static Date addMinute(Date dt, int m) {
        GregorianCalendar thisday = new GregorianCalendar();
        thisday.setTime(dt);
        thisday.add(GregorianCalendar.MINUTE, m);
        return thisday.getTime();
    }

    /**
     * 
     * @param dt
     * @param s
     * @return date
     */
    public static Date addSecond(Date dt, int s) {
        GregorianCalendar thisday = new GregorianCalendar();
        thisday.setTime(dt);
        thisday.add(GregorianCalendar.SECOND, s);
        return thisday.getTime();
    }

    /**
     * 
     * @param year
     * @param month
     * @param day
     * @return
     * @throws BaseException
     */
    public static Date form(int year, int month, int day) throws BaseException {
        String ds = String.valueOf(year) + "-" + month + "-" + day;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");
            return df.parse(ds);
        } catch (ParseException e) {
            throw new BaseException("e.dateformat.parse", "Unable to parse " + ds);
        }
    }

    /**
     * 得到当前时间的年月日
     * 
     * @return
     */
    public static int[] getNowCalendar() {
        Calendar cal = Calendar.getInstance();
        int[] result = { cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE) };
        return result;
    }

    /**
     * compareDate对应的year、month、day所对应的日期和当前时间比较大小，小于当前返回-1，等于当前返回0，大于当前返回1。
     * 
     * @param year
     * @param month
     * @param day
     * @return
     * @throws BaseException
     */
    public static int compareNow(Date compareDate) throws BaseException {
        Calendar cal=Calendar.getInstance();  
        cal.setTime(compareDate);  
        Date date = Dates.form(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
        
        int[] nowParam = Dates.getNowCalendar();
        Date now = Dates.form(nowParam[0], nowParam[1], nowParam[2]);
        return date.compareTo(now);
    }
    
    /**
     * year、month、day所对应的日期和当前时间比较大小，小于当前返回-1，等于当前返回0，大于当前返回1。
     * 
     * @param year
     * @param month
     * @param day
     * @return
     * @throws BaseException
     */
    public static int compareNow(int year, int month, int day) throws BaseException {
        Date date = Dates.form(year, month, day);
        int[] nowParam = Dates.getNowCalendar();
        Date now = Dates.form(nowParam[0], nowParam[1], nowParam[2]);
        return date.compareTo(now);
    }

    /**
     * year、month所对应的年月和当前时间比较大小，小于当前返回-1，等于当前返回0，大于当前返回1。
     * 
     * @param year
     * @param month
     * @return
     */
    public static int compareNow(int year, int month) {
        int[] nowParam = Dates.getNowCalendar();
        if (year < nowParam[0] || (year == nowParam[0] && month < nowParam[1])) {
            return -1;
        } else if (year == nowParam[0] && month == nowParam[1]) {
            return 0;
        } else {
            return 1;
        }
    }

    public static void main(String[] args) throws BaseException {
        System.out.println("-----compareNow----------------------------------");
        System.out.println("Compare:" + compareNow(2012, 1, 5));
        System.out.println("Compare:" + compareNow(2013, 2, 25));
        System.out.println("Compare:" + compareNow(2013, 3, 5));

        System.out.println("-----getNowCalendar----------------------------------");
        int[] now = getNowCalendar();
        System.out.println("NOW:" + now[0] + "-" + now[1] + "-" + now[2]);

        System.out.println("-----formatDate----------------------------------");
        String a = formatDate(new Date());
        System.out.println(a);

        String a1 = formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(a1);

        String b = formatDate(new Date(), "yyyy/MM/dd");
        System.out.println(b);

        String c = formatDate(new Date(), "yyyyMMdd");
        System.out.println(c);

        String cc = formatDate(new Date(), "HH:mm:ss");
        System.out.println(cc);

        System.out.println();
        System.out.println("-----getSimpleDate-------------------------------");
        Date d = getSimpleDate(new Date());
        String dd = formatDate(d, "yyyy-MM-dd HH:mm:ss");
        System.out.println(dd);

        System.out.println();
        System.out.println("-----parseDate-----------------------------------");
        Date p1 = parseDate("2008-02-20 10:25:33");
        String p1s = formatDate(p1);
        System.out.println(p1s);

        try {
            Date p11 = parseDate("2008/02/20");
            String p11s = formatDate(p11);
            System.out.println(p11s);
        } catch (Exception e1) {
            System.out.println("Unable parse '2008/02/20'");
        }
        try {
            Date p111 = parseDate("2008-02-20");
            String p111s = formatDate(p111);
            System.out.println(p111s);
        } catch (Exception e1) {
            System.out.println("Unable parse '2008-02-20'");
        }

        Date p2 = parseDate("2008-02-20", "yyyy-MM-dd");
        String p2s = formatDate(p2);
        System.out.println(p2s);

        Date p22 = parseDate("2008/02/20 10:25:33", "yyyy/MM/dd HH:mm:ss");
        String p22s = formatDate(p22);
        System.out.println(p22s);

        System.out.println();
        System.out.println("-----add-----------------------------------------");
        Date date = new Date();

        Date ay = addYear(date, 2);
        String ays = formatDate(ay);
        System.out.println(ays);

        Date am = addMonth(date, 2);
        String ams = formatDate(am);
        System.out.println(ams);

        Date ad = addDate(date, 2);
        String ads = formatDate(ad);
        System.out.println(ads);

        Date ah = addHour(date, 2);
        String ahs = formatDate(ah);
        System.out.println(ahs);

        Date amm = addMinute(date, 2);
        String amms = formatDate(amm);
        System.out.println(amms);

        Date as = addSecond(date, 2);
        String ass = formatDate(as);
        System.out.println(ass);

        System.out.println("-----patten-----------------------------------------");
        String s1 = "2012-03-20";
        String s2 = "2012-03-20 9:22:33";
        String s3 = "9:22:33";

        String s1Ret = guessDateFormatPatten(s1);
        System.out.println(s1Ret);
        String s2Ret = guessDateFormatPatten(s2);
        System.out.println(s2Ret);
        String s3Ret = guessDateFormatPatten(s3);
        System.out.println(s3Ret);
    }
}
