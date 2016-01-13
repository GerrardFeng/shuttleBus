package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.SimpleAdapter;
import java.util.Calendar;

public class SearchActivity extends Activity {

    private Button dateSelectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dateSelectBtn = (Button)findViewById(R.id.select_date_btn);
        dateSelectBtn.setOnClickListener(new DateSelectListener());
        Calendar calendar = Calendar.getInstance();
        dateSelectBtn.setText(getDateString(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)));


        ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.expandableListView);
        ExpandableAdapter adapter = new ExpandableAdapter(SearchActivity.this);

        expandableListView.setAdapter(adapter);

    }


    class DateSelectListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    dateSelectBtn.setText(getDateString(year, monthOfYear, dayOfMonth + 1));

                    refreshDataForListView();

                }
            },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) ).show();

        }
    }


    private String getDateString(int year, int month, int day) {
        return new StringBuilder().append(month).append("/").append(day).append("/").append(year).toString();
    }

    private void refreshDataForListView() {

    }




}
