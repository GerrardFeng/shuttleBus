package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
//        showDate(year, month + 1, day);
        List<HashMap<String, Object>> dateData = initDateData();
        initModifyDateListViewEvent(dateData);

        List<HashMap<String, Object>> searchData = initSearchData();
        initSearchListViewEvent(searchData);
    }

    private List<HashMap<String, Object>> initSearchData() {
        List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        for(int i=0; i<5; i++) {
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("route", "上班路线：10");
            item.put("vacant_site", "剩余空位：50");
            dataList.add(item);
        }
        return dataList;
    }

    private void initSearchListViewEvent(List<HashMap<String, Object>> searchData) {
        ListView listView = (ListView) this.findViewById(R.id.searchListView);
        SimpleAdapter adapter = new SimpleAdapter(this, searchData, R.layout.search_item, new String[] { "route", "vacant_site"},
                new int[] { R.id.route_type, R.id.vacant_site});
        listView.setAdapter(adapter);
    }

    private List<HashMap<String, Object>> initDateData() {
        List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> item = new HashMap<String, Object>();
        item.put("dateIcon", R.drawable.date);
        item.put("dateTime", (new StringBuilder().append(year).append(".").append(month).append(".").append(day)).toString());
        item.put("arrowIcon", R.drawable.arrow);
        dataList.add(item);
        return dataList;
    }

    private void initModifyDateListViewEvent(List<HashMap<String, Object>> dateData) {
        ListView listView = (ListView) this.findViewById(R.id.modify_date_time_bt);
        SimpleAdapter adapter = new SimpleAdapter(this, dateData, R.layout.modify_date_item, new String[] { "dateIcon", "dateTime", "arrowIcon" },
                new int[] { R.id.route1Icon, R.id.modify_date_time, R.id.search_arrowIcon});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ItemClickListener());
    }

    private final class ItemClickListener implements OnItemClickListener {

        @SuppressWarnings("unchecked")
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView listView = (ListView) parent;
            HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
            setDate(view);
        }
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
//            showDate(arg1, arg2 + 1, arg3);
            List<HashMap<String, Object>> dateData = initDateData();
            initModifyDateListViewEvent(dateData);

        }
    };

}
