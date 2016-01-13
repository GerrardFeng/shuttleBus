package com.utils;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ZHANGLU6 on 1/13/2016.
 */
public class PropertiesUtil {

    public static String getPropertiesURL(Context c, String str) {
        String url = null;
        Properties properties = new Properties();
        try {

            InputStream in = c.getResources().getAssets().open("property.properties");

            properties.load(in);
            url = properties.getProperty(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}
