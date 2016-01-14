package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ZHANGLU6 on 1/14/2016.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("BaseActivity", getClass().getSimpleName());
        ActivityCollector.addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    void showTips(Context context, int msg, boolean isLong) {
        Toast tTips;
        if (isLong) {
            tTips = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            tTips = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        tTips.show();
    }

}
