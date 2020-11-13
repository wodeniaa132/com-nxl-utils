package com.nxl.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    /**传入txt路径读取txt文件
     * @param txtPath
     * @return 返回读取到的内容
     */
    public static List<String> readTxt(String txtPath) {
        File file = new File(txtPath);
        if(file.isFile() && file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String text = null;
                List<String> result = new ArrayList<>();
                while((text = bufferedReader.readLine()) != null){
                    result.add(text);
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**使用FileOutputStream来写入txt文件
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
