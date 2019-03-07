package com.qyw.newdemo.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.base.SlidingActivity;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends SlidingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = (ListView) findViewById(R.id.list_view);
        List<String> list = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            list.add("第" + i + "条记录");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ListViewActivity.this, NormalActivity.class));
            }
        });
    }
    @Override
    protected int getLayoutResId() {
        //onCreate的方法中不需要写setContentView(),直接把当前activity的布局文件在这里返回就行了！
        return R.layout.activity_list_view;
    }

    @Override
    protected void initData() {

    }
}
