package com.example.fengge.shuttlebus;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends Activity {

    private EditText userEdit;
    private EditText pwdEdit;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        BusUser busUser = SharePreferenceHelper.getUser(LoginActivity.this);
        if(busUser != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        userEdit = (EditText)findViewById(R.id.account_userName);
        pwdEdit = (EditText)findViewById(R.id.account_pwd);

        Button loginBtn = (Button)findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new loginButtonListener());
    }


    class loginButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String domainId = userEdit.getText().toString();
            String pwd = pwdEdit.getText().toString();
            if (isUserInputHasEmpty(domainId, pwd)) {
                showTips(R.string.account_has_empty, false);
                return;
            }
            mProgress = new ProgressDialog(LoginActivity.this);
            mProgress.setTitle(R.string.login_loading);
            mProgress.setMessage(getResources().getString(R.string.please_wait));
            mProgress.show();
            doLogin(domainId, pwd);
        }
    }

    private void doLogin (String domainid, String pwd) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(ShuttleConstants.ACCOUNT_DOMAINID, domainid);
        params.put(ShuttleConstants.ACCOUNT_PWD, pwd);
        HttpUtil.post(PropertiesUtil.getPropertiesURL(LoginActivity.this, ShuttleConstants.URL_LOGIN), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                super.onSuccess(statusCode, headers, object);
                LoginAuthenticationResult currentUser = FastJasonTools.getParseBean(object.toString(), LoginAuthenticationResult.class);
                SharePreferenceHelper.saveUser(LoginActivity.this, currentUser.getUser());
                if (ShuttleConstants.LOGIN_SUCCESS.equalsIgnoreCase(currentUser.getStatus())) {
                    Intent loginSuceed = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(loginSuceed);
                } else {
                    showTips(R.string.account_error, false);
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

    private boolean isUserInputHasEmpty(String domainid, String pwd) {
        return TextUtils.isEmpty(domainid) || TextUtils.isEmpty(pwd);
    }

    private void showTips(int msg, boolean isLong) {
        Toast tTips;
        if (isLong) {
            tTips = Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG);
        } else {
            tTips = Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT);
        }
        tTips.show();
    }


}
