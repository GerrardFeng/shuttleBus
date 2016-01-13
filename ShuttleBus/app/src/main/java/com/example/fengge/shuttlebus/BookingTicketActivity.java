package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by GUOFR2 on 1/13/2016.
 */
public class BookingTicketActivity extends Activity{

    private Button onDutyButton;
    private Button offDutyButton;
    private ListView routeListView;
    private ListView timeListView;
    private View chooseView;

    private List<HashMap<String, Object>> routeList = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> timeList = new ArrayList<HashMap<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_ticket);
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        // 站点
        HashMap<String, Object> item = new HashMap<String, Object>();
        item.put("route_icon", R.drawable.bus_icon);
        item.put("name", SourceType.STOP.getName());
        item.put("value", "");
        item.put("arrow_icon", R.drawable.bus_icon);
        routeList.add(item);

        // 路线
        HashMap<String, Object> item1 = new HashMap<String, Object>();
        item1.put("route_icon", R.drawable.bus_icon);
        item1.put("name", SourceType.ROUTE.getName());
        item1.put("value", "");
        item1.put("arrow_icon", R.drawable.bus_icon);
        routeList.add(item1);

        // 时间
        HashMap<String, Object> item2 = new HashMap<String, Object>();
        item2.put("route_icon", R.drawable.bus_icon);
        item2.put("name", SourceType.TIME.getName());
        item2.put("value", new Date());
        item2.put("arrow_icon", R.drawable.bus_icon);
        timeList.add(item2);
    }

    private void initView() {
        onDutyButton = (Button) this.findViewById(R.id.on_duty);
        offDutyButton = (Button) this.findViewById(R.id.off_duty);
        routeListView = (ListView) this.findViewById(R.id.route_list_view);
        timeListView = (ListView) this.findViewById(R.id.time_list_view);
        SimpleAdapter adapter = new SimpleAdapter(this, routeList, R.layout.booking_list_item, new String[] { "route_icon", "name", "value",
                "arrow_icon" }, new int[] { R.id.route_icon , R.id.name,  R.id.value, R.id.arrow_icon });
        routeListView.setAdapter(adapter);

        SimpleAdapter timeAdapter = new SimpleAdapter(this, timeList, R.layout.booking_list_item, new String[] { "route_icon", "name", "value",
                "arrow_icon" }, new int[] { R.id.route_icon , R.id.name,  R.id.value, R.id.arrow_icon });
        timeListView.setAdapter(timeAdapter);
    }

    private void initEvent() {
        onDutyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDutyButton.setTextColor(getResources().getColor(R.color.bg_color));
                onDutyButton.setBackgroundColor(getResources().getColor(R.color.blood_dark));
            }
        });
        offDutyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offDutyButton.setTextColor(getResources().getColor(R.color.blood_dark));
                offDutyButton.setBackgroundColor(getResources().getColor(R.color.bg_color));
            }
        });
        routeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseView = view;
                ListView listView = (ListView) parent;
                HashMap<String, Object> map = (HashMap<String, Object>) listView.getItemAtPosition(position);
                String sourceType = String.valueOf(map.get("name"));
                Intent intent = new Intent(BookingTicketActivity.this, BookingTicketListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("sourceType", sourceType);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
        timeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String id = bundle.getString("id");
            String name = bundle.getString("name");
            TextView textView = (TextView) chooseView.findViewById(R.id.value);
            textView.setText(name);
            Toast.makeText(BookingTicketActivity.this, "id:" + id + ", name:" + name, Toast.LENGTH_SHORT).show();
        }
    }


}
