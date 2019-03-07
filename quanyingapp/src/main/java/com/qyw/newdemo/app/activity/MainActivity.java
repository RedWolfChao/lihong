package com.qyw.newdemo.app.activity;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.adapter.FragmentViewPagerAdapter;
import com.qyw.newdemo.app.base.SlidingActivity;
import com.qyw.newdemo.app.fragment.HomeFragment;
import com.qyw.newdemo.app.fragment.MessageFragment;
import com.qyw.newdemo.app.fragment.RecruitmentFragment;
import com.qyw.newdemo.app.fragment.ShowroomFragment;
import com.qyw.newdemo.app.fragment.UserFragment;
import com.qyw.newdemo.app.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MainActivity extends SlidingActivity implements View.OnClickListener{

    private long exitTime = 0;//统计二次退出的时间

    @BindView(R.id.home_viewpager)
    CustomViewPager view_pager;

    @BindViews({R.id.home_nav1,R.id.home_nav2,R.id.home_nav3,R.id.home_nav4,R.id.home_nav5})
    List<LinearLayout> nav_title;//选项卡按钮

    private List<Fragment> fragments = null;//fragment 集合

    @BindViews({R.id.nav_img1,R.id.nav_img2,R.id.nav_img3,R.id.nav_img4,R.id.nav_img5})
    List<ImageView> nav_Imgs ;
    @BindViews({R.id.nav_tv1,R.id.nav_tv2,R.id.nav_tv3,R.id.nav_tv4,R.id.nav_tv5})
    List<TextView> nav_Tvs ;

    private int[] resId = { R.mipmap.home_check,R.mipmap.home_check,R.mipmap.home_check,R.mipmap.home_check,R.mipmap.home_check };
    private int[] resIdU = {R.mipmap.home_uncheck,R.mipmap.home_uncheck,R.mipmap.home_uncheck,R.mipmap.home_uncheck,R.mipmap.home_uncheck};

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        initViews();
        initFragment();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void initViews() {

        nav_title.get(0).setOnClickListener(this);
        nav_title.get(1).setOnClickListener(this);
        nav_title.get(2).setOnClickListener(this);
        nav_title.get(3).setOnClickListener(this);
        nav_title.get(4).setOnClickListener(this);

    }

    private void initFragment() {
        HomeFragment mHomeFragment = HomeFragment.newInstance();
        RecruitmentFragment mRecruitmentFragment = RecruitmentFragment.newInstance();
        ShowroomFragment mShowroomFragment = ShowroomFragment.newInstance();
        MessageFragment mMessageFragment = MessageFragment.newInstance();
        UserFragment mUserFragment = UserFragment.newInstance();

        fragments = new ArrayList<>();
        fragments.add(mHomeFragment);
        fragments.add(mRecruitmentFragment);
        fragments.add(mShowroomFragment);
        fragments.add(mMessageFragment);
        fragments.add(mUserFragment);

        view_pager.setScanScroll(false);
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), view_pager, fragments);
        adapter.setOnExtraPageChangeListener(new FragmentViewPagerAdapter.OnExtraPageChangeListener() {
            @Override
            public void onExtraPageSelected(int i) {
                System.out.println("Extra...i: " + i);
            }
        });
        view_pager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        view_pager.setCurrentItem(0);
        view_pager.setOffscreenPageLimit(4);
        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }


   /*
    * 二次退出
    * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
//            activityManagerUtil.appExit();
//        退出

        }
    }

    /*
    * 更新导航栏视图
    * */
    private void updateView(int i) {
        // TODO Auto-generated method stub
        // 循环遍历 数组中哪个选项卡被点击
        for (int j = 0; j < 5; j++) {
            if (i == j) {
                // 被点击的切换视图,切换颜色
                nav_Imgs.get(j).setImageResource(resId[j]);

            } else {
                // 未被点击的 恢复默认
                nav_Imgs.get(j).setImageResource(resIdU[j]);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            R.id.home_nav1,R.id.home_nav2,R.id.home_nav3,R.id.home_nav4,R.id.home_nav5
            /*
            * 导航栏事件处理
            * */
            case R.id.home_nav1:
                updateView(0);
                view_pager.setCurrentItem(0);
                break;
            case R.id.home_nav2:
                updateView(1);
                view_pager.setCurrentItem(1);
                break;
            case R.id.home_nav3:
                updateView(2);
                view_pager.setCurrentItem(2);
                break;
            case R.id.home_nav4:
                updateView(3);
                view_pager.setCurrentItem(3);
                break;
            case R.id.home_nav5:
                updateView(4);
                view_pager.setCurrentItem(4);
                break;

        }
    }
}
