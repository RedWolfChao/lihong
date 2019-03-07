package com.qyw.newdemo.app.activity.recruitment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.adapter.SearchResultAdapter;
import com.qyw.newdemo.app.base.SlidingActivity;
import com.qyw.newdemo.app.entity.RecruitmentEntity;
import com.qyw.newdemo.app.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchEndActivity extends SlidingActivity {

    private List<RecruitmentEntity> mList;

    private int resourceId;
    private SearchResultAdapter mAdapter;
    private RecruitmentEntity   mEntity ;

    @BindView(R.id.searchend_listview)
    ListView mListView;

    @BindView(R.id.titlebar_view)
    TitleBar mTitleBar;
    @BindString(R.string.searchend_title)
    String title_Str;//sting

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initBindView();
        initData();
    }

    protected void initData() {
        mList = new ArrayList<>();
        String imgs1 ="https://hbimg.b0.upaiyun.com/eccd09a46689da89d57cbfaeaeeedbe20297187562f25-gSRFDu_fw658";
        mEntity = new RecruitmentEntity();
        mEntity.setRecruitment_Img(imgs1);
        mEntity.setRecruitment_City("潍坊");
        mEntity.setRecruitment_Date("昨天");
        mEntity.setRecruitment_Workdate("三年");
        mEntity.setRecruitment_Name("lovekevin4");
        mEntity.setRecruitment_Pay("5k-10k");
        mEntity.setRecruitment_Post("儿童摄影师");
        mList.add(mEntity);
        mAdapter = new SearchResultAdapter(context,resourceId,mList);
        mListView.setAdapter(mAdapter);

    }

    private void initBindView() {
        mTitleBar.setLeftImageResource(R.mipmap.back_img);
        mTitleBar.setTitle(title_Str);
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setLeftTextColor(Color.WHITE);
        // 获取颜色资源文件
//        int mycolor = getResources().getColor(R.color.colorBackground);

        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_searchend;
    }
/*
    //    登录
    @OnClick(R.id.setting_item1)
    public void AccountSafe() {
        startActivity(new Intent(mActivity,AccountSafeActivity.class));
    }*/

}
