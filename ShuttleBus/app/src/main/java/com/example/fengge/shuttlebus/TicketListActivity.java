package com.example.fengge.shuttlebus;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ShuttleConstants;
import com.example.dto.TicketResult;
import com.example.jason.FastJasonTools;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MeteoconsModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.fonts.TypiconsModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by WURY on 1/12/2016.
 */
public class TicketListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String userId = SharePreferenceHelper.getUser(TicketListActivity.this).getId();
        Log.v("TicketListActivity",userId);
        Log.v("hustzw", "ONCLICK");
        initIcon();
        getUserTicketForUser(userId);
        List<HashMap<String, String>> ticketData = initTicketData();
    }

    private void getUserTicketForUser(String userId) {
        final CustomProgressDialog progressLogin = new CustomProgressDialog(TicketListActivity.this);
        progressLogin.show();
        RequestParams params = new RequestParams();
        params.put(ShuttleConstants.USER_ID, userId);

        HttpUtil.get(PropertiesUtil.getPropertiesURL(TicketListActivity.this, ShuttleConstants.URL_GET_TICKETS), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.v("hustzw", "coming");
                List<TicketResult> allTickets = FastJasonTools.getParseBeanArray(response.toString(), TicketResult.class);
                initListViewEvent(allTickets);
                progressLogin.hide();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                showTips(TicketListActivity.this, R.string.server_not_avaiable, false);
                progressLogin.hide();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                showTips(TicketListActivity.this, R.string.server_not_avaiable, false);
                progressLogin.hide();
            }
        });
    }


    private void initIcon() {
        ImageButton but = (ImageButton)findViewById(R.id.button2);
        but.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_refresh)
                .colorRes(android.R.color.holo_blue_light)
//                .color(android.R.color.white)//Color.parseColor("#ffffff")
                .actionBarSize());
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = SharePreferenceHelper.getUser(TicketListActivity.this).getId();
                Log.v("Ticket with userId", userId);
                getUserTicketForUser(userId);
            }
        });
        but.invalidate();
    }

    private List<HashMap<String, String>> initTicketData() {
        List<HashMap<String, String>> datas = new ArrayList<HashMap<String, String>>();
        for(int i=0; i<5; i++) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("ticketInfo", "姓名：test" + i + " 线路： test 站点： test 类型： test");
            datas.add(item);
        }
        return datas;
    }


    private void initListViewEvent(List<TicketResult> allTickets) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ticketListLayout);

        linearLayout.removeAllViews();

        for(TicketResult ticket : allTickets) {
            TextView textView1 = new TextView(this);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.setMargins(5, 0, 5, 10);
            textView1.setLayoutParams(llp);
            textView1.setText("路线： " + (ticket.getRouteType() == "on" ? "上班 " : "下班 ") + ticket.getRoute() + "\n" +
                    "站点： " + ticket.getStation() + "\n" +
                    "类型： " + (ticket.getTicketType() == "longtime" ? "长期预定" : ("临时预定 (" + ticket.getTempRidingDate() + ")")));
            textView1.setBackgroundResource(R.drawable.shape);
            linearLayout.addView(textView1);
        }
    }

}
