package com.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dto.BusUser;
import com.google.gson.Gson;

public class SharePreferenceHelper {
		private static final String PREF_USER = "default_user";
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
}
