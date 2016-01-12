package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by WURY on 1/12/2016.
 */
public class UserInfoActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        Button userTypeButton = (Button)findViewById(R.id.userType);
        Button showTickets = (Button)findViewById(R.id.showTickets);

        userTypeButton.setOnClickListener(new userTypeButtonListener());
        showTickets.setOnClickListener(new showTicketsButtonListener());

    }

    class userTypeButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
            startActivity(intent);
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