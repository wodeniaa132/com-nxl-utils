package com.nxl.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 工具类，用于将汉语的数字转换为阿拉伯数字
 * @author : nixl
 * @date : 2020/11/17
 */
@Slf4j
public class NumberUtil {

    private static final Map<String, Double> ASCII;
    private static final String NUM_REGX = "^([0-9]{1,}[.]?[0-9]*)";
    private static final List<String> CHINESE_NUMS = new ArrayList<>(Arrays.asList("十","拾","一", "壹", "二", "贰", "三", "叁", "四", "肆", "五", "伍", "六", "陆", "七", "柒", "八", "捌", "九", "玖"));
    private static final HashMap<Character, Long> NUM_MAP = new HashMap<>();
    static {
        ASCII = new LinkedHashMap<>();
        ASCII.put("百亿", 10000000000.0);
        ASCII.put("十亿", 1000000000.0);
        ASCII.put("亿", 100000000.0);
        ASCII.put("千万", 10000000.0);
        ASCII.put("百万", 1000000.0);
        ASCII.put("十万", 100000.0);
        ASCII.put("万", 10000.0);
        ASCII.put("千", 1000.0);
        ASCII.put("百", 100.0);

        NUM_MAP.put('零', 0L);
        NUM_MAP.put('一', 1L);
        NUM_MAP.put('壹', 1L);
        NUM_MAP.put('二', 2L);
        NUM_MAP.put('贰', 2L);
        NUM_MAP.put('三', 3L);
        NUM_MAP.put('叁', 3L);
        NUM_MAP.put('四', 4L);
        NUM_MAP.put('肆', 4L);
        NUM_MAP.put('五', 5L);
        NUM_MAP.put('伍', 5L);
        NUM_MAP.put('六', 6L);
        NUM_MAP.put('陆', 6L);
        NUM_MAP.put('七', 7L);
        NUM_MAP.put('柒', 7L);
        NUM_MAP.put('八', 8L);
        NUM_MAP.put('捌', 8L);
        NUM_MAP.put('九', 9L);
        NUM_MAP.put('玖', 9L);
        NUM_MAP.put('十', 10L);
        NUM_MAP.put('拾', 10L);
        NUM_MAP.put('廿', 20L);
        NUM_MAP.put('卅', 30L);
        NUM_MAP.put('百', 100L);
        NUM_MAP.put('佰', 100L);
        NUM_MAP.put('千', 1000L);
        NUM_MAP.put('仟', 1000L);
        NUM_MAP.put('万', 10_000L);
        NUM_MAP.put('亿', 100_000_000L);
    }

    /**
     * 格式化各种类型的金额
     * @param numStr 金额字符串
     * @return 金额
     */
    public static BigDecimal convertAnyTypeNumToBigDecimal(String numStr) {
        if (StringUtils.isBlank(numStr)) {
            return new BigDecimal(0);
        }
        try {
            numStr = numStr.replaceAll("[\\[\\]\"元余]", "");
            if (checkChineseNum(numStr)) {
                return BigDecimal.valueOf(transChineseNum(numStr));
            }
            String moneyNum = RegxUtil.findMatcherGroupNum(NUM_REGX, numStr, 0);
            String unit = numStr.replace(moneyNum, "");
            String result = String.valueOf(Double.parseDouble(moneyNum));
            Set<String> units = ASCII.keySet();
            for (String s : units) {
                if (unit.equals(s)){
                    double resultNum = Double.parseDouble(moneyNum) * ASCII.get(s);
                    result = String.valueOf(resultNum);
                    break;
                }
            }
            return BigDecimal.valueOf(Double.parseDouble(result)).setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            log.error("normaliseMoney failed, str: {}", numStr);
        }
        return new BigDecimal(0);
    }
    private static long transChineseNum(String num) {
        if (num == null || num.length() == 0) {
            return 0L;
        }

        //特殊情况 ：例如 二零一二 一九八七 这种类似年份的数字
        long result = 0;
        if ((result = transChineseNumYears(num)) != -1) {
            return result;
        }
        result = 0;

        Stack<Long> s = new Stack<>();
        for (int i = 0; i < num.length(); i++) {
            long curNum = NUM_MAP.get(num.charAt(i));
            if (s.isEmpty() || curNum < s.peek()) {
                s.push(curNum);
            } else {
                int temp = 0;
                while (!s.isEmpty() && s.peek() < curNum) {
                    temp += s.pop();
                }
                temp = (temp == 0 ? 1 : temp);
                s.push(temp * curNum);
            }
        }
        while (!s.isEmpty()) {
            result += s.pop();
        }
        return result;
    }
    private static long transChineseNumYears(String num) {
        for (char c : num.toCharArray()) {
            if (NUM_MAP.get(c) >= 10L) {
                return -1;
            }
        }
        long result = 0;
        long unit = 1;
        for (int i = num.length() - 1; i >= 0; i--) {
            result += NUM_MAP.get(num.charAt(i)) * unit;
            unit *= 10;
        }

        return result;
    }
    /**
     * 转化成万进制
     * @param bigDecimal 传入值
     * @return 万进制值
     */
    public static BigDecimal tenThousandConvert(BigDecimal bigDecimal, Integer scale) {
        if (bigDecimal == null || bigDecimal.compareTo(new BigDecimal(0)) == 0) {
            return new BigDecimal(0);
        }
        return bigDecimal.divide(new BigDecimal(10000), scale, BigDecimal.ROUND_HALF_UP);
    }
    /**
     * 转化年月日到天数
     * @param penalty 年月日
     * @return 天数
     */
    public static Integer transformPenalty(String penalty) {
        if (StringUtils.isBlank(penalty)) {
            return 0;
        }
        penalty = penalty.replaceAll("(个|有期徒刑|无期徒刑|拘役|死刑|管制)", "");
        double result = 0;
//        String unit = penalty.replaceAll("[0-9]", "");
        String unit = penalty;
        if (unit.contains("年") && !unit.startsWith("年")) {
            String year = penalty.split("年")[0];
            try {
                penalty = penalty.split("年")[1];
            } catch (Exception e){
                // System.out.println("----------------");
            }
            BigDecimal yearNum = convertAnyTypeNumToBigDecimal(year);
            result = result + yearNum.doubleValue() * 365;
        }
        if (unit.contains("月")) {
            String mon = penalty.split("月")[0];
            try {
                penalty = penalty.split("月")[1];
            } catch (Exception e){
            }
            BigDecimal yearNum = convertAnyTypeNumToBigDecimal(mon);
            result = result + yearNum.doubleValue() * 30;
        }
        if (unit.contains("日") || unit.contains("天")) {
            String day = penalty.replaceAll("[日天]", "");
            BigDecimal yearNum = convertAnyTypeNumToBigDecimal(day);
            result = result + yearNum.doubleValue();
        }
        return BigDecimal.valueOf(result).intValue();
    }
    public static boolean checkChineseNum(String target){
        for (String chineseNum : CHINESE_NUMS) {
            if (target.contains(chineseNum)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(transformPenalty("二年零四个月"));
    }

}
