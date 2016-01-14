package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANGLU6 on 1/14/2016.
 */
public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {

            if (!activity.isFinishing()) {
                if(!activity.getComponentName().getClassName().equalsIgnoreCase(LoginActivity.class.getName()))
                    activity.finish();
            }
        }
    }
}
