package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.example.ShuttleConstants;
import com.example.dto.BusUser;
import com.example.dto.TicketResult;
import com.example.jason.FastJasonTools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.utils.HttpUtil;
import com.utils.PropertiesUtil;
import com.utils.SharePreferenceHelper;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by WURY on 1/12/2016.
 */
public class TicketActivity extends Activity {

    ProgressDialog mProgress;
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
                    Toast.makeText(getApplicationContext(), "您还没有任何票据信息", Toast.LENGTH_LONG).show();
                    circularButton1.setProgress(50);
                    if (circularButton1.getProgress() == 50) {
                        circularButton1.setProgress(100);
                    }
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void getTicket () {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        BusUser busUser = SharePreferenceHelper.getUser(TicketActivity.this);

        mProgress = new ProgressDialog(TicketActivity.this);
        mProgress.setTitle(R.string.loading_tick);
        mProgress.setMessage(getResources().getString(R.string.please_wait));
        mProgress.show();

        params.put(ShuttleConstants.USER_ID, busUser.getDomainId());
        HttpUtil.get(PropertiesUtil.getPropertiesURL(TicketActivity.this, ShuttleConstants.SHOW_TICKET), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                super.onSuccess(statusCode, headers, object);
                if (object != null) {
                    TicketResult ticketResult = FastJasonTools.getParseBean(object.toString(), TicketResult.class);
                    renderTicket(ticketResult);
                } else {

                }
                mProgress.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                showTips(R.string.server_not_avaiable, false);
                mProgress.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }


        });
    }
    private void renderTicket(TicketResult ticketResult){
        TextView nameOfFirstTicket = (TextView)findViewById(R.id.nameOfFirstTicket);
        nameOfFirstTicket.setText("姓名 ： " + ticketResult.getUsername());
        TextView routeOfFirstTicket = (TextView)findViewById(R.id.routeOfFirstTicket);
        routeOfFirstTicket.setText("路线 : " + ticketResult.getRoute());
        TextView stopOfFirstTicket = (TextView)findViewById(R.id.stopOfFirstTicket);
        stopOfFirstTicket.setText("站点 : " + ticketResult.getStation());
        TextView typeOfFirstTicket = (TextView)findViewById(R.id.typeOfFirstTicket);
        typeOfFirstTicket.setText("类型 : " + ticketResult.getType());
    }
    private void showTips(int msg, boolean isLong) {
        Toast tTips;
        if (isLong) {
            tTips = Toast.makeText(TicketActivity.this, msg, Toast.LENGTH_LONG);
        } else {
            tTips = Toast.makeText(TicketActivity.this, msg, Toast.LENGTH_SHORT);
        }
        tTips.show();
    }

}
