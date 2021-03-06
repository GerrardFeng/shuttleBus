package com.example.fengge.shuttlebus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.ShuttleConstants;
import com.example.dto.RouteInfo;
import com.example.dto.Station;
import com.example.jason.FastJasonTools;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.utils.HttpUtil;
import com.utils.PropertiesUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by GUOFR2 on 1/13/2016.
 */
public class BookingTicketListActivity extends BaseActivity {

    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private ListView listView;
    private TextView textView;
    private TextView backTextView;


    private Intent intent;
    private String sourceType;
    private String dutyType;

    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_ticket_list);
        initData();
    }


    private void initData() {
        intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        sourceType = bundle.getString("sourceType");
        dutyType = bundle.getString("selectDutyType");

        if (SourceType.ROUTE.getName().equals(sourceType)) {
            String stationId = (String) bundle.get(ShuttleConstants.STATION_ID);
            if(stationId == null || stationId.isEmpty()){
                initView();
                initEvent();
            } else {
                initRouteList(stationId);
            }
        }
        if (SourceType.STOP.getName().equals(sourceType)) {
            initStationList();
        }
    }

    private void initRouteList(String stationId) {
        RequestParams params = new RequestParams();
        params.put("stationid", stationId);
        HttpUtil.get(PropertiesUtil.getPropertiesURL(BookingTicketListActivity.this, ShuttleConstants.URL_GET_ROUTE), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                super.onSuccess(statusCode, headers, array);
                buidRouteDatalist(array.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray array) {
                super.onFailure(statusCode, headers, throwable, array);
                initView();
            }
        });
    }

    private void buidRouteDatalist(String str) {
        List<RouteInfo> routes = FastJasonTools.getParseBeanArray(str, RouteInfo.class);
        buildListFromRoutes(routes);
        initView();
        initEvent();
        if(list.size() == 0) {
            showTips(BookingTicketListActivity.this, R.string.sorry_no_station, false);
        }
    }

    private void initStationList() {
//        String url = "http://chenja17-w7.corp.oocl.com:8088/sba-webapp/api/mobile/getallstations";
        final List<Station> stations = new ArrayList<Station>();
        HttpUtil.get(PropertiesUtil.getPropertiesURL(BookingTicketListActivity.this, ShuttleConstants.URL_GET_ALL_STATION), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray object) {

                for (int i = 0; i < object.length(); i++) {
                    Station station = new Station();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = (JSONObject) object.get(i);
                        station.setId((int) jsonObject.get("id"));

                        station.setName(jsonObject.get("name").toString());
                        stations.add(station);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                buildListFromStations(stations);
                initView();
                initEvent();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                initView();
            }

        });
    }

    private void buildListFromStations(List<Station> stations) {
        for (Station station : stations) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("route_icon", R.drawable.stop);
            map.put("id", station.getId());
            map.put("name", station.getName());
            list.add(map);
        }
    }

    private void buildListFromRoutes(List<RouteInfo> routes) {
        list = new ArrayList<HashMap<String, Object>>();
        for (RouteInfo route : routes) {
            if(dutyType.equalsIgnoreCase(route.getType())){
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("route_icon", R.drawable.route);
                map.put("id", route.getId());
                map.put("name", route.getName());
                list.add(map);
            }
        }
    }


    private void initView() {
        textView = (TextView) this.findViewById(R.id.title);
        textView.setText(sourceType);
        listView = (ListView) this.findViewById(R.id.route_list_view);
        adapter = new SimpleAdapter(this, list, R.layout.booking_list_item, new String[]{"route_icon", "name"
        }, new int[]{R.id.route_icon, R.id.name});
        listView.setAdapter(adapter);

        backTextView = (TextView) this.findViewById(R.id.back);
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
                intent.putExtra(ShuttleConstants.SOURCE_TYPE, sourceType);
                intent.putExtra("name", name);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
