package com.silent.utils;

/**
 * @author zhao
 * @Title: FormatUtils
 * @ProjectName CODE-X
 * @Description: FormatUtils
 * @date 2020/9/14 17:55
 */
public class FormatUtils {



    public static String formatDouble(double d) {
        return String.format("%.2f", d);
    }


    /** 保留两位小数，四舍五入的一个老土的方法 */
    public static double formatDouble1(double d) {
        return (double)Math.round(d*100)/100;
    }

    public static double formatDouble2(int digit, double value) {
        int multi = (int) Math.pow(10, digit);
        return (double) Math.round(value * multi) / multi;
    }


}
