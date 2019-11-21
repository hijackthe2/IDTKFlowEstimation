package com.example.idtk.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String format(Date date, String dateFormat){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
    }

    public static Date parseDate(String time, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Time parseTime(String time, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        try{
            date = simpleDateFormat.parse(time);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return new Time(date.getTime());
    }

    public static String toHexString(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return StringUtils.format(Integer.toHexString(calendar.get(Calendar.YEAR) - 2000), 2) +
                StringUtils.format(Integer.toHexString(calendar.get(Calendar.MONTH)), 2) +
                StringUtils.format(Integer.toHexString(calendar.get(Calendar.DATE)), 2) +
                StringUtils.format(Integer.toHexString(calendar.get(Calendar.HOUR)), 2) +
                StringUtils.format(Integer.toHexString(calendar.get(Calendar.MINUTE)), 2) +
                StringUtils.format(Integer.toHexString(calendar.get(Calendar.SECOND)), 2);
    }

    public static String toHexString(Time time){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        date.setTime(time.getTime());
        calendar.setTime(date);
        return StringUtils.format(Integer.toHexString(calendar.get(Calendar.HOUR)), 2) +
                StringUtils.format(Integer.toHexString(calendar.get(Calendar.MINUTE)), 2);
    }

    public static String toHexString(LocalDateTime time){
        return StringUtils.format(Integer.toHexString(time.getYear() - 2000), 2) +
                StringUtils.format(Integer.toHexString(time.getMonthValue()), 2) +
                StringUtils.format(Integer.toHexString(time.getDayOfMonth()), 2) +
                StringUtils.format(Integer.toHexString(time.getHour()), 2) +
                StringUtils.format(Integer.toHexString(time.getMinute()), 2) +
                StringUtils.format(Integer.toHexString(time.getSecond()), 2);
    }
}
