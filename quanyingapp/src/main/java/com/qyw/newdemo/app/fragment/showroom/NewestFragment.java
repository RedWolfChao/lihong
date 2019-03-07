package com.qyw.newdemo.app.fragment.showroom;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.adapter.ShowroomListAdapter;
import com.qyw.newdemo.app.entity.ShowRoomEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class NewestFragment extends Fragment {

    private List<ShowRoomEntity> mList;

    private int resourceId;

    @BindView(R.id.newest_listview)
    ListView mListView;

    private  ShowroomListAdapter mAdapter;

    private Unbinder unbinder;

    public static NewestFragment newInstance(){
        NewestFragment f = new NewestFragment();
        Bundle b = new Bundle();
        b.putString("fragment","NewestFragment");
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newest,container,false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        mList = new ArrayList<>();

        ShowRoomEntity mEntity = new ShowRoomEntity();

/*        private String head_Img;    //头像
        private String name_Text;   //用户名
        private String time_Text;   //作品存在时间
        private String production_Img;  //作品封面图片链接
        private String production_Title;    //作品标题
        private String praise_Text;     // 作品点赞数
        private String message_Text;    //  作品评论数
        private String production_Id;   //  作品ID*/

        mEntity.setHead_Img("https://hbimg.b0.upaiyun.com/bf41a39dd8e17b2c270968ee86ff09c27ce646f63464-4xpFzw_fw658");
        mEntity.setName_Text("邻家老王");
        mEntity.setTime_Text("10分钟前");
        mEntity.setProduction_Img("https://hbimg.b0.upaiyun.com/85c94deccd78273b920d84d9904e70901c5144bf31630-wlq6DS_fw658");
        mEntity.setProduction_Title("我是标题哦");
        mEntity.setPraise_Text("100");
        mEntity.setMessage_Text("200");
        mEntity.setProduction_Id("452");

        mList.add(mEntity);
        mEntity = new ShowRoomEntity();
        mEntity.setHead_Img("https://hbimg.b0.upaiyun.com/bf41a39dd8e17b2c270968ee86ff09c27ce646f63464-4xpFzw_fw658");
        mEntity.setName_Text("邻家美丽");
        mEntity.setTime_Text("10分钟前");
        mEntity.setProduction_Img("https://hbimg.b0.upaiyun.com/f33e529685b4f82f07db14c22610f403afe0071654fdb-pXXzOp_fw658");
        mEntity.setProduction_Title("我是标题哦");
        mEntity.setPraise_Text("100");
        mEntity.setMessage_Text("200");
        mEntity.setProduction_Id("452");

        mList.add(mEntity);

   mEntity = new ShowRoomEntity();
        mEntity.setHead_Img("https://hbimg.b0.upaiyun.com/bf41a39dd8e17b2c270968ee86ff09c27ce646f63464-4xpFzw_fw658");
        mEntity.setName_Text("邻家美丽");
        mEntity.setTime_Text("10分钟前");
        mEntity.setProduction_Img("https://hbimg.b0.upaiyun.com/62b9e2dc8c22149d998bbb32a8188e7414747e5162bf9-mjOMQD_fw658");
        mEntity.setProduction_Title("我是标题哦");
        mEntity.setPraise_Text("100");
        mEntity.setMessage_Text("200");
        mEntity.setProduction_Id("452");

        mList.add(mEntity);
        mList.add(mEntity);
        mList.add(mEntity);
        mList.add(mEntity);
        mList.add(mEntity);
        mList.add(mEntity);
        mList.add(mEntity);
        mList.add(mEntity);
        mList.add(mEntity);

        mAdapter = new ShowroomListAdapter(getContext(),resourceId,mList);
        mListView.setAdapter(mAdapter);
    }

}
