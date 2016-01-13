package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ZHANGLU6 on 1/12/2016.
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {
    Activity activity;

    private List<String> groupArray;
    private  List<List<String>> childArray;

    public  ExpandableAdapter(Activity a)
    {
        activity = a;
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupArray.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupArray.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return  childArray.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String string = groupArray.get(groupPosition);
        TextView textView = getGenericView(string);
        textView.setText(getGroup(groupPosition).toString());
        return textView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String string = childArray.get(groupPosition).get(childPosition);
        return  getGenericView(string);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public  TextView getGenericView(String string)
    {
        // Layout parameters for the ExpandableListView
        AbsListView.LayoutParams layoutParams = new  AbsListView.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, 64 );
        TextView text = new  TextView(activity);
        text.setLayoutParams(layoutParams);
        // Center the text vertically
        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        // Set the text starting position
        text.setPadding(36 , 0 , 0 , 0 );
        text.setText(string);
        return  text;
    }


    private void initData() {
//        groupArray.add("第一行" );
//        groupArray.add("第二行" );
//
//        List<String> tempArray = new  ArrayList<String>();
//        tempArray.add("第一条" );
//        tempArray.add("第二条" );
//        tempArray.add("第三条" );
//
//        for (int  index = 0 ; index <groupArray.size(); ++index)
//        {
//            childArray.add(tempArray);
//        }
    }
}
