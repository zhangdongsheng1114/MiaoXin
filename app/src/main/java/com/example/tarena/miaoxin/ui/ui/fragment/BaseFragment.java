package com.example.tarena.miaoxin.ui.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tarena.miaoxin.ui.ui.ui.BaseActivity;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    BaseActivity baseActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createMyView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        baseActivity = (BaseActivity) getActivity();
        // 如果需要在fragment页面的ActionBar里显示按钮的画，需要调用下面代码
        setHasOptionsMenu(true);
        return view;
    }

    public abstract View createMyView(LayoutInflater inflater, ViewGroup container, Bundle saveeInstanceState);
}
