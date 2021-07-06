package com.nxl.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    /**
     * 传入txt路径读取txt文件
     * 返回列表(用于读取id等以行为单位的)
     * @param txtPath
     * @return 返回读取到的内容
     */
    public static List<String> readTxtForList(String txtPath) {
        File file = new File(txtPath);
        if (file.isFile() && file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                //有可能会出现编码问题，在new InputStreamReader(fileInputStream)中加"gbk"或"UTF-8"变为(fileInputStream, "gbk")
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String text = null;
                List<String> result = new ArrayList<>();
                while ((text = bufferedReader.readLine()) != null) {
                    result.add(text);
                }
                fileInputStream.close();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 传入txt路径读取txt文件
     * 返回文本
     * @param txtPath
     * @return 返回读取到的内容
     */
    public static String readTxtForString(String txtPath) {
        File file = new File(txtPath);
        if (file.isFile() && file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                //有可能会出现编码问题，在new InputStreamReader(fileInputStream)中加"gbk"或"UTF-8"变为(fileInputStream, "gbk")
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String text = null;
                while ((text = bufferedReader.readLine()) != null) {
                    sb.append(text);
                }
                fileInputStream.close();
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 使用FileOutputStream来写入txt文件
     * @param txtPath txt文件路径
     * @param content 需要写入的文本
     */
    public static void writeTxt(String txtPath,String content, boolean nextLine){
        FileOutputStream fileOutputStream = null;
        File file = new File(txtPath);
        try {
            if(file.exists()){
                //判断文件是否存在，如果不存在就新建一个txt
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file, true);
            if (nextLine) {
                fileOutputStream.write((content + "\r\n").getBytes());
            }else {
                fileOutputStream.write(content.getBytes());
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 传入文件或文件夹路径进行遍历
     * @param path 要遍历的文件或文件夹路径
     */
    public static void getFiles(String path) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (File value : files) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (value.isDirectory()) {
                    System.out.println("目录：" + value.getPath());
                    getFiles(value.getPath());
                } else {
                    //这里写找到文件后的操作方法
//                    System.out.println("这里写找到文件后的操作方法");
                }
            }
        } else {
            //这里写找到文件后的操作方法
//            System.out.println("这里写找到文件后的操作方法");
        }
    }

    public static void main(String[] args) {
//        List<String> list = FileUtil.readTxt("C:/Users/N/Desktop/json.txt");
        List<String> list = new ArrayList<>();
        list.add("asd");
        list.add("zxc");
//        StringBuilder sb = new StringBuilder();
//        list.stream().forEach(s ->{
//            sb.append(s);
//        });
//        JSONObject jsonObject = JSONObject.parseObject(sb.toString());
//        System.out.println(sb.toString());
        for(String s : list) {
            writeTxt("C:/Users/N/Desktop/test.txt", s, true);
        }
    }
}
