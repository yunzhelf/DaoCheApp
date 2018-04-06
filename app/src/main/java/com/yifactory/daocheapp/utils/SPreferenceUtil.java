package com.yifactory.daocheapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class SPreferenceUtil {

    private SharedPreferences mSharedPreferences;

    private SharedPreferences.Editor mEditor;

    public SPreferenceUtil(Context context, String fileName) {

        mSharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        mEditor = mSharedPreferences.edit();
    }

    /**
     * 保存用户是否在线的状态
     */
    public void setIsLine(boolean isLine) {
        mEditor.putBoolean("isLine", isLine);
        mEditor.commit();
    }

    public boolean getIsLine() {//获取当前用户是否在线的状态
        return mSharedPreferences.getBoolean("isLine", false);
    }

    /**
     * 保存用户的信息
     */
    public void saveUserInfo(String userInfoStr) {
        mEditor.putString("user_info", userInfoStr);
        mEditor.commit();
    }

    public String getUserInfo() {
        return mSharedPreferences.getString("user_info", "");
    }

    public void setEightModuleFcId(String name, String fcId) {
        mEditor.putString(name, fcId);
        mEditor.commit();
    }

    public String getEightModuleFcId(String name) {
        return mSharedPreferences.getString(name, "");
    }

    public void clearUserInfo() {
        mSharedPreferences.edit().clear().apply();
    }

    public void setSearch(List<String> dataList) {
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(dataList);
        mEditor.clear();
        mEditor.putString("search", strJson);
        mEditor.commit();
    }

    public List<String> getSearch() {
        List<String> dataList = new ArrayList<String>();
        String strJson = mSharedPreferences.getString("search", null);
        if (null == strJson) {
            return dataList;
        }
        Gson gson = new Gson();
        dataList = gson.fromJson(strJson, new TypeToken<List<String>>() {
        }.getType());
        return dataList;

    }
}
