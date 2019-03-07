package com.qyw.newdemo.app.fragment.showroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qyw.newdemo.R;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class AttentionFragment extends Fragment{

    public static AttentionFragment newInstance(){
        AttentionFragment f = new AttentionFragment();
        Bundle b = new Bundle();
        b.putString("fragment","AttentionFragment");
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attention,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
