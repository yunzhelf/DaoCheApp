package com.yifactory.daocheapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
public class SharePreferenceUtil {
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	static SharePreferenceUtil instance;

	public static SharePreferenceUtil getInstance(Context context){
		if(instance == null){
			return new SharePreferenceUtil(context);
		}
		return instance;
	}
	private SharePreferenceUtil(Context context) {
		sp = context.getSharedPreferences("daoChe", Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public void setUserId(String uId){
		editor.putString("userId",uId);
		editor.commit();
	}

	public String getUserId(){
		return sp.getString("userId","");
	}

	
}
