package com.nxl.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

/**
 * @author : nixl
 * @date : 2020/11/10
 */
@Slf4j
public class JSONUtil {

    /**
     *
     * @param jsonObject 需要递归的json
     * @param item  要找的字段
     */
    public void getTargetItem(JSONObject jsonObject, String item) {
        Iterator it = jsonObject.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            if (key.equals(item)) {
                //todo 这里写操作方法
//                save(jsonObject.getString(key), jsonObject.getString(queryItem));
//                getQueryValue(jsonObject, jsonObject.getString(key));
            }
            Object value = jsonObject.get(key);
            //todo 下面没有json了且不是要找的字段
            if (value instanceof Integer || value instanceof String || value instanceof Double) {
                continue;
            }
            //todo 递归
            try {
                JSONObject child = jsonObject.getJSONObject(key);
                getTargetItem(child, item);
            } catch (ClassCastException e) {
                //todo instanceof判断不出，暂时先用try/catch
                try {
                    JSONArray childArray = jsonObject.getJSONArray(key);
                    for (int i = 0; i < childArray.size(); i++) {
                        getTargetItem(childArray.getJSONObject(i), item);
                    }
                } catch (Exception childError) {
                    //多半是遇到不是JSONArray的list了，小问题
                    log.info(childError.getMessage());
                }
            }
        }
    }
}
