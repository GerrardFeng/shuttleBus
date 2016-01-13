package com.example.fengge.shuttlebus;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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
import com.utils.SharePreferenceHelper;

import org.apache.http.Header;
import org.json.JSONObject;

public class LoginActivity extends Activity {

    private EditText userEdit;
    private EditText pwdEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        userEdit = (EditText)findViewById(R.id.account_userName);
        pwdEdit = (EditText)findViewById(R.id.account__pwd);
        Button loginBtn = (Button)findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new loginButtonListener());
    }


    class loginButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String domainid = userEdit.getText().toString();
            String pwd = pwdEdit.getText().toString();

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put(ShuttleConstants.ACCOUNT_DOAMINID, domainid);
            params.put(ShuttleConstants.ACCOUNT_PWD, pwd);
            HttpUtil.post(ShuttleConstants.URL_LOGIN, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                    super.onSuccess(statusCode, headers, object);

                    LoginAuthenticationResult currentUser = FastJasonTools.getParseBean(object.toString(), LoginAuthenticationResult.class);
                    SharePreferenceHelper.saveUser(LoginActivity.this, currentUser.getUser());
                    if (ShuttleConstants.LOGIN_SUCCESS.equalsIgnoreCase(currentUser.getStatus())) {
                        Intent loginSuceed = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(loginSuceed);
                    } else {
                        Toast accountError = Toast.makeText(LoginActivity.this, R.string.account_error, Toast.LENGTH_SHORT);
                        accountError.show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);

//                    if (statusCode >= ShuttleConstants.HTTP_STATUS_500) {
                        Toast failureTips = Toast.makeText(LoginActivity.this, R.string.server_not_avaiable, Toast.LENGTH_SHORT);
                        failureTips.show();
//                    }
                }
            });
        }
    }


}
