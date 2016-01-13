package com.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.dto.BusUser;
import com.google.gson.Gson;

public class SharePreferenceHelper {
		private static final String PREF_USER = "default_user";
		public static boolean saveUser(Context context, BusUser user){
			SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = setting.edit();
			editor.putString(PREF_USER, new Gson().toJson(user));
			return editor.commit();
		}
		
	    public static BusUser getUser(Context context) {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
			String userJson = settings.getString(PREF_USER, "");
			BusUser user = new Gson().fromJson(userJson, BusUser.class);
			return user;
		}
}
