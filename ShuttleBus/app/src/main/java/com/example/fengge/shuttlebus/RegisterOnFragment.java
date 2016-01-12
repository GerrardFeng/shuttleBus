package com.example.fengge.shuttlebus;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RegisterOnFragment extends Fragment implements View.OnClickListener {

    private View settingLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View settingLayout = inflater.inflate(R.layout.regist_on, container, false);

        settingLayout.setOnClickListener(this);
        this.settingLayout = settingLayout;
        return settingLayout;
    }

    @Override
    public void onStart() {
        super.onStart();
        settingLayout.callOnClick();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
//        ((RegisterActivity) getActivity()).registerOnStop();
//        ((RegisterActivity) getActivity()).registerOffStop();
    }
}