package com.example.tarena.miaoxin.ui.ui.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.tarena.miaoxin.R;
import com.example.tarena.miaoxin.ui.ui.model.MyUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_regist_username)
    EditText etUsername;
    @BindView(R.id.et_regist_password)
    EditText etPassword;
    @BindView(R.id.et_regist_repassword)
    EditText etRepassword;
    @BindView(R.id.rg_regist_gender)
    RadioGroup rgGender;
    @BindView(R.id.btn_regist_regist)
    Button btnRegist;


    @Override
    public int getLayoutID() {
        return R.layout.activity_regist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        showBackBtn();
    }

    @OnClick(R.id.btn_regist_regist)
    public void onViewClicked() {
        // 构建实体类（MyUser）对象
        final MyUser user = new MyUser();
        user.setUsername(etUsername.getText().toString());
        // 是否md5加密？取决于同学自己的设计
        user.setPassword(etPassword.getText().toString());
        boolean gender = true;
        if (rgGender.getCheckedRadioButtonId() == R.id.rb_gender_girl) {
            gender = false;
        }
        user.setGender(gender);

        // 进行注册
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                // 进行登录
                user.login(RegisterActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        // 登陆成功
                        // 跳转页面，跳转到MainActivity
                        jumpTo(MainActivity.class, true, true);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        toastAndLog("登录失败", i, s);
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                switch (i) {
                    case 202:
                        toast("用户名重复");
                        break;
                    default:
                        toastAndLog("注册用户失败稍后重试", i, s);
                        break;
                }
            }
        });
    }
}
