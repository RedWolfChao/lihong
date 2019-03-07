package com.qyw.newdemo.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.adapter.MessageAdapter;
import com.qyw.newdemo.app.entity.MessageEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MessageFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.message_recycle)
    RecyclerView recyclerView;
    private MessageEntity mEntity;
    private MessageAdapter mAdapter;
    private List<MessageEntity> mList;

    String imgs1 ="https://hbimg.b0.upaiyun.com/eccd09a46689da89d57cbfaeaeeedbe20297187562f25-gSRFDu_fw658";
    String imgs2 = "https://hbimg.b0.upaiyun.com/7554d4a8a5556382607ce7a9ec1d9e907980a7517b3f-MtgmqQ_fw658";

    public static MessageFragment newInstance(){
        MessageFragment f = new MessageFragment();
        Bundle b = new Bundle();
        b.putString("fragment","MessageFragment");
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,container,false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter();
    }

    private void initAdapter() {

        mEntity = new MessageEntity();
        mList = new ArrayList<>();

        mEntity.setContent_text("我只是个虚拟的数据源而已，来看看效果");
        mEntity.setUsericon_url(imgs1);
        mEntity.setMesId("16");
        mEntity.setTime_text("11:11");
        mEntity.setUser_name("二蛋大师兄");
        mList.add(mEntity);
        mEntity = new MessageEntity();
        mEntity.setContent_text("我只是个虚拟的数据源而已，来看看效果");
        mEntity.setUsericon_url(imgs2);
        mEntity.setMesId("16");
        mEntity.setTime_text("11:11");
        mEntity.setUser_name("二蛋大师兄2");
        mList.add(mEntity);
        mEntity = new MessageEntity();
        mEntity.setContent_text("我只是个虚拟的数据源而已，来看看效果");
        mEntity.setUsericon_url(imgs1);
        mEntity.setMesId("16");
        mEntity.setTime_text("11:11");
        mEntity.setUser_name("二蛋大师兄3");
        mList.add(mEntity);
        mEntity = new MessageEntity();
        mEntity.setContent_text("我只是个虚拟的数据源而已，来看看效果");
        mEntity.setUsericon_url(imgs2);
        mEntity.setMesId("16");
        mEntity.setTime_text("11:11");
        mEntity.setUser_name("二蛋大师兄4");
        mList.add(mEntity);
        mEntity = new MessageEntity();
        mEntity.setContent_text("我只是个虚拟的数据源而已，来看看效果");
        mEntity.setUsericon_url(imgs1);
        mEntity.setMesId("16");
        mEntity.setTime_text("11:11");
        mEntity.setUser_name("二蛋大师兄5");
        mList.add(mEntity);
        mList.add(mEntity);

        mAdapter = new MessageAdapter(getContext(),mList);
        LinearLayoutManager layoutManage = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManage);
        recyclerView.setAdapter(mAdapter);

    }

}
