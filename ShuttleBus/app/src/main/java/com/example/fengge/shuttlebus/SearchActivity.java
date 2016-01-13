package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends Activity {

    private Calendar calendar;
    private int year, month, day;
    private EditText dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dateText = (EditText)findViewById(R.id.search_key);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
    }

    private List<HashMap<String, String>> initTicketData() {
        List<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
        for(int i=0; i<30; i++) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("route_type", "上");
            item.put("route_number", "1");
            item.put("vacant_site", "50");
            dataList.add(item);
        }
        return dataList;
    }

    private void initListViewEvent(List<HashMap<String, String>> routData) {
        ListView listView = (ListView) this.findViewById(R.id.searchListView);
        SimpleAdapter adapter = new SimpleAdapter(this, routData, R.layout.search_item, new String[] { "route_type", "route_number",
                "vacant_site" }, new int[] { R.id.route_type, R.id.route_number, R.id.vacant_site });
        listView.setAdapter(adapter);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2 + 1, arg3);
            List<HashMap<String, String>> routeData = initTicketData();
            initListViewEvent(routeData);

        }
    };

    private void showDate(int year, int month, int day) {
        dateText.setText(new StringBuilder().append(month).append("/").append(day).append("/").append(year));
    }

}
