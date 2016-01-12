package com.example.fengge.shuttlebus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.fengge.shuttlebus.R;

public class HomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final int REFRESH_COMPLETE = 0X110;
    private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private TextView mBusLocationView;
    private TextView mArriveTimeView;
    private CharSequence charSequence;
    private ArrayAdapter<String> mAdapter;
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("海怡湾畔", "唐家", "香柠花园", "美丽湾", "恒雅名园", "邮政大厦"));

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    refreshData();
                    mSwipeLayout.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    @SuppressLint("InlinedApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.activity_home);
//        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.bus1);

        mListView = (ListView) findViewById(R.id.id_listview);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        mBusLocationView = (TextView) findViewById(R.id.bus_location);
        mArriveTimeView = (TextView) findViewById(R.id.arrive_time);
        /*mBusLocationView.setText("当前位置：");*/
        String html = "<img src='" + android.R.drawable.ic_menu_mylocation + "'/>";
        Html.ImageGetter imgGetter = new Html.ImageGetter() {

            @Override
            public Drawable getDrawable(String source) {
                // TODO Auto-generated method stub
                int id = Integer.parseInt(source);
                Drawable d = getResources().getDrawable(id);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            }
        };
        charSequence = Html.fromHtml(html, imgGetter, null);
        mBusLocationView.setText(charSequence);
        mBusLocationView.append("新市花园");

        YoYo.with(Techniques.ZoomIn)
                .duration(1000)
                .playOn(findViewById(R.id.bus_location));
        YoYo.with(Techniques.ZoomIn)
                .duration(1000)
                .playOn(findViewById(R.id.arrive_time));
    }

    private void refreshData(){
        String busNo = "7";
        Date arriveTime = new Date();
        String location;

        int length=mDatas.size();
        int index=(int)(Math.random()*(length-1));
        location = mDatas.get(index);

        mBusLocationView.setText(charSequence);
        mBusLocationView.append(location);

        SimpleDateFormat formatter = new SimpleDateFormat("hh:ssaaa");
        mArriveTimeView.setText(formatter.format(new Date()));

    }

    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }

}
