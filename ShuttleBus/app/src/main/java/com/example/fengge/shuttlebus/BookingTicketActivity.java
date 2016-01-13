package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ShuttleConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by GUOFR2 on 1/13/2016.
 */
public class BookingTicketActivity extends Activity {

    private Button onDutyButton;
    private Button offDutyButton;
    private ListView routeListView;
    private ListView timeListView;
    private View chooseView;

    SimpleAdapter timeAdapter;

    private String selectStationId;

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
        item.put("route_icon", R.drawable.stop);
        item.put("name", SourceType.STOP.getName());
        item.put("value", "");
        item.put("arrow_icon", R.drawable.arrow);
        routeList.add(item);

        // 路线
        HashMap<String, Object> item1 = new HashMap<String, Object>();
        item1.put("route_icon", R.drawable.route);
        item1.put("name", SourceType.ROUTE.getName());
        item1.put("value", "");
        item1.put("arrow_icon", R.drawable.arrow);
        routeList.add(item1);

        // 时间
        HashMap<String, Object> item2 = new HashMap<String, Object>();
        item2.put("route_icon", R.drawable.date);
        item2.put("name", SourceType.TIME.getName());
        Calendar cal = Calendar.getInstance();
        item2.put("value", pingDateString(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));
        item2.put("arrow_icon", R.drawable.arrow);
        timeList.add(item2);
    }

    private void initView() {
        onDutyButton = (Button) this.findViewById(R.id.on_duty);
        offDutyButton = (Button) this.findViewById(R.id.off_duty);
        clickOnDutyButton();
        routeListView = (ListView) this.findViewById(R.id.route_list_view);
        timeListView = (ListView) this.findViewById(R.id.time_list_view);
        SimpleAdapter adapter = new SimpleAdapter(this, routeList, R.layout.booking_list_item, new String[]{"route_icon", "name", "value",
                "arrow_icon"}, new int[]{R.id.route_icon, R.id.name, R.id.value, R.id.arrow_icon});
        routeListView.setAdapter(adapter);

        timeAdapter = new SimpleAdapter(this, timeList, R.layout.booking_list_item, new String[]{"route_icon", "name", "value",
                "arrow_icon"}, new int[]{R.id.route_icon, R.id.name, R.id.value, R.id.arrow_icon});
        timeListView.setAdapter(timeAdapter);
    }

    private void initEvent() {
        onDutyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnDutyButton();
            }
        });
        offDutyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOffDutyButton();
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
                HashMap<String, String> dateMap = (HashMap<String, String>)routeListView.getItemAtPosition(0);
                bundle.putString(ShuttleConstants.STATION_ID, selectStationId);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
        timeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
                Log.v("hustzw", "onDateSet");
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(BookingTicketActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Log.v("Hustzw", year + "");
                        setSelectDate(year, monthOfYear + 1, dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.setTitle(R.string.date_picker_title);
                dialog.show();
            }
        });
    }

    private void setSelectDate(int year, int monthOfYear, int dayOfMonth) {
        HashMap<String, String> dateMap = (HashMap<String, String>) timeListView.getItemAtPosition(0);
        dateMap.put("value", pingDateString(year, monthOfYear, dayOfMonth));
        timeAdapter.notifyDataSetChanged();
    }

    private String pingDateString(int year, int monthOfYear, int dayOfMonth) {
        return new StringBuffer("").append(year).append("/").append(monthOfYear).append("/").append(dayOfMonth).toString();
    }

    private void clickOnDutyButton() {
        onDutyButton.setTextColor(Color.WHITE);
        onDutyButton.setBackgroundColor(Color.GRAY);
        offDutyButton.setTextColor(Color.BLACK);
        offDutyButton.setBackgroundColor(Color.WHITE);
    }

    private void clickOffDutyButton() {
        offDutyButton.setTextColor(Color.WHITE);
        offDutyButton.setBackgroundColor(Color.GRAY);
        onDutyButton.setTextColor(Color.BLACK);
        onDutyButton.setBackgroundColor(Color.WHITE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String resouceType = bundle.getString(ShuttleConstants.SOURCE_TYPE);
            if (SourceType.STOP.toString().equalsIgnoreCase(resouceType)) {
                HashMap<String, String> routeMap = (HashMap<String, String>)routeListView.getItemAtPosition(1);
                routeMap.put(ShuttleConstants.ROUTE_VALUE, "");
            }
            String id = bundle.getString("id");
            selectStationId = id;
            String name = bundle.getString("name");
            TextView textView = (TextView) chooseView.findViewById(R.id.value);
            textView.setText(name);
            Toast.makeText(BookingTicketActivity.this, "id:" + id + ", name:" + name, Toast.LENGTH_SHORT).show();
        }
    }


}
