package com.qyw.newdemo.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.activity.UserHomePageActivity;
import com.qyw.newdemo.app.activity.navuser.SettingsActivity;
import com.qyw.newdemo.app.base.BaseFragment;
import com.qyw.newdemo.app.impl.ActionBarClickListener;
import com.qyw.newdemo.app.view.TranslucentActionBar;
import com.qyw.newdemo.app.view.TranslucentScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class UserFragment extends BaseFragment implements ActionBarClickListener, TranslucentScrollView.TranslucentChangedListener{

    @BindView(R.id.pullzoom_scrollview)
    TranslucentScrollView translucentScrollView;    //实现拉伸头像背景
    @BindView(R.id.actionbar)
    TranslucentActionBar actionBar;
    @BindView(R.id.lay_header)      //头部
    View zoomView;
    @BindView(R.id.setting_button)
    LinearLayout setting_Button;    //设置button
    @BindView(R.id.userpageUi)
    LinearLayout userpageUi;    //设置button

    private Unbinder unbinder;



    public static UserFragment newInstance(){
        UserFragment f = new UserFragment();
        Bundle b = new Bundle();
        b.putString("fragment","UserFragment");
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        //初始actionBar
        actionBar.setData("个人中心", 0, null, 0, null, null);
        //开启渐变
        actionBar.setNeedTranslucent();
        //设置状态栏高度
        actionBar.setStatusBarHeight(getStatusBarHeight());
        //设置透明度变化监听
        translucentScrollView.setTranslucentChangedListener(this);
        //关联需要渐变的视图
        translucentScrollView.setTransView(actionBar);

        //关联伸缩的视图
        translucentScrollView.setPullZoomView(zoomView);
    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onTranslucentChanged(int transAlpha) {
        actionBar.tvTitle.setVisibility(transAlpha > 48 ? View.VISIBLE : View.GONE);
    }

    //    注册
    @OnClick(R.id.setting_button)
    public void setting_Event() {
        startActivity(new Intent(getActivity(),SettingsActivity.class));
    }

    //    注册
    @OnClick(R.id.userpageUi)
    public void jumpUserHomePage() {
        startActivity(new Intent(getActivity(),UserHomePageActivity.class));
    }


}
