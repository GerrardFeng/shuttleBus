package com.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dto.BusUser;
import com.google.gson.Gson;

public class SharePreferenceHelper {
		private static final String PREF_USER = "default_user";

	private static final String AUTO_LOGIN = "auto_login";

	private static final String REMEMBER_PWD = "remember_pwd";

	private static final String PWD = "pwd";

		public static void saveUser(Context context, BusUser user){
			SharedPreferences setting = context.getSharedPreferences("config",context.MODE_PRIVATE);
			SharedPreferences.Editor editor = setting.edit();
			editor.putString(PREF_USER, new Gson().toJson(user));
			editor.commit();
		}
		
	    public static BusUser getUser(Context context) {
			SharedPreferences setting = context.getSharedPreferences("config", context.MODE_PRIVATE);
			String userJson = setting.getString(PREF_USER, "").toString();
			return new Gson().fromJson(userJson, BusUser.class);
		}

	public static boolean isAutoLogin(Context context) {
		SharedPreferences setting = context.getSharedPreferences("config", context.MODE_PRIVATE);
		return setting.getBoolean(AUTO_LOGIN, false);
	}

	public static void setAutoLogin (Context context, boolean isAuto) {
		SharedPreferences setting = context.getSharedPreferences("config",context.MODE_PRIVATE);
		SharedPreferences.Editor editor = setting.edit();
		editor.putBoolean(AUTO_LOGIN, isAuto);
		editor.commit();
	}

	public static void setRememberPwd (Context context, boolean isRemember) {
		SharedPreferences setting = context.getSharedPreferences("config",context.MODE_PRIVATE);
		SharedPreferences.Editor editor = setting.edit();
		editor.putBoolean(REMEMBER_PWD, isRemember);
		editor.commit();
	}

	public static boolean isRememberPwd(Context context) {
		SharedPreferences setting = context.getSharedPreferences("config", context.MODE_PRIVATE);
		return setting.getBoolean(REMEMBER_PWD, false);
	}

	public static void savePwd (Context context, String pwd) {
		SharedPreferences setting = context.getSharedPreferences("config",context.MODE_PRIVATE);
		SharedPreferences.Editor editor = setting.edit();
		editor.putString(PWD, pwd);
		editor.commit();
	}

	public static String getPwd (Context context) {
		SharedPreferences setting = context.getSharedPreferences("config", context.MODE_PRIVATE);
		return setting.getString(PWD, "");
	}

}
