package com.yifactory.daocheapp.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.yifactory.daocheapp.bean.LoginBean;
import com.yifactory.daocheapp.bean.VideoListBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class UserInfoUtil {
    /**
     * 保存user信息
     * @param userInfoBean
     */
    public static void saveUserInfo(LoginBean.DataBean userInfoBean, Context mContext) {
        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            // 创建对象输出流，并封装字节流
            oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(userInfoBean);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 将字节流编码成base64的字符串
        String userInfo = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        SPreferenceUtil sPreferenceUtil = new SPreferenceUtil(mContext, "user_info.txt");
        sPreferenceUtil.saveUserInfo(userInfo);
    }

    /**
     * 获取用户信息的Bean
     */
    public static LoginBean.DataBean getUserInfoBean(Context mContext) {
        SPreferenceUtil sPreferenceUtil = new SPreferenceUtil(mContext, "user_info.txt");
        String userInfoStr = sPreferenceUtil.getUserInfo();
        if (TextUtils.isEmpty(userInfoStr)) {
            return null;
        }
        // 读取字节
        byte[] base64 = Base64.decode(userInfoStr.getBytes(), Base64.DEFAULT);
        // 封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            // 再次封装
            ObjectInputStream ois = new ObjectInputStream(bais);
            // 读取对象
            return (LoginBean.DataBean) ois.readObject();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void clearUserInfoBean(Context mContext) {
        SPreferenceUtil sPreferenceUtil = new SPreferenceUtil(mContext, "user_info.txt");
        sPreferenceUtil.clearUserInfo();
    }
}
