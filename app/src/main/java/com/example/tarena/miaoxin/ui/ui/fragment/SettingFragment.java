package com.example.tarena.miaoxin.ui.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarena.miaoxin.R;
import com.example.tarena.miaoxin.ui.ui.ui.LoginActivity;
import com.example.tarena.miaoxin.ui.ui.ui.UserInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.tv_setting_username)
    TextView tvSettingUsername;
    @BindView(R.id.iv_setting_editorusername)
    ImageView ivSettingEditorusername;
    @BindView(R.id.tv_setting_notification)
    TextView tvSettingNotification;
    @BindView(R.id.iv_setting_editornotification)
    ImageView ivSettingEditornotification;
    @BindView(R.id.tv_setting_sound)
    TextView tvSettingSound;
    @BindView(R.id.lv_setting_editorsound)
    ImageView lvSettingEditorsound;
    @BindView(R.id.tv_setting_vibrate)
    TextView tvSettingVibrate;
    @BindView(R.id.lv_setting_editorvibrate)
    ImageView lvSettingEditorvibrate;
    @BindView(R.id.btn_setting_logout)
    Button btnSettingLogout;

    @Override
    public View createMyView(LayoutInflater inflater, ViewGroup container, Bundle saveeInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_setting_logout)
    public void onViewClicked() {
        SharedPreferences sharedPreferences = baseActivity.getSharedPreferences("test", Activity.MODE_PRIVATE);
        // 实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");

        // 提交当前数据
        editor.apply();
        // 跳转到LoginActivity
        Intent intent = new Intent(baseActivity, LoginActivity.class);
        startActivity(intent);
        // 结束当前的Activity
        baseActivity.finish();
    }

    @OnClick(R.id.iv_setting_editorusername)
    public void onEditUsernameClicked() {
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        intent.putExtra("from", "me");
        baseActivity.jumpTo(intent, false, true);
    }
}
