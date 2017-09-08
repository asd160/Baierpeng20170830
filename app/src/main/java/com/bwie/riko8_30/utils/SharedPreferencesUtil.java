package com.bwie.riko8_30.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bwie.riko8_30.MyApp;

/**
 * Created by Administrator on 2017/9/1.
 */

public class SharedPreferencesUtil {

    private final static String SP_NAME="common_data";//要创建sp的名字

    /**
     * 得到SharedPreferences对象
     */

    public static SharedPreferences getPreferences(){

        return MyApp.mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    /**
     *存一行数据 uid
     */
    public static void putPreference(String key, String value){
        SharedPreferences.Editor mEditor=getPreferences().edit();
        mEditor.putString(key,value);
        mEditor.commit();
    }
    /**
     * 获取 uid 数据
     * @param key
     * @return
     */
    public static String getPreferencesValue(String key) {
        return getPreferences().getString(key, "");
    }

    /**
     * 清除指定数据
     * @param key
     */
    public static void clearPreferences(String key) {
        SharedPreferences.Editor mEditor=getPreferences().edit();
        mEditor.remove(key);
        mEditor.commit();
    }
    /**
     * 清空所有数据
     */
    public static void clearPreferences(){
        SharedPreferences.Editor mEditor=getPreferences().edit();
        mEditor.clear();
        mEditor.commit();
    }

}
