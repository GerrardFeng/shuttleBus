package com.example.fengge.shuttlebus;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by WURY on 1/12/2016.
 */
public class TicketListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        List<HashMap<String, String>> ticketData = initTicketData();
        initListViewEvent(ticketData);


    }

    private List<HashMap<String, String>> initTicketData() {
        List<HashMap<String, String>> datas = new ArrayList<HashMap<String, String>>();
        for(int i=0; i<5; i++) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("ticketInfo", "姓名：test" + i + " 线路： test 站点： test 类型： test");
            datas.add(item);
        }
        return datas;
    }


    private void initListViewEvent(List<HashMap<String, String>> ticketData) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ticketListLayout);

        for(HashMap<String, String> ticket : ticketData) {
            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView1.setText(ticket.get("ticketInfo"));
            linearLayout.addView(textView1);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
