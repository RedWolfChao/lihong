package com.quanying.app.ui.user;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quanying.app.R;
import com.quanying.app.adapter.FragmentViewPagerAdapter;
import com.quanying.app.app.MyApplication;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.ui.fragment.NewLoginFragment;
import com.quanying.app.ui.fragment.NewRegistFragment;
import com.quanying.app.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * create by kevin on 2019/3/7.
 * ░░░░░░░░░░░░░░░░░░░░░░░░▄░░
 * ░░░░░░░░░▐█░░░░░░░░░░░▄▀▒▌░
 * ░░░░░░░░▐▀▒█░░░░░░░░▄▀▒▒▒▐
 * ░░░░░░░▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐
 * ░░░░░▄▄▀▒░▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐
 * ░░░▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌
 * ░░▐▒▒▒▄▄▒▒▒▒░░░▒▒▒▒▒▒▒▀▄▒▒
 * ░░▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐
 * ░▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄
 * ░▌░▒▄██▄▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒
 * ▀▒▀▐▄█▄█▌▄░▀▒▒░░░░░░░░░░▒▒▒
 * <p>
 * 写这段代码的时候，只有上帝和我知道它是干嘛的
 * 现在，只有上帝知道
 */
public class NewLoginActivity extends BaseActivity {


    @BindViews({R.id.login_btn,R.id.regist_btn})
    List<LinearLayout> btnList;//
    @BindView(R.id.back_btn)
    TextView back_btn ;

    @BindViews({R.id.login_text,R.id.regist_text})
    List<TextView> viewList;//

    @BindViews({R.id.login_line,R.id.regist_line})
    List<View> lineList;//

    @BindView(R.id.home_viewpager)
    CustomViewPager view_pager;
    private List<Fragment> fragments = null;//fragment 集合
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_newlogin;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        initFragment();
    }

    private void initFragment() {

        NewLoginFragment mHomeFragment = new NewLoginFragment();
        NewRegistFragment pushFragment = new NewRegistFragment();

        fragments = new ArrayList<>();
        fragments.add(mHomeFragment);
        fragments.add(pushFragment);

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
        view_pager.setOffscreenPageLimit(2);
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

    @OnClick({R.id.login_btn,R.id.regist_btn})
    public void switchFragment(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                updateView(0);
                view_pager.setCurrentItem(0);
                break;
            case R.id.regist_btn:
                updateView(1);
                view_pager.setCurrentItem(1);
                break;
        }
    }
    /*
     * 更新导航栏视图
     * */
    private void updateView(int i) {
        // TODO Auto-generated method stub
        // 循环遍历 数组中哪个选项卡被点击
        for (int j = 0; j < 2; j++) {
            if (i == j) {
                // 被点击的切换视图,切换颜色
//                    nav_Imgs.get(j).setImageResource(resId[j]);

                viewList.get(j).setTextColor(getResources().getColor(R.color.black));
                lineList.get(j).setVisibility(View.VISIBLE);

            } else {
                // 未被点击的 恢复默认
//                nav_Imgs.get(j).setImageResource(resIdU[j]);
//                viewList.get(j).setTextColor(Color.parseColor("#000000"));
//                viewList.get(j).setVisibility(View.GONE);

                viewList.get(j).setTextColor(getResources().getColor(R.color.tab_normal_color));
                lineList.get(j).setVisibility(View.INVISIBLE);
            }
        }
    }

    @OnClick(R.id.back_btn)
    public void backUi(){
        finish();
    }
}
