package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.ShuttleConstants;
import com.example.dto.LoginAuthenticationResult;
import com.example.dto.RouteInfo;
import com.example.jason.FastJasonTools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.utils.HttpUtil;
import com.utils.PropertiesUtil;
import com.utils.SharePreferenceHelper;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends BaseActivity {

    private Calendar calendar;
    private int year, month, day;
    private List<RouteInfo> currentRouteList;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        currentRouteList = new ArrayList<RouteInfo>();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        initProgressDialog();

        List<HashMap<String, Object>> dateData = initDateData(year, month, day);
        initModifyDateListViewEvent(dateData);
        List<HashMap<String, Object>> searchData = initSearchData(currentRouteList);
        initSearchListViewEvent(searchData);
        getBusVacancy (year, month, day);
    }

    private void initProgressDialog(){
        mProgress = new ProgressDialog(SearchActivity.this);
        mProgress.setTitle(R.string.check_loading);
        mProgress.setMessage(getResources().getString(R.string.please_wait));
    }

    private List<HashMap<String, Object>> initSearchData(List<RouteInfo> currentRouteList) {
        List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        if(currentRouteList.size() > 0){
            for(RouteInfo routeInfo : currentRouteList) {
                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("route", "上班路线：" + routeInfo.getId());
                item.put("vacant_site", "剩余空位：" + routeInfo.getBalance());
                dataList.add(item);
            }
        }
        return dataList;
    }

    private void initSearchListViewEvent(List<HashMap<String, Object>> searchData) {
        ListView listView = (ListView) this.findViewById(R.id.searchListView);
        SimpleAdapter adapter = new SimpleAdapter(this, searchData, R.layout.search_item, new String[] { "route", "vacant_site"},
                new int[] { R.id.route_type, R.id.vacant_site});
        listView.setAdapter(adapter);
    }

    private List<HashMap<String, Object>> initDateData(int year, int month, int day) {
        List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> item = new HashMap<String, Object>();
        item.put("dateIcon", R.drawable.date);
        item.put("name", SourceType.TIME.getName());
        item.put("dateTime", (new StringBuilder().append(year).append(".").append(month).append(".").append(day)).toString());
        item.put("arrowIcon", R.drawable.arrow);
        dataList.add(item);
        return dataList;
    }

    private void initModifyDateListViewEvent(List<HashMap<String, Object>> dateData) {
        ListView listView = (ListView) this.findViewById(R.id.modify_date_time_bt);
        SimpleAdapter adapter = new SimpleAdapter(this, dateData, R.layout.modify_date_item, new String[] { "dateIcon", "name", "dateTime", "arrowIcon" },
                new int[] { R.id.route1Icon, R.id.name, R.id.modify_date_time, R.id.search_arrowIcon});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ItemClickListener());
    }

    private final class ItemClickListener implements OnItemClickListener {

        @SuppressWarnings("unchecked")
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView listView = (ListView) parent;
            HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);

            new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker dateView, int currentYear, int currentMonth, int currentDay) {
                    List<HashMap<String, Object>> dateData = initDateData(currentYear, currentMonth + 1, currentDay);
                    initModifyDateListViewEvent(dateData);
                    getBusVacancy(currentYear, currentMonth + 1, currentDay);
                    // TODO refresh data

                }
            },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) ).show();


        }
    }

    private void getBusVacancy (int year, int month, int day) {
        String searchDate;
        if(month > 9){
            searchDate =  year + "-" + month + "-" + day;
        }else{
            searchDate =  year + "-0" + month + "-" + day;
        }

        RequestParams params = new RequestParams();
        params.put(ShuttleConstants.SEARCH_DATE, searchDate);
        mProgress.show();
        HttpUtil.get(PropertiesUtil.getPropertiesURL(SearchActivity.this, ShuttleConstants.URL_BUS_VACANCY), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray object) {
                super.onSuccess(statusCode, headers, object);
                Log.v("SearchActivity", object.toString());
                currentRouteList = FastJasonTools.getParseBeanArray(object.toString(), RouteInfo.class);
                List<HashMap<String, Object>> searchData = initSearchData(currentRouteList);
                initSearchListViewEvent(searchData);
                mProgress.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(SearchActivity.this, R.string.server_not_avaiable, Toast.LENGTH_LONG).show();
                mProgress.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(SearchActivity.this, R.string.server_not_avaiable, Toast.LENGTH_LONG).show();
            }
        });
    }

}
