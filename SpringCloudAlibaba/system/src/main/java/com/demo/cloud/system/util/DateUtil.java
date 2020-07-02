package com.demo.cloud.system.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 *
 * @author sheng
 * @date 2020/07/01
 */
public class DateUtil {
    private static final SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    private static final SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdfTimes = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static final SimpleDateFormat sdfTimess = new SimpleDateFormat("yyyyMMddHHmmss");

    public DateUtil() {
    }

    public static String getSdfTimes() {
        return sdfTimes.format(new Date());
    }

    public static String getSdfTimess() {
        return sdfTimess.format(new Date());
    }

    public static String getYear() {
        return sdfYear.format(new Date());
    }

    public static String getDay() {
        return sdfDay.format(new Date());
    }

    public static String getDays() {
        return sdfDays.format(new Date());
    }

    public static String getTime() {
        return sdfTime.format(new Date());
    }

    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) != null && fomatDate(e) != null) {
            return fomatDate(s).getTime() >= fomatDate(e).getTime();
        } else {
            return false;
        }
    }

    public static Date fomatDate(String date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return fmt.parse(date);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Date fomatDate(String date, String format) {
        SimpleDateFormat fmt = new SimpleDateFormat(format);

        try {
            return fmt.parse(date);
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static boolean isValidDate(String s) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            fmt.parse(s);
            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    public static int getDiffYear(String startTime, String endTime) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            int years = (int)((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / 86400000L / 365L);
            return years;
        } catch (Exception var4) {
            return 0;
        }
    }

    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0L;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException var8) {
            var8.printStackTrace();
        }

        day = (endDate.getTime() - beginDate.getTime()) / 86400000L;
        return day;
    }

    public static long getMinuteSub(String beginDateStr, String endDateStr) {
        long minute = 0L;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException var8) {
            var8.printStackTrace();
        }

        minute = (endDate.getTime() - beginDate.getTime()) / 60000L;
        return minute;
    }

    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance();
        canlendar.add(5, daysInt);
        Date date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        return dateStr;
    }

    public static String getAfterDayDate(Object obj, String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance();
        canlendar.setTime(fomatDate(obj + ""));
        canlendar.add(5, daysInt);
        Date date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        return dateStr;
    }

    public static String getAfterYearDate(Object obj, String year) {
        int daysInt = Integer.parseInt(year);
        Calendar canlendar = Calendar.getInstance();
        canlendar.setTime(fomatDate(obj + ""));
        canlendar.add(1, daysInt);
        Date date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfd.format(date);
        return dateStr;
    }

    public static String getAfterMonthDate(Object obj, String month) {
        int daysInt = Integer.parseInt(month);
        Calendar canlendar = Calendar.getInstance();
        canlendar.setTime(fomatDate(obj + ""));
        canlendar.add(2, daysInt);
        Date date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfd.format(date);
        return dateStr;
    }

    public static String getAfterSecsDate(String secs) {
        int daysInt = Integer.parseInt(secs);
        Calendar canlendar = Calendar.getInstance();
        canlendar.add(13, daysInt);
        Date date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        return dateStr;
    }

    public static String getAfterSecsDate(Object obj, String secs) {
        return getAfterSecsDate(obj, secs, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getAfterSecsDate(Object obj, String secs, String format) {
        if (obj != null && obj.toString().length() != 0) {
            int daysInt = Integer.parseInt(secs);
            Calendar canlendar = Calendar.getInstance();
            canlendar.setTime(fomatDate(obj + ""));
            canlendar.add(13, daysInt);
            Date date = canlendar.getTime();
            SimpleDateFormat sdfd = new SimpleDateFormat(format);
            String dateStr = sdfd.format(date);
            return dateStr;
        } else {
            return "";
        }
    }

    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance();
        canlendar.add(5, daysInt);
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static String getFormatDate(Object obj, String fromat) {
        if (obj != null && obj.toString().length() != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(fromat);
            return sdf.format(obj);
        } else {
            return "";
        }
    }

    public static PageData repeatDate(List<PageData> list) {
        if (list != null && list.size() != 0) {
            List<PageData> list1 = new ArrayList();
            list1.addAll(list);

            for(int i = list.size() - 1; i >= 0; --i) {
                Boolean bl = repeatDate((PageData)list.get(i), list1);
                if (bl) {
                    return (PageData)list.get(i);
                }
            }

            return null;
        } else {
            return null;
        }
    }

    private static Boolean repeatDate(PageData pd, List<PageData> list) {
        if (pd.containsKey("start_date") && pd.containsKey("end_date")) {
            String start_date = pd.getString("start_date");
            String end_date = pd.getString("end_date");
            if (list.contains(pd)) {
                list.remove(pd);
            }

            Date start_date1 = fomatDate(start_date, "yyyy-MM-dd");
            Date end_date1 = fomatDate(end_date, "yyyy-MM-dd");

            for(int i = 0; i < list.size(); ++i) {
                Date start_date2 = fomatDate(((PageData)list.get(i)).getString("start_date"), "yyyy-MM-dd");
                Date end_date2 = fomatDate(((PageData)list.get(i)).getString("end_date"), "yyyy-MM-dd");
                if (start_date1.getTime() >= start_date2.getTime() && start_date1.getTime() <= end_date2.getTime()) {
                    return true;
                }

                if (end_date1.getTime() >= start_date2.getTime() && end_date1.getTime() <= end_date2.getTime()) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }

    public static String getMonthFirstDay(String date) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.setTime(fomatDate(date, "yyyy-MM-dd"));
        canlendar.set(5, 1);
        return sdfDay.format(canlendar.getTime());
    }

    public static String getMonthLastDay(String date) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.setTime(fomatDate(date, "yyyy-MM-dd"));
        canlendar.set(5, canlendar.getActualMaximum(5));
        return sdfDay.format(canlendar.getTime());
    }

    public static void main(String[] args) throws ParseException {

    }
}
