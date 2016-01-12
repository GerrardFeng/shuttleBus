package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
        List<HashMap<String, String>> ticketData = initTicketData();
        initListViewEvent(ticketData);


    }

    private List<HashMap<String, String>> initTicketData() {
        List<HashMap<String, String>> datas = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("ticketInfo", "姓名：test 线路： test 站点： test 类型： test");
        item.put("ticketInfo", "姓名：test1 线路： test1 站点： test1 类型： test1");
        datas.add(item);
        return datas;
    }


    private void initListViewEvent(List<HashMap<String, String>> routData) {
        ListView listView = (ListView) this.findViewById(R.id.ticketListView);
        SimpleAdapter adapter = new SimpleAdapter(this, routData, R.layout.ticket_item, new String[] { "ticketInfo"},
                new int[] { R.id.ticketInfo });
        listView.setAdapter(adapter);
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
