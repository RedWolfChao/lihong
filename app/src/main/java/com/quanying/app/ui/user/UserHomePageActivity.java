package com.quanying.app.ui.user;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.gson.Gson;
import com.lzy.widget.PullZoomView;
import com.lzy.widget.manager.ExpandLinearLayoutManager;
import com.quanying.app.R;
import com.quanying.app.adapter.UserPageAdapter;
import com.quanying.app.adapter.UserPageEnterpriseAdapter;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.MyCreationBean;
import com.quanying.app.bean.QyUserPageBean;
import com.quanying.app.bean.UpBean;
import com.quanying.app.bean.UserPageHeadBean;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.utils.AppUtils;
import com.quanying.app.weburl.WebUri;
import com.tsy.sdk.social.PlatformType;
import com.tsy.sdk.social.SocialApi;
import com.tsy.sdk.social.listener.ShareListener;
import com.tsy.sdk.social.share_media.ShareWebMedia;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGABaseAdapterUtil;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.imageloader.BGAImage;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import me.curzbin.library.BottomDialog;
import me.curzbin.library.Item;
import me.curzbin.library.OnItemClickListener;
import okhttp3.Call;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class UserHomePageActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView ;
    @BindView(R.id.touxiang)
    SimpleDraweeView touxiang ;
    @BindView(R.id.pzv)
    PullZoomView pzv ;
    @BindView(R.id.biaoqian)
    TextView biaoqian ;
    @BindView(R.id.up_usersex)
    TextView up_usersex ;
    @BindView(R.id.up_age)
    TextView up_age ;
    @BindView(R.id.up_city)
    TextView up_city ;
//    顶部标题
    private View titleBar_Bg;
    private TextView titleBar_title;
    private View status_bar_fix;
    private View titleBar;

    @BindView(R.id.ub_bck)
    SimpleDraweeView ub_bck;
    @BindView(R.id.isVip)
    ImageView isVip;
    @BindView(R.id.isQy)
    ImageView isQy;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.add_worksbtn)
    ImageView add_worksbtn;

    @BindView(R.id.fenxiang)
    ImageView fenxiang;


    private static final int REQUEST_CODE_PERMISSION_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_PERMISSION_CHOOSE_PHOTO2 = 10;

    private UserPageHeadBean mBean ;
    private UserPageAdapter upAdapter;
    private UserPageEnterpriseAdapter qyAdapter;
    private MyCreationBean zpBean ;

    private boolean isjz;
    private String ids;
    private String Page = "0";
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 102;
    private boolean isQyStatus ;
    private QyUserPageBean qyBean;
    private BGAPhotoHelper mPhotoHelper;
    private static final int REQUEST_CODE_CROP = 3;


    private static final int RC_CHOOSE_PHOTO = 101;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_userhomepage;
    }

    @Override
    protected void initData() {


        OkHttpUtils
                .post()
                .url(WebUri.USERPAGEHEAD)
                .addParams("token", MyApplication.getToken())
                .addParams("userid", ids)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("userresp",ids+response);

                        mBean = new Gson().fromJson(response,UserPageHeadBean.class);
                        if(mBean.getErrcode().equals("200")){

                            titleBar_title.setText(mBean.getInfo().getNickname());
                            if(!TextUtils.isEmpty(mBean.getInfo().getAreaname())){
                                up_city.setText(mBean.getInfo().getAreaname());
                            }
                            String face = mBean.getFace();
                            Uri uri = Uri.parse(face);
                            ImagePipeline imagePipeline = Fresco.getImagePipeline();
                            imagePipeline.evictFromMemoryCache(uri);
                            imagePipeline.evictFromDiskCache(uri);
//                                AppSharePreferenceMgr.set(getMContext(),"userid","");
// combines above two lines
                            imagePipeline.evictFromCache(uri);
                            touxiang.setImageURI(uri);

                            if(mBean.getInfo().getBg().equals("")||mBean.getInfo().getBg()==null){

                                Uri img1 = Uri.parse("res://com.quanying.app/"+R.mipmap.userpagebck1);
                                Uri img2 = Uri.parse("res://com.quanying.app/"+R.mipmap.userpagebck2);
                                Uri img3 = Uri.parse("res://com.quanying.app/"+R.mipmap.userpagebck3);
                                Uri img4 = Uri.parse("res://com.quanying.app/"+R.mipmap.userpagebck4);
                                Uri img5 = Uri.parse("res://com.quanying.app/"+R.mipmap.userpagebck5);

                                int bk_ = Integer.parseInt(mBean.getUid().substring(mBean.getUid().length()-1, mBean.getUid().length()));
                                int jl = bk_ / 2;
                                switch(jl){
                                        case 0:
                                            ub_bck.setImageURI(img1);
                                            break;
                                        case 1:
                                            ub_bck.setImageURI(img2);
                                            break;
                                   case 2:
                                       ub_bck.setImageURI(img3);
                                            break;
                                   case 3:
                                       ub_bck.setImageURI(img4);
                                            break;
                                   case 4:
                                       ub_bck.setImageURI(img5);
                                            break;

                                }


                            }else {
                                Uri zoomviewurl = Uri.parse(mBean.getInfo().getBg());
                                ub_bck.setImageURI(zoomviewurl);
                            }

                            if(mBean.getHrtype().equals("2")){

                                Log.e("dingweiqiye","1");

                                isQyStatus = true;
                                qyBean = new Gson().fromJson(response,QyUserPageBean.class);
                                Log.e("dingweiqiye","2");


                                isVip.setVisibility(View.VISIBLE);
                                if(qyBean.getInfo().getIsvip().equals("yes")){
                                    isVip.setImageResource(R.mipmap.vip_userhead);}else {

                                    isVip.setImageResource(R.mipmap.vip_wei);
                                }
                                isQy.setVisibility(View.VISIBLE);
                                if(!qyBean.getIscert().equals("1")){
                                    isQy.setClickable(true);
                                    isQy.setImageResource(R.mipmap.qyrz_mei);

                                }
                                Log.e("dingweiqiye","3");
                                getQyZp(qyBean);
                                return;
                            }else{
                                if(!TextUtils.isEmpty(mBean.getInfo().getLabs())){
                                    biaoqian.setVisibility(View.VISIBLE);
                                    biaoqian.setText(mBean.getInfo().getLabs());

                                }if(!TextUtils.isEmpty(mBean.getInfo().getAge())){
                                    up_age.setVisibility(View.VISIBLE);
                                    up_age.setText(mBean.getInfo().getAge());

                                }
                            }if(!TextUtils.isEmpty(mBean.getInfo().getGender())){
                                up_usersex.setVisibility(View.VISIBLE);
                                up_usersex.setText(mBean.getInfo().getGender());

                            }
                        }
                        getZp(mBean);

                        }


                });



    }

    private void getZp(final UserPageHeadBean mBean) {

        OkHttpUtils
                .post()
                .url(WebUri.USERPAGEIMG)
                .addParams("token", MyApplication.getToken())
                .addParams("userid", ids)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("kevinthis",ids+response);

                        zpBean = new Gson().fromJson(response,MyCreationBean.class);
                        if(zpBean.getErrcode().equals("200")){
                            Page = zpBean.getData().get(zpBean.getData().size()-1).getId();

                            Log.e("kevinthis","lalal!!!!!!!!!");

                            if(upAdapter==null){
                                upAdapter = new UserPageAdapter(context,mBean,zpBean.getData());
                                recyclerView.setAdapter(upAdapter);
                            }else{

                                upAdapter.updata(zpBean.getData());
                            }

                        }else{
                            if(upAdapter==null){
                                upAdapter = new UserPageAdapter(context,mBean,zpBean.getData());
                                recyclerView.setAdapter(upAdapter);
                            }else{

                                upAdapter.updata(zpBean.getData());
                            }

                        }


                    }
                });



    }

    private void getQyZp(final QyUserPageBean qyBean) {

        OkHttpUtils
                .post()
                .url(WebUri.USERPAGEIMG)
                .addParams("token", MyApplication.getToken())
                .addParams("userid", ids)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("kevinthis",ids+response);

                        zpBean = new Gson().fromJson(response,MyCreationBean.class);
                        if(zpBean.getErrcode().equals("200")){
                            Page = zpBean.getData().get(zpBean.getData().size()-1).getId();

                            Log.e("kevinthis","lalal!!!!!!!!!");

                                qyAdapter = new UserPageEnterpriseAdapter(context,qyBean,zpBean.getData());
                                recyclerView.setAdapter(qyAdapter);

                        }else{

                            if(qyAdapter==null){
                                qyAdapter = new UserPageEnterpriseAdapter(context,qyBean,zpBean.getData());
                                recyclerView.setAdapter(qyAdapter);
                            }else{

//                                qyAdapter.updata(zpBean.getData());
                                qyAdapter = new UserPageEnterpriseAdapter(context,qyBean,zpBean.getData());
                                recyclerView.setAdapter(qyAdapter);
                            }

                          /*  if(qyAdapter==null){
                                qyAdapter = new UserPageEnterpriseAdapter(context,qyBean,zpBean.getData());
                                recyclerView.setAdapter(qyAdapter);
                            }else{
                                qyAdapter.updata(zpBean.getData());
                            }*/

                        }


                    }
                });

    }

    private void delQyZp(String qy_img_id){

        OkHttpUtils
                .post()
                .url(WebUri.DELQYIMG)
                .addParams("token", MyApplication.getToken())
                .addParams("id",qy_img_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("errcode").equals("200"))
                            {

//                                initHeader();
                                initData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void initHeader() {

        OkHttpUtils
                .post()
                .url(WebUri.USERPAGEHEAD)
                .addParams("token", MyApplication.getToken())
                .addParams("userid", ids)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("kankan",response);
                        qyBean = null;
                        qyBean = new Gson().fromJson(response,QyUserPageBean.class);
                        if(mBean.getErrcode().equals("200")){

                            qyAdapter.updataHeader(qyBean);
//                            qyAdapter.notifyDataSetChanged();
                        }

                    }
                });

    }

    private void addZp(final UserPageHeadBean mBean) {
        isjz = true;
        OkHttpUtils
                .post()
                .url(WebUri.USERPAGEIMG)
                .addParams("token", MyApplication.getToken())
                .addParams("userid",ids)
                .addParams("page",Page+"")

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("kevinthis",response);
//                        MyCreationBean.DataBean caonima = (MyCreationBean.DataBean) zpBean.getData();
                        MyCreationBean addBean = new Gson().fromJson(response,MyCreationBean.class);
                        if(addBean.getErrcode().equals("200")){
                            Page = addBean.getData().get(addBean.getData().size()-1).getId();

                            Log.e("kevinthis","lalal!!!!!!!!!");

                            if(upAdapter==null){
                            upAdapter = new UserPageAdapter(context,mBean,addBean.getData());
                            recyclerView.setAdapter(upAdapter);
                            }else{
                                for(int i=0;i<addBean.getData().size();i++){

                                    UpBean uBean = addBean.getData().get(i);
                                    zpBean.getData().add(uBean);

                                }

                                upAdapter.updata(zpBean.getData());
                                isjz = false;
                            }

                        }else{

                           /* if(upAdapter==null){
                                upAdapter = new UserPageAdapter(context,mBean,zpBean);
                                recyclerView.setAdapter(upAdapter);
                            }else{

                                upAdapter.updata(zpBean);
                            }
*/
                        }


                    }
                });



    }

    @OnClick(R.id.add_worksbtn)
    public void jumpAddCreationUi(){

        setIntentClass(AddCreationActivity.class);

    }


    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        Log.e("meiyouzhi?",ids+"");

        ids = getIntent().getStringExtra("ids");
        if(!ids.equals(MyApplication.getUserID())){

            add_worksbtn.setVisibility(View.INVISIBLE);
            add_worksbtn.setClickable(false);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        titleBar = findViewById(R.id.titleBar);
        titleBar_Bg = titleBar.findViewById(R.id.bg);
        //当状态栏透明后，内容布局会上移，这里使用一个和状态栏高度相同的view来修正内容区域
        status_bar_fix = titleBar.findViewById(R.id.status_bar_fix);
        status_bar_fix.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtils.getStatusHeight(context)));
        /*
        * 设置透明标题
        * */
        titleBar_title = (TextView) titleBar.findViewById(R.id.title);
        titleBar_Bg.setAlpha(0);
        status_bar_fix.setAlpha(0);

        ExpandLinearLayoutManager ms= new ExpandLinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);// 设置 recyclerview 布局方式为横向布局
        recyclerView.setLayoutManager(new ExpandLinearLayoutManager(context));
//        recyclerView.setAdapter(new MyAdapter());

        float sensitive =  1.5f;
        int zoomTime = 700;
        boolean isParallax =false;
        boolean isZoomEnable = true;

        pzv.setIsParallax(isParallax);
        pzv.setIsZoomEnable(isZoomEnable);
        pzv.setSensitive(sensitive);
        pzv.setZoomTime(zoomTime);

        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "qy_photo");
        mPhotoHelper = new BGAPhotoHelper(takePhotoDir);

        pzv.setOnScrollListener(new PullZoomView.OnScrollListener() {
            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
                System.out.println("onScroll   t:" + t + "  oldt:" + oldt);

//                Log.d("gaodu",""+Math.abs(totalDy)+" X "+dx);

                View view = (View) pzv.getChildAt(pzv.getChildCount() - 1);

                int d = view.getBottom();

                //根据距离判断是否滑到了底部
                d -= (pzv.getHeight() + pzv.getScrollY());

//        Log.e("---------->","d"+d);
                if (d == 0) {
                    if(!isjz) {


                        if(isQyStatus) {
                            addQyZp(qyBean);
                        }else{

                            addZp(mBean);
                        }

                    }                    Log.e("daodile","111");

                }

            }

            @Override
            public void onHeaderScroll(int currentY, int maxY) {
                System.out.println("onHeaderScroll   currentY:" + currentY + "  maxY:" + maxY);
                float alpha = 1.0f * currentY / maxY;

                titleBar_Bg.setAlpha(alpha);
                status_bar_fix.setAlpha(alpha);

                    titleBar_Bg.setAlpha(alpha);
                    status_bar_fix.setAlpha(alpha);


                /*  titleBar_Bg.setAlpha(alpha);
                  //注意头部局的颜色也需要改变
                  status_bar_fix.setAlpha(alpha);*/

            }

            @Override
            public void onContentScroll(int l, int t, int oldl, int oldt) {
                System.out.println("onContentScroll   t:" + t + "  oldt:" + oldt);



            }
        });
        pzv.setOnPullZoomListener(new PullZoomView.OnPullZoomListener() {
            @Override
            public void onPullZoom(int originHeight, int currentHeight) {
                System.out.println("onPullZoom  originHeight:" + originHeight + "  currentHeight:" + currentHeight);
            }

            @Override
            public void onZoomFinish() {
                System.out.println("onZoomFinish");
            }
        });

    }

    private void addQyZp(final QyUserPageBean qyBean) {
        isjz = true;
        OkHttpUtils
                .post()
                .url(WebUri.USERPAGEIMG)
                .addParams("token", MyApplication.getToken())
                .addParams("userid",ids)
                .addParams("page",Page)
                .addParams("offset", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("kevinthis",response);
//                        MyCreationBean.DataBean caonima = (MyCreationBean.DataBean) zpBean.getData();
                        MyCreationBean addBean = new Gson().fromJson(response,MyCreationBean.class);
                        if(addBean.getErrcode().equals("200")){
                            Page = addBean.getData().get(addBean.getData().size()-1).getId();

                            Log.e("kevinthis","lalal!!!!!!!!!");

                            if(qyAdapter==null){
                                qyAdapter = new UserPageEnterpriseAdapter(context,qyBean,zpBean.getData());
                                recyclerView.setAdapter(qyAdapter);
                            }else{
                                for(int i=0;i<addBean.getData().size();i++){

                                    UpBean uBean = addBean.getData().get(i);
                                    zpBean.getData().add(uBean);

                                }

                                qyAdapter.updata(zpBean.getData());
                                isjz = false;
                            }

                        }else{

                           /* if(upAdapter==null){
                                upAdapter = new UserPageAdapter(context,mBean,zpBean);
                                recyclerView.setAdapter(upAdapter);
                            }else{

                                upAdapter.updata(zpBean);
                            }
*/
                        }


                    }
                });


    }

    /*
    * 设置背景图、1:1裁剪
    * */
    @OnClick(R.id.ub_bck)
    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_CHOOSE_PHOTO2)
    public void setBG(){

       if(!ids.equals(MyApplication.getUserID())){

           return;
       }else {
           String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
           if (EasyPermissions.hasPermissions(this, perms)) {
               startActivityForResult(mPhotoHelper.getChooseSystemGalleryIntent(), REQUEST_CODE_CHOOSE_PHOTO);
           } else {
               EasyPermissions.requestPermissions(this, "请开起存储空间和相机权限，以正常使用上传图片功能！", REQUEST_CODE_PERMISSION_CHOOSE_PHOTO2, perms);
           }
       }
    }


    private void upUserHead(String cropFilePath) {

        OkHttpUtils.post()//
                .addFile("image", "bg.png", new File(cropFilePath))//
                .url(WebUri.UPDATAUSERPAGEBG)
                .addParams("token", MyApplication.getToken())
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("kevinfile",response);
//                        Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("errcode").equals("200")){

//                                更新背景后的操作
                                EventBus.getDefault().post(new MessageEvent("","5"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if(messageEvent.getStatus().equals("gx")){
            if(isQyStatus){
             initData();
            }else{
            getZp(mBean);}
            isjz = false;
        }
        if(messageEvent.getStatus().equals("delzp")){

            if(isQyStatus){
                delQyZp(messageEvent.getMessage());
            }

        } if(messageEvent.getStatus().equals("addzp")){

            Log.e("diaoyong","------------------------------------");

            if(isQyStatus){
//                Toast.makeText(context, "添加作品", Toast.LENGTH_SHORT).show();
                choosePhoto();
            }

        }
      /*  if(messageEvent.getStatus().equals("add")){


            getZp(mBean);
        }*/
    }


    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_CHOOSE_PHOTO)
    public void choosePhoto() {

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "qyw");

        Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                .cameraFileDir(true? takePhotoDir : null) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                .maxChooseCount(1) // 图片选择张数的最大值
                .selectedPhotos(null) // 当前已选中的图片路径集合
                .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                .build();
        startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);}else{
            EasyPermissions.requestPermissions(this, "请开起存储空间和相机权限，以正常使用上传图片功能！", REQUEST_CODE_PERMISSION_CHOOSE_PHOTO, perms);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            if (true) {
//                mPhotosSnpl.setData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                Toast.makeText(context, ""+BGAPhotoPickerActivity.getSelectedPhotos(data).get(0), Toast.LENGTH_SHORT).show();
                Log.e("haha",BGAPhotoPickerActivity.getSelectedPhotos(data).get(0));
                OkHttpUtils.post()//
                        .addFile("image", "qyimg.png", new File(BGAPhotoPickerActivity.getSelectedPhotos(data).get(0)))//
                        .url(WebUri.UPDATAQYIMG)
                        .addParams("token", MyApplication.getToken())
                        .build()//
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("kevinfile",response);
//                        Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject.getString("errcode").equals("200")){


//                                        initHeader();
                                        initData();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                return;
            }
        } else if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
                try {
//                    startActivityForResult(mPhotoHelper.getCropIntent(mPhotoHelper.getFilePathFromUri(data.getData()), 600, 600), REQUEST_CODE_CROP);

//                    mPhotoHelper.getFilePathFromUri(data.getData();
                    upUserHead(mPhotoHelper.getFilePathFromUri(data.getData()));

                    BGAImage.display(ub_bck, R.mipmap.nowebimg, mPhotoHelper.getFilePathFromUri(data.getData()), BGABaseAdapterUtil.dp2px(1000));

                } catch (Exception e) {
                    mPhotoHelper.deleteCropFile();
                    BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CODE_CROP) {
//                Toast.makeText(context, ""+mPhotoHelper.getCropFilePath(), Toast.LENGTH_SHORT).show();

//                upUserHead(mPhotoHelper.getCropFilePath());
//
//                BGAImage.display(ub_bck, R.mipmap.nowebimg, mPhotoHelper.getCropFilePath(), BGABaseAdapterUtil.dp2px(1000));
            }


        } else {
            if (requestCode == REQUEST_CODE_CROP) {
                mPhotoHelper.deleteCameraFile();
                mPhotoHelper.deleteCropFile();
            }
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private SocialApi mSocialApi;
    /*
    * fenxiang
    * */
    @OnClick(R.id.fenxiang)
    public void shareUi(){
        mSocialApi = SocialApi.get(getApplicationContext());
        showShareUi("全影网",mBean.getInfo().getSharelink(),"7192.com影楼行业第一门户网站");
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

                            mSocialApi.doShare(UserHomePageActivity.this, PlatformType.QQ, shareMedia, new ShareListener() {
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

                            mSocialApi.doShare(UserHomePageActivity.this, PlatformType.WEIXIN, shareMedia, new ShareListener() {
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

                            mSocialApi.doShare(UserHomePageActivity.this, PlatformType.QZONE, shareMedia, new ShareListener() {
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

                            mSocialApi.doShare(UserHomePageActivity.this, PlatformType.WEIXIN_CIRCLE, shareMedia, new ShareListener() {
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

}
