package com.quanying.app.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;

import com.quanying.app.R;
import com.quanying.app.adapter.FragmentViewPagerAdapter;
import com.quanying.app.app.MyApplication;
import com.quanying.app.ui.base.BaseFragment;
import com.quanying.app.ui.showroominsidepage.FocusOnFragment;
import com.quanying.app.ui.showroominsidepage.LatestFragment;
import com.quanying.app.ui.showroominsidepage.OurCityFragment;
import com.quanying.app.ui.user.AddCreationActivity;
import com.quanying.app.ui.user.LoginActivity;
import com.quanying.app.view.CustomViewPager;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class ShowRoomFragment extends BaseFragment {

    @BindView(R.id.home_viewpager)
    CustomViewPager view_pager;

    @BindViews({R.id.showroom_view1,R.id.showroom_view2,R.id.showroom_view3})
    List<View> viewList;//

    @BindViews({ R.id.showroom_nav1,R.id.showroom_nav2,R.id.showroom_nav3 })
    List<RelativeLayout> buttonList ;

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;

    private List<Fragment> fragments = null;//fragment 集合

    @Override
    protected int setLayoutResourceID() {
        return R.layout.f_home;
    }

    @Override
    protected void initView() {
        titlebar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_RIGHT_TEXT) {

                }
                if(action==CommonTitleBar.ACTION_RIGHT_BUTTON){

                    startActivity(new Intent(getMContext(), AddCreationActivity.class));

                }
            }
        });
        initFragment();
    }

    @Override
    protected void initData() {

    }

    private void initFragment() {

        LatestFragment mHomeFragment1 = new LatestFragment();
        FocusOnFragment mHomeFragment2 = new FocusOnFragment();
        OurCityFragment mHomeFragment3 = new OurCityFragment();

        fragments = new ArrayList<>();
        fragments.add(mHomeFragment1);
        fragments.add(mHomeFragment2);
        fragments.add(mHomeFragment3);

        view_pager.setScanScroll(false);
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getActivity().getSupportFragmentManager(), view_pager, fragments);
        adapter.setOnExtraPageChangeListener(new FragmentViewPagerAdapter.OnExtraPageChangeListener() {
            @Override
            public void onExtraPageSelected(int i) {
                System.out.println("Extra...i: " + i);
            }
        });
        view_pager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        view_pager.setCurrentItem(0);
        view_pager.setOffscreenPageLimit(3);
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

    @OnClick({R.id.showroom_nav1,R.id.showroom_nav2,R.id.showroom_nav3})
    public void switchFragment(View v) {
        switch (v.getId()) {
            case R.id.showroom_nav1:
                updateView(0);
                view_pager.setCurrentItem(0);

                break;
            case R.id.showroom_nav2:
                updateView(1);
                view_pager.setCurrentItem(1);
                showLoginDialog("登陆后可查看关注作品");
                break;
            case R.id.showroom_nav3:
                updateView(2);
                view_pager.setCurrentItem(2);
                showLoginDialog("登陆后可查看同城作品");
                break;
        }
    }


    public void  showLoginDialog(String msg){

        if(!MyApplication.getToken().equals("")){

            return;

        }

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();//创建对话框
        dialog.setTitle("提示");//设置对话框标题
        dialog.setMessage(msg);//设置文字显示内容
        //分别设置三个button
        dialog.setButton(DialogInterface.BUTTON_POSITIVE,"立即登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
                getActivity().startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
            }
        });
        dialog.show();//显示对话框


    }


    /*
     * 更新导航栏视图
     * */
    private void updateView(int i) {
        // TODO Auto-generated method stub
        // 循环遍历 数组中哪个选项卡被点击
        for (int j = 0; j < 3; j++) {
            if (i == j) {
                // 被点击的切换视图,切换颜色
//                    nav_Imgs.get(j).setImageResource(resId[j]);

                viewList.get(j).setVisibility(View.VISIBLE);
            } else {
                // 未被点击的 恢复默认
//                nav_Imgs.get(j).setImageResource(resIdU[j]);
//                viewList.get(j).setTextColor(Color.parseColor("#000000"));
                viewList.get(j).setVisibility(View.GONE);
            }
        }
    }

}
