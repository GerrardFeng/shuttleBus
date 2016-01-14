package com.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.example.fengge.shuttlebus.R;

/**
 * Created by ZHANGLU6 on 1/14/2016.
 */
public class CustomProgressDialog extends ProgressDialog {

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
    }

    public static CustomProgressDialog show(Context ctx){
        CustomProgressDialog d = new CustomProgressDialog(ctx);
        d.show();
        return d;
    }
}
