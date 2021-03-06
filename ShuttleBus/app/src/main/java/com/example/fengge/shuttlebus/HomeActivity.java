package com.example.fengge.shuttlebus;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.zxing.activity.CaptureActivity;

public class HomeActivity extends BaseActivity {

    @Override
    @SuppressLint("InlinedApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.activity_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button showTicket = (Button)findViewById(R.id.viewBusTicket);
        Button scanBarcode = (Button)findViewById(R.id.scan_barcode_btn);
        showTicket.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent showTicketIntent = new Intent(HomeActivity.this, TicketActivity.class);
                startActivity(showTicketIntent);
            }
        });

        scanBarcode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent scanBarcodeIntent = new Intent(HomeActivity.this, CaptureActivity.class);
                startActivity(scanBarcodeIntent);
            }
        });
    }

}
