package com.qyw.newdemo.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.activity.recruitment.SearchActivity;
import com.qyw.newdemo.app.adapter.RecruitmentAdapter;
import com.qyw.newdemo.app.entity.RecruitmentEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecruitmentFragment extends Fragment {

    @BindView(R.id.recruitment_recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.search_btn)
    LinearLayout search_Btn;

//  适配器
    RecruitmentAdapter mAdapter;

    private List<RecruitmentEntity> mRecruitmentList; //  首页Adapter数据源

    private Unbinder unbinder;

    //    List<String> images = null;
    String imgs1 ="https://hbimg.b0.upaiyun.com/eccd09a46689da89d57cbfaeaeeedbe20297187562f25-gSRFDu_fw658";
    String imgs2 = "https://hbimg.b0.upaiyun.com/7554d4a8a5556382607ce7a9ec1d9e907980a7517b3f-MtgmqQ_fw658";
    String imgs3 = "https://hbimg.b0.upaiyun.com/1cd5db06d83e6bd6ea04e8ce59fcdc0893b10e172fadf-T7OF3t_fw658";
    String imgs4 ="https://hbimg.b0.upaiyun.com/4785b1ccf0bd3b9a40982f65ab4026c191953110393b2-Y2ayIg_fw658";



    public static RecruitmentFragment newInstance(){
        RecruitmentFragment f = new RecruitmentFragment();
        Bundle b = new Bundle();
        b.putString("fragment","MessageFragment");
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruitment,container,false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter();
    }

    private void initAdapter() {

        RecruitmentEntity mEntity = new RecruitmentEntity();

        mRecruitmentList = new ArrayList<>();


        mEntity.setRecruitment_Img(imgs1);
        mEntity.setRecruitment_City("潍坊");
        mEntity.setRecruitment_Date("昨天");
        mEntity.setRecruitment_Workdate("三年");
        mEntity.setRecruitment_Name("lovekevin");
        mEntity.setRecruitment_Pay("5k-10k");
        mEntity.setRecruitment_Post("儿童摄影师");

        mRecruitmentList.add(mEntity);

        mEntity = new RecruitmentEntity();
        mEntity.setRecruitment_Img(imgs1);
        mEntity.setRecruitment_City("潍坊");
        mEntity.setRecruitment_Date("昨天");
        mEntity.setRecruitment_Workdate("三年");
        mEntity.setRecruitment_Name("lovekevin2");
        mEntity.setRecruitment_Pay("5k-10k");
        mEntity.setRecruitment_Post("儿童摄影师");

        mRecruitmentList.add(mEntity);

        mEntity = new RecruitmentEntity();
        mEntity.setRecruitment_Img(imgs1);
        mEntity.setRecruitment_City("潍坊");
        mEntity.setRecruitment_Date("昨天");
        mEntity.setRecruitment_Workdate("三年");
        mEntity.setRecruitment_Name("lovekevin3");
        mEntity.setRecruitment_Pay("5k-10k");
        mEntity.setRecruitment_Post("儿童摄影师");

        mRecruitmentList.add(mEntity);

        mEntity = new RecruitmentEntity();
        mEntity.setRecruitment_Img(imgs1);
        mEntity.setRecruitment_City("潍坊");
        mEntity.setRecruitment_Date("昨天");
        mEntity.setRecruitment_Workdate("三年");
        mEntity.setRecruitment_Name("lovekevin4");
        mEntity.setRecruitment_Pay("5k-10k");
        mEntity.setRecruitment_Post("儿童摄影师");

        mRecruitmentList.add(mEntity);
        mRecruitmentList.add(mEntity);

        mAdapter = new RecruitmentAdapter(getContext(),mRecruitmentList);
        LinearLayoutManager layoutManage = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManage);
        recyclerView.setAdapter(mAdapter);

    }


    @OnClick(R.id.search_btn)
    public void searchUi() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

}
