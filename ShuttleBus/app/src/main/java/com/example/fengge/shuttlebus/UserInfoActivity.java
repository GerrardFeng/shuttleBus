package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ShuttleConstants;
import com.example.dto.BusUser;
import com.example.dto.LoginAuthenticationResult;
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
public class UserInfoActivity extends BaseActivity {

    private int selectedUserType = 0;
    private Dialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button userTypeButton = (Button)findViewById(R.id.userType);
        Button showTickets = (Button)findViewById(R.id.showTickets);
        Button userLogout = (Button)findViewById(R.id.userLogout);

        userTypeButton.setOnClickListener(new userTypeButtonListener());
        showTickets.setOnClickListener(new showTicketsButtonListener());
        userLogout.setOnClickListener(new userLogoutButtonListener());
    }

    class userTypeButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String userId = SharePreferenceHelper.getUser(UserInfoActivity.this).getId();
            Log.v("UserInfoActivity", userId);
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put(ShuttleConstants.USER_ID, userId);
            HttpUtil.get(PropertiesUtil.getPropertiesURL(UserInfoActivity.this, ShuttleConstants.URL_GET_USER_INFO), params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                    super.onSuccess(statusCode, headers, object);
                    BusUser user = FastJasonTools.getParseBean(object.toString(), BusUser.class);
                    Log.v("UserInfoActivity", user.getType());
                    showUserType(user.getType());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
//                    showTips(R.string.server_not_avaiable, false);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
    }

    private void showUserType(String userType) {
        final String[] arrayUserType = new String[] { "普通上下班", "轮班"};
        int index = 0;
        if("shift".equals(userType)) {
            index = 1;
        }

        if(alertDialog == null) {
            Dialog alertDialog = new AlertDialog.Builder(UserInfoActivity.this).
                    setTitle("修改用户类型")
                    .setSingleChoiceItems(arrayUserType, index, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedUserType = which;
                        }
                    }).
                            setPositiveButton("确认", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(UserInfoActivity.this, arrayUserType[selectedUserType], Toast.LENGTH_SHORT).show();
                                    String tpye = "regular";
                                    if (selectedUserType == 1) {
                                        tpye = "shift";
                                    }
                                    Log.v("UserInfoActivity", tpye);
                                    updateUserType(tpye);
                                }
                            }).
                            setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            }).
                            create();
            alertDialog.show();
        }

    }

    private void updateUserType(String userType) {
        String userId = SharePreferenceHelper.getUser(UserInfoActivity.this).getId();
        Log.v("UserInfoActivity", userId);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(ShuttleConstants.USER_ID, userId);
        params.put(ShuttleConstants.USER_TYPE, userType);
        HttpUtil.post(PropertiesUtil.getPropertiesURL(UserInfoActivity.this, ShuttleConstants.URL_UPDATE_USER_TYPE), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                super.onSuccess(statusCode, headers, object);
                Log.v("UserInfoActivity", "Update User Type");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
//                showTips(R.string.server_not_avaiable, false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    class showTicketsButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent ticketsIntent = new Intent(UserInfoActivity.this, TicketListActivity.class);
            startActivity(ticketsIntent);
        }
    }

    class userLogoutButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
//            SharePreferenceHelper.saveUser(UserInfoActivity.this, null);
//            Intent loginActivity = new Intent(UserInfoActivity.this, LoginActivity.class);
//            startActivity(loginActivity);
            SharePreferenceHelper.setAutoLogin(UserInfoActivity.this, false);
            ActivityCollector.finishAll();
        }
    }

}