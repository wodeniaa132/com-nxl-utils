package com.nxl.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则处理
 * Created by Administrator on 2017-12-04.
 */
public class RegxUtil {

    ////////////////////////////////////////////// 正则处理 ///////////////////////////////////////////////////////////


    /**
     * 用规则提取钱
     * @param txt
     * @return
     */
    public static List<String> findMoneyByMatcher(String txt) {
        return findMatchersGroupNum("[\\d一二三四五六七八九十百千万亿]*余?元",txt,0);
    }

    /**
     * 用规则提取牌照
     * @param text
     */
    public static List<String> findLicensePlateByMatcher(String text) {
        return findMatchersGroupNum("[京津沪渝蒙新藏宁桂港澳黑吉辽晋冀青鲁豫苏皖浙闽赣湘鄂粤琼甘陕黔滇川][A-Z][A-Z1-9]{5}",text,0);
    }

    /**
     * 通过规则提取地点
     * @param txt
     * @return
     */
    public static Set<String> findPlaceByMatcher(String txt) {
        return findPlaceByMatcher(txt,"[^超]{1,2}市");
    }

    public static Set<String> findPlaceByMatcher(String txt,String prefix) {
        Set<String> result=new HashSet<>();
        String regxCity=prefix;
        String regxStreet="(.{1,10}街道)?";
        result.addAll(findMatchersGroupNum(regxCity+regxStreet+"(.{1,10}[区村])?(.{1,10}([号栋幢楼]|单元))*([^，。]{1,10}(门前|附近|市场|超市|边|内|城|旁))*",txt,0));
//        result.addAll(findMatchersGroupNum(regxCity+regxStreet+".*?幢.{1,5}号",txt,0));
//        result.addAll(findMatchersGroupNum("在("+regxCity+regxStreet+".*?村.*?幢.*?楼(附近)?(.*?门前)?)",txt,1));
//        result.addAll(findMatchersGroupNum(regxCity+regxStreet+"(.*?市场)",txt,0));
        return result;
    }


    /**
     * 返回匹配的第一个文本
     * @param regx
     * @param txt
     * @return
     */
    public static String findMatcherAll(String regx, String txt) {
        return findMatcherGroupNum(regx,txt,0);
    }

    /**
     * 匹配指定位置的文本
     * @param regx
     * @param txt
     * @param num
     * @return
     */
    public static String findMatcherGroupNum(String regx, String txt,int num) {
        Matcher m=findMatcher(regx,txt);
        String result="";
        if(m.find()){
            result=m.group(num);
        }
        result=result!=null?result.trim():"";
        //result=result.replaceAll("\r\n","\\\\n");
        return result;
    }


    /**
     * 匹配最后的文本
     * @param regx
     * @param txt
     * @param num
     * @return
     */
    public static String findMatcherGroupNumForLast(String regx, String txt,int num) {
        Matcher m=findMatcher(regx,txt);
        String result = "";
        while(m.find()){
            String str = m.group(num);
            if(StringUtils.isNotBlank(str)) {
                result = str;
            }
        }
        return result;
    }
    /**
     * 获取匹配的列表
     * @param regx
     * @param txt
     * @param num
     * @return
     */
    public static List<String> findMatchersGroupNum(String regx, String txt,int num) {
        Matcher m=findMatcher(regx,txt);
        List<String> strs=new ArrayList<>();
        while(m.find()){
            String str = m.group(num);
            if(str != null) {
                strs.add(str);
            }
        }
        return strs;
    }

    /**
     * 获取正则匹配
     * @param regx
     * @param txt
     * @return
     */
    public static Matcher findMatcher(String regx, String txt) {
        return Pattern.compile(regx).matcher(txt);
    }



    /**
     * 正则匹配
     * @param text
     * @param regx
     * @return
     */
    public static boolean match(String regx,String text){
        if(StringUtils.isBlank(text)){
            return false;
        }
        Pattern p=Pattern.compile(regx);
        Matcher m=p.matcher(text);
        return  m.find();//返回true
    }

    public static boolean match(String text,List<String> regxs){
        for(String regx:regxs){
            if(match(regx,text)){
                return true;
            }
        }
        return  false;//返回true
    }
}
