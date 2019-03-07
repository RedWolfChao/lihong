package com.quanying.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quanying.app.R;
import com.quanying.app.adapter.MessageAdapter;
import com.quanying.app.bean.MessageEntity;

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


    public static MessageFragment newInstance(){
        MessageFragment f = new MessageFragment();
        Bundle b = new Bundle();
        b.putString("fragment","MessageFragment");
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_message,container,false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {

//        UserDao dao=new UserDao(getContext());
////        ContactList
//         Map map =  dao.getContactList();
//        Log.e("mapssssss",map.toString());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter();
    }

    private void initAdapter() {
        mEntity = new MessageEntity();
        mList = new ArrayList<>();

        mAdapter = new MessageAdapter(getContext(),mList);
        LinearLayoutManager layoutManage = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManage);
        recyclerView.setAdapter(mAdapter);
    }
}
