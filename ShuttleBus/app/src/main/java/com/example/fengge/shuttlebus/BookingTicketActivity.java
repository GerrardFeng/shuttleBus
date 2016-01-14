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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ShuttleConstants;
import com.google.zxing.common.StringUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.utils.HttpUtil;
import com.utils.PropertiesUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

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
    private RadioGroup radioGroup;
    private RadioButton temporaryRadioButton;
    private Button genTicketButton;
    private View selectView;

    SimpleAdapter timeAdapter;
    SimpleAdapter routeAdapter;

    private String selectStationId;
    private String selectRouteId;
    private String selectDate;
    private String selectTicketType;
    private String selectDutyType;

    private List<HashMap<String, Object>> routeList = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> timeList = new ArrayList<HashMap<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_ticket);
        initData();
        initView();
        initEvent();
        clickOnDutyButton();
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
        item2.put("value", parseDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));
        item2.put("arrow_icon", R.drawable.arrow);
        timeList.add(item2);
    }

    private void initView() {
        onDutyButton = (Button) this.findViewById(R.id.on_duty);
        offDutyButton = (Button) this.findViewById(R.id.off_duty);
        routeListView = (ListView) this.findViewById(R.id.route_list_view);
        timeListView = (ListView) this.findViewById(R.id.time_list_view);
        routeAdapter = new SimpleAdapter(this, routeList, R.layout.booking_list_item, new String[]{"route_icon", "name", "value",
                "arrow_icon"}, new int[]{R.id.route_icon, R.id.name, R.id.value, R.id.arrow_icon});
        routeListView.setAdapter(routeAdapter);

        timeAdapter = new SimpleAdapter(this, timeList, R.layout.booking_list_item, new String[]{"route_icon", "name", "value",
                "arrow_icon"}, new int[]{R.id.route_icon, R.id.name, R.id.value, R.id.arrow_icon});
        timeListView.setAdapter(timeAdapter);

        radioGroup = (RadioGroup) this.findViewById(R.id.ticket_type);
        temporaryRadioButton = (RadioButton) this.findViewById(R.id.temporary);
        genTicketButton = (Button) this.findViewById(R.id.gen_ticket);
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
                selectView = view;
                ListView listView = (ListView) parent;
                HashMap<String, Object> map = (HashMap<String, Object>) listView.getItemAtPosition(position);
                String sourceType = String.valueOf(map.get("name"));
                Intent intent = new Intent(BookingTicketActivity.this, BookingTicketListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("sourceType", sourceType);
                bundle.putString(ShuttleConstants.STATION_ID, selectStationId);
                bundle.putString("selectDutyType", selectDutyType);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
        timeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {

                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(BookingTicketActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        HashMap<String, String> dateMap = (HashMap<String, String>) timeListView.getItemAtPosition(0);
                        dateMap.put("value", parseDate(year, monthOfYear + 1, dayOfMonth));
                        timeAdapter.notifyDataSetChanged();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.setTitle(R.string.date_picker_title);
                dialog.show();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.regular) {
                    timeListView.setVisibility(View.INVISIBLE);
                    selectTicketType = ShuttleConstants.TICKET_TYPE_LONGTIME;
                } else {
                    timeListView.setVisibility(View.VISIBLE);
                    selectTicketType = ShuttleConstants.TICKET_TYPE_TEMP;
                }
            }
        });
        genTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = validationData();
                if (result) {
                    submitTicket();
                } else {
                    Toast.makeText(getApplicationContext(), "请填入所有信息!", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void submitTicket() {
        RequestParams params = new RequestParams();
        params.put("userid", 4); // TODO
        params.put("routeid", Integer.valueOf(selectRouteId));
        params.put("stationid", Integer.valueOf(selectStationId));
        params.put("type", selectTicketType);
        params.put("ridingdate", selectDate);

        HttpUtil.post(PropertiesUtil.getPropertiesURL(BookingTicketActivity.this, ShuttleConstants.URL_GEN_TICKET), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                super.onSuccess(statusCode, headers, object);
                resetBookingTicketData();
                Toast.makeText(getApplicationContext(), "成功了!", Toast.LENGTH_LONG);

                Intent intent = new Intent(BookingTicketActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                super.onFailure(statusCode, headers, throwable, object);
                Toast.makeText(getApplicationContext(), "失败了!", Toast.LENGTH_LONG);
            }
        });

    }

    private boolean validationData() {
        if (selectStationId == null || selectStationId.isEmpty()) {
            return false;
        }
        if (selectRouteId == null || selectRouteId.isEmpty()) {
            return false;
        }
        return true;
    }

    private String parseDate(int year, int monthOfYear, int dayOfMonth) {
        selectDate = new StringBuffer("").append(year).append("-").append(monthOfYear).append("-").append(dayOfMonth).toString();
        return selectDate;
    }

    private void clickOnDutyButton() {
        onDutyButton.setTextColor(Color.WHITE);
        onDutyButton.setBackgroundColor(Color.GRAY);
        offDutyButton.setTextColor(Color.BLACK);
        offDutyButton.setBackgroundColor(Color.WHITE);
        resetBookingTicketData();
        selectDutyType = ShuttleConstants.ON_DUTY;
    }

    private void clickOffDutyButton() {
        offDutyButton.setTextColor(Color.WHITE);
        offDutyButton.setBackgroundColor(Color.GRAY);
        onDutyButton.setTextColor(Color.BLACK);
        onDutyButton.setBackgroundColor(Color.WHITE);
        resetBookingTicketData();
        selectDutyType = ShuttleConstants.OFF_DUTY;
    }

    private void resetBookingTicketData() {
        selectRouteId = "";
        selectStationId = "";
        for (int i = 0;i < routeListView.getChildCount(); i++) {
            View view = routeListView.getChildAt(i);
            TextView textView = (TextView) view.findViewById(R.id.value);
            textView.setText("");
        }
        Calendar calendar = Calendar.getInstance();
        HashMap<String, String> dateMap = (HashMap<String, String>) timeListView.getItemAtPosition(0);
        dateMap.put("value", parseDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));
        timeAdapter.notifyDataSetChanged();
        temporaryRadioButton.setChecked(true);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String resouceType = bundle.getString(ShuttleConstants.SOURCE_TYPE);
            if (SourceType.STOP.getName().equalsIgnoreCase(resouceType)) {
                handleSelectStation(bundle);
            }
            if (SourceType.ROUTE.getName().equalsIgnoreCase(resouceType)) {
                handleSelectRoute(bundle);
            }
            String name = bundle.getString("name");
            TextView textView = (TextView) selectView.findViewById(R.id.value);
            textView.setText(name);
        }
    }

    private void handleSelectStation(Bundle bundle) {
        selectStationId = bundle.getString("id");
        selectRouteId = "";
        View view = routeListView.getChildAt(1);
        TextView textView = (TextView) view.findViewById(R.id.value);
        textView.setText("");
    }

    private void handleSelectRoute(Bundle bundle) {
        selectRouteId = bundle.getString("id");
    }


}
