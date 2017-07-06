package com.example.tarena.miaoxin.ui.ui.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.tarena.miaoxin.R;
import com.example.tarena.miaoxin.ui.ui.fragment.FindFragment;
import com.example.tarena.miaoxin.ui.ui.fragment.FriendFragment;
import com.example.tarena.miaoxin.ui.ui.fragment.MessageFragment;
import com.example.tarena.miaoxin.ui.ui.fragment.SettingFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_main_viewpager)
    ViewPager mainVP;
    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @BindView(R.id.rg_main_footer)
    RadioGroup mainRG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("首页");

        fragments.add(new MessageFragment());
        fragments.add(new FriendFragment());
        fragments.add(new FindFragment());
        fragments.add(new SettingFragment());

        mainVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton rb = (RadioButton) mainRG.getChildAt(position);
                titleTV.setText(rb.getText());

                switch (position) {
                    case 0:
                        mainRG.check(R.id.rb_main_footer_home);
                        break;
                    case 1:
                        mainRG.check(R.id.rb_main_footer_tuan);
                        break;
                    case 2:
                        mainRG.check(R.id.rb_main_footer_find);
                        break;
                    case 3:
                        mainRG.check(R.id.rb_main_footer_my);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mainVP.setAdapter(new FragmentPagerAdapter(this.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    @OnClick({R.id.rb_main_footer_home, R.id.rb_main_footer_tuan, R.id.rb_main_footer_find, R.id.rb_main_footer_my})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.rb_main_footer_home:
                mainVP.setCurrentItem(0, true);
                break;
            case R.id.rb_main_footer_tuan:
                mainVP.setCurrentItem(1, true);
                break;
            case R.id.rb_main_footer_find:
                mainVP.setCurrentItem(2, true);
                break;
            case R.id.rb_main_footer_my:
                mainVP.setCurrentItem(3, true);
                break;
        }

        RadioButton rb = (RadioButton) mainRG.getChildAt(mainVP.getCurrentItem());
        titleTV.setText(rb.getText());
    }
}
