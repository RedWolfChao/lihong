package com.qyw.newdemo.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.adapter.ShowroomAdapter;
import com.qyw.newdemo.app.fragment.showroom.AttentionFragment;
import com.qyw.newdemo.app.fragment.showroom.NewestFragment;
import com.qyw.newdemo.app.fragment.showroom.SamecityFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShowroomFragment extends Fragment {



    private Unbinder unbinder;

    private ShowroomAdapter fAdapter;
    @BindView(R.id.viewpager)
     ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                            //tab的名称列表

    private NewestFragment mNewestFragment;
    private AttentionFragment mAttentionFragment;
    private SamecityFragment mSamecityFragment;

    public static ShowroomFragment newInstance(){
        ShowroomFragment f = new ShowroomFragment();
        Bundle b = new Bundle();
        b.putString("fragment","ShowroomFragment");
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showroom,container,false);
        unbinder = ButterKnife.bind(this, view);
        initControls(view);
        return view;
    }

    private void initControls(View view) {
//        初始化Fragment
        mNewestFragment = new NewestFragment();
        mAttentionFragment = new AttentionFragment();
        mSamecityFragment = new SamecityFragment();
//         Fragment 装入容器
        list_fragment = new ArrayList<>();
        list_fragment.add(mNewestFragment);
        list_fragment.add(mAttentionFragment);
        list_fragment.add(mSamecityFragment);
//          将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("最新");
        list_title.add("关注");
        list_title.add("同城");
//        设置tablayout样式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

//         为TabLayout添加tab名称
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(2)));

//        tabLayout.setIndicator(tabLayout, 10, 10);
        fAdapter = new ShowroomAdapter(getActivity().getSupportFragmentManager(),list_fragment,list_title);

        //viewpager加载adapter
        viewPager.setAdapter(fAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initTabAdapter();
    }


}
