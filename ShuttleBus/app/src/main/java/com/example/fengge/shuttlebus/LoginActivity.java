package com.example.fengge.shuttlebus;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    private EditText userEdit;
    private EditText pwdEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userEdit = (EditText)findViewById(R.id.account_userName);
        pwdEdit = (EditText)findViewById(R.id.account__pwd);
        Button loginBtn = (Button)findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new loginButtonListener());
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

    class loginButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String userName = userEdit.getText().toString();
            String pwd = pwdEdit.getText().toString();
            Log.v("zw", "click login");
            Intent loginSuceed = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(loginSuceed);


        }
    }


}
