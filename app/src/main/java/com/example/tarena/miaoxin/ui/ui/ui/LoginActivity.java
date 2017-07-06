package com.example.tarena.miaoxin.ui.ui.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarena.miaoxin.R;
import com.example.tarena.miaoxin.ui.ui.model.MyUser;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.iv_login_login)
    ImageView ivLogin;
    @BindView(R.id.et_login_username)
    EditText etUsername;
    @BindView(R.id.et_login_password)
    EditText etPassword;
    @BindView(R.id.tv_login_regist)
    TextView tvRegist;


    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void login(String username, String password) {
        MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(password);
        user.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                SharedPreferences sharedPreferences = getSharedPreferences("test", Activity.MODE_PRIVATE);
                // 实例化SharedPreferences.Editor对象
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // 用putString的方法保存数据
                editor.putString("username", etUsername.getText().toString());
                editor.putString("password", etUsername.getText().toString());
                // 提交当前数据
                editor.apply();
                jumpTo(MainActivity.class, false, true);
            }

            @Override
            public void onFailure(int i, String s) {
                // 登录失败
                // 根据不同的i,尽量给出详细的提示
                switch (i) {
                    case 101:
                        toast("用户名或密码错误");
                        break;
                    default:
                        toastAndLog("登录失败", i, s);
                }
            }
        });
    }

    @OnClick(R.id.btn_login)
    public void onViewClickd() {
        if (isEmpty(etUsername, etPassword)) {
            return;
        }
        login(etUsername.getText().toString(), etPassword.getText().toString());
    }

    @OnClick(R.id.tv_login_regist)
    public void onRegisterClicked() {
        jumpTo(RegisterActivity.class, false, true);
    }
}
