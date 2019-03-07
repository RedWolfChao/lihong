package com.quanying.app.ui.showroominsidepage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.quanying.app.R;
import com.quanying.app.adapter.ShowRoomDetailsAdapter;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.ShowRoomDetailsBean;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.ui.user.LoginActivity;
import com.quanying.app.utils.AppKeyBoardMgr;
import com.quanying.app.weburl.WebUri;
import com.tsy.sdk.social.PlatformType;
import com.tsy.sdk.social.SocialApi;
import com.tsy.sdk.social.listener.ShareListener;
import com.tsy.sdk.social.share_media.ShareWebMedia;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import me.curzbin.library.BottomDialog;
import me.curzbin.library.Item;
import me.curzbin.library.OnItemClickListener;
import okhttp3.Call;

public class ShowRoominsideActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.ll_head)
    LinearLayout ll_head;
    @BindView(R.id.details_msg_send)
    TextView details_msg_send;
    @BindView(R.id.details_msg_edit)
    EditText details_msg_edit;
    @BindView(R.id.edit_ll)
    LinearLayout edit_ll;
    @BindView(R.id.recyclerview)
    LRecyclerView recyclerview;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ShowRoomDetailsAdapter mAdapter;
    private ShowRoomDetailsBean mBean;

    //这是你的数据
    List<String> list = new ArrayList<>();


/*
    @BindView(R.id.photo1)
    KImageView view1;
    @BindView(R.id.photo2)
    KImageView view2;
    @BindView(R.id.photo3)
    KImageView view3;
    @BindView(R.id.photo4)
    KImageView view4;
    @BindView(R.id.photo5)
    KImageView view5;
    @BindView(R.id.photo6)
    KImageView view6;

*/

    private String pid;
    private SocialApi mSocialApi;

//    public static


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_show_roominside;
    }


    @OnClick(R.id.details_msg_send)
    public void sendMsg(){

        if(MyApplication.getToken().equals("")){

            setIntentClass(LoginActivity.class);
            return;
        }


           String msg = details_msg_edit.getText().toString().trim();
           if(TextUtils.isEmpty(msg)){

               showBaseDialog("请输入评论内容","好");
               return;

           }
           OkHttpUtils
                   .post()
                   .url(WebUri.PL)
                   .addParams("token", MyApplication.getToken())
                   .addParams("id", mBean.getData().getId())
                   .addParams("pid",pid+"")
                   .addParams("content",msg+"")
                   .build()
                   .execute(new StringCallback() {
                       @Override
                       public void onError(Call call, Exception e, int id) {

                       }

                       @Override
                       public void onResponse(String response, int id) {

                           try {
                               JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.getString("errcode").equals("200")){

                                    pid = "";
                                    details_msg_edit.setText("");
                                    details_msg_edit.setHint("添加评论");
                                    mAdapter.loadFoot();
                                    mAdapter.loadNew();
                                    freashData();

                                }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }


                       }
                   });






    }


    public void freashData(){


        String ids = getIntent().getStringExtra("ids");
        Log.e("photoids",ids);
        OkHttpUtils
                .post()
                .url(WebUri.SHOWROOMDETAILS)
                .addParams("token", MyApplication.getToken())
                .addParams("id", ids)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("details",response);
                        LogUtils.json(response);
                        mBean = new Gson().fromJson(response,ShowRoomDetailsBean.class);
                        if(mBean.getErrcode().equals("200")){
                            mAdapter.updataItem(mBean);

//                            recyclerview.setAdapter(mAdapter);
//                            recyclerview.smoothScrollToPosition(4);
                        }


                    }
                });

    }

    @Override
    protected void initData() {

        String ids = getIntent().getStringExtra("ids");
        Log.e("phpids",ids);
        OkHttpUtils
                .post()
                .url(WebUri.SHOWROOMDETAILS)
                .addParams("token", MyApplication.getToken())
                .addParams("id", ids)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("details",response);
                        LogUtils.json(response);
                        mBean = new Gson().fromJson(response,ShowRoomDetailsBean.class);
                        if(mBean.getErrcode().equals("200")){
                            mAdapter = new ShowRoomDetailsAdapter(context,mBean);
                            recyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                            recyclerview.setPullRefreshEnabled(false);
//                            recyclerview.setLoadMoreEnabled(false);;

                            List<ShowRoomDetailsBean.DataBean.ImagesBean> mList = mBean.getData().getImages();
                            for(int i =0 ;i<mList.size();i++){

                                list .add(""+mList.get(i).getSrc());

                            }
                            mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
                            recyclerview.setAdapter(mLRecyclerViewAdapter);
                            mLRecyclerViewAdapter.removeFooterView();
//                            recyclerview.setAdapter(mAdapter);
                            recyclerview.smoothScrollToPosition(0);
                        }


                    }
                });

        details_msg_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppKeyBoardMgr.openKeybord(details_msg_edit,context);
            }
        });


        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //当前RecyclerView显示出来的最后一个的item的position

//                Toast.makeText(mContext, "daodile", Toast.LENGTH_SHORT).show();
               /* Log.e("weizhi123",newState+"");
                int lastPosition = -1;
                if(newState ==lastPosition){

                    Toast.makeText(context, "daodile", Toast.LENGTH_SHORT).show();

                }*/


            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

//                SoftKeyBoardListener.

//                AppKeyBoardMgr.closeKeybord(details_msg_edit,context);
                pid = "";
                details_msg_edit.setText("");
                details_msg_edit.setHint("添加评论");


                //得到当前显示的最后一个item的view
                View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount()-1);
                //得到lastChildView的bottom坐标值
                int lastChildBottom = lastChildView.getBottom();
                //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                int recyclerBottom =  recyclerView.getBottom()-recyclerView.getPaddingBottom();
                //通过这个lastChildView得到这个view当前的position值
                int lastPosition  = recyclerView.getLayoutManager().getPosition(lastChildView);

                //判断lastChildView的bottom值跟recyclerBottom
                //判断lastPosition是不是最后一个position
                //如果两个条件都满足则说明是真正的滑动到了底部
                if(lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount()-1 ){
//                    Toast.makeText(context, "滑动到底了", Toast.LENGTH_SHORT).show();
                    mAdapter.loadFoot();

                }
            }

        });



/*

        recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                Toast.makeText(context, "gahsjiashasij", Toast.LENGTH_SHORT).show();
                mAdapter.loadFoot();
         */
/*       OkHttpUtils
                        .post()
                        .url(WebUri.COMMENTSLIST)
                        .addParams("id", ids)
                        .addParams("page", ids)

                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {

//                                Log.e("dongdong",response);
                                ShowRoomDetailsCommentsBean mBean = new  Gson().fromJson(response,ShowRoomDetailsCommentsBean.class);
                                if(mBean.getErrcode().equals("200")){

//                                    ShowRoomCommentsAdapter adapter = new ShowRoomCommentsAdapter(mBean,mContext);

                                    if(mBean.getData().size()>0){
                                        Page = mBean.getData().get(mBean.getData().size()-1).getId();

                                        adapter.addAll(mBean);
//                            mAdapter.notifyItemRangeInserted(0,size);
                                        mCompaniesViewHolder.mRecyclerView.refreshComplete(mBean.getData().size());// REQUEST_COUNT为每页加载数量
                                    }else{
                                        mLRecyclerViewAdapter.removeFooterView();
                                    }

//                                    mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
//
//                                    mCompaniesViewHolder.mRecyclerView.setAdapter(mLRecyclerViewAdapter);


                                }
                            }
                        });
*//*


            }
        });

*/


    }

    /*
     * 注册事件，监听是否改变用户信息
     * */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if(messageEvent.getStatus().equals("send")){
//            mText.setText(messageEvent.getMessage());
//            initData();
//            Toast.makeText(context, "sendddddd", Toast.LENGTH_SHORT).show();
            details_msg_edit.setHint("回复　"+messageEvent.getContext()+"　:");
            pid = messageEvent.getMessage();

        }if(messageEvent.getStatus().equals("checkImg")){

//            Toast.makeText(context, "dianji"+messageEvent.getMessage(), Toast.LENGTH_SHORT).show();

            int dz = Integer.parseInt(messageEvent.getMessage())-1;

            File downloadDir = new File(Environment.getExternalStorageDirectory(), "quanying");
            BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(this)
                    .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能
            if (list.size() == 1) {
                // 预览单张图片
                photoPreviewIntentBuilder.previewPhoto(list.get(0));
            } else if (list.size() > 1) {
                // 预览多张图片
                photoPreviewIntentBuilder.previewPhotos((ArrayList<String>) list)
                        .currentPosition(dz); // 当前预览图片的索引
            }
            startActivity(photoPreviewIntentBuilder.build());
        }
    }

    private void showShareUi(final String title, final String link, final String nr) {

        new BottomDialog(context)
                .title("分享到")
                .orientation(BottomDialog.HORIZONTAL)
                .inflateMenu(R.menu.menu_share, new OnItemClickListener() {
                    @Override
                    public void click(Item item) {
//                        Toast.makeText(HomeActivity.this, getString(R.string.share_title) + item.getTitle(), Toast.LENGTH_SHORT).show();

                        if(item.getTitle().equals("QQ")){

                            ShareWebMedia shareMedia = new ShareWebMedia();
                            shareMedia.setTitle(title);
                            shareMedia.setDescription(nr);
                            shareMedia.setWebPageUrl(link);
                            shareMedia.setThumb(readBitMap(getApplicationContext(), R.mipmap.appicon));

                            mSocialApi.doShare(ShowRoominsideActivity.this, PlatformType.QQ, shareMedia, new ShareListener() {
                                @Override
                                public void onComplete(PlatformType platform_type) {
                                    Log.i("tsy", "share onComplete");
                                }

                                @Override
                                public void onError(PlatformType platform_type, String err_msg) {
                                    Log.i("tsy", "share onError:" + err_msg);
                                }

                                @Override
                                public void onCancel(PlatformType platform_type) {
                                    Log.i("tsy", "share onCancel");
                                }
                            });
                        }

                        if(item.getTitle().equals("微信")) {
                            ShareWebMedia shareMedia = new ShareWebMedia();
                            shareMedia.setTitle(title);
                            shareMedia.setDescription(nr);
                            shareMedia.setWebPageUrl(link);
                            shareMedia.setThumb(readBitMap(getApplicationContext(), R.mipmap.appicon));

                            mSocialApi.doShare(ShowRoominsideActivity.this, PlatformType.WEIXIN, shareMedia, new ShareListener() {
                                @Override
                                public void onComplete(PlatformType platform_type) {
                                    Log.i("tsy", "share onComplete");
                                }

                                @Override
                                public void onError(PlatformType platform_type, String err_msg) {
                                    Log.i("tsy", "share onError:" + err_msg);
                                }

                                @Override
                                public void onCancel(PlatformType platform_type) {
                                    Log.i("tsy", "share onCancel");
                                }
                            });

                        }

                        if(item.getTitle().equals("QQ空间")){
                            ShareWebMedia shareMedia = new ShareWebMedia();
                            shareMedia.setTitle(title);
                            shareMedia.setDescription(nr);
                            shareMedia.setWebPageUrl(link);
                            shareMedia.setThumb(readBitMap(getApplicationContext(), R.mipmap.appicon));

                            mSocialApi.doShare(ShowRoominsideActivity.this, PlatformType.QZONE, shareMedia, new ShareListener() {
                                @Override
                                public void onComplete(PlatformType platform_type) {
                                    Log.i("tsy", "share onComplete");
                                }

                                @Override
                                public void onError(PlatformType platform_type, String err_msg) {
                                    Log.i("tsy", "share onError:" + err_msg);
                                }

                                @Override
                                public void onCancel(PlatformType platform_type) {
                                    Log.i("tsy", "share onCancel");
                                }
                            });
                        }

                        if(item.getTitle().equals("朋友圈")){
                            ShareWebMedia shareMedia = new ShareWebMedia();
                            shareMedia.setTitle(title);
                            shareMedia.setDescription(nr);
                            shareMedia.setWebPageUrl(link);
                            shareMedia.setThumb(readBitMap(getApplicationContext(), R.mipmap.appicon));

                            mSocialApi.doShare(ShowRoominsideActivity.this, PlatformType.WEIXIN_CIRCLE, shareMedia, new ShareListener() {
                                @Override
                                public void onComplete(PlatformType platform_type) {
                                    Log.i("tsy", "share onComplete");
                                }

                                @Override
                                public void onError(PlatformType platform_type, String err_msg) {
                                    Log.i("tsy", "share onError:" + err_msg);
                                }

                                @Override
                                public void onCancel(PlatformType platform_type) {
                                    Log.i("tsy", "share onCancel");
                                }
                            });
                        }
                    }
                })
                .show();

    }

    private Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
    @Override
    protected void initView() {

        EventBus.getDefault().register(this);
        mSocialApi = SocialApi.get(getApplicationContext());
        titlebar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
                if (action == CommonTitleBar.ACTION_RIGHT_BUTTON) {

                    showShareUi(mBean.getData().getNickname()+"-全影作品",mBean.getData().getSharelink()+"","7192.com影楼行业第一门户网站");
                }
            }
        });

        autoScrollView(ll_head,edit_ll);

//        SHOWROOMDETAILS



      /*  final Myview view1 = findViewById(R.id.photo1);
        final Myview view2 = findViewById(R.id.photo2);
        final Myview view3 = findViewById(R.id.photo3);
        final Myview view4 = findViewById(R.id.photo4);
        final Myview view5 = findViewById(R.id.photo5);
        final Myview view6 = findViewById(R.id.photo6);


        WindowManager wm1 = this.getWindowManager();
        int width = wm1.getDefaultDisplay().getWidth();
        int miniX = (width-15*2-4)/3*2;
        int miniX2 = miniX/2;
        Log.e("viewheight",miniX+"");
//        view1.viewWidth = miniX;
        ViewGroup.LayoutParams lp;
        lp=view1.getLayoutParams();
        lp.width=miniX2*2+4;
        lp.height=miniX;
        view1.setLayoutParams(lp);

        ViewGroup.LayoutParams lp2;
        lp2=view2.getLayoutParams();
        lp2.width=miniX/2;
        lp2.height=miniX/2;
        view2.setLayoutParams(lp2);

        ViewGroup.LayoutParams lp3;
        lp3=view3.getLayoutParams();
        lp3.width=miniX/2;
        lp3.height=miniX/2;
        view3.setLayoutParams(lp3);


        ViewGroup.LayoutParams lp4;
        lp4=view4.getLayoutParams();
        lp4.width=miniX2;
        lp4.height=miniX2;
        view4.setLayoutParams(lp4);

        ViewGroup.LayoutParams lp5;
        lp5=view5.getLayoutParams();
        lp5.width=miniX2;
        lp5.height=miniX2;
        view5.setLayoutParams(lp5);

        ViewGroup.LayoutParams lp6;
        lp6=view6.getLayoutParams();
        lp6.width=miniX2;
        lp6.height=miniX2;
        view6.setLayoutParams(lp6);

        margin(view1,15,0,4,0);
        margin(view2,0,0,15,4);
        margin(view3,0,0,15,0);
        margin(view4,15,4,4,0);
        margin(view5,0,4,4,0);
        margin(view6,0,4,15,0);

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+view1.getHeight()+"kuan:"+view1.getWidth(), Toast.LENGTH_SHORT).show();
            }
        });
       view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+view2.getHeight()+"kuan:"+view2.getWidth(), Toast.LENGTH_SHORT).show();
            }
        });
       view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+view3.getHeight()+"kuan:"+view3.getWidth(), Toast.LENGTH_SHORT).show();
            }
        });
       view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+view4.getHeight()+"kuan:"+view4.getWidth(), Toast.LENGTH_SHORT).show();
            }
        });
       view5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+view5.getHeight()+"kuan:"+view5.getWidth(), Toast.LENGTH_SHORT).show();
            }
        });
       view6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+view6.getHeight()+"kuan:"+view6.getWidth(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void margin(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        list.clear();
        list =null;

        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

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


}
