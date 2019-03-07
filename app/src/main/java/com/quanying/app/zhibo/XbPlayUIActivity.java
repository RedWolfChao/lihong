package com.quanying.app.zhibo;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.anbetter.danmuku.DanMuParentView;
import com.anbetter.danmuku.DanMuView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.ZbjBean;
import com.quanying.app.helper.DanMuHelper;
import com.quanying.app.helper.DivergeView;
import com.quanying.app.ui.user.LoginActivity;
import com.quanying.app.ui.user.WebActivity;
import com.quanying.app.utils.AppImageMgr;
import com.quanying.app.utils.AppKeyBoardMgr;
import com.quanying.app.utils.AppScreenMgr;
import com.quanying.app.weburl.WebUri;
import com.quanying.danmu.DanmakuEntity;
import com.quanying.danmu.RichMessage;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.techery.progresshint.ProgressHintDelegate;
import okhttp3.Call;
import okhttp3.Cookie;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;
import static com.quanying.app.R.id.luminance_seekbar;
import static com.quanying.app.R.id.recorf_seekbar;
import static io.techery.progresshint.ProgressHintDelegate.POPUP_FOLLOW;
import static io.techery.progresshint.ProgressHintDelegate.SeekBarHintDelegateHolder;

public class XbPlayUIActivity extends FragmentActivity implements View.OnClickListener{
    @BindView(R.id.surfaceView_play)
    SurfaceView mSurfaceView;
    @BindView(R.id.surfaceView_play_img)
    ImageView surfaceView_play_img;
    private RelativeLayout menus;
    private RelativeLayout hp_ui;
    private boolean mIsLand = false; // 是否是横屏
    private OnClickOrientationListener onClickOrientationListener;

    private RelativeLayout play_title;
    private LinearLayout play_bottom;

    private ImageView play_btn_2;
    private ImageView lock_btn;
    private ImageView back_btn;
    private ImageView refresh_btn;
    private ImageView refresh_btn2;
    private AliVcMediaPlayer mPlayer;
    private boolean mMute = false;
    private String mUrl = null;
    private boolean isCompleted = false;
    private  boolean isPlay =false;
    private boolean mClick = false; // 是否点击
    private ImageView full_srceen;      //全屏

    private ImageView back_full_2;      //全屏
    private boolean mClickLand = true; // 点击进入横屏
    private boolean mClickPort = true; // 点击进入竖屏

    @BindView(R.id.db_title)
    RelativeLayout db_title;
    @BindView(R.id.webdialog)
    RelativeLayout webdialog;
    @BindView(R.id.dpv_broadcast)
    DanMuParentView dpv_broadcast;
    @BindView(R.id.dpv_broadcast2)
    DanMuParentView dpv_broadcast2;

    //  是否锁定
    private boolean isLock = false;
    private boolean isShowGift = false;

    private boolean isPlayInit = false;

    @BindView(R.id.edit_ll)
    LinearLayout edit_ll;
    @BindView(R.id.play_ui_ts)
    RelativeLayout play_ui_ts;
    @BindView(R.id.send)
    EditText send;
    @BindView(R.id.hp_edit)
    EditText hp_edit;

    @BindView(R.id.ts_title)
    TextView ts_title;
    @BindView(R.id.ts_button)
    Button ts_button;

    @BindView(R.id.gift_btn_h)
    ImageView gift_btn_h;

    @BindView(R.id.send_msg)
    ImageView send_msg;
    @BindView(R.id.send_btn)
    ImageView send_btn;

    private GridViewAdapter mGridViewAdapter ;
    private LwListAdapter lwAdapter ;
    private ArrayList<GridItem> mGridData ;
    private ArrayList<GridListItem> mListData ;


    @BindView(R.id.play_urls)
    TextView play_urls;
    @BindView(R.id.zbcontent)
    TextView zbcontent;
    @BindView(R.id.zbcontent2)
    TextView zbcontent2;
    @BindView(R.id.zbstatus)
    TextView zbstatus;
    @BindView(R.id.zbstatus2)
    TextView zbstatus2;

    @BindView(R.id.show_list)
    RelativeLayout  show_list;

    @BindView(R.id.show_list_ui)
    RelativeLayout  show_list_ui;

    @BindView(R.id.check_btn)
    View  check_btn;


    @BindView(R.id.zb_ui)
    RelativeLayout  zb_ui;

    @BindView(R.id.zbj_title)
    TextView zbj_title;
    private int isBuyLocation = -1;
    private int isBuyLocation_h = -1;

    @BindViews({
            recorf_seekbar, R.id.luminance_seekbar
    }) List<SeekBarHintDelegateHolder> seekBars;
    private HashMap<String, String> params;
    private String play_id;
    private OrientationEventListener mOrientationListener;

    @BindView(R.id.showzbj_list)
    ImageView showzbj_list;

    /*
     * 弹幕相关
     * */

    private DanMuView mDanMuContainerBroadcast;
    private DanMuView mDanMuContainerBroadcast2;
    private DanMuHelper mDanMuHelper;
    private DanMuHelper mDanMuHelper2;
    private boolean isHide = false;
    @BindView(R.id.divergeView)
    DivergeView mDivergeView;
    @BindView(R.id.danmustart)
    ImageView mImageView;
    @BindView(R.id.danmustart_2)
    ImageView mImageView_2;
    @BindView(R.id.lwimg)
    ImageView lwimg;
    private ArrayList<Bitmap> mList;
    private int mIndex = 0;


    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.listwebview)
    WebView listwebview;
    private RequestOptions options;

    int phMillis = 800;


    @BindView(R.id.touxiang)
    SimpleDraweeView touxiang;
    @BindView(R.id.hp_touxiang)
    SimpleDraweeView hp_touxiang;
    private  boolean loginBack;
    private CountDownTimer timer;
    private CountDownTimer timer2;
    private ObjectAnimator animator;

    private void spSetting(){

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        hp_ui.setVisibility(View.GONE);
        mDivergeView.setVisibility(View.VISIBLE);
        menus.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(cj)) {
            lwimg.setVisibility(View.VISIBLE);
        }


        db_title.setVisibility(View.VISIBLE);
        dpv_broadcast.setVisibility(View.VISIBLE);
        dpv_broadcast2.setVisibility(View.GONE);
        edit_ll.setVisibility(View.VISIBLE);

        ViewGroup.LayoutParams pa_rl = mSurfaceView.getLayoutParams();
        float gd = AppScreenMgr.getScreenHeight(XbPlayUIActivity.this);
        float kd = AppScreenMgr.getScreenWidth(XbPlayUIActivity.this);
        Log.e("gaodugaodu_",   gd +":kd:"+kd);

        if(gd>kd) {
            pa_rl.height = (int) (kd / 16f * 9f);
        }else{
            pa_rl.height = (int) (gd / 16f * 9f);
        }
        Log.e("gaodugaodu",   pa_rl.height +"");

        pa_rl.width = ActionBar.LayoutParams.MATCH_PARENT;
        mSurfaceView.setLayoutParams(pa_rl);
        play_ui_ts.setLayoutParams(pa_rl);
        surfaceView_play_img.setLayoutParams(pa_rl);

        //                    横屏返回半屏业务逻辑

        mClickPort = false;
        mIsLand = false;
        mClick = false;




    }


    private final void startListener() {
        mOrientationListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int rotation) {

                try {
                    int screenchange = Settings.System.getInt(XbPlayUIActivity.this.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
                    if(screenchange==1&&play_ui_ts.getVisibility()!=View.VISIBLE){
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

                            spSetting();

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
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                            if (onClickOrientationListener != null) {
                                onClickOrientationListener.landscape();
                            }
                            //                    横屏显示业务逻辑
                            hp_ui.setVisibility(View.VISIBLE);
                            mDivergeView.setVisibility(View.VISIBLE);
                            menus.setVisibility(View.GONE);
                            db_title.setVisibility(View.GONE);
                            dpv_broadcast.setVisibility(View.GONE);
                            dpv_broadcast2.setVisibility(View.VISIBLE);
                            edit_ll.setVisibility(View.GONE);
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) ;//隐藏状态栏
                            ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
                            lp.height = ActionBar.LayoutParams.MATCH_PARENT;
                            lp.width = ActionBar.LayoutParams.MATCH_PARENT;
                            mSurfaceView.setLayoutParams(lp);
                            play_ui_ts.setLayoutParams(lp);
                            surfaceView_play_img.setLayoutParams(lp);

                            Log.e("weizhizhizhizhiqpqpqp","1");

                            mIsLand = true;
                            mClickLand = false;

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

                            spSetting();

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
                            if (onClickOrientationListener != null) {
                                onClickOrientationListener.landscape();
                            }
                            //                    横屏显示业务逻辑
                            hp_ui.setVisibility(View.VISIBLE);
                            mDivergeView.setVisibility(View.VISIBLE);
                            menus.setVisibility(View.GONE);
                            db_title.setVisibility(View.GONE);
                            dpv_broadcast.setVisibility(View.GONE);
                            dpv_broadcast2.setVisibility(View.VISIBLE);
                            edit_ll.setVisibility(View.GONE);

                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) ;//隐藏状态栏
                            ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
                            lp.height = ActionBar.LayoutParams.MATCH_PARENT;
                            lp.width = ActionBar.LayoutParams.MATCH_PARENT;
                            mSurfaceView.setLayoutParams(lp);
                            play_ui_ts.setLayoutParams(lp);
                            surfaceView_play_img.setLayoutParams(lp);
                            Log.e("weizhizhizhizhiqpqpqp","2");
                            mClickLand = false;
                            mIsLand = true;
                            mClick = false;
                        }
                    }
                }

                    }else {
                        return;
                    }

                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }


            }
        };
        mOrientationListener.enable();



    }
    private ImmersionBar mImmersionBar;


    final Handler handler = new Handler();
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            // TODO Auto-generated method stub
            // 在此处添加执行的代码
            if(mIndex == mList.size()){
                mIndex = 0 ;
            }
            mDivergeView.startDiverges(mIndex);
            mIndex ++;
            handler.postDelayed(this, phMillis);// 50是延时时长

            if(phMillis<800){

                phMillis+=30;

            }

        }
    };
    boolean ends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置取消AppCompatActivity 的自带标题栏
        mImmersionBar = ImmersionBar.with(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
        setContentView(R.layout.activity_play_ui_xb);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        MyApplication.PLAYSTATUS = 1+"";

        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            play_id = intent.getStringExtra("play_id");
        }

        initView();
        initWebView();
        initUi();
        initPlayUI();
        initVodPlayer();
        initDanmu();
        initOper();
        dpv_broadcast.setVisibility(View.VISIBLE);
        dpv_broadcast2.setVisibility(View.GONE);
        showzbj_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                show_list.setAnimation(R.style.mystyle);
                //移动属性

              /*  TranslateAnimation ta;
                ta = new TranslateAnimation(0,0,300,0);*/
                Log.e("buhuidiaoyong","111");


                listwebview.loadUrl(roomurl);

                show_list.startAnimation(AnimationUtils.loadAnimation(XbPlayUIActivity.this,R.anim.dialog_enter));
                show_list.setVisibility(View.VISIBLE);
                webdialog.setVisibility(View.VISIBLE);



            }
        });

        lwimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                show_list.setAnimation(R.style.mystyle);
                //移动属性

              /*  TranslateAnimation ta;
                ta = new TranslateAnimation(0,0,300,0);*/
                Log.e("buhuidiaoyong","111");

                if(mIsLand){
                   fullScreenOper();
                }

                listwebview.loadUrl(cj);

                show_list.startAnimation(AnimationUtils.loadAnimation(XbPlayUIActivity.this,R.anim.dialog_enter));
                show_list.setVisibility(View.VISIBLE);
                webdialog.setVisibility(View.VISIBLE);


            }
        });


        show_list_ui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                show_list.setAnimation(R.style.mystyle);
                //移动属性

              /*  TranslateAnimation ta;
                ta = new TranslateAnimation(0,0,300,0);*/
        /*        show_list.startAnimation(AnimationUtils.loadAnimation(XbPlayUIActivity.this,R.anim.dialog_exit));
                show_list.setVisibility(View.GONE);*/
                disShowListUi();
            }
        });
        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                show_list.setAnimation(R.style.mystyle);
                //移动属性

              /*  TranslateAnimation ta;
                ta = new TranslateAnimation(0,0,300,0);*/
        /*        show_list.startAnimation(AnimationUtils.loadAnimation(XbPlayUIActivity.this,R.anim.dialog_exit));
                show_list.setVisibility(View.GONE);*/
                disShowListUi();
            }
        });

                startListener();

        hideAllDanMuView(false);

        animator = tada(lwimg);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();

/*

        ends =  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                    animator.end();
                    Toast.makeText(XbPlayUIActivity.this, "结束", Toast.LENGTH_SHORT).show();

*/
/** 倒计时60秒，一次1秒 *//*

                CountDownTimer timer = new CountDownTimer(1*1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onFinish() {
                        animator.start();
                    }
                }.start();

//                Toast.makeText(XbPlayUIActivity.this, "zhixing", Toast.LENGTH_SHORT).show();

      */
/*          ObjectAnimator nopeAnimator = nope(lwimg);
                nopeAnimator.setRepeatCount(ValueAnimator.INFINITE);
                nopeAnimator.start();*//*


            }
        }, 2000);
*/
        startDh();

    }

    CountDownTimer dhTimer = new CountDownTimer(4*1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onFinish() {
            animator.start();
            startDh();
        }
    }.start();

    public void startDh(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                animator.end();
//                Toast.makeText(XbPlayUIActivity.this, "结束", Toast.LENGTH_SHORT).show();

/** 倒计时60秒，一次1秒 */
                dhTimer.start();

//                Toast.makeText(XbPlayUIActivity.this, "zhixing", Toast.LENGTH_SHORT).show();

      /*          ObjectAnimator nopeAnimator = nope(lwimg);
                nopeAnimator.setRepeatCount(ValueAnimator.INFINITE);
                nopeAnimator.start();*/

            }
        }, 2000);
    }


    public static ObjectAnimator tada(View view) {
        return tada(view, 1f);
    }


    public static ObjectAnimator tada(View view, float shakeFactor) {

        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, 1f),
       /*         Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.1f),
                Keyframe.ofFloat(.4f, 1.1f),
                Keyframe.ofFloat(.5f, 1.1f),
                Keyframe.ofFloat(.6f, 1.1f),
                Keyframe.ofFloat(.7f, 1.1f),
                Keyframe.ofFloat(.8f, 1.1f),
                Keyframe.ofFloat(.9f, 1.1f),
                Keyframe.ofFloat(.10f, 1.1f),
                Keyframe.ofFloat(.11f, 1.1f),
                Keyframe.ofFloat(.12f, 1.1f),*/
                Keyframe.ofFloat(1f, 1f)
        );

        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1f),
          /*      Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.1f),
                Keyframe.ofFloat(.4f, 1.1f),
                Keyframe.ofFloat(.5f, 1.1f),
                Keyframe.ofFloat(.6f, 1.1f),
                Keyframe.ofFloat(.7f, 1.1f),
                Keyframe.ofFloat(.8f, 1.1f),
                Keyframe.ofFloat(.9f, 1.1f),
                Keyframe.ofFloat(.10f, 1.1f),
                Keyframe.ofFloat(.11f, 1.1f),
                Keyframe.ofFloat(.12f, 1.1f),*/
                Keyframe.ofFloat(1f, 1f)
        );

        PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(1f, 0),
                Keyframe.ofFloat(.1f, 3f * shakeFactor),
                Keyframe.ofFloat(.2f, -10f * shakeFactor),
                Keyframe.ofFloat(.3f, 3f * shakeFactor),
                Keyframe.ofFloat(.4f, -10f * shakeFactor),
                Keyframe.ofFloat(.5f, 3f * shakeFactor),
                Keyframe.ofFloat(.6f, -10f * shakeFactor),
                Keyframe.ofFloat(.7f, 3f * shakeFactor),
                Keyframe.ofFloat(.8f, -10f * shakeFactor),
                Keyframe.ofFloat(.9f, 3f * shakeFactor),
                Keyframe.ofFloat(.10f, -10f * shakeFactor),
                Keyframe.ofFloat(.11f, 3f * shakeFactor),
                Keyframe.ofFloat(.12f, -10f * shakeFactor),
                Keyframe.ofFloat(.11f, 3f * shakeFactor),
                Keyframe.ofFloat(.12f, -10f * shakeFactor),
                Keyframe.ofFloat(.11f, 3f * shakeFactor),
                Keyframe.ofFloat(.12f, -10f * shakeFactor),
                Keyframe.ofFloat(.11f, 3f * shakeFactor),
                Keyframe.ofFloat(.12f, -10f * shakeFactor),
                Keyframe.ofFloat(1f, 0)
        );

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY, pvhRotate).
                setDuration(2000);
    }

    public static ObjectAnimator nope(View view) {
        int delta = view.getResources().getDimensionPixelOffset(R.dimen.spacing_medium);

        PropertyValuesHolder pvhTranslateX = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X,
                Keyframe.ofFloat(0f, 0),
                Keyframe.ofFloat(.10f, -delta),
                Keyframe.ofFloat(.26f, delta),
                Keyframe.ofFloat(.42f, -delta),
                Keyframe.ofFloat(.58f, delta),
                Keyframe.ofFloat(.74f, -delta),
                Keyframe.ofFloat(.90f, delta),
                Keyframe.ofFloat(1f, 0f)
        );

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhTranslateX).
                setDuration(500);
    }



    private void initDanmu() {

        mDanMuHelper = new DanMuHelper(this);
        mDanMuHelper2 = new DanMuHelper(this);

        // 全站弹幕（广播）
        mDanMuContainerBroadcast = (DanMuView) findViewById(R.id.danmaku_container_broadcast);
        mDanMuContainerBroadcast2 = (DanMuView) findViewById(R.id.danmaku_container_broadcast2);
        mDanMuContainerBroadcast.prepare();
        mDanMuContainerBroadcast2.prepare();
        mDanMuHelper.add(mDanMuContainerBroadcast);
        mDanMuHelper2.add(mDanMuContainerBroadcast2);

    }

    private void initWebView() {


        //支持屏幕缩放
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);
        //不显示webview缩放按钮
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() +WebUri.USERAGAENT);

//        webView.setWebViewClient(client);
        // 设置setWebChromeClient对象
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.addJavascriptInterface(new JSInterface(), "qyapp");
//        CookieSyncManager.getInstance().sync();

        CookieManager cookieManager = CookieManager.getInstance();

        String CookieStr = cookieManager.getCookie("http://tv.7192.com/chathy/"+play_id+".html");

        webView.setBackgroundColor(getResources().getColor(R.color.black)); // 设置背景色
        webView.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255

        MyApplication.Cookies = CookieStr;
        webView.loadUrl("http://tv.7192.com/chathy/"+play_id+".html");


        webView.setWebChromeClient(
                new WebChromeClient() {
                    public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
                        if (progress == 100) {
                            //handler.sendEmptyMessage(1);// 如果全部载入,隐藏进度对话框

                        }

                        super.onProgressChanged(view, progress);
                    }
                    //扩展支持alert事件
                    @Override
                    public void onReceivedIcon(WebView view, Bitmap icon) {
                        super.onReceivedIcon(view, icon);

                    }

                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
//                        Log.i(TAG, "onReceivedTitle:title ------>" + title);
                      /*  if (title.contains("404")){
                            noweb_ui.setV+isibility(View.VISIBLE);
                        }*/
                    }

                    @Override
                    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                        callback.invoke(origin, true, false);
                        super.onGeolocationPermissionsShowPrompt(origin, callback);
                    }

                    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("提示").setMessage(message).setPositiveButton("确定", null);
                        builder.setCancelable(false);
                        //  builder.setIcon(R.drawable.ic_launcher);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        result.confirm();
                        return true;
                    }


                    @Override
                    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                        AlertDialog.Builder b = new AlertDialog.Builder(XbPlayUIActivity.this);
                        b.setTitle("Confirm");
                        b.setMessage(message);
                        b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        });
                        b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        });
                        b.create().show();
                        return true;
                    }




                }
        );

        webView.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("kevin","url:"+url);

                if (url.toLowerCase().startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);





                /** 倒计时60秒，一次1秒 */
                timer = new CountDownTimer(5*1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onFinish() {
//                        Toast.makeText(XbPlayUIActivity.this, "调用", Toast.LENGTH_SHORT).show();
                        Log.e("kankan","javascript:tvdown('"+urlmark+"')");
                        if(webView!=null){
                        webView.loadUrl("javascript:tvdown('"+urlmark+"')");
                    }}
                }.start();




            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    return;
                }

            }

            //处理网页加载失败时
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

        });
    }
    private void initListWebView() {


        //支持屏幕缩放
        listwebview.getSettings().setSupportZoom(false);
        listwebview.getSettings().setBuiltInZoomControls(false);
        //不显示webview缩放按钮
        listwebview.getSettings().setDisplayZoomControls(false);
        listwebview.getSettings().setUseWideViewPort(true);
        listwebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        listwebview.getSettings().setLoadWithOverviewMode(true);
        listwebview.getSettings().setJavaScriptEnabled(true);
        listwebview.getSettings().setUserAgentString(listwebview.getSettings().getUserAgentString() +WebUri.USERAGAENT);

        listwebview.getSettings().setDomStorageEnabled(true);

//        webView.setWebViewClient(client);
        // 设置setWebChromeClient对象
        listwebview.getSettings().setUseWideViewPort(true);
        listwebview.getSettings().setLoadWithOverviewMode(true);
        listwebview.addJavascriptInterface(new JSInterface(), "qyapp");
//        CookieSyncManager.getInstance().sync();

   /*     CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr;
        if(cookieManager==null){
            CookieStr = "";
        }else {
            CookieStr = cookieManager.getCookie(urls);
        }

        MyApplication.Cookies = CookieStr;*/

//        listwebview.loadUrl(urls);
        listwebview.setWebChromeClient(
                new WebChromeClient() {
                    public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
                        if (progress == 100) {
                            //handler.sendEmptyMessage(1);// 如果全部载入,隐藏进度对话框

                        }

                        super.onProgressChanged(view, progress);
                    }
                    //扩展支持alert事件
                    @Override
                    public void onReceivedIcon(WebView view, Bitmap icon) {
                        super.onReceivedIcon(view, icon);

                    }

                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
//                        Log.i(TAG, "onReceivedTitle:title ------>" + title);
                      /*  if (title.contains("404")){
                            noweb_ui.setV+isibility(View.VISIBLE);
                        }*/
                    }

                    @Override
                    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                        callback.invoke(origin, true, false);
                        super.onGeolocationPermissionsShowPrompt(origin, callback);
                    }

                    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("提示").setMessage(message).setPositiveButton("确定", null);
                        builder.setCancelable(false);
                        //  builder.setIcon(R.drawable.ic_launcher);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        result.confirm();
                        return true;
                    }


                    @Override
                    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                        AlertDialog.Builder b = new AlertDialog.Builder(XbPlayUIActivity.this);
                        b.setTitle("Confirm");
                        b.setMessage(message);
                        b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        });
                        b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        });
                        b.create().show();
                        return true;
                    }




                }
        );

        listwebview.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("kevin","url:"+url);

                if (url.toLowerCase().startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
/** 倒计时60秒，一次1秒 */
                CountDownTimer timer = new CountDownTimer(3*500, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
//                        tv.setText("还剩"+millisUntilFinished/1000+"秒");
                    }

                    @Override
                    public void onFinish() {
                        webdialog.setVisibility(View.GONE);
                    }
                }.start();


            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    return;
                }

            }

            //处理网页加载失败时
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

        });
    }
    public void disShowListUi(){

        show_list.startAnimation(AnimationUtils.loadAnimation(XbPlayUIActivity.this,R.anim.dialog_exit));
        show_list.setVisibility(View.GONE);

    }

    private boolean isBf = true ;

    @SuppressLint("JavascriptInterface")
    @SuppressWarnings("unused")
    class JSInterface {

        public JSInterface() {

        }

        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void shouqi(String s){

   /*         Toast.makeText(XbPlayUIActivity.this, "收起", Toast.LENGTH_SHORT).show();
            show_list.startAnimation(AnimationUtils.loadAnimation(XbPlayUIActivity.this,R.anim.dialog_exit));
            show_list.setVisibility(View.GONE);*/

//            show_list.startAnimation(AnimationUtils.loadAnimation(XbPlayUIActivity.this,R.anim.dialog_exit));
//            show_list.setVisibility(View.GONE);

//            disShowListUi();
            EventBus.getDefault().post(new AnyEventType("close"));
        }
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void gobackAlert(String s){

//            Toast.makeText(XbPlayUIActivity.this, "zoule", Toast.LENGTH_SHORT).show();


            final AlertDialog mDialog=new AlertDialog.Builder(XbPlayUIActivity.this).

                    setTitle("您的账户在其他终端登录，被迫下线")
                    .setPositiveButton("确定", null).create();
            mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button positionButton=mDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                    positionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(MainActivity.this,"确定",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                            finish();


                        }
                    });

                }
            });
            mDialog.setCancelable(false);
            mDialog.show();

        }

        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void adShow(String resp){
//             phMillis
//            Toast.makeText(XbPlayUIActivity.this, "收起", Toast.LENGTH_SHORT).show();

//             Toast.makeText(XbPlayUIActivity.this, "resp"+resp+"gaizhi", Toast.LENGTH_SHORT).show();

            try {
                JSONObject jsonObject = new JSONObject(resp);
                phMillis = jsonObject.getInt("rate");

                phMillis = 400- phMillis*40;
//                 Toast.makeText(XbPlayUIActivity.this, "resp"+resp+"gaizhi"+phMillis, Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        @JavascriptInterface
        public void playlive(String id) {
//            Toast.makeText(context, "```````````````````````"+id, Toast.LENGTH_SHORT).show();

            //在Intent对象当中添加一个键值对
            MyApplication.PLAYID = id;
            MyApplication.PLAYSTATUS = 1+"";

            Intent zbIntent =new Intent(XbPlayUIActivity.this, XbPlayUIActivity.class);
            zbIntent.putExtra("play_id",id);
            startActivity(zbIntent);

        }



        @JavascriptInterface
        public void goback(String orderJson) {
            // the callback function of demo page is hardcode as 'getDiffDataCallback'


            try {
                JSONObject jsonObject = new JSONObject(orderJson);

                if (jsonObject.getString("url").equals("back")) {


                    finish();


                } else if (jsonObject.getString("url").equals("goback")) {

              finish();
                } else {

                    MyApplication.BACKURL = jsonObject.getString("url");
                    finish();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        @JavascriptInterface
        public void opennewview(String orderJson) {
//            WebView
            try {
//                Toast.makeText(context, ""+orderJson, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = new JSONObject(orderJson);
                String webUrl = jsonObject.getString("url");
                Log.e("kevin", "ist" + webUrl);
                String title = "title";
                Intent intent = new Intent(XbPlayUIActivity.this, WebActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("urls", webUrl);
                startActivity(intent);
                /*
                 *
                 * */
               /* MyApplication.stackUrl.add(webUrl);
//                MyApplication.webUrl = webUrl;
                startActivity(new Intent(HomeActivity.this,HomeActivity.class));*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void livesta(String id) {
//            Toast.makeText(context, "```````````````````````"+id, Toast.LENGTH_SHORT).show();
//          {'type':'livesta','action':'publish'}
            Log.e("lalatag",id);
             try {
                 JSONObject jsonObject = new JSONObject(id);
                 String type = jsonObject.getString("type");
                 String action = jsonObject.getString("action");
                 if (action.equals("publish")) {
//                     Toast.makeText(XbPlayUIActivity.this, "走了", Toast.LENGTH_SHORT).show();
//                     play_ui_ts.setVisibility(View.GONE);
//                     surfaceView_play_img.setVisibility(View.GONE);
//                     initPlayUI();
//                     Toast.makeText(XbPlayUIActivity.this, "走了", Toast.LENGTH_SHORT).show();

                     EventBus.getDefault().post(new AnyEventType("startzb"));


                 }else{

//                     停止推流逻辑

                     play_ui_ts.setVisibility(View.VISIBLE);
                     surfaceView_play_img.setVisibility(View.VISIBLE);
                     menus.setVisibility(View.GONE);
//                     ts_title
                     ts_title.setText("暂停");

                 }


             } catch (JSONException e) {
                 e.printStackTrace();
             }


     }


        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void danMu(String orderJson){

            try {
//                Toast.makeText(XbPlayUIActivity.this, ""+orderJson, Toast.LENGTH_SHORT).show();

                JSONObject jsonObject = new JSONObject(orderJson);
                String content = jsonObject.getString("msg");
                ArrayList<RichMessage> mArray  = new ArrayList<>();
                RichMessage msg = new RichMessage();
                msg.setColor("ffffff");
                msg.setContent(content);
                msg.setType("text");
                mArray .add(msg);

                DanmakuEntity danmakuEntity = new DanmakuEntity();
       /*         danmakuEntity.setText("shenme xiaoxi");
                danmakuEntity.setAvatar("setAvatar");
                danmakuEntity.setName("setName");*/
//                danmakuEntity.set("setName");
                danmakuEntity.setType(306);
                danmakuEntity.setText(content);
                danmakuEntity.setType(DanmakuEntity.DANMAKU_TYPE_SYSTEM);
                danmakuEntity.setRichText(mArray);
                addDanmaku(danmakuEntity);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
    public void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        SharedPrefsCookiePersistor mData = new SharedPrefsCookiePersistor(context);
        List<Cookie> cookies = new ArrayList<Cookie>();
        cookies = mData.loadAll();
        StringBuffer sb = new StringBuffer();
        for (Cookie cookie : cookies) {
            String cookieName = cookie.name();
            String cookieValue = cookie.value();
            if (!TextUtils.isEmpty(cookieName)
                    && !TextUtils.isEmpty(cookieValue)) {
                sb.append(cookieName + "=");
                sb.append(cookieValue + ";");
            }
        }
        android.webkit.CookieManager cookieManager1 = android.webkit.CookieManager.getInstance();
//        cookieManager1.setCookie(url,sb.toString());
        String[] cookie = sb.toString().split(";");
        for (int i = 0; i < cookie.length; i++) {
            cookieManager.setCookie(url, cookie[i]);// cookies是在HttpClient中获得的cookie
        }
        CookieSyncManager.getInstance().sync();
    }
    @Override
    protected void onResume() {

        super.onResume();

        if(loginBack){
            initPlayUI();
            loginBack= false;

            webView.loadUrl("http://tv.7192.com/chathy/"+play_id+".html");
            synCookies(this,"http://tv.7192.com/chathy/"+play_id+".html");
            listwebview.reload();
        }
        if(MyApplication.BACKURL.equals("reloadurl")){

//            showBaseDialog("登录成功！","确认");
//            synCookies(context,urls);
            if(mPlayer!=null){
                start();
                return;
            }
            initVodPlayer();
//            initWebView();

            MyApplication.BACKURL = "";
            webView.reload();
        }

        if(mPlayer!=null){
            start();
        }


    }

    private Bitmap returnBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }
    private  String urlmark;
    private String cj;
    private String roomurl;

    private void initPlayUI() {

        Log.e("jisuancishu","-----111111111111111111-----------");

        params = new HashMap<String,String>();
        params.put( "Cookie",MyApplication.Cookies+"");
        OkHttpUtils
                .post()
                .url("http://tv.7192.com/index/api/live")
                .headers(params)
                .addParams("id", play_id+"")
                .build()
                .execute(new StringCallback() {
                    @Override public void onError(Call call, Exception e, int id) {

                    }

                    @Override public void onResponse(String response, int id) {
                        Log.e("initplay","id="+play_id+response);

//                        JSONObject jsonObject = new JSONObject(response);

                        final ZbjBean mBean = new Gson().fromJson(response,ZbjBean.class);
                        zbj_title.setText(mBean.getTitle());
                        String title = mBean.getTitle();
                        play_urls.setText(title);
                        zbcontent2.setText(mBean.getView()+"人在线");
                        zbcontent.setText(mBean.getView()+"人在线");
                        initListWebView();
                        options = new RequestOptions();
                        options.placeholder(R.mipmap.nowebimg);
                        options.error(R.mipmap.nowebimg);
                        String errmsg =mBean.getErrmsg();
                        zbstatus2.setText(""+errmsg);
                        zbstatus.setText(""+errmsg);
                        urlmark = mBean.getUrlmark();


                        roomurl=mBean.getRoom();
                        cj = mBean.getCj();

                        if(TextUtils.isEmpty(cj)){

                            lwimg.setVisibility(View.GONE);

                        }

                        if(errmsg.equals("休息中")) {
                            Drawable drawableLeft = getResources().getDrawable(
                                    R.mipmap.reddian);

                            zbstatus.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                                    null, null, null);

                            zbstatus2.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                                    null, null, null);
                        }

                        Uri uri = Uri.parse(mBean.getFace());
                        ImagePipeline imagePipeline = Fresco.getImagePipeline();
                        imagePipeline.evictFromMemoryCache(uri);
                        imagePipeline.evictFromDiskCache(uri);
//                                AppSharePreferenceMgr.set(getMContext(),"userid","");
// combines above two lines
                        imagePipeline.evictFromCache(uri);

                        touxiang.setImageURI(uri);
                        hp_touxiang.setImageURI(uri);

//                    mImageView
                        Glide.with( XbPlayUIActivity.this )
                                .load(mBean.getAd().getMain())
                                .apply(options)//图片加载出来前，显示的图片
                                //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                                .into( mImageView);
                        Glide.with( XbPlayUIActivity.this )
                                .load(mBean.getAd().getMain())
                                .apply(options)//图片加载出来前，显示的图片
                                //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                                .into( mImageView_2);
                        if(mList==null) {
                            new Thread() {
                                public void run() {
//访问网络代码
                                    mList = new ArrayList<>();

                                    Bitmap bit1 = AppImageMgr.getBitMBitmap(mBean.getAd().getIcons().get(0));
                                    Bitmap bit2 = AppImageMgr.getBitMBitmap(mBean.getAd().getIcons().get(1));
                                    Bitmap bit3 = AppImageMgr.getBitMBitmap(mBean.getAd().getIcons().get(2));
                                    Bitmap bit4 = AppImageMgr.getBitMBitmap(mBean.getAd().getIcons().get(3));
                                    mList.add(bit1);
                                    mList.add(bit2);
                                    mList.add(bit3);
                                    mList.add(bit4);

                                    Log.e("caonima", bit1 + "");
                                    initYanhua();
                                    if (mList.get(0) != null) {
                                        handler.postDelayed(runnable, 1000);
                                    }

                                }
                            }.start();

                        }
                        String code= mBean.getErrcode();
                        if(code.equals("200")){
                            play_ui_ts.setVisibility(View.GONE);
                            surfaceView_play_img.setVisibility(View.GONE);
                            menus.setVisibility(View.VISIBLE);

                            Drawable drawableLeft = getResources().getDrawable(
                                    R.mipmap.dian);

                            zbstatus.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                                    null, null, null);

                            zbstatus2.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                                    null, null, null);
                            mUrl =mBean.getPlayUrl().getRtmp();


                            isPlayInit = true;

                            play_btn_2.setImageDrawable(getResources().getDrawable(R.drawable.zt_));
                            start();
                            isPlay = true;
                        }
                        else if(code.equals("40002")){
                            play_ui_ts.setVisibility(View.VISIBLE);
                            menus.setVisibility(View.INVISIBLE);


                            zbstatus.setText(errmsg);
                            ts_button.setVisibility(View.VISIBLE);

                            ts_button.setText("立即登录");
                            ts_title.setText("当前账号未登录，请登录账号后观看直播。");
                            ts_button.setOnClickListener(new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    startActivity(new Intent(XbPlayUIActivity.this,LoginActivity.class));
                                    loginBack = true;
                                }
                            });
                        }else if(code.equals("40206")){

                            play_ui_ts.setVisibility(View.VISIBLE);
                            zbstatus.setText(errmsg);
                            ts_button.setVisibility(View.INVISIBLE);
                            ts_title.setText(errmsg);

                        }else if(code.equals("40005")){
                            play_ui_ts.setVisibility(View.VISIBLE);

                            ts_title.setText("请先激活账户，或购买会场门票！");
                            ts_button.setVisibility(View.VISIBLE);
                            ts_button.setText("激活/购买");
                            ts_button.setOnClickListener(new View.OnClickListener() {
                                @Override public void onClick(View v) {
//                                    startActivity(new Intent(XbPlayUIActivity.this,PayPlayWebActivity.class));

                                    Intent intent = new Intent(XbPlayUIActivity.this,WebActivity.class);
                                    intent.putExtra("title","goumai");
                                    intent.putExtra("urls",mBean.getVip());
                                    intent.putExtra("playid",play_id);

                                    startActivity(intent);
                                    finish();
//                                    play_ui_ts.setVisibility(View.GONE);
                                }
                            });
                        }else{

                            play_ui_ts.setVisibility(View.VISIBLE);

                            zbstatus.setText(errmsg);
                            ts_button.setVisibility(View.INVISIBLE);
                            ts_title.setText(errmsg);


                        }


                    }
                });
    }

    private void initYanhua(){

        mDivergeView.post(new Runnable() {
            @Override
            public void run() {
                mDivergeView.setEndPoint(new PointF(mDivergeView.getMeasuredWidth()/2,0));
                mDivergeView.setDivergeViewProvider(new Provider());
            }
        });

    }


    /**
     * @param root 最外层的View
     * @param scrollToView 不想被遮挡的View,会移动到这个Veiw的可见位置
     */
    private int scrollToPosition=0;
    private void autoScrollView(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect rect = new Rect();

                        //获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);

                        //获取root在窗体的不可视区域高度(被遮挡的高度)
                        int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;

                        //若不可视区域高度大于150，则键盘显示
                        if (rootInvisibleHeight > 150) {

                            //获取scrollToView在窗体的坐标,location[0]为x坐标，location[1]为y坐标
                            int[] location = new int[2];
                            scrollToView.getLocationInWindow(location);

                            //计算root滚动高度，使scrollToView在可见区域的底部
                            int scrollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;

                            //注意，scrollHeight是一个相对移动距离，而scrollToPosition是一个绝对移动距离
                            scrollToPosition += scrollHeight;

                        } else {
                            //键盘隐藏
                            scrollToPosition = 0;
                        }
                        root.scrollTo(0, scrollToPosition);

                    }
                });
    }

    /**
     * 发送一条全站弹幕
     */
    private void addDanmaku(DanmakuEntity danmakuEntity) {
        if (mDanMuHelper != null) {
            mDanMuHelper.addDanMu(danmakuEntity, true);
        }  if (mDanMuHelper2 != null) {
            mDanMuHelper2.addDanMu(danmakuEntity, true);
        }
    }


    class Provider implements DivergeView.DivergeViewProvider {

        @Override
        public Bitmap getBitmap(Object obj) {
            return mList == null ? null : mList.get((int)obj);
        }
    }
    /**
     * 显示或者隐藏弹幕
     * @param hide
     */
    private void hideAllDanMuView(boolean hide) {
    /*    if(mDanMuContainerBroadcast != null) {
            mDanMuContainerBroadcast.hideAllDanMuView(hide);
        }*/



        if(mDanMuContainerBroadcast2 != null) {
            mDanMuContainerBroadcast2.hideAllDanMuView(hide);
        }

    }
    private void initOper() {
        full_srceen.setOnClickListener(this);

        play_btn_2 .setOnClickListener(this);

        lock_btn .setOnClickListener(this);
        back_btn .setOnClickListener(this);

        gift_btn_h .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHide = !isHide;
                gift_btn_h.setImageResource(isHide?R.mipmap.zb_danmu_dissmiss:R.mipmap.zb_danmu_open);

                hideAllDanMuView(isHide);
            }
        });
        refresh_btn .setOnClickListener(this);
        refresh_btn2 .setOnClickListener(this);
        mSurfaceView .setOnClickListener(this);

        back_full_2 .setOnClickListener(this);



        //拦截键盘确认
        send.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                sendMeg();

                return true;
            }
        });

        //拦截键盘确认
        hp_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                sendMegHp();

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



    @OnClick(R.id.send_msg)
    public void sendMeg() {

        if(TextUtils.isEmpty(MyApplication.getUserID())){

            startActivity(new Intent(XbPlayUIActivity.this,LoginActivity.class));
            loginBack = true;
        }

        String text_ = send.getText().toString().trim();
        send.setText("");
        if(TextUtils.isEmpty(text_)){
            Toast.makeText(XbPlayUIActivity.this, "输入内容为空", Toast.LENGTH_SHORT).show();

        }else{
           /* params = new HashMap<String,String>();
            params.put( "Cookie",MyApplication.Cookies);*/
            webView.loadUrl("javascript:sendmsg('"+text_+"')");
            hp_edit.setText("");
            AppKeyBoardMgr.closeKeybord(send,XbPlayUIActivity.this);
            /*OkHttpUtils
                    .post()
                    .headers(params)
                    .url("http://tv.7192.com/index/api/note")
                    .addParams("infoid",play_id)
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
                                    AppKeyBoardMgr.closeKeybord(send,XbPlayUIActivity.this);
                                }else{
                                    String msg = jsonObject.getString("errmsg");
//                                Toast.makeText(XbPlayUIActivity.this,""+msg,Toast.LENGTH_SHORT).show();
                                    AppKeyBoardMgr.closeKeybord(send,XbPlayUIActivity.this);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });*/
        }

    }

    @OnClick({R.id.send_btn})
    public void sendMegHp() {

        String text_ = hp_edit.getText().toString().trim();
        if(TextUtils.isEmpty(text_)){
            Toast.makeText(XbPlayUIActivity.this, "输入内容为空", Toast.LENGTH_SHORT).show();

        }else{
            params = new HashMap<String,String>();
            params.put( "Cookie",MyApplication.Cookies);
            webView.loadUrl("javascript:sendmsg('"+text_+"')");
            hp_edit.setText("");
            AppKeyBoardMgr.closeKeybord(send,XbPlayUIActivity.this);
          /*  OkHttpUtils
                    .post()
                    .headers(params)
                    .url("http://tv.7192.com/index/api/note")
                    .addParams("infoid",play_id)
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
                                    hp_edit.setText("");
                                    AppKeyBoardMgr.closeKeybord(send,XbPlayUIActivity.this);
                                }else{
                                    String msg = jsonObject.getString("errmsg");
                                    Toast.makeText(XbPlayUIActivity.this,""+msg,Toast.LENGTH_SHORT).show();
                                    AppKeyBoardMgr.closeKeybord(send,XbPlayUIActivity.this);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });*/
        }

    }
    /*****************计时器*******************/
    private Runnable timeRunable = new Runnable() {
        @Override
        public void run() {

//            currentSecond = currentSecond + 1000;

            if (!isPause) {
                //递归调用本runable对象，实现每隔一秒一次执行任务
                if(count<9&&count>4){
//                    Toast.makeText(XbPlayUIActivity.this, ""+count, Toast.LENGTH_SHORT).show();
                    webView.loadUrl("javascript:sendAD("+count+")");
                }
                count = 0;
                mhandle.postDelayed(this, 1000);
            }
        }
    };
    //计时器
    private Handler mhandle = new Handler();
    private boolean isPause = false;//是否暂停
//    private long currentSecond = 0;//当前毫秒数
    /*****************计时器*******************/

    int count = 0;

    private void initView() {

        menus = (RelativeLayout) findViewById(R.id.menus);
        hp_ui = (RelativeLayout) findViewById(R.id.hp_ui);

        play_btn_2 = (ImageView) findViewById(R.id.play_btn_2);
        play_bottom = (LinearLayout) findViewById(R.id.play_bottom);
        play_title = (RelativeLayout) findViewById(R.id.play_title);
        lock_btn = (ImageView) findViewById(R.id.lock_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        refresh_btn = (ImageView) findViewById(R.id.refresh_btn);
        refresh_btn2 = (ImageView) findViewById(R.id.refresh_btn2);
        full_srceen = (ImageView) findViewById(R.id.full_srceen);

        back_full_2 = (ImageView) findViewById(R.id.back_full_2);

        for (SeekBarHintDelegateHolder seekBar : seekBars) {
            seekBar.getHintDelegate().setPopupStyle(POPUP_FOLLOW);
        }

        autoScrollView(zb_ui, edit_ll);//弹出软键盘时滚动视图

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


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count ++;

                if(mIndex == mList.size()){
                    mIndex = 0 ;
                }
                mDivergeView.startDiverges(mIndex);
                mIndex ++;
            }
        });
        mImageView_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count ++;

                if(mIndex == mList.size()){
                    mIndex = 0 ;
                }
                mDivergeView.startDiverges(mIndex);
                mIndex ++;
            }
        });
        handler.postDelayed(timeRunable, 1000);
    }

    private void initUi() {
        //      动态设置，播放器组件高度设为屏幕16:9比例


        Log.e("weizhizhizhizhi","3");

        ViewGroup.LayoutParams pa_rl = mSurfaceView.getLayoutParams();
        pa_rl.height = (int) (getResources().getDisplayMetrics().widthPixels / 16f * 9f);
        pa_rl.width = ActionBar.LayoutParams.MATCH_PARENT;
        mSurfaceView.setLayoutParams(pa_rl);
        play_ui_ts.setLayoutParams(pa_rl);
        surfaceView_play_img.setLayoutParams(pa_rl);
        Log.e("weizhizhizhizhiqpqpqp","3");
        ViewGroup.LayoutParams menus_rl = menus.getLayoutParams();
        menus_rl.height = (int) (getResources().getDisplayMetrics().widthPixels / 16f * 9f);
        menus_rl.width = ActionBar.LayoutParams.MATCH_PARENT;
        menus.setLayoutParams(menus_rl);

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

        mPlayer = new AliVcMediaPlayer(XbPlayUIActivity.this, mSurfaceView);

        mPlayer.setPreparedListener(new MediaPlayer.MediaPlayerPreparedListener() {
            @Override
            public void onPrepared() {
//                showVideoSizeInfo();
                Log.d("kevin", "huidiao--- " + "7");
//                play_ui_ts.setVisibility(View.VISIBLE);
//                surfaceView_play_img.setVisibility(View.VISIBLE);

//                Toast.makeText(XbPlayUIActivity.this, "111111", Toast.LENGTH_SHORT).show();

                play_ui_ts.setVisibility(View.GONE);
                surfaceView_play_img.setVisibility(View.GONE);
                if(hp_ui.getVisibility()!=View.VISIBLE){
                menus.setVisibility(View.VISIBLE);}

            }
        });
        mPlayer.setFrameInfoListener(new MediaPlayer.MediaPlayerFrameInfoListener() {
            @Override
            public void onFrameInfoListener() {
                Log.d("kevin", "huidiao--- " + "6");
                Map<String, String> debugInfo = mPlayer.getAllDebugInfo();
                long createPts = 0;
                if (debugInfo.get("create_player") != null) {
                    String time = debugInfo.get("create_player");
                    createPts = (long) Double.parseDouble(time);
                    //.add(format.format(new Date(createPts)) + getString(R.string.log_player_create_success));
                }
                if (debugInfo.get("open-url") != null) {
                    String time = debugInfo.get("open-url");
                    long openPts = (long) Double.parseDouble(time) + createPts;
                    //.add(format.format(new Date(openPts)) + getString(R.string.log_open_url_success));
                }
                if (debugInfo.get("find-stream") != null) {
                    String time = debugInfo.get("find-stream");
                    Log.d(TAG + "lfj0914", "find-Stream time =" + time + " , createpts = " + createPts);
                    long findPts = (long) Double.parseDouble(time) + createPts;
                    //.add(format.format(new Date(findPts)) + getString(R.string.log_request_stream_success));
                }
                if (debugInfo.get("open-stream") != null) {
                    String time = debugInfo.get("open-stream");
                    Log.d(TAG + "lfj0914", "open-Stream time =" + time + " , createpts = " + createPts);
                    long openPts = (long) Double.parseDouble(time) + createPts;
                    //.add(format.format(new Date(openPts)) + getString(R.string.log_start_open_stream));
                }
                //.add(format.format(new Date()) + getString(R.string.log_first_frame_played));
            }
        });
        mPlayer.setErrorListener(new MediaPlayer.MediaPlayerErrorListener() {
            @Override
            public void onError(int i, String msg) {
                Log.d("kevin", "huidiao--- " + "5");
                play_ui_ts.setVisibility(View.VISIBLE);
                surfaceView_play_img.setVisibility(View.VISIBLE);
                menus.setVisibility(View.GONE);
                String errmsg = msg;
                ts_button.setText(errmsg);
//                ts_button.setVisibility(View.GONE);
//                Toast.makeText(XbPlayUIActivity.this, "222222", Toast.LENGTH_SHORT).show();
//                initPlayUI();
//                start();
                mPlayer.stop();
                start();
            }
        });
        mPlayer.setCompletedListener(new MediaPlayer.MediaPlayerCompletedListener() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted--- ");
                Log.d("kevin", "huidiao--- " + "4");
                isCompleted = true;
                play_ui_ts.setVisibility(View.VISIBLE);
                surfaceView_play_img.setVisibility(View.VISIBLE);
                menus.setVisibility(View.GONE);
                String errmsg = "未开始";
                ts_title.setText(errmsg);
                ts_button.setVisibility(View.INVISIBLE);
            }
        });
        mPlayer.setSeekCompleteListener(new MediaPlayer.MediaPlayerSeekCompleteListener() {
            @Override
            public void onSeekCompleted() {
                //.add(format.format(new Date()) + getString(R.string.log_seek_completed));
                Log.d("kevin", "huidiao--- " + "3");
//                Toast.makeText(XbPlayUIActivity.this, "33333333", Toast.LENGTH_SHORT).show();
            }
        });
        mPlayer.setStoppedListener(new MediaPlayer.MediaPlayerStoppedListener() {
            @Override
            public void onStopped() {
                isCompleted = false;
                //.add(format.format(new Date()) + getString(R.string.log_play_stopped));
                Log.d("kevin", "huidiao--- " + "2");
                isBf = false;
                play_ui_ts.setVisibility(View.VISIBLE);
                surfaceView_play_img.setVisibility(View.VISIBLE);
                menus.setVisibility(View.GONE);
//                String errmsg = "直播暂停";
//                ts_button.setText(errmsg);
                ts_button.setVisibility(View.INVISIBLE);
//                Toast.makeText(XbPlayUIActivity.this, "4444444", Toast.LENGTH_SHORT).show();
            }
        });
        mPlayer.setBufferingUpdateListener(new MediaPlayer.MediaPlayerBufferingUpdateListener() {
            @Override
            public void onBufferingUpdateListener(int percent) {

                Log.d("kevin", "huidiao--- " + "1");
//                Toast.makeText(XbPlayUIActivity.this, "55555555", Toast.LENGTH_SHORT).show();

//                initPlayUI();
         start();

            }
        });
        mPlayer.enableNativeLog();

    }

    private void replay() {
        if(isPlay){

            play_btn_2.setImageDrawable(getResources().getDrawable(R.drawable.bf_));
            isPlay = false;
            stop();
        }else{

            play_btn_2.setImageDrawable(getResources().getDrawable(R.drawable.zt_));
            start();
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
//        play_ui_ts.setVisibility(View.GONE);
//        surfaceView_play_img.setVisibility(View.GONE);

        if(!isBf){
            isBf = true;
            play_ui_ts.setVisibility(View.GONE);
            surfaceView_play_img.setVisibility(View.GONE);
            if(!mIsLand){
            menus.setVisibility(View.VISIBLE);
        }
        }

        if (mPlayer != null) {
            try{
                mPlayer.prepareAndPlay(mUrl);
                mPlayer.play();

            }
            catch (NullPointerException ex){

            }
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
                mPlayer.pause();}
            catch (NullPointerException ex){
                Log.e("mPlayer","走了"+mPlayer.toString());
            }
        }
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.full_srceen:

                if(play_ui_ts.getVisibility()==View.VISIBLE){
                    return;
                }

                fullScreenOper();
                break;

            case R.id.back_full_2:
                fullScreenOper();
                break;
            case R.id.surfaceView_play:
                if(play_ui_ts.getVisibility() == View.VISIBLE){
                    return;
                }
                if(mIsLand){
                    if(hp_ui.getVisibility()==View.VISIBLE){

                        hp_ui.setVisibility(View.GONE);
                        mDivergeView.setVisibility(View.GONE);
                        lwimg.setVisibility(View.GONE);

                    }else{
                        hp_ui.setVisibility(View.VISIBLE);
                        mDivergeView.setVisibility(View.VISIBLE);
                        lwimg.setVisibility(View.VISIBLE);
                    }
                }else{
                    if(!isPlayInit){
                        return;
                    }
                   /* if(menus.getVisibility()==View.VISIBLE){
                        menus.setVisibility(View.GONE);
                    }else{
                        menus.setVisibility(View.VISIBLE);
                    }*/
                }

                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.refresh_btn:
                refresh();
                break;
            case R.id.refresh_btn2:
                refresh();
                break;


            case R.id.score_text:
                startActivity(new Intent(XbPlayUIActivity.this,PayWebActivity.class));
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


    //锁定屏幕
    private void lock() {
        if(isLock){
            lock_btn.setImageDrawable(getResources().getDrawable(R.drawable.sd_));
            play_bottom.setVisibility(View.VISIBLE);
            play_title.setVisibility(View.VISIBLE);
            mDivergeView.setVisibility(View.VISIBLE);

            lwimg.setVisibility(View.VISIBLE);

            SeekBar bar1 = ButterKnife.findById(this, recorf_seekbar);
            bar1.setVisibility(View.VISIBLE);
            SeekBar bar2= ButterKnife.findById(this, luminance_seekbar);
            bar2.setVisibility(View.VISIBLE);
            isLock = false;
        }else{
            lock_btn.setImageDrawable(getResources().getDrawable(R.drawable.js_));
            play_bottom.setVisibility(View.GONE);
            play_title.setVisibility(View.GONE);
            mDivergeView.setVisibility(View.GONE);

            lwimg.setVisibility(View.GONE);

            SeekBar bar1 = ButterKnife.findById(this, recorf_seekbar);
            bar1.setVisibility(View.GONE);
            SeekBar bar2= ButterKnife.findById(this, luminance_seekbar);
            bar2.setVisibility(View.GONE);
            isLock = true;
        }
    }

    private void destroy() {
        try{
            if (mPlayer != null) {
                mPlayer.stop();
                mPlayer.destroy();
            }}catch (NullPointerException e){
            Log.e("call",e.toString());
        }
    }

    private void refresh() {

       /* stop();
        start();*/

//停止播放
        ts_title.setText("正在加载...");
        ts_button.setVisibility(View.INVISIBLE);
        mPlayer.stop();



        start();

/*
        */
/** 倒计时60秒，一次1秒 *//*

        timer2 = new CountDownTimer(1*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {
//                        Toast.makeText(XbPlayUIActivity.this, "调用", Toast.LENGTH_SHORT).show();
                Log.e("kankan","javascript:tvdown('"+urlmark+"')");
                //开始播放


            }
        }.start();
*/



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
            mDivergeView.setVisibility(View.VISIBLE);
            menus.setVisibility(View.GONE);
            db_title.setVisibility(View.GONE);
            dpv_broadcast.setVisibility(View.GONE);
            dpv_broadcast2.setVisibility(View.VISIBLE);
            edit_ll.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) ;//隐藏状态栏
            ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
            lp.height = ActionBar.LayoutParams.MATCH_PARENT;
            lp.width = ActionBar.LayoutParams.MATCH_PARENT;
            mSurfaceView.setLayoutParams(lp);
            play_ui_ts.setLayoutParams(lp);
            surfaceView_play_img.setLayoutParams(lp);
            Log.e("weizhizhizhizhiqpqpqp","4");
            mIsLand = true;
            mClickLand = false;
        } else {

            if (onClickOrientationListener != null) {
                onClickOrientationListener.portrait();
            }
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
            hp_ui.setVisibility(View.GONE);
            mDivergeView.setVisibility(View.VISIBLE);
            menus.setVisibility(View.VISIBLE);
            db_title.setVisibility(View.VISIBLE);
            dpv_broadcast.setVisibility(View.VISIBLE);
            dpv_broadcast2.setVisibility(View.GONE);
            edit_ll.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams pa_rl = mSurfaceView.getLayoutParams();
            pa_rl.height = (int) (getResources().getDisplayMetrics().heightPixels / 16f * 9f);
            pa_rl.width = ActionBar.LayoutParams.MATCH_PARENT;
            mSurfaceView.setLayoutParams(pa_rl);
            play_ui_ts.setLayoutParams(pa_rl);
            surfaceView_play_img.setLayoutParams(pa_rl);
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
            mDivergeView.setVisibility(View.VISIBLE);
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
/*        Log.e("eventNub","cishu");
        initPlayUI();*/
        if(event.getStatus().equals("close")){

            disShowListUi();

        }if(event.getStatus().equals("startzb")){
//        Toast.makeText(this, "设置了隐藏", Toast.LENGTH_SHORT).show();
        play_ui_ts.setVisibility(View.GONE);
                     surfaceView_play_img.setVisibility(View.GONE);

            menus.setVisibility(View.VISIBLE);
            initPlayUI();
            start();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();

        play_btn_2.setImageDrawable(getResources().getDrawable(R.drawable.bf_));
        isPlay = false;

        if(mPlayer!=null){
            mPlayer.stop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("isplay","123");

    }

    @Override
    protected void onDestroy() {
        stop();
        destroy();
        if(webView!=null) {
            Log.e("dojs","javascript:closeWs()");
            webView.loadUrl("javascript:closeWs()");

            webView.destroy();
        }
     if(listwebview!=null) {
         listwebview.destroy();
        }

        webView=null;

        listwebview = null;
        //maxBufDurationEdit.removeTextChangedListener(watcher);

        if(timer!=null) {
            timer.cancel();
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
