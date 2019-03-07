package com.qyw.newdemo.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.adapter.HomeFragmentAdapter;
import com.qyw.newdemo.app.base.BaseFragment;
import com.qyw.newdemo.app.entity.HomeFragmentEntity;
import com.qyw.newdemo.app.utils.Utils;
import com.qyw.newdemo.app.view.ShadowActionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页
 * Created by SmileXie on 2017/6/29.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.home_recycleview)
    RecyclerView recyclerView;

    HomeFragmentAdapter mAdapter;

    private List<HomeFragmentEntity> mHomeList; //  首页Adapter数据源

    private Unbinder unbinder;

    @BindString(R.string.app_name)
    String title_Str;//sting

    //    List<String> images = null;
    String imgs1 ="https://hbimg.b0.upaiyun.com/eccd09a46689da89d57cbfaeaeeedbe20297187562f25-gSRFDu_fw658";
    String imgs2 = "https://hbimg.b0.upaiyun.com/7554d4a8a5556382607ce7a9ec1d9e907980a7517b3f-MtgmqQ_fw658";
    String imgs3 = "https://hbimg.b0.upaiyun.com/1cd5db06d83e6bd6ea04e8ce59fcdc0893b10e172fadf-T7OF3t_fw658";
    String imgs4 ="https://hbimg.b0.upaiyun.com/4785b1ccf0bd3b9a40982f65ab4026c191953110393b2-Y2ayIg_fw658";

/*
* 首页titlebar 上拉特效
* */
//  字体BaseColor 黑色
    private String mBaseTextColor = "000000";
    //  标题栏BaseColor colorPrimary
    private String mBaseActionBarColor = "3F51B5";
    //  15个级别 00为透明,FF为不透明
    private String[] mTextColors = {
            "#FF" + mBaseTextColor, "#EE" + mBaseTextColor, "#DD" + mBaseTextColor, "#CC" + mBaseTextColor,
            "#AA" + mBaseTextColor, "#99" + mBaseTextColor, "#88" + mBaseTextColor, "#77" + mBaseTextColor,
            "#66" + mBaseTextColor, "#55" + mBaseTextColor, "#44" + mBaseTextColor, "#33" + mBaseTextColor,
            "#22" + mBaseTextColor, "#11" + mBaseTextColor, "#00" + mBaseTextColor};
    //  同理
    private String[] mActionBarColors = {
            "#FF" + mBaseActionBarColor, "#EE" + mBaseActionBarColor, "#DD" + mBaseActionBarColor, "#CC" + mBaseActionBarColor,
            "#AA" + mBaseActionBarColor, "#99" + mBaseActionBarColor, "#88" + mBaseActionBarColor, "#77" + mBaseActionBarColor,
            "#66" + mBaseActionBarColor, "#55" + mBaseActionBarColor, "#44" + mBaseActionBarColor, "#33" + mBaseActionBarColor,
            "#22" + mBaseActionBarColor, "#11" + mBaseActionBarColor, "#00" + mBaseActionBarColor};
    //  这个是Adapter的数据
    private String[] mData = {
            "#FF" + mBaseActionBarColor, "#EE" + mBaseActionBarColor, "#DD" + mBaseActionBarColor, "#CC" + mBaseActionBarColor,
            "#AA" + mBaseActionBarColor, "#99" + mBaseActionBarColor, "#88" + mBaseActionBarColor, "#77" + mBaseActionBarColor,
            "#66" + mBaseActionBarColor, "#55" + mBaseActionBarColor, "#44" + mBaseActionBarColor, "#33" + mBaseActionBarColor,
            "#22" + mBaseActionBarColor, "#11" + mBaseActionBarColor, "#00" + mBaseActionBarColor, "#00" + mBaseActionBarColor,
            "#FF" + mBaseActionBarColor, "#EE" + mBaseActionBarColor, "#DD" + mBaseActionBarColor, "#CC" + mBaseActionBarColor,
            "#AA" + mBaseActionBarColor, "#99" + mBaseActionBarColor, "#88" + mBaseActionBarColor, "#77" + mBaseActionBarColor,
            "#66" + mBaseActionBarColor, "#55" + mBaseActionBarColor, "#44" + mBaseActionBarColor, "#33" + mBaseActionBarColor,
            "#FF" + mBaseActionBarColor, "#EE" + mBaseActionBarColor, "#DD" + mBaseActionBarColor, "#CC" + mBaseActionBarColor,
            "#AA" + mBaseActionBarColor, "#99" + mBaseActionBarColor, "#88" + mBaseActionBarColor, "#77" + mBaseActionBarColor,
            "#66" + mBaseActionBarColor, "#55" + mBaseActionBarColor, "#44" + mBaseActionBarColor, "#33" + mBaseActionBarColor,};
    //  全局控制透明度
    private float mViewAlpha = 0F;
    @BindView(R.id.mShadowActionBar)
    ShadowActionBar mShadowActionBar;

    private View titleBar_Bg;
    private TextView titleBar_title;
    private View status_bar_fix;
    private View titleBar;


    public static HomeFragment newInstance(){
        HomeFragment f = new HomeFragment();
        Bundle b = new Bundle();
        b.putString("fragment","HomeFragment");
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        unbinder = ButterKnife.bind(this, view);
        initTitle(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter();

    }

    private void initTitle(View v) {

        titleBar = v.findViewById(R.id.titleBar);
        titleBar_Bg = titleBar.findViewById(R.id.bg);
        //当状态栏透明后，内容布局会上移，这里使用一个和状态栏高度相同的view来修正内容区域
        status_bar_fix = titleBar.findViewById(R.id.status_bar_fix);
        status_bar_fix.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.getStatusHeight(getContext())));
        titleBar_title = (TextView) titleBar.findViewById(R.id.title);
        titleBar_Bg.setAlpha(0);
        status_bar_fix.setAlpha(0);
        titleBar_title.setText("标题栏透明度(0%)");



//        mTitleBar.setTitle("\n"+title_Str);
       /* recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                Log.d("gaodu1",""+newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("gaodu",""+dy+" X "+dx);
//                setActionBarColor(dy);
                float alpha = 1.0f * dx / 180;
                titleBar_Bg.setAlpha(alpha);
                //注意头部局的颜色也需要改变
                status_bar_fix.setAlpha(alpha);
                titleBar_title.setText("标题栏透明度(" + (int) (alpha * 100) + "%)");
            }
        });*/

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totalDy += dy;
//                Math.abs(totalDy);
                Log.d("gaodu",""+Math.abs(totalDy)+" X "+dx);
                float alpha = 1.0f *totalDy / 200;

                titleBar_Bg.setAlpha(alpha);
                //注意头部局的颜色也需要改变
                status_bar_fix.setAlpha(alpha);
                titleBar_title.setText("标题栏透明度(" + (int) (alpha * 100) + "%)");
                }


        });

    }
    public void setActionBarColor(float offset) {
        //  上滑为正 下滑为负 上滑显示 下滑隐藏
        //  这里可设置 透明度为0-1
        //  可调整的单位则为 offset除的那个数
        mViewAlpha += (offset / 600);
        mShadowActionBar.setViewAlpha(mViewAlpha);
    }

    private void initAdapter() {
        HomeFragmentEntity mHomeEntity = new HomeFragmentEntity();

        mHomeList = new ArrayList<>();
        List<String> images = new ArrayList<>();
        images.add(imgs1);
        images.add(imgs2);
        images.add(imgs3);
        images.add(imgs4);

        String[] recommend_Imgs = {"https://hbimg.b0.upaiyun.com/ec1d3a1ed09fcb128847f6a8c8e7ba3e94b4ee3c91a1-YXR87l_fw658","https://hbimg.b0.upaiyun.com/5bff239a67c47c1c1dc603a7889c419a3acb29fc7b9b-Gt7waY_fw658"
                ,"https://hbimg.b0.upaiyun.com/f6751806e7f49b4223fe26bdfb87f2300f0662914eda-93CWtj_fw658","https://hbimg.b0.upaiyun.com/5c8312e4f57d70e1d98deb165aa7bd3d52b0b59ee9b8-Mj4FRu_fw658",
                "https://hbimg.b0.upaiyun.com/7b8613be8d75f3b3b5e517392f5b85419a4dfe316b9e-lIvU3d_fw658","https://hbimg.b0.upaiyun.com/fd36dd2380a3fd51f44b5d05dcc1982030a9881c1955-ad8Ntx_fw658"};
        mHomeEntity.setBanner_Images(images);
        mHomeEntity.setRecommend_imgs(recommend_Imgs);
        mHomeEntity.setEventsItemImgs("https://hbimg.b0.upaiyun.com/4785b1ccf0bd3b9a40982f65ab4026c191953110393b2-Y2ayIg_fw658");
        mHomeEntity.setEventsItemDate("7月1日");
        mHomeEntity.setEventsItemEventsTitles("gogogogogogoogogoogogogogoogog");
        mHomeList.add(mHomeEntity);
        mHomeEntity = new HomeFragmentEntity();
        mHomeEntity.setBanner_Images(images);
        mHomeEntity.setRecommend_imgs(recommend_Imgs);
        mHomeEntity.setEventsItemImgs("https://hbimg.b0.upaiyun.com/4785b1ccf0bd3b9a40982f65ab4026c191953110393b2-Y2ayIg_fw658");
        mHomeEntity.setEventsItemDate("7月2日");
        mHomeEntity.setEventsItemEventsTitles("gogogogogogoogogoogogogogoogog");
        mHomeList.add(mHomeEntity);
        mHomeEntity = new HomeFragmentEntity();
        mHomeEntity.setBanner_Images(images);
        mHomeEntity.setRecommend_imgs(recommend_Imgs);
        mHomeEntity.setEventsItemImgs("https://hbimg.b0.upaiyun.com/4785b1ccf0bd3b9a40982f65ab4026c191953110393b2-Y2ayIg_fw658");
        mHomeEntity.setEventsItemDate("7月3日");
        mHomeEntity.setEventsItemEventsTitles("gogogogogogoogogoogogogogoogog");
        mHomeList.add(mHomeEntity);
        mHomeEntity = new HomeFragmentEntity();
        mHomeEntity.setBanner_Images(images);
        mHomeEntity.setRecommend_imgs(recommend_Imgs);
        mHomeEntity.setEventsItemImgs("https://hbimg.b0.upaiyun.com/4785b1ccf0bd3b9a40982f65ab4026c191953110393b2-Y2ayIg_fw658");
        mHomeEntity.setEventsItemDate("7月4日");
        mHomeEntity.setEventsItemEventsTitles("gogogogogogoogogoogogogogoogog");
        mHomeList.add(mHomeEntity);
        mHomeEntity = new HomeFragmentEntity();
        mHomeEntity.setBanner_Images(images);
        mHomeEntity.setRecommend_imgs(recommend_Imgs);
        mHomeEntity.setEventsItemImgs("https://hbimg.b0.upaiyun.com/4785b1ccf0bd3b9a40982f65ab4026c191953110393b2-Y2ayIg_fw658");
        mHomeEntity.setEventsItemDate("7月5日");
        mHomeEntity.setEventsItemEventsTitles("gogogogogogoogogoogogogogoogog");
        mHomeList.add(mHomeEntity);
        mHomeEntity = new HomeFragmentEntity();
        mHomeEntity.setBanner_Images(images);
        mHomeEntity.setRecommend_imgs(recommend_Imgs);
        mHomeEntity.setEventsItemImgs("https://hbimg.b0.upaiyun.com/4785b1ccf0bd3b9a40982f65ab4026c191953110393b2-Y2ayIg_fw658");
        mHomeEntity.setEventsItemDate("7月6日");
        mHomeEntity.setEventsItemEventsTitles("gogogogogogoogogoogogogogoogog");
        mHomeList.add(mHomeEntity);
        mHomeEntity = new HomeFragmentEntity();
        mHomeEntity.setBanner_Images(images);
        mHomeEntity.setRecommend_imgs(recommend_Imgs);
        mHomeEntity.setEventsItemImgs("https://hbimg.b0.upaiyun.com/4785b1ccf0bd3b9a40982f65ab4026c191953110393b2-Y2ayIg_fw658");
        mHomeEntity.setEventsItemDate("7月7日");
        mHomeEntity.setEventsItemEventsTitles("gogogogogogoogogoogogogogoogog");
        mHomeList.add(mHomeEntity);
        mHomeEntity = new HomeFragmentEntity();
        mHomeEntity.setBanner_Images(images);
        mHomeEntity.setRecommend_imgs(recommend_Imgs);
        mHomeEntity.setEventsItemImgs("https://hbimg.b0.upaiyun.com/4785b1ccf0bd3b9a40982f65ab4026c191953110393b2-Y2ayIg_fw658");
        mHomeEntity.setEventsItemDate("7月8日");
        mHomeEntity.setEventsItemEventsTitles("gogogogogogoogogoogogogogoogog");
        mHomeList.add(mHomeEntity);
        mHomeList.add(mHomeEntity);
        mHomeList.add(mHomeEntity);
        mHomeList.add(mHomeEntity);
        mHomeList.add(mHomeEntity);



        mAdapter = new HomeFragmentAdapter(getContext(),mHomeList);
        LinearLayoutManager layoutManage = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManage);
        recyclerView.setAdapter(mAdapter);
    }


}