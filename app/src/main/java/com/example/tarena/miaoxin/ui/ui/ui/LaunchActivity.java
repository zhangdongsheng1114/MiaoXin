package com.example.tarena.miaoxin.ui.ui.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tarena.miaoxin.R;
import com.example.tarena.miaoxin.ui.ui.model.MyUser;

import cn.bmob.v3.listener.SaveListener;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        // 加载启动图片
        autoLogin();
    }

    private void autoLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("test", Activity.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");
        if (username != null && password != null) {
            MyUser user = new MyUser();
            user.setUsername(username);
            user.setPassword(password);
            user.login(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    // 跳转到MainActivity
                    Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                    startActivity(intent);
                    // 结束当前的Activity
                    LaunchActivity.this.finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    // 跳转到LoginActivity
                    Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                    startActivity(intent);
                    // 结束当前的Activity
                    LaunchActivity.this.finish();
                }
            });
        }
    }
}
