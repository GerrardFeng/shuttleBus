package com.example.fengge.shuttlebus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.ShuttleConstants;
import com.example.dto.BusUser;
import com.example.jason.FastJasonTools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.utils.HttpUtil;
import com.utils.PropertiesUtil;
import com.utils.SharePreferenceHelper;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by WURY on 1/12/2016.
 */
public class UserInfoActivity extends BaseActivity {

    private int selectedUserType = 0;
    private ListView userTypeListView;
    private ListView ticketListView;
    private ListView logoutListView;
    SimpleAdapter userTypeAdapter;
    SimpleAdapter ticketAdapter;
    SimpleAdapter logoutAdapter;
    private List<HashMap<String, Object>> userTypeList = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> ticketList = new ArrayList<HashMap<String, Object>>();
    private Dialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initData();
        initView();
        initEvent();
        String[] array={"退出"};
        AppAdapter adapter=new AppAdapter(this,array);
        logoutListView.setAdapter(adapter);
    }

    private void initData() {
        HashMap<String, Object> item = new HashMap<String, Object>();
        item.put("user_type", "用户类型");
        item.put("arrow_icon", R.drawable.arrow);
        userTypeList.add(item);

        HashMap<String, Object> item1 = new HashMap<String, Object>();
        item1.put("ticket_type", "乘车凭证");
        item1.put("arrow_icon", R.drawable.arrow);
        ticketList.add(item1);
    }

    private void initView() {
        userTypeListView = (ListView) this.findViewById(R.id.userType);
        ticketListView = (ListView) this.findViewById(R.id.showTickets);
        logoutListView = (ListView) this.findViewById(R.id.userLogout);

        userTypeAdapter = new SimpleAdapter(this, userTypeList, R.layout.activity_userinfo_list_item, new String[]{"user_type", "arrow_icon"},
                new int[]{R.id.name, R.id.arrow_icon});
        userTypeListView.setAdapter(userTypeAdapter);

        ticketAdapter = new SimpleAdapter(this, ticketList, R.layout.activity_userinfo_list_item, new String[]{"ticket_type","arrow_icon"},
                new int[]{R.id.name, R.id.arrow_icon});
        ticketListView.setAdapter(ticketAdapter);

    }

    class AppAdapter extends BaseAdapter {
        Context context;
        String[] array;
        AppAdapter(Context context,String[] array){
            this.context=context;
            this.array=array;

        }
        public int getCount() {
            return array.length;
        }

        public Object getItem(int position) {
            return array[position];
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null) {
                LayoutInflater inflater=LayoutInflater.from(context);
                convertView=inflater.inflate(R.layout.activity_userinfo_list_item, null);
            }
            TextView t_0=(TextView)convertView.findViewById(R.id.name);
            t_0.setText(array[position]);
            t_0.setTextColor(Color.RED);
            return convertView;
        }

    }


    private void initEvent() {
        userTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
        });
        ticketListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
                Intent ticketsIntent = new Intent(UserInfoActivity.this, TicketListActivity.class);
                startActivity(ticketsIntent);
            }
        });
        logoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
                SharePreferenceHelper.setAutoLogin(UserInfoActivity.this, false);
                ActivityCollector.finishAll();
            }
        });

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


}