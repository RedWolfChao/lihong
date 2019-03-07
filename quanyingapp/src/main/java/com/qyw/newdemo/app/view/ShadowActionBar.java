package com.qyw.newdemo.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qyw.newdemo.R;

/**
 * @作者 RedWolf
 * @时间 2017/8/17 0017 19:20
 * @简介 ShadowActionBar.java
 */


public class ShadowActionBar extends LinearLayout {
    private static final String TAG = "ShadowActionBar";
    //  属性
    //  左
    private Bitmap mLeftImage;
    private int mLeftW;
    private int mLeftH;
    private int mLeftScaleType;
    //  字
    private String mLeftText;
    private float mLeftTextSize;
    private int mLeftTextColor;
    //  右
    private Bitmap mRightImage;
    private int mRightW;
    private int mRightH;
    private int mRightScaleType;
    //  字
    private String mRightText;
    private float mRightTextSize;
    private int mRightTextColor;
    //  中
    private String mTitleText;
    private float mTitleTextSize;
    private int mTitleTextColor;

    private ImageView.ScaleType scaleTypes[] = {
            ImageView.ScaleType.MATRIX, ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START, ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END, ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP, ImageView.ScaleType.CENTER_INSIDE,
    };
    private ImageView mLeftImageView;
    private TextView mLeftTextView;
    private TextView mTitleTextView;
    private TextView mRightTextView;
    private ImageView mRightImageView;

    public ShadowActionBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initParam(context, attrs);
    }

    //  初始化各种属性
    private void initParam(Context context, @Nullable AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ShadowActionBar);
        //  Title
        mTitleText = ta.getString(R.styleable.ShadowActionBar_mTitleText);
        // 默认颜色设置为百色
        mTitleTextColor = ta.getColor(R.styleable.ShadowActionBar_mTitleTextColor, Color.WHITE);
        mTitleTextSize = ta.getDimensionPixelSize(R.styleable.ShadowActionBar_mTitleTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        //  LeftText
        mLeftText = ta.getString(R.styleable.ShadowActionBar_mLeftText);
        // 默认颜色设置为百色
        mLeftTextColor = ta.getColor(R.styleable.ShadowActionBar_mLeftTextColor, Color.WHITE);
        mLeftTextSize = ta.getDimensionPixelSize(R.styleable.ShadowActionBar_mLeftTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 8, getResources().getDisplayMetrics()));
        //  RightText
        mRightText = ta.getString(R.styleable.ShadowActionBar_mRightText);
        // 默认颜色设置为百色
        mRightTextColor = ta.getColor(R.styleable.ShadowActionBar_mRightTextColor, Color.WHITE);
        mRightTextSize = ta.getDimensionPixelSize(R.styleable.ShadowActionBar_mRightTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 8, getResources().getDisplayMetrics()));
        //  LeftImage
        mLeftImage = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.ShadowActionBar_mLeftImage, 0));
        mLeftH = ta.getDimensionPixelSize(R.styleable.ShadowActionBar_mLeftH, dp2px(32));
        mLeftW = ta.getDimensionPixelSize(R.styleable.ShadowActionBar_mLeftW, dp2px(32));
        mLeftScaleType = ta.getInt(R.styleable.ShadowActionBar_mLeftScaleType, 5);
        //  RightImage
        Log.i(TAG, "ShadowActionBar_mRightImage: ");
        mRightImage = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.ShadowActionBar_mRightImage, 0));
        Log.i(TAG, "mRightImage: " + mRightImage);
        mRightH = ta.getDimensionPixelSize(R.styleable.ShadowActionBar_mRightH, dp2px(32));
        mRightW = ta.getDimensionPixelSize(R.styleable.ShadowActionBar_mRightW, dp2px(32));
        mRightScaleType = ta.getInt(R.styleable.ShadowActionBar_mRightScaleType, 5);


        ta.recycle();
        //  左图
        if (mLeftImage != null)

        {
            mLeftImageView = new ImageView(context);
            mLeftImageView.setImageBitmap(mLeftImage);
            mLeftImageView.setScaleType(scaleTypes[mLeftScaleType]);
            //  左 图
            LayoutParams mLeftImageParams = new LayoutParams(mLeftW, mLeftH);
            mLeftImageParams.leftMargin = dp2px(16);
            addView(mLeftImageView, mLeftImageParams);
        }
        //  左字
        if (!TextUtils.isEmpty(mLeftText))

        {
            mLeftTextView = new TextView(context);
            mLeftTextView.setText(mLeftText);
            mLeftTextView.setTextColor(mLeftTextColor);
            mLeftTextView.setTextSize(mLeftTextSize);
            mLeftTextView.setGravity(Gravity.CENTER);
            //  左 字
            LayoutParams mLeftTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            if (mLeftImage != null) {
                mLeftTextParams.leftMargin = dp2px(4);
            } else {
                mLeftTextParams.leftMargin = dp2px(16);
            }
            addView(mLeftTextView, mLeftTextParams);
        }
        //  中间字
        if (!TextUtils.isEmpty(mTitleText))

        {
            mTitleTextView = new TextView(context);
            mTitleTextView.setText(mTitleText);
            mTitleTextView.setTextColor(mTitleTextColor);
            mTitleTextView.setTextSize(mTitleTextSize);
            mTitleTextView.setGravity(Gravity.CENTER);
            //  中 间
            LayoutParams mTitleTextParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
            addView(mTitleTextView, mTitleTextParams);
        }
        //  右字
        if (!TextUtils.isEmpty(mRightText))

        {
            mRightTextView = new TextView(context);
            mRightTextView.setText(mRightText);
            mRightTextView.setTextColor(mRightTextColor);
            mRightTextView.setTextSize(mRightTextSize);
            mRightTextView.setGravity(Gravity.CENTER);
            //  右 字
            LayoutParams mRightTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            if (mRightImage != null) {
                mRightTextParams.rightMargin = dp2px(4);
            } else {
                mRightTextParams.rightMargin = dp2px(16);
            }
            addView(mRightTextView, mRightTextParams);
        }
        //  右图
        if (mRightImage != null)

        {
            mRightImageView = new ImageView(context);
            mRightImageView.setImageBitmap(mRightImage);
            mRightImageView.setScaleType(scaleTypes[mRightScaleType]);
            //  右 图
            LayoutParams mRightImageParams = new LayoutParams(mRightW, mRightH);
            mRightImageParams.rightMargin = dp2px(16);
            addView(mRightImageView, mRightImageParams);
        }
        //  默认不显示
        setViewAlpha(1F);
    }

    private int dp2px(int def) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, def, getResources().getDisplayMetrics());
    }

    public void setViewAlpha(float alpha) {
        setAlpha(alpha);
        if (mLeftImageView != null) {
            mLeftImageView.setAlpha(alpha);
        }
        if (mTitleTextView != null) {
            mTitleTextView.setAlpha(alpha);
        }
        if (mRightTextView != null) {
            mRightTextView.setAlpha(alpha);
        }
        if (mLeftTextView != null) {
            mLeftTextView.setAlpha(alpha);
        }
        if (mRightImageView != null) {
            mRightImageView.setAlpha(alpha);
        }
    }
}
