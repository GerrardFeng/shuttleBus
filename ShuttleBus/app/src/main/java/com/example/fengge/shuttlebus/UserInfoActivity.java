package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by WURY on 1/12/2016.
 */
public class UserInfoActivity extends Activity {

    private int selectedUserType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button userTypeButton = (Button)findViewById(R.id.userType);
        Button showTickets = (Button)findViewById(R.id.showTickets);

        userTypeButton.setOnClickListener(new userTypeButtonListener());
        showTickets.setOnClickListener(new showTicketsButtonListener());
    }

    class userTypeButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            final String[] arrayUserType = new String[] { "普通上下班", "轮班"};
            Dialog alertDialog = new AlertDialog.Builder(UserInfoActivity.this).
                    setTitle("修改用户类型")
                    .setSingleChoiceItems(arrayUserType, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedUserType = which;
                        }
                    }).
                    setPositiveButton("确认", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(UserInfoActivity.this, arrayUserType[selectedUserType], Toast.LENGTH_SHORT).show();
                        }
                    }).
                    setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    }).
                    create();
            alertDialog.show();
        }
    }

    class showTicketsButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent ticketsIntent = new Intent(UserInfoActivity.this, TicketListActivity.class);
            startActivity(ticketsIntent);
        }
    }
}