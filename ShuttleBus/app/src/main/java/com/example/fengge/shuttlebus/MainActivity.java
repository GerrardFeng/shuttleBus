package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

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

public class MainActivity extends TabActivity implements TabHost.OnTabChangeListener {

    public static MainActivity mTabActivityInstance;
    private TabHost tabHost;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.home_tab);

        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new TypiconsModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new MeteoconsModule())
                .with(new WeathericonsModule())
                .with(new SimpleLineIconsModule())
                .with(new IoniconsModule());

        tabHost = getTabHost();
        res = getResources();
        TabHost.TabSpec spec;
//        setTitle(res.getText(R.string.app_name));

        mTabActivityInstance=this;

        //加载底部Tab布局
        LinearLayout tab1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.action_item, null);
        ImageView icon1 = (ImageView) tab1.findViewById(R.id.icon);
//        icon1.setBackgroundResource(R.drawable.home36);
        icon1.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_home)
                .colorRes(android.R.color.white)
                .actionBarSize());
        TextView title1 = (TextView) tab1.findViewById(R.id.title);
        title1.setText(res.getText(R.string.activity_home_tab_name));
        title1.setTextColor(Color.rgb(255, 255, 255));

        LinearLayout tab2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.action_item, null);
        ImageView icon2 = (ImageView) tab2.findViewById(R.id.icon);
//        icon2.setBackgroundResource(android.R.drawable.ic_menu_search);
        icon2.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_search)
                .colorRes(android.R.color.white)
                .actionBarSize());
        TextView title2 = (TextView) tab2.findViewById(R.id.title);
        title2.setText(res.getText(R.string.activity_search_tab_name));
        title2.setTextColor(Color.rgb(255, 255, 255));

        LinearLayout tab3 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.action_item, null);
        ImageView icon3 = (ImageView) tab3.findViewById(R.id.icon);
        icon3.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_tasks)
                .colorRes(android.R.color.white)
                .actionBarSize());
        TextView title3 = (TextView) tab3.findViewById(R.id.title);
        title3.setText(res.getText(R.string.activity_register_tab_name));
        title3.setTextColor(Color.rgb(255, 255, 255));


        LinearLayout useInfoTab = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.action_item, null);
        ImageView userIcon = (ImageView) useInfoTab.findViewById(R.id.icon);
        userIcon.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_user)
                .colorRes(android.R.color.white)
                .actionBarSize());
        TextView userTitle = (TextView) useInfoTab.findViewById(R.id.title);
        userTitle.setText(res.getText(R.string.activity_user_tab_name));
        userTitle.setTextColor(Color.rgb(255, 255, 255));


        // 加载TabSpec
        tabHost.setup(getLocalActivityManager());

        spec = tabHost.newTabSpec("Home")
                .setIndicator(tab1)
                .setContent(new Intent(this, HomeActivity.class));
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Register")
                .setIndicator(tab3)
                .setContent(new Intent(this, RegisterActivity.class));

        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Search")
                .setIndicator(tab2)
                .setContent(new Intent(this, SearchActivity.class));
        tabHost.addTab(spec);

        // me
        spec = tabHost.newTabSpec("UseInfo")
                .setIndicator(useInfoTab)
                .setContent(new Intent(this, SearchActivity.class));
        tabHost.addTab(spec);

        TabWidget tw = tabHost.getTabWidget();
        tw.setBackgroundResource(android.R.drawable.screen_background_dark);

        tabHost.setCurrentTab(0);

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

    @Override
    public void onTabChanged(String tabId) {

//        View currentTabView = tabHost.getCurrentTabView();
//        ImageView icon = (ImageView) currentTabView.findViewById(R.id.icon);
//        TextView title = (TextView) currentTabView.findViewById(R.id.title);
//        title.setTextColor(android.R.color.holo_blue_bright);
//
//        if (tabId.equalsIgnoreCase("Home")) {
//            icon.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_home)
//                    .colorRes(android.R.color.holo_blue_bright)
//                    .actionBarSize());
//        } else if (tabId.equalsIgnoreCase("Search")) {
//            icon.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_search)
//                    .colorRes(android.R.color.holo_blue_bright)
//                    .actionBarSize());
//        } else if (tabId.equalsIgnoreCase("Register")) {
//            icon.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_tasks)
//                    .colorRes(android.R.color.holo_blue_bright)
//                    .actionBarSize());
//        }
    }
}
