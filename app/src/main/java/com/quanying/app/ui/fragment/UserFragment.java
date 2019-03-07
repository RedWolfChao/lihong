package com.quanying.app.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.lzy.widget.PullZoomView;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.ui.base.BaseFragment;
import com.quanying.app.ui.user.MyFansActivity;
import com.quanying.app.ui.user.MyFocusActivity;
import com.quanying.app.ui.user.PerfectUserMsgActivity;
import com.quanying.app.ui.user.SettingActivity;
import com.quanying.app.ui.user.UserHomePageActivity;
import com.quanying.app.ui.user.VipActivity;
import com.quanying.app.ui.user.WebActivity;
import com.quanying.app.ui.user.ZpActivity;
import com.quanying.app.utils.AppSharePreferenceMgr;
import com.quanying.app.view.TranslucentScrollView;
import com.quanying.app.weburl.WebUri;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class UserFragment extends BaseFragment implements TranslucentScrollView.TranslucentChangedListener{

    @BindView(R.id.lay_header)      //头部
    SimpleDraweeView zoomView;

    @BindView(R.id.touxiang)      //头像
    SimpleDraweeView touxiang;
    @BindView(R.id.bianji)      //编辑图
    ImageView bianji;
    @BindView(R.id.bianji_text)      //编辑文字
    TextView bianji_text;

    @BindView(R.id.tv_name)      //用户名
    TextView tv_name;
    @BindView(R.id.tv_sign)      //个性签名
    TextView tv_sign;

//    跳转UI
    @BindView(R.id.zhuye)      //主页
        LinearLayout zhuye;
    @BindView(R.id.baoming)      //报名
        LinearLayout baoming;
    @BindView(R.id.zuopin)      //作品收藏
        LinearLayout zuopin;
    @BindView(R.id.guanzhu)      //关注
        LinearLayout guanzhu;
    @BindView(R.id.fensi)      //粉丝
        LinearLayout fensi;
    @BindView(R.id.huiyuan)      //会员中心
        LinearLayout huiyuan;
    @BindView(R.id.shezhi)      //设置
        LinearLayout shezhi;
    @BindView(R.id.tiwen)      //设置
        LinearLayout tiwen;

    /*
    * "userid": "229267",
        "nickname": "测试2",
        "gender": "男",
        "birthday": "1993-10-20",
        "areaid": 726,
        "sign": "我来自大草原",
        "labs": "模特",
        "bg": "",
        "updatetime": "1508464816",
        "applyid": "569512",
        "areaname": "伊春市",
        "age": "24"
    * */
    private String userid,nickname,gender,birthday,areaid,sign,labs,bg,areaname,age;
    private String face;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.f_user;
    }

    @Override
    protected void initView() {

        float sensitive =  1.5f;
        int zoomTime = 700;
        boolean isParallax =true;
        boolean isZoomEnable = true;

        PullZoomView pzv = getContentView().findViewById(R.id.pzv);
        pzv.setIsParallax(isParallax);
        pzv.setIsZoomEnable(isZoomEnable);
        pzv.setSensitive(sensitive);
        pzv.setZoomTime(zoomTime);
        pzv.setOnScrollListener(new PullZoomView.OnScrollListener() {
            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
                System.out.println("onScroll   t:" + t + "  oldt:" + oldt);
            }

            @Override
            public void onHeaderScroll(int currentY, int maxY) {
                System.out.println("onHeaderScroll   currentY:" + currentY + "  maxY:" + maxY);
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

        EventBus.getDefault().register(this);

    }

    @Override
    protected void initData() {

        if(MyApplication.getToken().equals("")){

//            Toast.makeText(getMContext(), "请先登录", Toast.LENGTH_SHORT).show();
            return;

        }


//        GETUSERMSG
        OkHttpUtils
                .post()
                .url(WebUri.GETUSERMSG)
                .addParams("token",MyApplication.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("zoulezhe?",response);
                        Log.e("zoulezhe?!!",MyApplication.getToken());

                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            String errCode = jsonObject.getString("errcode");
                            if(errCode.equals("200")){

                                EventBus.getDefault().post(new MessageEvent("updatacity"));

                                String infosta = jsonObject.getString("infosta");
                                face = jsonObject.getString("face");
//设置图片圆角角度
                                AppSharePreferenceMgr.put(getMContext(),"userid",jsonObject.getString("uid"));
                                AppSharePreferenceMgr.put(getMContext(),"faceurl",face);
                                Uri uri = Uri.parse(face);
                                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                                imagePipeline.evictFromMemoryCache(uri);
                                imagePipeline.evictFromDiskCache(uri);
//                                AppSharePreferenceMgr.set(getMContext(),"userid","");
// combines above two lines
                                imagePipeline.evictFromCache(uri);

                                touxiang.setImageURI(uri);


//                                0 啥都没有1是个人 2是企业，才会有会员
                                String hrtype = jsonObject.getString("hrtype");
                                if(hrtype.equals("2")){

                                }else{

                                    huiyuan.setVisibility(View.GONE);

                                }
                                tv_sign.setText("全影号: "+jsonObject.getString("username"));
                                AppSharePreferenceMgr.put(getMContext(),"qycode",jsonObject.getString("username"));
                                AppSharePreferenceMgr.put(getMContext(),"qyphone",jsonObject.getString("mobile"));  

                                if (infosta.equals("1")){

                                    String jsonInfo = jsonObject.getString("info");
                                    /*
                                    *
                                    * */
                                    JSONObject infoBean = new JSONObject(jsonInfo);
//                                    String
//                                    userid,nickname,gender,birthday,areaid,sign,labs,bg,areaname,age;
                                    userid = infoBean.getString("userid");
                                    nickname = infoBean.getString("nickname");
                                    gender = infoBean.getString("gender");
                                    birthday = infoBean.getString("birthday");
                                    areaid = infoBean.getString("areaid");
                                    sign = infoBean.getString("sign");
                                    labs = infoBean.getString("labs");

                                    areaname = infoBean.getString("areaname");
                                    age = infoBean.getString("age");
                                    tv_name.setText(nickname);
                                    bg = infoBean.getString("bg");
                                    Log.e("zoule","bg:"+bg);
                                    if(bg.equals("")||bg==null){

                                        Uri img1 = Uri.parse("res://com.quanying.app/"+R.mipmap.userpagebck1);
                                        Uri img2 = Uri.parse("res://com.quanying.app/"+R.mipmap.userpagebck2);
                                        Uri img3 = Uri.parse("res://com.quanying.app/"+R.mipmap.userpagebck3);
                                        Uri img4 = Uri.parse("res://com.quanying.app/"+R.mipmap.userpagebck4);
                                        Uri img5 = Uri.parse("res://com.quanying.app/"+R.mipmap.userpagebck5);

                                        int bk_ = Integer.parseInt(userid.substring(userid.length()-1, userid.length()));
                                        int jl = bk_/2;
                                        switch(jl){
                                            case 0:
                                                zoomView.setImageURI(img1);
                                                break;
                                            case 1:
                                                zoomView.setImageURI(img2);
                                                break;
                                            case 2:
                                                zoomView.setImageURI(img3);
                                                break;
                                            case 3:
                                                zoomView.setImageURI(img4);
                                                break;
                                            case 4:
                                                zoomView.setImageURI(img5);
                                                break;
                                        }

                                    }else {
                                        Uri zoomviewurl = Uri.parse(bg);
                                        Log.e("zoule","bgbgbgbgbg");
                                        zoomView.setImageURI(zoomviewurl);
                                    }
                                    if(infoBean.has("areaid")) {
                                        AppSharePreferenceMgr.put(getMContext(), "cityname", infoBean.getString("areaname"));
                                        AppSharePreferenceMgr.put(getMContext(), "cityid", infoBean.getString("areaid"));
                                    }
                                }else if (infosta.equals("2")){
                                    AlertDialog dialog = new AlertDialog.Builder(getMContext()).create();//创建对话框

                                    dialog.setTitle("提示");//设置对话框标题
                                    dialog.setMessage("您尚未完善资料，请立即完善! ");//设置文字显示内容
                                    dialog.setCanceledOnTouchOutside(false);
                                    //分别设置三个button
                                    dialog.setButton(DialogInterface.BUTTON_POSITIVE,"立即完善", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();//关闭对话框

                                        }
                                    });
                                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();//关闭对话框
                                            
                                        }
                                    });
                                    dialog.show();//显示对话框
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    @Override
    public void onTranslucentChanged(int transAlpha) {

    }

    @OnClick({R.id.zhuye,R.id.zuopin,R.id.guanzhu,R.id.fensi,R.id.huiyuan,R.id.shezhi,R.id.touxiang,R.id.bianji,R.id.bianji_text,R.id.baoming,R.id.tiwen})
    public void jumpUi(View views){

        switch (views.getId()) {

            case R.id.zhuye:

                Intent intent = new Intent(getMContext(),UserHomePageActivity.class);
                intent.putExtra("ids",MyApplication.getUserID());
                getMContext().startActivity(intent);

                break;
            case R.id.zuopin:
                setIntentClass(ZpActivity.class);
                break;
            case R.id.guanzhu:
                setIntentClass(MyFocusActivity.class);
                break;
            case R.id.fensi:
                setIntentClass(MyFansActivity.class);
                break;
            case R.id.huiyuan:
                setIntentClass(VipActivity.class);
                break;
            case R.id.shezhi:
                setIntentClass(SettingActivity.class);
                break;
            case R.id.touxiang:
                jumpEditUserMsg();
//                setIntentClass(PerfectUserMsgActivity.class);
                break;
            case R.id.bianji_text:
                jumpEditUserMsg();
                break;
                case R.id.baoming:
                Intent bmIntent = new Intent(getMContext(), WebActivity.class);
                bmIntent.putExtra("title","title");
                bmIntent.putExtra("urls","http://wap.7192.com/?mod=app&act=wdbm");
                startActivity(bmIntent);
                break;
                case R.id.tiwen:
                Intent tIntent = new Intent(getMContext(), WebActivity.class);
                    tIntent.putExtra("title","title");
                    tIntent.putExtra("urls","http://wap.7192.com/?mod=app&act=myquest");
                startActivity(tIntent);
                break;
        }

    }

    /*
     * 注册事件，监听是否改变用户信息
     * */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if(messageEvent.getStatus().equals("5")){
//            mText.setText(messageEvent.getMessage());
            Log.e("youdianyongde","~~~~~~~");
            initData();
        }

    }
/*
* 解除注册
* */
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void jumpEditUserMsg(){

        Intent intent = new Intent(getMContext(),PerfectUserMsgActivity.class);
//                   userid,nickname,gender,birthday,areaid,sign,labs,bg,areaname,age;

        intent.putExtra("headimg",face+"");
        intent.putExtra("nickname",nickname+"");
        intent.putExtra("gender",gender+"");
        intent.putExtra("birthday",birthday+"");
        intent.putExtra("areaid",areaid+"");
        intent.putExtra("sign",sign+"");
        intent.putExtra("labs",labs+"");
        intent.putExtra("areaname",areaname+"");

        getMContext().startActivity(intent);

    }
}
