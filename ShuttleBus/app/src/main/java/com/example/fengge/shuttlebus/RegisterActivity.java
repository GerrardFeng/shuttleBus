package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ZHANGLU6 on 1/8/2016.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

//    private View goToWorkLayout;
//
//    private View afterWorkLayout;
//
//    private RegisterOffFragment registerOffFragment;
//
//    private RegisterOnFragment registerOnFragment;
//
//    private FragmentManager fragmentManager;
//
//    private TextView goToWorkText;
//
//    private TextView afterWorkText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
//        initViews();
//        fragmentManager = getFragmentManager();
//        setTabSelection(1);
//        setTabSelection(0);
//
//
//
//
//
//        Button submitBtn = (Button)findViewById(R.id.submit_regist);
//        submitBtn.setOnClickListener(new submitRegisterListener());


    }

//    private void initViews() {
//        goToWorkLayout = findViewById(R.id.go_work_layout);
//        afterWorkLayout = findViewById(R.id.after_work_layout);
//        goToWorkLayout.setOnClickListener(this);
//        afterWorkLayout.setOnClickListener(this);
//
//        goToWorkText = (TextView) findViewById(R.id.message_text);
//        afterWorkText = (TextView) findViewById(R.id.contacts_text);
//    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.go_work_layout:
//                setTabSelection(0);
//                registerOnStop();
//                break;
//            case R.id.after_work_layout:
//                setTabSelection(1);
//                registerOffStop();
//                break;
//            default:
//                break;
//        }
    }

    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
//        clearSelection();
//        // 开启一个Fragment事务
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
//        hideFragments(transaction);
//        switch (index) {
//            case 0:
//                // 当点击了消息tab时，改变控件的图片和文字颜色
////                messageImage.setImageResource(R.drawable.message_selected);
//                goToWorkText.setTextColor(Color.BLUE);
//                goToWorkText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
//                goToWorkText.getPaint().setAntiAlias(true);//抗锯齿
////                goToWorkText.setBackgroundColor(Color.GREEN);
//                if (registerOnFragment == null) {
//                    // 如果MessageFragment为空，则创建一个并添加到界面上
//                    registerOnFragment = new RegisterOnFragment();
//                    transaction.add(R.id.content, registerOnFragment);
//                } else {
//                    // 如果MessageFragment不为空，则直接将它显示出来
//                    transaction.show(registerOnFragment);
//                    registerOnStop();
//                }
//
//                break;
//            case 1:
//                // 当点击了联系人tab时，改变控件的图片和文字颜色
////                contactsImage.setImageResource(R.drawable.contacts_selected);
//                afterWorkText.setTextColor(Color.BLUE);
//                afterWorkText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
//                afterWorkText.getPaint().setAntiAlias(true);//抗锯齿
////                afterWorkText.setBackgroundColor(Color.GREEN);
//                if (registerOffFragment == null) {
//                    // 如果ContactsFragment为空，则创建一个并添加到界面上
//                    registerOffFragment = new RegisterOffFragment();
//                    transaction.add(R.id.content, registerOffFragment);
//                } else {
//                    // 如果ContactsFragment不为空，则直接将它显示出来
//                    transaction.show(registerOffFragment);
//                }
//                break;
//        }
//        transaction.commit();
    }

    private void clearSelection() {
////        messageImage.setImageResource(R.drawable.message_unselected);
//        goToWorkText.setTextColor(Color.parseColor("#888888"));
//        goToWorkText.getPaint().setFlags(0); //取消下划线
////        goToWorkText.setBackgroundColor(Color.WHITE);
//
////        contactsImage.setImageResource(R.drawable.contacts_unselected);
//        afterWorkText.setTextColor(Color.parseColor("#888888"));
//        afterWorkText.getPaint().setFlags(0); //取消下划线
////        afterWorkText.setBackgroundColor(Color.WHITE);
    }

    private void hideFragments(FragmentTransaction transaction) {
//        if (registerOnFragment != null) {
//            transaction.hide(registerOnFragment);
//        }
//        if (registerOffFragment != null) {
//            transaction.hide(registerOffFragment);
//        }
    }

    class submitRegisterListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
//            registerOffFragment.get

//            Spinner goWorkPoint =  (Spinner)findViewById(R.id.on_location_point);
//            Spinner goWorkLine =  (Spinner)findViewById(R.id.on_line_num);
//
//            String goWorkPontName = goWorkPoint.getSelectedItem().toString();
//            String goWorkLineName = goWorkLine.getSelectedItem().toString();
//
//            TextView goWorkShowText =  (TextView)findViewById(R.id.go_work_text);
//            goWorkShowText.setText("上班: "+ goWorkPontName+"—公司，" + goWorkLineName);
//
//            // 下班
//            Spinner afterWorkPoint =  (Spinner)findViewById(R.id.off_location_point);
//            Spinner afterWorkLine =  (Spinner)findViewById(R.id.off_location_line);
//
//            String afterPointName = afterWorkPoint.getSelectedItem().toString();
//            String afterWorkLineName = afterWorkLine.getSelectedItem().toString();
//
//            TextView afterWorkShowText =  (TextView)findViewById(R.id.after_work_text);
//            afterWorkShowText.setText("下班: "+ "公司—" + afterPointName+ "，" + afterWorkLineName);

        }
    }

    public void registerOffStop(){
//        final Spinner spinerDowntown = (Spinner) findViewById(R.id.off_location_Group);
//        final ArrayAdapter<String> adapterDowntown = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getDowntowns());
////        adapterDowntown.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinerDowntown.setAdapter(adapterDowntown);
//        spinerDowntown.setSelection(0, true);
//
//        final Spinner spinerArea = (Spinner)findViewById(R.id.off_location_region);
//        final ArrayAdapter<String> adapterArea = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getAreas(null));
//        spinerArea.setAdapter(adapterArea);
//        spinerArea.setSelection(0, true);
//
//        final Spinner spinersites = (Spinner)findViewById(R.id.off_location_point);
//        ArrayAdapter<String> adapterSite = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getSites(null,null));
//        spinersites.setAdapter(adapterSite);
//
//        final Spinner spinerlines = (Spinner)findViewById(R.id.off_location_line);
//        ArrayAdapter<String> adapterLine = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getLines(null, null, null));
//        spinerlines.setAdapter(adapterLine);
//
//        spinerDowntown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String downtown = (String) spinerDowntown.getSelectedItem();
////                Toast.makeText(RegisterActivity.this, downtown, Toast.LENGTH_LONG).show();
//                if(downtown == "" || downtown == null){
//                    spinerArea.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getAreas(null)));
//                    spinersites.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getSites(null, null)));
//                    spinerlines.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getLines(null, null, null)));
//                }else {
//                    spinerArea.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getAreas(downtown)));
//                    spinersites.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getSites(downtown, null)));
//                    spinerlines.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getLines(downtown, null, null)));
//                }
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        spinerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String downtown = (String) spinerDowntown.getSelectedItem();
//                String area = (String)spinerArea.getSelectedItem();
//                if(area == "" || area == null){
//                    spinersites.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getSites(null, null)));
//                    spinerlines.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getLines(null, null, null)));
//                }else {
//                    spinersites.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getSites(downtown, area)));
//                    spinerlines.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getLines(downtown, area, null)));
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        spinersites.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String downtown = (String) spinerDowntown.getSelectedItem();
//                String area = (String)spinerArea.getSelectedItem();
//                String site = (String)spinersites.getSelectedItem();
//                spinerlines.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getLines(downtown, area, site)));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }
//    public void registerOnStop(){
//        final Spinner spinerDowntown = (Spinner) findViewById(R.id.on_location_Group);
//        final ArrayAdapter<String> adapterDowntown = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getDowntowns());
////        adapterDowntown.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinerDowntown.setAdapter(adapterDowntown);
//        spinerDowntown.setSelection(0, true);
//
//        final Spinner spinerArea = (Spinner)findViewById(R.id.on_location_region);
//        final ArrayAdapter<String> adapterArea = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getAreas(null));
//        spinerArea.setAdapter(adapterArea);
//        spinerArea.setSelection(0, true);
//
//        final Spinner spinersites = (Spinner)findViewById(R.id.on_location_point);
//        ArrayAdapter<String> adapterSite = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getSites(null,null));
//        spinersites.setAdapter(adapterSite);
//
//        final Spinner spinerlines = (Spinner)findViewById(R.id.on_line_num);
//        ArrayAdapter<String> adapterLine = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getLines(null, null, null));
//        spinerlines.setAdapter(adapterLine);
//
//        spinerDowntown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String downtown = (String) spinerDowntown.getSelectedItem();
////                Toast.makeText(RegisterActivity.this, downtown, Toast.LENGTH_LONG).show();
//                if(downtown == "" || downtown == null){
//                    spinerArea.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getAreas(null)));
//                    spinersites.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getSites(null, null)));
//                    spinerlines.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getLines(null, null, null)));
//                }else {
//                    spinerArea.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getAreas(downtown)));
//                    spinersites.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getSites(downtown, null)));
//                    spinerlines.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getLines(downtown, null, null)));
//                }
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        spinerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String downtown = (String) spinerDowntown.getSelectedItem();
//                String area = (String)spinerArea.getSelectedItem();
//                if(area == "" || area == null){
//                    spinersites.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getSites(null, null)));
//                    spinerlines.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getLines(null, null, null)));
//                }else {
//                    spinersites.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getSites(downtown, area)));
//                    spinerlines.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getLines(downtown, area, null)));
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        spinersites.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String downtown = (String) spinerDowntown.getSelectedItem();
//                String area = (String)spinerArea.getSelectedItem();
//                String site = (String)spinersites.getSelectedItem();
//                spinerlines.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DataSource.getLines(downtown, area, site)));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
}
