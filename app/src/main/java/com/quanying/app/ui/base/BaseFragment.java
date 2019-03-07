package com.quanying.app.ui.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    private View mContentView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(),container,false);

        ButterKnife.bind(this,mContentView);
        mContext = getContext();
        init();
        initView();
        initData();
        return mContentView;
    }

    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID
     *
     * @return 布局文件资源ID
     */
    protected abstract int setLayoutResourceID();

    /**
     * 一些View的相关操作
     */
    protected abstract void initView();

    /*
     * Activity的跳转
     */
    public void setIntentClass(Class<?> cla) {
        Intent intent = new Intent();
        intent.setClass(getMContext(), cla);
        startActivity(intent);
//        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 一些Data的相关操作
     */
    protected abstract void initData();

    /**
     * 此方法用于初始化成员变量及获取Intent传递过来的数据
     * 注意：这个方法中不能调用所有的View，因为View还没有被初始化，要使用View在initView方法中调用
     */
    protected void init() {}

    public View getContentView() {
        return mContentView;
    }

    public Context getMContext() {
        return mContext;
    }

    /**
     * @param title
     * @param buttitle
     */
    public void showBaseDialog(String title,String buttitle){



        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage(title)//设置对话框的标题
                //设置对话框的按钮
                .setPositiveButton(buttitle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

		/*Button zbbutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		zbbutton.setTextColor(getResources().getColor(R.color.colorBackground));
*/

        dialog.show();

    }

}
