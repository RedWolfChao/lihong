package com.quanying.app.ui.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.gson.Gson;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.CityJsonBean;
import com.quanying.app.bean.GetTagBean;
import com.quanying.app.bean.TagBean;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.utils.GetJsonDataUtil;
import com.quanying.app.weburl.WebUri;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGABaseAdapterUtil;
import cn.bingoogolapple.photopicker.imageloader.BGAImage;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import okhttp3.Call;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
public class PerfectUserMsgActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, View.OnClickListener {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;

    @BindView(R.id.jump_editname)
    LinearLayout jumpEditName;
    @BindView(R.id.address_btn)
    LinearLayout addressBtn;
    @BindView(R.id.jump_editsign)
    LinearLayout jump_editsign;
    @BindView(R.id.select_birthady)
    LinearLayout selested_b;
    @BindView(R.id.tag_btn)
    LinearLayout tag_btn;

    public final static int REQUESTCODENAME = 101; // 返回的结果码
    private final static int REQUESTCODESIGN = 102; // 返回的结果码

    @BindViews({R.id.round_girl,R.id.round_boy})
    List<ImageView> viewList;//


    private static final int REQUEST_CODE_PERMISSION_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_CROP = 3;

    @BindView(R.id.userhead)
    SimpleDraweeView mAvatarIv;
    @BindView(R.id.edit_name)
    TextView edit_name;
    @BindView(R.id.edit_sign)
    TextView edit_sign;
    @BindView(R.id.address_text)
    TextView addressText;

    @BindView(R.id.tag_name)
    TextView tag_name;

    @BindView(R.id.select_btext)
    TextView select_btext;
//    确认修改
    @BindView(R.id.check_btn)
    TextView check_btn;

    @BindView(R.id.round_girl)
    ImageView round_girl;
    @BindView(R.id.round_boy)
    ImageView round_boy;

    private BGAPhotoHelper mPhotoHelper;
    private int SIX=1;
    private OptionsPickerView  pvCustomOptions;
    private ArrayList<TagBean> TagBeans;

    /*
    * 头像
    * */
    private String srcid="";
    /*
    *  城市选择相关
    * */

    private ArrayList<CityJsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private boolean isLoaded = false;
    private String cityId = "" ;
    private Intent intent;
    private String headimg;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_perfect_user_msg;
    }

    @Override
    protected void initView() {

        titlebar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
            }
        });

        intent = getIntent();
        headimg = intent.getStringExtra("headimg");
        String nickname = intent.getStringExtra("nickname");
        String gender = intent.getStringExtra("gender");
        String birthday = intent.getStringExtra("birthday");
        String areaid = intent.getStringExtra("areaid");
        String sign = intent.getStringExtra("sign");
        String labs = intent.getStringExtra("labs");
        String areaname = intent.getStringExtra("areaname");

        if(!TextUtils.isEmpty(nickname)){
            Uri uri = Uri.parse(headimg);
            mAvatarIv.setImageURI(uri);

//            AppGlideModule.class

        }
        if(!TextUtils.isEmpty(nickname)){
            edit_name.setText(nickname);
        }
        if(!TextUtils.isEmpty(gender)){
            if(gender.equals("男")){
                updateView(1);
                SIX = 0;
            }else{
                updateView(0);
                SIX = 1;
            }
        }
        if(!TextUtils.isEmpty(birthday)){
            if(birthday.equals("null")){
                select_btext.setText("");
            }else{
            select_btext.setText(birthday);
        }
        }
        if(!TextUtils.isEmpty(sign)){
            if(sign.equals("null")){
                edit_sign.setText("");
            }else {
                edit_sign.setText(sign);
            }
        }
        if(!TextUtils.isEmpty(labs)){
            if(labs.equals("null")){
                tag_name.setText("");
            }else {
                tag_name.setText(labs);
            }


        }
        if(!TextUtils.isEmpty(areaid)){


            cityId = areaid;

            if(areaname.equals("null")){
                addressText.setText("");
            }else {
                addressText.setText(areaname);
            }

        }

    }
    @Override
    protected void initData() {
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "qy_photo");
        mPhotoHelper = new BGAPhotoHelper(takePhotoDir);
//        获取用户标签数据
        getCardData();

    }
/*
* 姓名编辑
* */
    @OnClick(R.id.jump_editname)
    public void jumpEditUi(){
        Intent intent = new Intent(context,EditorUI.class);
        intent.putExtra(EditorUI.TITLE,"姓名");
        intent.putExtra(EditorUI.LIMIT,10);
        startActivityForResult(intent, REQUESTCODENAME); //REQUESTCODE--->1
    }
    /*
* 标签选择
* */
    @OnClick(R.id.tag_btn)
    public void selectTag(){
        pvCustomOptions.show();
    }
    /*
* 标签选择
* */
    @OnClick(R.id.address_btn)
    public void selectAddress(){

        showPickerView();
    }
    /*
* 确认修改
* */
    @OnClick(R.id.check_btn)
    public void updataUserMsg(){

//        showPickerView();
        String name = edit_name.getText().toString().trim();
        String birS = select_btext.getText().toString().trim();
        String sign = edit_sign.getText().toString().trim();
        String tag = tag_name.getText().toString().trim();

        OkHttpUtils
                .post()
                .url(WebUri.UPDATAUSERMSG)
                .addParams("token", MyApplication.getToken())
                .addParams("nickname", name+"")
                .addParams("gender",SIX+"")
                .addParams("birthday",birS)
                .addParams("areaid", cityId)
                .addParams("sign", sign+"")
                .addParams("labs", tag+"")
                .addParams("srcid", srcid+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d("kevinchange",e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("kevinchange",response);

                        Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String errCode = jsonObject.getString("errcode");
                            if(errCode.equals("200")){
                                Toast.makeText(context, "基础资料修改成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new MessageEvent("","5"));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }
    /*
    * 签名编辑
    * */
    @OnClick(R.id.jump_editsign)
    public void jumpEditSignUi(){
        Intent intent = new Intent(context,EditorUI.class);
        intent.putExtra(EditorUI.TITLE,"签名");
        intent.putExtra(EditorUI.LIMIT,25);
        startActivityForResult(intent, REQUESTCODESIGN); //REQUESTCODE--->1
    }
 /*
    * 设置生日
    * */
    @OnClick(R.id.select_birthady)
    public void doSelectBirthady(){

      /*  //时间选择器
        TimePickerView pvTime2 = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
                String dateStr = df.format(date);
                Toast.makeText(context, dateStr, Toast.LENGTH_SHORT).show();
                select_btext.setText(dateStr);
            }
        }).setType(new boolean[]{true, true, true, false, false, false})

                .build();


        pvTime2.show();*/

        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
//                DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
                DateFormat df = DateFormat.getDateInstance();
                String dateStr = df.format(date);
                String temp1 = dateStr.replace("年", "-");
                String temp2 = temp1.replace("月", "-");
                String temp3 = temp2.replace("日", "");
                select_btext.setText(temp3);
//                Toast.makeText(context, dateStr, Toast.LENGTH_SHORT).show();
            }
        }).build();
        pvTime.show();


    }

    /*
    * 设置标签
    * */

    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = TagBeans.get(options1).getPickerViewText();
                tag_name.setText(tx);
            }
        })
                .setLayoutRes(R.layout.select_tag, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);

                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });

                    }
                })
                .isDialog(true)
                .build();

        pvCustomOptions.setPicker(TagBeans);//添加数据


    }

    private void getCardData() {
        TagBeans = new ArrayList<>();
        OkHttpUtils
                .post()
                .url(WebUri.GETUSERTAG)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int ErrCode = jsonObject.getInt("errcode");
                            if(ErrCode==200){
                                GetTagBean mBean = new Gson().fromJson(response,GetTagBean.class);
//                                TagBean tagBean = new TagBean(0,)
                                List<String> labs = mBean.getLabs();
                                for (int i =0;i<labs.size();i++){

                                    TagBean tagBean = new TagBean(0,labs.get(i));
                                    TagBeans.add(tagBean);
                                    initCustomOptionPicker();
                                }
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BGAPhotoHelper.onSaveInstanceState(mPhotoHelper, outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        BGAPhotoHelper.onRestoreInstanceState(mPhotoHelper, savedInstanceState);
    }

    @OnClick(R.id.userhead)
    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_CHOOSE_PHOTO)
    public void choosePhoto() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            startActivityForResult(mPhotoHelper.getChooseSystemGalleryIntent(), REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "请开起存储空间和相机权限，以正常使用上传图片功能！", REQUEST_CODE_PERMISSION_CHOOSE_PHOTO, perms);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
                try {
                    startActivityForResult(mPhotoHelper.getCropIntent(mPhotoHelper.getFilePathFromUri(data.getData()), 200, 200), REQUEST_CODE_CROP);
                } catch (Exception e) {
                    mPhotoHelper.deleteCropFile();
                    BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CODE_CROP) {
//                Toast.makeText(context, ""+mPhotoHelper.getCropFilePath(), Toast.LENGTH_SHORT).show();

                upUserHead(mPhotoHelper.getCropFilePath());

                BGAImage.display(mAvatarIv, R.mipmap.bga_pp_ic_holder_light, mPhotoHelper.getCropFilePath(), BGABaseAdapterUtil.dp2px(200));
            } else if(requestCode == REQUESTCODENAME) {
                edit_name.setText(data.getStringExtra(EditorUI.EDITCONTEXT));
            } else if(requestCode == REQUESTCODESIGN) {
                edit_sign.setText(data.getStringExtra(EditorUI.EDITCONTEXT));
            }

        } else {
            if (requestCode == REQUEST_CODE_CROP) {
                mPhotoHelper.deleteCameraFile();
                mPhotoHelper.deleteCropFile();
            }
        }
    }

    private void upUserHead(String cropFilePath) {

       /* OkHttpUtils
                .postFile()
                .url(WebUri.UPDATAUSERHEAD)
                .file(new File(cropFilePath))
                .addParams("nickname", name+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("kevinfile",response);
                        Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();

                    }
                });*/
        OkHttpUtils.post()//
                .addFile("image", "head.png", new File(cropFilePath))//
                .url(WebUri.UPDATAUSERHEAD)
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

                                srcid = jsonObject.getString("srcid");
                                Uri uri = Uri.parse(headimg);
                                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                                imagePipeline.evictFromMemoryCache(uri);
                                imagePipeline.evictFromDiskCache(uri);
// combines above two lines
                                imagePipeline.evictFromCache(uri);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @OnClick({R.id.round_girl,R.id.round_boy})
    public void switchSex(View v){

        switch(v.getId()){
            case R.id.round_girl:
                updateView(0);
                SIX = 1;
                break;
            case R.id.round_boy:
                updateView(1);
                SIX = 0;
                break;
        }

    }

    /*
     * 更新男女圆点
     * */
    private void updateView(int i) {
        // TODO Auto-generated method stub
        // 循环遍历 数组中哪个选项卡被点击
        for (int j = 0; j < 2; j++) {
            if (i == j) {
                // 被点击的切换视图,切换颜色
//                    nav_Imgs.get(j).setImageResource(resId[j]);

                viewList.get(j).setImageResource(R.mipmap.round_c);
            } else {
                // 未被点击的 恢复默认
//                nav_Imgs.get(j).setImageResource(resIdU[j]);
//                viewList.get(j).setTextColor(Color.parseColor("#000000"));
                viewList.get(j).setImageResource(R.mipmap.round);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onClick(View view) {

    }

    /*
    * 城市选择
    * */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:

                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:

                    break;
            }
        }
    };

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                addressText.setText(options2Items.get(options1).get(options2));
                cityId = getCityID(options1,options2,true);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
//        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.setPicker(options1Items, options2Items);
        pvOptions.show();
    }

    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        ArrayList<CityJsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
            }
            /**
             * 添加城市数据
             */
            options2Items.add(CityList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    public ArrayList<CityJsonBean> parseData(String result) {//Gson 解析
        ArrayList<CityJsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CityJsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), CityJsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    private String getCityID(int start,int end,boolean isName) {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String fanhui = "";
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        ArrayList<CityJsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */


        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getId();
                CityList.add(CityName);//添加城市
            }

            fanhui =  jsonBean.get(start).getCityList().get(end).getId();

        }
        return fanhui;

    }

}
