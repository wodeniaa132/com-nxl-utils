package com.nxl.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**时间工具类
 * @author : nixl
 * @date : 2020/11/10
 */
@Slf4j
public class TimeUtil {

    private final static String regEx = "[\n`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， ？\"]";
    private final static String regExForYearAndMonth = "[年月]";
    private final static String regExForDay = "[日]";
    private final static String regExForNumTen = "[十]";
    private final static String CN_NUMBER = "零一二三四五六七八九";

    /**
     * 中文时间转Date
     * @param date
     * @return
     */
//    public Date convertTime(String date) {
//        date = date.replaceAll("[\n`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， ？\"]", "");
//
//        StringBuilder sb = new StringBuilder();
//        if (date.startsWith("二") || date.startsWith("2")) {
//            sb.append("20");
//        } else {
//            sb.append("1").append();
//        }
//        String templateDateString = date.replaceAll("[年月]", "-").replaceAll("[日]", "").substring(2);
//
//        //为了处理一种错误的时间格式如二〇二〇年年十一月月十日
//        if (templateDateString.contains("--")) {
//            templateDateString = templateDateString.replaceAll("--", "-");
//        }
//        //根据把中文数字转换成阿拉伯数字
//        for (int i = 0; i < templateDateString.length(); i++) {
//            char c = templateDateString.charAt(i);
//            //不加if判断会把-给变成/
//            if(CN_NUMBER.contains(Character.toString(c))) {
//                templateDateString = templateDateString.replace(c, (char) (CN_NUMBER.indexOf(c) + 48));
//            }
//        }
//        //会有其他的中文零没有换成阿拉伯数字0
//        if (templateDateString.charAt(1) > '9' || templateDateString.charAt(1) < '0') {
//            templateDateString = templateDateString.replace(templateDateString.charAt(1), '0');
//        }
//        //会出现元月
//        if (templateDateString.charAt(3) == '元') {
//            templateDateString = templateDateString.replace(templateDateString.charAt(3), '1');
//        }
//        String[] split = templateDateString.split("-");
//
//        //年
//        if (split[0].charAt(0) > '9' || split[0].charAt(0) < '0') {
//            sb.append(split[0].replace(split[0].charAt(0), '0')).append("-");
//        } else {
//            sb.append(split[0]).append("-");
//        }
//
//        //月
//        //十出现在第一位且后面还有数字
//        if (split[1].startsWith("十") && split[1].length() > 1) {
//            sb.append("1").append(split[1].charAt(1)).append("-");
//        } else if (split[1].startsWith("十") && split[1].length() == 1) {
//            sb.append("10").append("-");
//        } else {
//            sb.append(split[1].replaceAll("[十]", "")).append("-");
//        }
//
//        //日
//        int index = split[2].indexOf("十");
//        if (index != -1) {
//            if (index == 0 && split[2].length() > 1) {
//                sb.append(split[2].replaceAll("[十]", "1"));
//            } else if (index == 0 && split[2].length() == 1) {
//                sb.append("10");
//            } else if (index == 1 && split[2].length() == 2) {
//                sb.append(split[2].replaceAll("[十]", "0"));
//            } else if (index == 1 && split[2].length() == 3) {
//                sb.append(split[2].replaceAll("[十]", ""));
//            } else if (index == 2) {
//                sb.append(split[2].replaceAll("[十]", "0"));
//            }
//        } else {
//            sb.append(split[2]);
//        }
//        Date parse = null;
//        try {
//            parse = new SimpleDateFormat("yyyy-MM-dd").parse(sb.toString());
//        } catch (ParseException e) {
//            log.error("TimeConvert ParseException: " + e);
//        }
//        return parse;
//    }


    /**
     * 中文时间转Date
     * @param date
     * @return
     */
    public Date convertTime(String date) {
        date = date.replaceAll(regEx, "");

        StringBuilder sb = new StringBuilder();
        if (date.startsWith("二") || date.startsWith("2")) {
            sb.append("20");
        } else {
            sb.append("19");
        }
        String finishDate = date.replaceAll(regExForYearAndMonth, "-").replaceAll(regExForDay, "").substring(2);

        //为了处理一种错误的时间格式如二〇二〇年年十一月月十日
        if (finishDate.contains("--")) {
            finishDate = finishDate.replaceAll("--", "-");
        }
        //根据把中文数字转换成阿拉伯数字
        for (int i = 0; i < finishDate.length(); i++) {
            char c = finishDate.charAt(i);
            //不加if判断会把-给变成/
            if(CN_NUMBER.contains(Character.toString(c))) {
                finishDate = finishDate.replace(c, (char) (CN_NUMBER.indexOf(c) + 48));
            }
        }
        //会有其他的中文零没有换成阿拉伯数字0
        if (finishDate.charAt(1) > '9' || finishDate.charAt(1) < '0') {
            finishDate = finishDate.replace(finishDate.charAt(1), '0');
        }
        //会出现元月
        if (finishDate.charAt(3) == '元') {
            finishDate = finishDate.replace(finishDate.charAt(3), '1');
        }
        String[] split = finishDate.split("-");
        //年
        if (split[0].charAt(0) > '9' || split[0].charAt(0) < '0') {
            sb.append(split[0].replace(split[0].charAt(0), '0')).append("-");
        } else {
            sb.append(split[0]).append("-");
        }
        //月
        //十出现在第一位且后面还有数字
        if (split[1].startsWith("十") && split[1].length() > 1) {
            sb.append("1").append(split[1].charAt(1)).append("-");
        } else if (split[1].startsWith("十") && split[1].length() == 1) {
            sb.append("10").append("-");
        } else {
            sb.append(split[1].replaceAll(regExForNumTen, "")).append("-");
        }

        int index = split[2].indexOf("十");
        if (index != -1) {
            if (index == 0 && split[2].length() > 1) {
                sb.append(split[2].replaceAll(regExForNumTen, "1"));
            } else if (index == 0 && split[2].length() == 1) {
                sb.append("10");
            } else if (index == 1 && split[2].length() == 2) {
                sb.append(split[2].replaceAll(regExForNumTen, "0"));
            } else if (index == 1 && split[2].length() == 3) {
                sb.append(split[2].replaceAll(regExForNumTen, ""));
            } else if (index == 2) {
                sb.append(split[2].replaceAll(regExForNumTen, "0"));
            }
        } else {
            sb.append(split[2]);
        }
        Date parse = null;
        try {
            parse = new SimpleDateFormat("yyyy-MM-dd").parse(sb.toString());
        } catch (ParseException e) {
            log.error("TimeConvert ParseException: " + e);
        }
        return parse;
    }


    /**
     * 计算两个时间的时间差
     * @param start 开始时间
     * @param end 结束时间
     * @return
     */
    private static long until(Date start, Date end) {
        LocalDate startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return startDate.until(endDate, ChronoUnit.DAYS);
    }
}
