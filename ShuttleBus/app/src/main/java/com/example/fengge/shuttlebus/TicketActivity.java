package com.example.fengge.shuttlebus;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.example.ShuttleConstants;
import com.example.dto.BusUser;
import com.example.dto.CommonResult;
import com.example.dto.TicketResult;
import com.example.jason.FastJasonTools;
import com.gpstracker.GPSTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.utils.CustomProgressDialog;
import com.utils.HttpUtil;
import com.utils.PropertiesUtil;
import com.utils.SharePreferenceHelper;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.fengge.shuttlebus.R.color.orange;

/**
 * Created by WURY on 1/12/2016.
 */
public class TicketActivity extends BaseActivity {

    private Long ticketId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getTicket();
        final CircularProgressButton circularButton1 = (CircularProgressButton) findViewById(R.id.checkInButton);

        circularButton1.setIndeterminateProgressMode(true);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton1.getProgress() == 0) {
                    doCheckIn(circularButton1);
                }
            }
        });
    }

    private void doCheckIn (final CircularProgressButton circularButton1) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        GPSTracker gpsTracker = new GPSTracker(TicketActivity.this);
        gpsTracker.getLocation();
        //TODO getValue
        params.put(ShuttleConstants.TICKET_ID, ticketId);
        params.put(ShuttleConstants.LONGITUDE, gpsTracker.getLongitude());
        params.put(ShuttleConstants.LATITUDE, gpsTracker.getLatitude());
        circularButton1.setProgress(50);

        HttpUtil.post(PropertiesUtil.getPropertiesURL(TicketActivity.this, ShuttleConstants.URL_CHECK_IN), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                super.onSuccess(statusCode, headers, object);

                Log.v("HUSZW", object.toString());
                CommonResult result = FastJasonTools.getParseBean(object.toString(), CommonResult.class);
                if (ShuttleConstants.LOGIN_SUCCESS.equalsIgnoreCase(result.getStatus())){
                    if (circularButton1.getProgress() == 50) {
                        circularButton1.setProgress(100);
                    }
                } else {
                    showTips(TicketActivity.this, R.string.has_not_ticket, false);
                    circularButton1.setProgress(0);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                showTips(TicketActivity.this, R.string.server_not_avaiable, false);
                circularButton1.setProgress(0);
            }
        });
    }


    private void getTicket () {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        BusUser busUser = SharePreferenceHelper.getUser(TicketActivity.this);

        final CustomProgressDialog progressDialog = new CustomProgressDialog(TicketActivity.this);
        progressDialog.show();

        params.put(ShuttleConstants.USER_ID, busUser.getId());

        HttpUtil.get(PropertiesUtil.getPropertiesURL(TicketActivity.this, ShuttleConstants.SHOW_TICKET), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                super.onSuccess(statusCode, headers, object);
                if (object != null) {
                    TicketResult ticketResult = FastJasonTools.getParseBean(object.toString(), TicketResult.class);
                    renderTicket(ticketResult);
                } else {
                    showTips(TicketActivity.this, R.string.has_not_ticket, false);
                }
                Log.v("HUSTZW", "TicketActivity -onSuccess");
                progressDialog.hide();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(statusCode == 200){
                    showTips(TicketActivity.this, R.string.has_not_ticket, false);
                }else{
                    showTips(TicketActivity.this, R.string.server_not_avaiable, false);
                }
                Log.v("HUSTZW", "TicketActivity -onFailure 1");
                progressDialog.hide();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if(statusCode == 200){
                    showTips(TicketActivity.this, R.string.has_not_ticket, false);
                }else{
                    showTips(TicketActivity.this, R.string.server_not_avaiable, false);
                }
                Log.v("HUSTZW", "TicketActivity -onFailure 3");
                progressDialog.hide();
            }
        });
    }
    private void renderTicket(TicketResult ticketResult){
        TextView routeOfFirstTicket = (TextView)findViewById(R.id.routeOfFirstTicket);
        routeOfFirstTicket.setText("路线 : " + (ShuttleConstants.ON_DUTY.equals(ticketResult.getRouteType()) ? "上班" : "下班"));
        TextView routeNumber = (TextView)findViewById(R.id.bus_number_icon);
        routeNumber.setText(ticketResult.getRoute());
        if(ShuttleConstants.OFF_DUTY.equals(ticketResult.getRouteType())){
            routeNumber.setTextColor(Color.BLACK);
        }
        TextView stopOfFirstTicket = (TextView)findViewById(R.id.stopOfFirstTicket);
        stopOfFirstTicket.setText("站点 : " + ticketResult.getStation());
        TextView typeOfFirstTicket = (TextView)findViewById(R.id.typeOfFirstTicket);
        typeOfFirstTicket.setText("类型 : " + ticketResult.getUserType());
        ticketId = ticketResult.getTicketId();
    }

}
