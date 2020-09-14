package com.silent.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zhao
 * @Title: DateUtils
 * @ProjectName CODE-X
 * @Description:
 * @date 2020/9/11 14:08
 */
public class DateUtils {


    private  static String getTheDayAfter(int n){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, n);
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }


}
