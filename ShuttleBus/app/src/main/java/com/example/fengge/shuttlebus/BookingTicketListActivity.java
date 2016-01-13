package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by GUOFR2 on 1/13/2016.
 */
public class BookingTicketListActivity  extends Activity {

    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private ListView listView;
    private TextView textView;
    private Intent intent;
    private String sourceType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_ticket_list);
        initData();
        initView();
        initEvent();
    }


    private void initData() {
        intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        sourceType = bundle.getString("sourceType");
        if (SourceType.ROUTE.getName().equals(sourceType)) {
            // get route
        }
        if (SourceType.STOP.getName().equals(sourceType)) {
            // TODO get stop
            createData();
        }
    }


    private void initView() {
        textView = (TextView) this.findViewById(R.id.title);
        textView.setText(sourceType);
        listView = (ListView) this.findViewById(R.id.route_list_view);
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.booking_list_item, new String[] { "route_icon", "name",
                "arrow_icon" }, new int[] { R.id.route_icon , R.id.name,  R.id.arrow_icon });
        listView.setAdapter(adapter);
    }


    private void initEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long value) {
                ListView listView = (ListView) parent;
                HashMap<String, Object> map = (HashMap<String, Object>) listView.getItemAtPosition(position);
                String id = String.valueOf(map.get("id"));
                String name = String.valueOf(map.get("name"));
                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    private void createData() {
        HashMap<String, Object> item = new HashMap<String, Object>();
        item.put("route_icon", R.drawable.bus_icon);
        item.put("id", 1);
        item.put("name", "唐家");
        item.put("arrow_icon", R.drawable.bus_icon);
        list.add(item);

        HashMap<String, Object> item1 = new HashMap<String, Object>();
        item1.put("route_icon", R.drawable.bus_icon);
        item1.put("id", 3);
        item1.put("name", "金鼎");
        item1.put("arrow_icon", R.drawable.bus_icon);
        list.add(item1);

        HashMap<String, Object> item2 = new HashMap<String, Object>();
        item2.put("route_icon", R.drawable.bus_icon);
        item2.put("id", 5);
        item2.put("name", "拱北");
        item2.put("arrow_icon", R.drawable.bus_icon);
        list.add(item2);
    }
}
