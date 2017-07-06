package com.example.tarena.miaoxin.ui.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tarena.miaoxin.R;

public class FriendFragment extends BaseFragment {

    @Override
    public View createMyView(LayoutInflater inflater, ViewGroup container, Bundle saveeInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        return view;
    }

}
