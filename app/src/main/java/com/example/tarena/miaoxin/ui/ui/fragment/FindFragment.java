package com.example.tarena.miaoxin.ui.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.tarena.miaoxin.R;
import com.example.tarena.miaoxin.ui.ui.ui.PostBlogActivity;

public class FindFragment extends BaseFragment {


    @Override
    public View createMyView(LayoutInflater inflater, ViewGroup container, Bundle saveeInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        baseActivity.jumpTo(PostBlogActivity.class, false, false);
        return super.onOptionsItemSelected(item);
    }
}
