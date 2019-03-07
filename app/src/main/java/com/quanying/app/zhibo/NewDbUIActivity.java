package com.quanying.app.zhibo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.ui.user.LoginActivity;
import com.quanying.app.utils.AppKeyBoardMgr;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import io.techery.progresshint.ProgressHintDelegate;
import okhttp3.Call;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;
import static com.quanying.app.R.id.luminance_seekbar;
import static com.quanying.app.R.id.recorf_seekbar;
import static com.quanying.app.R.id.surfaceView;
import static com.zhy.http.okhttp.OkHttpUtils.post;
import static io.techery.progresshint.ProgressHintDelegate.POPUP_FOLLOW;
import static io.techery.progresshint.ProgressHintDelegate.SeekBarHintDelegateHolder;

public class NewDbUIActivity extends FragmentActivity implements View.OnClickListener{

    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SS");

    private float speed = 1.0f;
    private TextView positionTxt;
    private TextView durationTxt;
    private SeekBar progressBar;
    private List<String> logStrs = new ArrayList<>();
    private boolean inSeek = false;



    private SurfaceView mSurfaceView;
    private RelativeLayout menus;
    @BindView(R.id.seekbar_ui)
    RelativeLayout seekbar_ui;

    private RelativeLayout hp_ui;
    private boolean mIsLand = false; // 是否是横屏
    private OnClickOrientationListener onClickOrientationListener;

    private LinearLayout play_title;
    private LinearLayout play_bottom;

    private ImageView play_btn;
    private ImageView play_btn_2;
    private ImageView lock_btn;
    private ImageView back_btn;

    private AliVcMediaPlayer mPlayer;
    private boolean mMute = false;
    private String mUrl = null;
    private boolean isCompleted = false;
    private  boolean isPlay =false;
    private boolean mClick = false; // 是否点击
    private ImageView full_srceen;      //全屏
    private ImageView back_full;      //全屏
    private ImageView back_full_2;      //全屏
    private boolean mClickLand = true; // 点击进入横屏
    private boolean mClickPort = true; // 点击进入竖屏

    //  是否锁定
    private boolean isLock = false;
    private boolean isShowGift = false;

    private boolean isPlayInit = false;

    @BindView(R.id.edit_ll)
    LinearLayout edit_ll;
    @BindView(R.id.play_ui_ts)
    LinearLayout play_ui_ts;
    @BindView(R.id.send)
    EditText send;

    private PlayAdapter fAdapter;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    @BindView(R.id.score_text)
    TextView score_text;
    @BindView(R.id.ts_title)
    TextView ts_title;
    @BindView(R.id.ts_button)
    Button ts_button;
    @BindView(R.id.send_gift)
    TextView send_gift;

    private ChatFragment mChatFragment;
    private CallBackFragment mCallBackFragment;
    private ParticularsFragment mParticularsFragment;

    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                            //tab的名称列表

    //礼物列表
    @BindView(R.id.grid_test)
    GridView mGridView;
    private GridViewAdapter mGridViewAdapter ;
    private LwListAdapter lwAdapter ;
    private ArrayList<GridItem> mGridData ;
    private ArrayList<GridListItem> mListData ;
    @BindView(R.id.gift_ui)
    LinearLayout gift_Ui;
    @BindView(R.id.gift_ui_h)
    LinearLayout gift_ui_h;

    @BindView(R.id.lw_list)
    ListView lw_list;

    @BindView(R.id.play_urls)
    TextView play_urls;

    //横屏礼物余额
    @BindView(R.id.h_price_text)
    TextView h_price_text;
    @BindView(R.id.send_gift_2)
    TextView send_gift_2;
    private int isBuyLocation = -1;
    private int isBuyLocation_h = -1;
    @BindView(R.id.closegift)
    View closegift;

    @BindViews({
        recorf_seekbar, R.id.luminance_seekbar
    }) List<SeekBarHintDelegateHolder> seekBars;
    private HashMap<String, String> params;
    private OrientationEventListener mOrientationListener;

    private final void startListener() {
        mOrientationListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int rotation) {

                if( rotation > 350 || rotation< 10 ) { //0度
                    if (isLock){
                        return;
                    }
                    rotation = 0;
                    Log.e("jiaodu","1");
                    if (mClick) {
                        if (mIsLand && !mClickLand) {
                            return;
                        } else {
                            mClickPort = true;
                            mClick = false;
                            mIsLand = false;
                        }
                    } else {
                        if (mIsLand) {

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
                            hp_ui.setVisibility(View.GONE);                            menus.setVisibility(View.VISIBLE);
                            edit_ll.setVisibility(View.VISIBLE);

                            ViewGroup.LayoutParams pa_rl = mSurfaceView.getLayoutParams();
                            pa_rl.height = (int) (getResources().getDisplayMetrics().heightPixels / 16f * 9f);
                            pa_rl.width = ActionBar.LayoutParams.MATCH_PARENT;
                            mSurfaceView.setLayoutParams(pa_rl);

                            ViewGroup.LayoutParams pa_rl_2 = seekbar_ui.getLayoutParams();
                            pa_rl_2.height = (int) (getResources().getDisplayMetrics().heightPixels / 16f * 9f);
                            pa_rl_2.width = ActionBar.LayoutParams.MATCH_PARENT;
                            seekbar_ui.setLayoutParams(pa_rl_2);

                            ViewGroup.LayoutParams menus_rl = menus.getLayoutParams();
                            menus_rl.height = (int) (getResources().getDisplayMetrics().heightPixels / 16f * 9f);
                            menus_rl.width = ActionBar.LayoutParams.MATCH_PARENT;
                            menus.setLayoutParams(menus_rl);
                            //                    横屏返回半屏业务逻辑
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            mIsLand = false;
                            mClickPort = false;

                            mClick = false;
                        }
                    }
                }
                else if( rotation > 80 &&rotation < 100 ) { //90度
                    if (isLock){
                        return;
                    }
                    rotation= 90;
                    Log.e("jiaodu","2");
                    if (mClick) {
                        if (!mIsLand && !mClickPort) {
                            return;
                        } else {
                            mClickLand = true;
                            mClick = false;
                            mIsLand = true;
                        }
                    } else {
                        if (!mIsLand) {

                            hp_ui.setVisibility(View.VISIBLE);
                            menus.setVisibility(View.GONE);
                            edit_ll.setVisibility(View.GONE);
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) ;//隐藏状态栏

                            ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
                            lp.height = ActionBar.LayoutParams.MATCH_PARENT;
                            lp.width = ActionBar.LayoutParams.MATCH_PARENT;
                            mSurfaceView.setLayoutParams(lp);

                            ViewGroup.LayoutParams lp_2 = seekbar_ui.getLayoutParams();
                            lp_2.height = ActionBar.LayoutParams.MATCH_PARENT;
                            lp_2.width = ActionBar.LayoutParams.MATCH_PARENT;
                            seekbar_ui.setLayoutParams(lp_2);
                            seekbar_ui.setVisibility(View.VISIBLE);
                            mClickLand = false;
                            play_bottom.setVisibility(View.VISIBLE);
                            mIsLand = true;
                            mClick = false;
                        }
                    }
                }
                else if( rotation > 170 &&rotation < 190 ) { //180度
                    rotation= 180;
                    if (isLock){
                        return;
                    }

                    Log.e("jiaodu","3");
                    if (mClick) {
                        if (mIsLand && !mClickLand) {
                            return;
                        } else {
                            mClickPort = true;
                            mClick = false;
                            mIsLand = false;
                        }
                    } else {
                        if (mIsLand) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
                            hp_ui.setVisibility(View.GONE);
                            menus.setVisibility(View.VISIBLE);
                            edit_ll.setVisibility(View.VISIBLE);

                            ViewGroup.LayoutParams pa_rl = mSurfaceView.getLayoutParams();
                            pa_rl.height = (int) (getResources().getDisplayMetrics().heightPixels / 16f * 9f);
                            pa_rl.width = ActionBar.LayoutParams.MATCH_PARENT;
                            mSurfaceView.setLayoutParams(pa_rl);

                            ViewGroup.LayoutParams pa_rl_2 = seekbar_ui.getLayoutParams();
                            pa_rl_2.height = (int) (getResources().getDisplayMetrics().heightPixels / 16f * 9f);
                            pa_rl_2.width = ActionBar.LayoutParams.MATCH_PARENT;
                            seekbar_ui.setLayoutParams(pa_rl_2);
                            seekbar_ui.setVisibility(View.VISIBLE);
                            ViewGroup.LayoutParams menus_rl = menus.getLayoutParams();
                            menus_rl.height = (int) (getResources().getDisplayMetrics().heightPixels / 16f * 9f);
                            menus_rl.width = ActionBar.LayoutParams.MATCH_PARENT;
                            menus.setLayoutParams(menus_rl);
                            //                    横屏返回半屏业务逻辑
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            mIsLand = false;
                            mClickPort = false;
                            mIsLand = false;
                            mClick = false;
                        }
                    }
                }
                else if( rotation > 260 &&rotation < 280 ) { //270度
                    if (isLock){
                        return;
                    }
                    rotation= 270;

                    Log.e("jiaodu","4");
                    if (mClick) {
                        if (!mIsLand && !mClickPort) {
                            return;
                        } else {
                            mClickLand = true;
                            mClick = false;
                            mIsLand = true;
                        }
                    } else {
                        if (!mIsLand) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            hp_ui.setVisibility(View.VISIBLE);
                            menus.setVisibility(View.GONE);
                            edit_ll.setVisibility(View.GONE);
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) ;//隐藏状态栏

                            ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
                            lp.height = ActionBar.LayoutParams.MATCH_PARENT;
                            lp.width = ActionBar.LayoutParams.MATCH_PARENT;
                            mSurfaceView.setLayoutParams(lp);

                            ViewGroup.LayoutParams lp_2 = seekbar_ui.getLayoutParams();
                            lp_2.height = ActionBar.LayoutParams.MATCH_PARENT;
                            lp_2.width = ActionBar.LayoutParams.MATCH_PARENT;
                            seekbar_ui.setLayoutParams(lp_2);
                            play_bottom.setVisibility(View.VISIBLE);
                            mClickLand = false;
                            mIsLand = true;
                            mClick = false;
                        }
                    }
                }



            }
        };
        mOrientationListener.enable();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdb_ui);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        MyApplication.PLAYSTATUS = 2+"";
        initView();
        initUi();

    }

    @Override protected void onResume() {
        super.onResume();
        startListener();
        if (onClickOrientationListener != null) {
            onClickOrientationListener.portrait();
        }
        AppKeyBoardMgr.closeKeybord(send,NewDbUIActivity.this);
        initOper();
        initVodPlayer();
        //礼物列表
        //initGift();
        //getScore();
        SeekBar bar = ButterKnife.findById(this, recorf_seekbar);
        bar.setProgress(mPlayer.getVolume());
        //brightnessBar.setProgress(mPlayer.getScreenBrightness());
        SeekBar luminance_seekbar = ButterKnife.findById(this, R.id.luminance_seekbar);
        luminance_seekbar.setProgress(mPlayer.getScreenBrightness());
        initControls();

    }

    private void initPlayUI() {

        params = new HashMap<String,String>();
        params.put( "Cookie",MyApplication.Cookies+"");

        OkHttpUtils
            .post()
            .url("http://tv.7192.com/index/api/mv")
            .headers(params)
            .addParams("mvid", MyApplication.PLAYCALLBACKID+"")
            .addParams("cid", MyApplication.PLAYCID+"")
            .build()
            .execute(new StringCallback() {
                @Override public void onError(Call call, Exception e, int id) {

                }

                @Override public void onResponse(String response, int id) {
                    Log.e("initdb","id="+MyApplication.PLAYCALLBACKID+":"+MyApplication.PLAYCID);
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String code= jsonObject.getString("errcode");
                        if(code.equals("200")){
                            String title = jsonObject.getString("title");
                            play_urls.setText(title);
                            mUrl = jsonObject.getString("playUrl");
                            isPlayInit = true;
                            play_btn.setImageDrawable(getResources().getDrawable(R.drawable.zt_));
                            play_btn_2.setImageDrawable(getResources().getDrawable(R.drawable.zt_));
                            start();
                            isPlay = true;
                        }
                       else if(code.equals("40002")){
                            play_ui_ts.setVisibility(View.VISIBLE);
                            String errmsg = jsonObject.getString("errmsg");
                            ts_title.setText(errmsg);
                            ts_button.setVisibility(View.VISIBLE);
                            ts_button.setText("立即登录");
                            ts_button.setOnClickListener(new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    startActivity(new Intent(NewDbUIActivity.this,LoginActivity.class));
                                }
                            });
                        }else if(code.equals("40199")){
                            play_ui_ts.setVisibility(View.VISIBLE);
                            String errmsg = jsonObject.getString("errmsg");
                            ts_title.setText(errmsg);
                            ts_button.setVisibility(View.VISIBLE);
                            ts_button.setText("预约报名");
                        }else if(code.equals("40005")){
                            play_ui_ts.setVisibility(View.VISIBLE);
                            String errmsg = jsonObject.getString("errmsg");
                            ts_title.setText(errmsg);
                            ts_button.setVisibility(View.VISIBLE);
                            ts_button.setText("立即购买");
                            ts_button.setOnClickListener(new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    startActivity(new Intent(NewDbUIActivity.this,PayPlayWebActivity.class));
                                }
                            });
                        }else{
                            play_ui_ts.setVisibility(View.VISIBLE);
                            String errmsg = jsonObject.getString("errmsg");
                            ts_title.setText(errmsg);
                            ts_button.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
    }

    //竖屏礼物
    private void initGift() {
        params = new HashMap<String,String>();
        params.put( "Cookie",MyApplication.Cookies+"");
        post()
            .url("http://tv.7192.com/index/api/gifts")
            .headers(params)
            .build()
            .execute(new StringCallback() {
                @Override public void onError(Call call, Exception e, int id) {
                    Log.e("gifterror",call.toString());
                }

                @Override public void onResponse(String response, int id) {
                    Log.e("shifou","yes");
                    Log.e("gift",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String errcode = jsonObject.getString("errcode");
                        mGridData = new ArrayList<GridItem>();
                        mListData = new ArrayList<GridListItem>();
                        GridItem items ;
                        GridListItem listitems ;
                        if(errcode.equals("200")){
                            String score = jsonObject.getString("score");
                            score_text.setText(score);
                            h_price_text.setText(score);

                            JSONArray data = jsonObject.getJSONArray("data");
                            for(int i=0;i<data.length();i++){
                                JSONObject giftData = data.getJSONObject(i);
                                String gift_Id = giftData.getString("id");
                                String title = giftData.getString("title");
                                String fee = giftData.getString("price");
                                String url = giftData.getString("url");
                                items = new GridItem();
                                items.setGrif_id(gift_Id);
                                items.setImage(url);
                                items.setPrice(fee);
                                items.setTitle(title);

                                listitems = new GridListItem();
                                listitems.setGrif_id(gift_Id);
                                listitems.setImage(url);
                                listitems.setPrice(fee);
                                listitems.setTitle(title);
                                mGridData.add(items);
                                mListData.add(listitems);
                            }
                            mGridViewAdapter = new GridViewAdapter(NewDbUIActivity.this,0, mGridData,mGridView);
                            lwAdapter = new LwListAdapter(NewDbUIActivity.this,0, mListData,mGridView);
                            mGridView.setAdapter(mGridViewAdapter);
                            lw_list.setAdapter(lwAdapter);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

    }

    private void initControls() {
        //        初始化Fragment
        mChatFragment = new ChatFragment();
        mParticularsFragment = new ParticularsFragment();
        mCallBackFragment = new CallBackFragment();
        //         Fragment 装入容器
        list_fragment = new ArrayList<>();
        list_fragment.add(mChatFragment);
        list_fragment.add(mParticularsFragment);
        list_fragment.add(mCallBackFragment);
        //          将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("聊天");
        list_title.add("详情");
        list_title.add("回播");
        //        设置tablayout样式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //         为TabLayout添加tab名称
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(2)));

        //        tabLayout.setIndicator(tabLayout, 10, 10);
        fAdapter = new PlayAdapter(NewDbUIActivity.this.getSupportFragmentManager(),list_fragment,list_title);
        //viewpager加载adapter
        viewPager.setAdapter(fAdapter);
        viewPager.setOffscreenPageLimit(3);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager

        send_gift.setClickable(false);

        tabLayout.setupWithViewPager(viewPager);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("shifou","position"+position);
                if(isBuyLocation>-1){
                    if(isBuyLocation == position){
                        send_gift.setClickable(false);
                        mGridData.get(position).setIsBuy(-1);
                        mGridViewAdapter.notifyDataSetChanged();
                        isBuyLocation = -1;
                        send_gift.setBackgroundColor(android.graphics.Color.parseColor("#d7d7d7"));
                        return;
                    }
                    send_gift.setClickable(true);
                    send_gift.setBackgroundColor(android.graphics.Color.parseColor("#FFFF0000"));
                    mGridData.get(isBuyLocation).setIsBuy(-1);
                    mGridData.get(position).setIsBuy(1);
                    mGridViewAdapter.notifyDataSetChanged();
                    isBuyLocation =position;

                }else{

                    //send_gift.
                        send_gift.setBackgroundColor(android.graphics.Color.parseColor("#FFFF0000"));
                    send_gift.setClickable(true);
                    isBuyLocation =position;
                    mGridData.get(position).setIsBuy(1);
                    mGridViewAdapter.notifyDataSetChanged();

                }
            }
        });
        lw_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isBuyLocation_h>-1){
                    if(isBuyLocation_h == position){
                        send_gift_2.setClickable(false);
                        mListData.get(position).setIsBuy(-1);
                        lwAdapter.notifyDataSetChanged();
                        isBuyLocation_h = -1;
                        send_gift_2.setBackgroundColor(android.graphics.Color.parseColor("#d7d7d7"));
                        return;
                    }

                    send_gift_2.setClickable(true);
                    send_gift_2.setBackgroundColor(android.graphics.Color.parseColor("#FFFF0000"));
                    mListData.get(isBuyLocation_h).setIsBuy(-1);
                    mListData.get(position).setIsBuy(1);
                    lwAdapter.notifyDataSetChanged();
                    isBuyLocation_h =position;

                }else{
                    //send_gift.
                    send_gift_2.setBackgroundColor(android.graphics.Color.parseColor("#FFFF0000"));
                    send_gift_2.setClickable(true);
                    isBuyLocation_h =position;
                    mListData.get(position).setIsBuy(1);
                    lwAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    private void initOper() {
        full_srceen.setOnClickListener(this);
        play_btn .setOnClickListener(this);
        play_btn_2 .setOnClickListener(this);
        lock_btn .setOnClickListener(this);
        back_btn .setOnClickListener(this);
        send_gift .setOnClickListener(this);
        send_gift_2 .setOnClickListener(this);

        mSurfaceView .setOnClickListener(this);
        back_full .setOnClickListener(this);
        back_full_2 .setOnClickListener(this);

        /*
        * 进度条
        * */

        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mPlayer != null) {
                    mPlayer.seekTo(seekBar.getProgress());
                    if (isCompleted) {
                        inSeek = false;
                    } else {
                        inSeek = true;
                    }

                    Log.d("lfj0929", "onStopTrackingTouch , inSeek= " + inSeek);
                }
            }
        });

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                //                holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
                holder.setKeepScreenOn(true);
                Log.e("lfj0930", "surfaceCreated ");
                // Important: surfaceView changed from background to front, we need reset surface to mediaplayer.
                // 对于从后台切换到前台,需要重设surface;部分手机锁屏也会做前后台切换的处理
                if (mPlayer != null) {
                    mPlayer.setVideoSurface(holder.getSurface());
                }

            }

            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

                Log.e("lfj0930", "surfaceChanged ");
                if (mPlayer != null) {
                    mPlayer.setSurfaceChanged();
                }
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.e("lfj0930", "surfaceDestroyed ");
                //                if (mPlayer != null) {
                //                    mPlayer.releaseVideoSurface();
                //                }
            }
        });



        //拦截键盘确认
        send.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                String text_ = send.getText().toString().trim();
                if(TextUtils.isEmpty(text_)){
                    Toast.makeText(NewDbUIActivity.this, "输入内容为空", Toast.LENGTH_SHORT).show();
                   return false;
                }else{
                    params = new HashMap<String,String>();
                    params.put( "Cookie",MyApplication.Cookies);
                OkHttpUtils
                    .post()
                    .headers(params)
                    .url("http://tv.7192.com/index/api/note")
                    .addParams("infoid", "23")
                    .addParams("text",text_+ "")
                    .build()
                    .execute(new StringCallback() {
                        @Override public void onError(Call call, Exception e, int id) {

                        }

                        @Override public void onResponse(String response, int id) {
                            Log.e("kevins!",response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String errcode = jsonObject.getString("errcode");
                                if(errcode.equals("200")){
                                    send.setText("");
                                    AppKeyBoardMgr.closeKeybord(send,NewDbUIActivity.this);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                return true;
            }
        });

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
                holder.setKeepScreenOn(true);
                Log.d(TAG, "AlivcPlayer onSurfaceCreated." + mPlayer);

                // Important: surfaceView changed from background to front, we need reset surface to mediaplayer.
                // 对于从后台切换到前台,需要重设surface;部分手机锁屏也会做前后台切换的处理
                if (mPlayer != null) {
                    mPlayer.setVideoSurface(mSurfaceView.getHolder().getSurface());
                }

                Log.d(TAG, "AlivcPlayeron SurfaceCreated over.");
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
                Log.d(TAG, "onSurfaceChanged is valid ? " + holder.getSurface().isValid());
                if (mPlayer != null)
                    mPlayer.setSurfaceChanged();
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG, "onSurfaceDestroy.");
            }
        });
    }

    private void initView() {
        mSurfaceView = (SurfaceView) findViewById(surfaceView);
        menus = (RelativeLayout) findViewById(R.id.menus);
        hp_ui = (RelativeLayout) findViewById(R.id.hp_ui);
        play_btn = (ImageView) findViewById(R.id.play_btn);
        play_btn_2 = (ImageView) findViewById(R.id.play_btn_2);
        play_bottom = (LinearLayout) findViewById(R.id.play_bottom);
        play_title = (LinearLayout) findViewById(R.id.play_title);
        lock_btn = (ImageView) findViewById(R.id.lock_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        full_srceen = (ImageView) findViewById(R.id.full_srceen);
        back_full = (ImageView) findViewById(R.id.back_full);
        back_full_2 = (ImageView) findViewById(R.id.back_full_2);

        positionTxt = (TextView) findViewById(R.id.currentPosition);
        durationTxt = (TextView) findViewById(R.id.totalDuration);
        progressBar = (SeekBar) findViewById(R.id.progress);

        for (SeekBarHintDelegateHolder seekBar : seekBars) {
            seekBar.getHintDelegate().setPopupStyle(POPUP_FOLLOW);
        }

        closegift.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                gift_Ui.setVisibility(View.GONE);
            }
        });

        //音量动态调节
        ((SeekBarHintDelegateHolder) findViewById(recorf_seekbar)).getHintDelegate()
            .setHintAdapter(new ProgressHintDelegate.SeekBarHintAdapter() {
                @Override public String getHint(SeekBar seekBar, int progress) {
                    if (seekBar!=null && mPlayer != null) {
                        mPlayer.setVolume(progress);
                        //muteOnBtn.setChecked(false);
                    }
                    return String.valueOf(progress);
                }
            });
        ((SeekBarHintDelegateHolder) findViewById(R.id.luminance_seekbar)).getHintDelegate()
            .setHintAdapter(new ProgressHintDelegate.SeekBarHintAdapter() {
                @Override public String getHint(SeekBar seekBar, int progress) {
                    if (seekBar!=null && mPlayer != null) {
                        mPlayer.setScreenBrightness(progress);
                        //muteOnBtn.setChecked(false);
                    }
                    return String.valueOf(progress);
                }
            });
    }

    private void initUi() {
        //      动态设置，播放器组件高度设为屏幕16:9比例
        ViewGroup.LayoutParams pa_rl = mSurfaceView.getLayoutParams();
        pa_rl.height = (int) (getResources().getDisplayMetrics().widthPixels / 16f * 9f);
        pa_rl.width = ActionBar.LayoutParams.MATCH_PARENT;
        mSurfaceView.setLayoutParams(pa_rl);

        ViewGroup.LayoutParams menus_rl = menus.getLayoutParams();
        menus_rl.height = (int) (getResources().getDisplayMetrics().widthPixels / 16f * 9f);
        menus_rl.width = ActionBar.LayoutParams.MATCH_PARENT;
        menus.setLayoutParams(menus_rl);

        ViewGroup.LayoutParams seekbar_rl = seekbar_ui.getLayoutParams();
        seekbar_rl.height = (int) (getResources().getDisplayMetrics().widthPixels / 16f * 9f);
        seekbar_rl.width = ActionBar.LayoutParams.MATCH_PARENT;
        seekbar_ui.setLayoutParams(seekbar_rl);

        ViewGroup.LayoutParams play_rl = play_ui_ts.getLayoutParams();
        play_rl.height = (int) (getResources().getDisplayMetrics().widthPixels / 16f * 9f);
        play_rl.width = ActionBar.LayoutParams.MATCH_PARENT;
        play_ui_ts.setLayoutParams(play_rl);
//      初始化一下竖屏操作

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        edit_ll.setVisibility(View.VISIBLE);
        mIsLand = false;
        mClickPort = false;

        params = new HashMap<String,String>();
        params.put( "Cookie",MyApplication.Cookies);

    }
    private void initVodPlayer() {
        mPlayer = new AliVcMediaPlayer(this, mSurfaceView);

        mPlayer.setPreparedListener(new MediaPlayer.MediaPlayerPreparedListener() {
            @Override
            public void onPrepared() {

                mPlayer.play();

            }
        });
        mPlayer.setPcmDataListener(new MediaPlayer.MediaPlayerPcmDataListener() {
            @Override
            public void onPcmData(byte[] bytes, int i) {

            }
        });
        mPlayer.setFrameInfoListener(new MyFrameInfoListener(this));
        mPlayer.setErrorListener(new MediaPlayer.MediaPlayerErrorListener() {
            @Override
            public void onError(int i, String msg) {
                play_ui_ts.setVisibility(View.VISIBLE);
                String errmsg = "播放出错，请检查网络";
                ts_title.setText(errmsg);
                ts_button.setVisibility(View.GONE);
                Toast.makeText(NewDbUIActivity.this.getApplicationContext(), "视频出错", Toast.LENGTH_SHORT).show();
                mPlayer.stop();

            }
        });
        mPlayer.setCompletedListener(new MediaPlayer.MediaPlayerCompletedListener() {
            @Override
            public void onCompleted() {
                isCompleted = true;
                showVideoProgressInfo();
                stopUpdateTimer();
            }
        });
        mPlayer.setSeekCompleteListener(new MediaPlayer.MediaPlayerSeekCompleteListener() {
            @Override
            public void onSeekCompleted() {
                //logStrs.add(format.format(new Date()) + getString(R.string.log_seek_completed));
                inSeek = false;
                Log.d("lfj0929", "MediaPlayerSeekCompleteListener inSeek = " + inSeek);
            }
        });
        mPlayer.setStoppedListener(new MediaPlayer.MediaPlayerStoppedListener() {
            @Override
            public void onStopped() {
                //logStrs.add(format.format(new Date()) + getString(R.string.log_play_stopped));
            }
        });
        mPlayer.setBufferingUpdateListener(new MediaPlayer.MediaPlayerBufferingUpdateListener() {
            @Override
            public void onBufferingUpdateListener(int percent) {
                updateBufferingProgress(percent);
            }
        });
        mPlayer.enableNativeLog();

    }


    private static class MyFrameInfoListener implements MediaPlayer.MediaPlayerFrameInfoListener {

        private WeakReference<NewDbUIActivity> vodModeActivityWeakReference;

        public MyFrameInfoListener(NewDbUIActivity vodModeActivity) {
            vodModeActivityWeakReference = new WeakReference<NewDbUIActivity>(vodModeActivity);
        }

        @Override
        public void onFrameInfoListener() {
            NewDbUIActivity vodModeActivity = vodModeActivityWeakReference.get();
            if (vodModeActivity != null) {
                vodModeActivity.onFrameShow();
            }
        }
    }


    private void onFrameShow() {
        inSeek = false;
        showVideoProgressInfo();
        updateLogInfo();
    }
    private void updateLogInfo() {
        Map<String, String> debugInfo = mPlayer.getAllDebugInfo();
        long createPts = 0;
        if (debugInfo.get("create_player") != null) {
            String time = debugInfo.get("create_player");
            createPts = (long) Double.parseDouble(time);
            //logStrs.add(format.format(new Date(createPts)) + getString(R.string.log_player_create_success));
        }
        if (debugInfo.get("open-url") != null) {
            String time = debugInfo.get("open-url");
            long openPts = (long) Double.parseDouble(time) + createPts;
            //logStrs.add(format.format(new Date(openPts)) + getString(R.string.log_open_url_success));
        }
        if (debugInfo.get("find-stream") != null) {
            String time = debugInfo.get("find-stream");
            long findPts = (long) Double.parseDouble(time) + createPts;
            //logStrs.add(format.format(new Date(findPts)) + getString(R.string.log_request_stream_success));
        }
        if (debugInfo.get("open-stream") != null) {
            String time = debugInfo.get("open-stream");
            long openPts = (long) Double.parseDouble(time) + createPts;
            //logStrs.add(format.format(new Date(openPts)) + getString(R.string.log_start_open_stream));
        }
        //logStrs.add(format.format(new Date()) + getString(R.string.log_first_frame_played));
    }
    private void updateBufferingProgress(int percent) {
        int duration = (int) mPlayer.getDuration();
        int secondaryProgress = (int) (duration * percent * 1.0f / 100);
        progressBar.setSecondaryProgress(secondaryProgress);
    }
    private void showVideoProgressInfo() {
        int curPosition = (int) mPlayer.getCurrentPosition();
        int duration = (int) mPlayer.getDuration();
        int bufferPosition = mPlayer.getBufferPosition();
        Log.d("lfj0929", "curPosition = " + curPosition + " , duration = " + duration + " ， inSeek = " + inSeek);
        if ((mPlayer.isPlaying())
            && !inSeek) {

            positionTxt.setText(Formatter.formatTime(curPosition));
            durationTxt.setText(Formatter.formatTime(duration));
            progressBar.setMax(duration);
            progressBar.setSecondaryProgress(bufferPosition);
            progressBar.setProgress(curPosition);
        }

        startUpdateTimer();
    }
    private void startUpdateTimer() {
        progressUpdateTimer.removeMessages(0);
        progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
    }

    private void stopUpdateTimer() {
        progressUpdateTimer.removeMessages(0);
    }

    private Handler progressUpdateTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            showVideoProgressInfo();
        }
    };
    private void savePlayerState() {
        if (mPlayer.isPlaying()) {
            //we pause the player for not playing on the background
            // 不可见，暂停播放器
            pause();
        }
    }

    public static int getWight(Context mContext) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }


    private void replay() {
        if(isPlay){
            play_btn.setImageDrawable(getResources().getDrawable(R.drawable.bf_));
            play_btn_2.setImageDrawable(getResources().getDrawable(R.drawable.bf_));
            isPlay = false;
            pause();
        }else{
            play_btn.setImageDrawable(getResources().getDrawable(R.drawable.zt_));
            play_btn_2.setImageDrawable(getResources().getDrawable(R.drawable.zt_));
            resume();
            isPlay = true;
        }
    }

    private void setMaxBufferDuration() {

        int maxBD = 5000;

        if (mPlayer != null) {
            mPlayer.setMaxBufferDuration(maxBD);
        }
    }



    private void start() {
        Log.e("jiazaichengg","id="+MyApplication.PLAYCALLBACKID+":"+MyApplication.PLAYCID);
        if (mPlayer != null) {
            try{
                mPlayer.prepareToPlay(mUrl);
                Log.e("murls",mUrl);}
            catch (NullPointerException ex){
                Log.e("startex","123");
            }
        }
    }

    private void resume() {
        if (mPlayer != null) {

            mPlayer.play();

        }
    }


    private void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    private void stop() {
        if (mPlayer != null) {
            Log.e("mPlayer","::"+mPlayer.toString());
            try{
            mPlayer.stop();}
            catch (NullPointerException ex){
                Log.e("mPlayer","走了"+mPlayer.toString());
            }
        }
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.full_srceen:
                fullScreenOper();
                break;
            case R.id.back_full:
                fullScreenOper();
                break;
            case R.id.back_full_2:
                fullScreenOper();
                break;
            case R.id.surfaceView:

                if(play_ui_ts.getVisibility() == View.VISIBLE){
                    return;
                }
                if(gift_ui_h.getVisibility() == View.VISIBLE){
                    showGift();
                    return;
                }

                if(gift_Ui.getVisibility()==View.VISIBLE){
                    gift_Ui.setVisibility(View.GONE);
                    return;
                }

                if(mIsLand){
                    if(hp_ui.getVisibility()==View.VISIBLE){
                        hp_ui.setVisibility(View.GONE);

                        play_bottom.setVisibility(View.GONE);
                    }else{
                        hp_ui.setVisibility(View.VISIBLE);
                        play_bottom.setVisibility(View.VISIBLE);
                    }
                }else{
                    if(!isPlayInit){
                        return;
                    }
                    if(menus.getVisibility()==View.VISIBLE){
                        menus.setVisibility(View.GONE);
                        play_bottom.setVisibility(View.GONE);
                    }else{
                        menus.setVisibility(View.VISIBLE);
                        play_bottom.setVisibility(View.VISIBLE);
                    }
                }

                break;
            case R.id.play_btn:

                start();
                if (mMute) {
                    mPlayer.setMuteMode(mMute);
                }
                mPlayer.setPlaySpeed(speed);
//                brightnessBar.setProgress(mPlayer.getScreenBrightness());
//                volumeBar.setProgress(mPlayer.getVolume());
                break;
            case R.id.back_btn:
                finish();
                break;

            case R.id.send_gift:
                sendGift();
                break;
            case R.id.send_gift_2:
                sendGift_h();
                break;
            case R.id.play_btn_2:
                setMaxBufferDuration();
                replay();
                if (mMute) {
                    mPlayer.setMuteMode(mMute);
                }
//                brightnessBar.setProgress(mPlayer.getScreenBrightness());
//                volumeBar.setProgress(mPlayer.getVolume());
                break;
            case R.id.lock_btn:
                lock();
                break;

        }
    }
    /*
    * 发送礼物
    * */
    private void sendGift() {
        params = new HashMap<String,String>();
        params.put( "Cookie",MyApplication.Cookies+"");
        OkHttpUtils
            .post()
            .url("http://tv.7192.com/index/api/sendgift")
            .headers(params)
            .addParams("id", mGridData.get(isBuyLocation).getGrif_id())
            .addParams("num", "1")
            .addParams("liveid", "23")
            .addParams("islive", "2")
            .build()
            .execute(new StringCallback() {
                @Override public void onError(Call call, Exception e, int id) {
                    Log.e("gifterror",call.toString());
                }

                @Override public void onResponse(String response, int id) {
                    Log.e("gift",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String errcode = jsonObject.getString("errcode");
                        if(errcode.equals("200")){
                            String errmsg = jsonObject.getString("errmsg");
                            Toast.makeText(NewDbUIActivity.this,""+errmsg,Toast.LENGTH_LONG).show();
                            getScore();
                        }else{
                            String errmsg = jsonObject.getString("errmsg");
                            Toast.makeText(NewDbUIActivity.this,""+errmsg,Toast.LENGTH_LONG).show();

                            getScore();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

    }
    //横屏发送礼物
    private void sendGift_h() {
        params = new HashMap<String,String>();
        params.put( "Cookie",MyApplication.Cookies+"");
        OkHttpUtils
            .post()
            .url("http://tv.7192.com/index/api/sendgift")
            .headers(params)
            .addParams("id", mListData.get(isBuyLocation_h).getGrif_id())
            .addParams("num", "1")
            .addParams("liveid", "23")
            .addParams("islive", "2")
            .build()
            .execute(new StringCallback() {
                @Override public void onError(Call call, Exception e, int id) {
                    Log.e("gifterror",call.toString());
                }

                @Override public void onResponse(String response, int id) {
                    Log.e("gift",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String errcode = jsonObject.getString("errcode");
                        if(errcode.equals("200")){
                            String errmsg = jsonObject.getString("errmsg");
                            Toast.makeText(NewDbUIActivity.this,""+errmsg,Toast.LENGTH_LONG).show();
                            getScore();
                        }else{
                            String errmsg = jsonObject.getString("errmsg");
                            Toast.makeText(NewDbUIActivity.this,""+errmsg,Toast.LENGTH_LONG).show();

                            getScore();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

    }

    //获取当前金币余额
    private void getScore(){
        params = new HashMap<String,String>();
        params.put( "Cookie",MyApplication.Cookies+"");
        post()
            .url("http://tv.7192.com/index/api/gifts")
            .headers(params)
            .build()
            .execute(new StringCallback() {
                @Override public void onError(Call call, Exception e, int id) {
                    Log.e("gifterror",call.toString());
                }

                @Override public void onResponse(String response, int id) {
                    Log.e("sendgift",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String errcode = jsonObject.getString("errcode");
                        if(errcode.equals("200")){
                            String score = jsonObject.getString("score");
                            score_text.setText(score);
                            h_price_text.setText(score);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

    }

    //锁定屏幕
    private void lock() {
        if(isLock){
            lock_btn.setImageDrawable(getResources().getDrawable(R.drawable.sd_));
            play_bottom.setVisibility(View.VISIBLE);
            play_title.setVisibility(View.VISIBLE);
            SeekBar bar1 = ButterKnife.findById(this, recorf_seekbar);
            bar1.setVisibility(View.VISIBLE);
            SeekBar bar2= ButterKnife.findById(this, luminance_seekbar);
            bar2.setVisibility(View.VISIBLE);
            play_bottom.setVisibility(View.VISIBLE);
            isLock = false;
        }else{
            lock_btn.setImageDrawable(getResources().getDrawable(R.drawable.js_));
            play_bottom.setVisibility(View.GONE);
            play_title.setVisibility(View.GONE);
            SeekBar bar1 = ButterKnife.findById(this, recorf_seekbar);
            bar1.setVisibility(View.GONE);
            SeekBar bar2= ButterKnife.findById(this, luminance_seekbar);
            bar2.setVisibility(View.GONE);
            play_bottom.setVisibility(View.GONE);
            isLock = true;
        }
    }
    //show礼物
    private void showGift() {
        if(isShowGift){

            play_bottom.setVisibility(View.VISIBLE);
            play_title.setVisibility(View.VISIBLE);
            SeekBar bar1 = ButterKnife.findById(this, recorf_seekbar);
            bar1.setVisibility(View.VISIBLE);
            SeekBar bar2= ButterKnife.findById(this, luminance_seekbar);
            bar2.setVisibility(View.VISIBLE);
            lock_btn.setVisibility(View.VISIBLE);
            gift_ui_h.setVisibility(View.GONE);
            isShowGift = false;
        }else{

            play_bottom.setVisibility(View.GONE);
            play_title.setVisibility(View.GONE);
            SeekBar bar1 = ButterKnife.findById(this, recorf_seekbar);
            bar1.setVisibility(View.GONE);
            SeekBar bar2= ButterKnife.findById(this, luminance_seekbar);
            bar2.setVisibility(View.GONE);
            lock_btn.setVisibility(View.GONE);
            gift_ui_h.setVisibility(View.VISIBLE);
            isShowGift = true;
        }
    }

    private void destroy() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.destroy();
        }
    }

    private void refresh() {
        stop();
        start();
    }

    //    全屏半屏业务逻辑
    private void fullScreenOper() {

        mClick = true;
        if (!mIsLand) {
            if (onClickOrientationListener != null) {
                onClickOrientationListener.landscape();
            }
//                    横屏显示业务逻辑
            hp_ui.setVisibility(View.VISIBLE);
            menus.setVisibility(View.GONE);
            edit_ll.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) ;//隐藏状态栏

            ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
            lp.height = ActionBar.LayoutParams.MATCH_PARENT;
            lp.width = ActionBar.LayoutParams.MATCH_PARENT;
            mSurfaceView.setLayoutParams(lp);

            ViewGroup.LayoutParams lp_2 = seekbar_ui.getLayoutParams();
            lp_2.height = ActionBar.LayoutParams.MATCH_PARENT;
            lp_2.width = ActionBar.LayoutParams.MATCH_PARENT;
            seekbar_ui.setLayoutParams(lp_2);


            mIsLand = true;
            mClickLand = false;
        } else {

            if (onClickOrientationListener != null) {
                onClickOrientationListener.portrait();
            }
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
            hp_ui.setVisibility(View.GONE);
            menus.setVisibility(View.VISIBLE);
            edit_ll.setVisibility(View.VISIBLE);

            ViewGroup.LayoutParams pa_rl = mSurfaceView.getLayoutParams();
            pa_rl.height = (int) (getResources().getDisplayMetrics().heightPixels / 16f * 9f);
            pa_rl.width = ActionBar.LayoutParams.MATCH_PARENT;
            mSurfaceView.setLayoutParams(pa_rl);

            ViewGroup.LayoutParams pa_rl_2 = seekbar_ui.getLayoutParams();
            pa_rl_2.height = (int) (getResources().getDisplayMetrics().heightPixels / 16f * 9f);
            pa_rl_2.width = ActionBar.LayoutParams.MATCH_PARENT;
            seekbar_ui.setLayoutParams(pa_rl_2);

            ViewGroup.LayoutParams menus_rl = menus.getLayoutParams();
            menus_rl.height = (int) (getResources().getDisplayMetrics().heightPixels / 16f * 9f);
            menus_rl.width = ActionBar.LayoutParams.MATCH_PARENT;
            menus.setLayoutParams(menus_rl);
//                    横屏返回半屏业务逻辑
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mIsLand = false;
            mClickPort = false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK&&isLock){
            hp_ui.setVisibility(View.VISIBLE);
            return false;
        }else if(keyCode == KeyEvent.KEYCODE_BACK){
            if(mIsLand){
                fullScreenOper();
                return false;
            }else{
                finish();
                return super.onKeyDown(keyCode, event);}
        }else{
            return super.onKeyDown(keyCode, event);
        }

       /*else {
            return super.onKeyDown(keyCode, event);
        }*/

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AnyEventType event) {
        Log.e("callback?","+++"+event.getCallBackStatus());
        if(MyApplication.PLAYCID!=null&&!MyApplication.PLAYCID.equals("0")){
            Log.e("callback?","---");
            destroy();
            initVodPlayer();
            initPlayUI();
            isPlayInit = true;
            play_btn.setImageDrawable(getResources().getDrawable(R.drawable.zt_));
            play_btn_2.setImageDrawable(getResources().getDrawable(R.drawable.zt_));
            isPlay = true;

        }else{
            initGift();
            initPlayUI();

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        play_btn.setImageDrawable(getResources().getDrawable(R.drawable.bf_));
        play_btn_2.setImageDrawable(getResources().getDrawable(R.drawable.bf_));
        isPlay = false;
        stop();
        mPlayer.destroy();
    }

    @Override
    protected void onDestroy() {
        stop();
        destroy();
        //maxBufDurationEdit.removeTextChangedListener(watcher);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
//    this is 点播

}
