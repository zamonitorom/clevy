package com.bms.rabbit.binding;
// Created by Konstantin on 30.08.2018.

import android.annotation.SuppressLint;
import android.databinding.BindingConversion;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TextViewBinding {
    @SuppressLint("SimpleDateFormat")
    @BindingConversion
    public static String convertDateToString(Date date) {
        try {
            SimpleDateFormat formatter;
            Calendar c1 = Calendar.getInstance(); // today
            c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday
            Calendar c2 = Calendar.getInstance();
            c2.setTime(date);
//            if (date.after(cal.getTime())) {
            if (DateUtils.isToday(date.getTime())) {
                formatter = new SimpleDateFormat("HH:mm");
            } else if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                return "вчера";
            } else {
                formatter = new SimpleDateFormat("dd.MM.yyyy");
            }
            return formatter.format(date);
        } catch (Exception e) {

        }
        return "";
    }
}
/*
 public static String getMyPrettyDate(long neededTimeMilis) {
    Calendar nowTime = Calendar.getInstance();
    Calendar neededTime = Calendar.getInstance();
    neededTime.setTimeInMillis(neededTimeMilis);

    if ((neededTime.get(Calendar.YEAR) == nowTime.get(Calendar.YEAR))) {

        if ((neededTime.get(Calendar.MONTH) == nowTime.get(Calendar.MONTH))) {

            if (neededTime.get(Calendar.DATE) - nowTime.get(Calendar.DATE) == 1) {
                //here return like "Tomorrow at 12:00"
                return "Tomorrow at " + DateFormat.format("HH:mm", neededTime);

            } else if (nowTime.get(Calendar.DATE) == neededTime.get(Calendar.DATE)) {
                //here return like "Today at 12:00"
                return "Today at " + DateFormat.format("HH:mm", neededTime);

            } else if (nowTime.get(Calendar.DATE) - neededTime.get(Calendar.DATE) == 1) {
                //here return like "Yesterday at 12:00"
                return "Yesterday at " + DateFormat.format("HH:mm", neededTime);

            } else {
                //here return like "May 31, 12:00"
                return DateFormat.format("MMMM d, HH:mm", neededTime).toString();
            }

        } else {
            //here return like "May 31, 12:00"
            return DateFormat.format("MMMM d, HH:mm", neededTime).toString();
        }

    } else {
        //here return like "May 31 2010, 12:00" - it's a different year we need to show it
        return DateFormat.format("MMMM dd yyyy, HH:mm", neededTime).toString();
    }
}
 */