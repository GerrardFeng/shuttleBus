package com.example.fengge.shuttlebus;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.ShuttleConstants;
import com.example.dto.BusUser;
import com.example.dto.LoginAuthenticationResult;
import com.example.jason.FastJasonTools;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.utils.HttpUtil;
import com.utils.PropertiesUtil;
import com.utils.SharePreferenceHelper;

import org.apache.http.Header;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    private EditText userEdit;
    private EditText pwdEdit;
    private ProgressDialog mProgress;


    CheckBox rememberPwdCb;
    CheckBox autoLoginCb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (SharePreferenceHelper.isAutoLogin(LoginActivity.this)) {
            processAutoLogin();
        }
        userEdit = (EditText)findViewById(R.id.account_userName);
        pwdEdit = (EditText)findViewById(R.id.account_pwd);
        rememberPwdCb = ((CheckBox)findViewById(R.id.remember_pwd));
        autoLoginCb = (CheckBox)findViewById(R.id.login_auto);

        if (SharePreferenceHelper.isRememberPwd(LoginActivity.this)) {
            userEdit.setText(SharePreferenceHelper.getDomainid(LoginActivity.this));
            pwdEdit.setText(SharePreferenceHelper.getPwd(LoginActivity.this));
            rememberPwdCb.setChecked(true);
            Log.v("Hustzw", "set user info");
        }
        rememberPwdCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    autoLoginCb.setClickable(true);
                } else {
                    pwdEdit.setText("");
                    autoLoginCb.setChecked(false);
                    autoLoginCb.setClickable(false);
                }
            }
        });
        Button loginBtn = (Button)findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new loginButtonListener());
    }


    class loginButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String domainId = userEdit.getText().toString();
            String pwd = pwdEdit.getText().toString();
            if (isUserInputHasEmpty(domainId, pwd)) {
                showTips(LoginActivity.this, R.string.account_has_empty, false);
                return;
            }
            mProgress = new ProgressDialog(LoginActivity.this);
            mProgress.setTitle(R.string.login_loading);
            mProgress.setMessage(getResources().getString(R.string.please_wait));
            mProgress.show();
            doLogin(domainId, pwd);
            processSettingForLogin();
        }
    }

    private void doLogin (final String domainid, String pwd) {
//        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(ShuttleConstants.ACCOUNT_DOMAINID, domainid);
        params.put(ShuttleConstants.ACCOUNT_PWD, pwd);
        HttpUtil.post(PropertiesUtil.getPropertiesURL(LoginActivity.this, ShuttleConstants.URL_LOGIN), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                super.onSuccess(statusCode, headers, object);
                LoginAuthenticationResult currentUser = FastJasonTools.getParseBean(object.toString(), LoginAuthenticationResult.class);

                if (ShuttleConstants.LOGIN_SUCCESS.equalsIgnoreCase(currentUser.getStatus())) {
                    SharePreferenceHelper.saveUser(LoginActivity.this, currentUser.getUser());
                    SharePreferenceHelper.saveDomainId(LoginActivity.this, domainid);
                    Intent loginSuceed = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(loginSuceed);
                } else {
                    showTips(LoginActivity.this, R.string.account_error, false);
                }
                if (mProgress != null){
                    mProgress.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                showTips(LoginActivity.this, R.string.server_not_avaiable, false);
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

    private void processSettingForLogin() {
        rememberPwdCb = (CheckBox)findViewById(R.id.remember_pwd);
        autoLoginCb = (CheckBox)findViewById(R.id.login_auto);
        if (rememberPwdCb.isChecked()) {
            Log.v("Hustzw", "checked remember pwd");
//TODO　
            SharePreferenceHelper.savePwd(LoginActivity.this, pwdEdit.getText().toString());
            SharePreferenceHelper.setRememberPwd(LoginActivity.this, true);
        } else {
            SharePreferenceHelper.setRememberPwd(LoginActivity.this, false);
        }
        if (autoLoginCb.isChecked()) {
            SharePreferenceHelper.setAutoLogin(LoginActivity.this, true);
            Log.v("Hustzw", "checked autoLogin");
        } else {
            SharePreferenceHelper.setAutoLogin(LoginActivity.this, false);
        }
    }

    private void processAutoLogin () {
        BusUser busUser = SharePreferenceHelper.getUser(LoginActivity.this);
        if(busUser != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Log.v("Hustzw", "autoLogin");
            doLogin(busUser.getDomainId(), SharePreferenceHelper.getPwd(LoginActivity.this));
        } else {
            userEdit = (EditText)findViewById(R.id.account_userName);
            pwdEdit = (EditText)findViewById(R.id.account_pwd);
            rememberPwdCb = (CheckBox)findViewById(R.id.remember_pwd);
            autoLoginCb = (CheckBox)findViewById(R.id.login_auto);
            userEdit.setText(SharePreferenceHelper.getDomainid(LoginActivity.this));
            pwdEdit.setText(SharePreferenceHelper.getPwd(LoginActivity.this));
            rememberPwdCb.setChecked(SharePreferenceHelper.isRememberPwd(LoginActivity.this));
            autoLoginCb.setChecked(SharePreferenceHelper.isAutoLogin(LoginActivity.this));
        }
    }


}
