package com.quanying.app.ui.user;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.PushCreationsJsonBean;
import com.quanying.app.bean.PushInfoBean;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.utils.AppImageMgr;
import com.quanying.app.weburl.WebUri;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import okhttp3.Call;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class AddCreationActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, BGASortableNinePhotoLayout.Delegate {

    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;

    @BindView(R.id.push_creation)
    TextView push_creation;
    @BindView(R.id.push_context)
    EditText push_context;
    @BindView(R.id.push_title)
    EditText push_title;
    int dataSize ;

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    private static final int PRC_PHOTO_PICKER = 1;

    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;

    private ProgressDialog progressDialog;

    private int imgPosition = 1;

    private String p_Title;
    private String p_Context;

    private ArrayList<Integer> imgIdList = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_creation;
    }

    @Override
    protected void initData() {

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

        mPhotosSnpl.setMaxItemCount(9);
        mPhotosSnpl.setEditable(true);
        mPhotosSnpl.setPlusEnable(true);
        mPhotosSnpl.setSortable(true);

        mPhotosSnpl.setDelegate(this);
        showPDialog();



    }


    /*
    *
    * 发布信息
    * */
    @OnClick(R.id.push_creation)
    public void pushCreation(){

        p_Title = getEdit(push_title);
        p_Context = getEdit(push_context);

        if (TextUtils.isEmpty(p_Title) && TextUtils.isEmpty(p_Context)) {
//            Toast.makeText(this, "必须填写这一刻的想法或选择照片！", Toast.LENGTH_SHORT).show();
//            return;
            showBaseDialog("必须填写标题、内容","好");
            return;

        }if(mPhotosSnpl.getItemCount() == 0){

            showBaseDialog("请上传作品图片","好");

            return;

        }


            String filePath = mPhotosSnpl.getData().get(imgPosition-1).trim();

        dataSize = mPhotosSnpl.getData().size();

            upImg(imgPosition,filePath);




    }

    public  void  showPDialog(){

        progressDialog = new ProgressDialog(context);//1.创建一个ProgressDialog的实例
        progressDialog.setTitle("提示");//2.设置标题
        progressDialog.setMessage("正在上传中，请稍等......");//3.设置显示内容
        progressDialog.setCancelable(true);//4.设置可否用back键关闭对话框
//        progressDialog.show();

    }


    public void upImg(int positon,String file){
        progressDialog.show();
        File tempFile =new File(file);
        String fileName = tempFile.getName();



        Bitmap  bitmap =  AppImageMgr.getBitmapFromFile(tempFile,1500,1920);

        File upFile = new File(AppImageMgr.saveBitmap(context,bitmap));

//        AppImageMgr.lessenBitmap(context,tempFile,1024,1920);

        OkHttpUtils.post()//
                .addFile("image", fileName, upFile)//
                .url(WebUri.PUSHCREATIONIMG)
                .addParams("token", MyApplication.getToken())
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("kankanrsp",response+""+mPhotosSnpl.getData().size());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("errcode").equals("200")){

                                imgIdList.add(jsonObject.getInt("srcid"));


                                if(imgPosition<dataSize||imgPosition==dataSize){

                                    imgPosition= imgPosition+1;
                                    Log.e("kankanrsp","1:"+imgPosition);

                                    int sz = imgPosition-1;
                                    if(dataSize<=sz){
                                        Log.e("kankanrsp","jump:"+imgPosition);
                                        progressDialog.dismiss();   
                                        upLoadCreation();
                                        return;

                                    }
                                    int tz = imgPosition-1;
                                    upImg(imgPosition,mPhotosSnpl.getData().get(tz));

                                }
                            }else{

                                Toast.makeText(context, ""+jsonObject.getString("errmsg"), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        Log.e("kankan",progress+"");
                        if(progress<0.35f&&progress>0) {
                            progressDialog.setMessage("正在上传第" + imgPosition + "个文件.");
                        }if(progress>0.35f&&progress<0.75) {
                            progressDialog.setMessage("正在上传第" + imgPosition + "个文件..");
                        }if(progress>0.75f&&progress<1) {
                            progressDialog.setMessage("正在上传第" + imgPosition + "个文件...");
                        }


                    }
                });

    }

    private void upLoadCreation() {

        final PushCreationsJsonBean pBean = new PushCreationsJsonBean();
        pBean.setTitle(p_Title);
        pBean.setContent(p_Context);
        pBean.setCover(imgIdList.get(0)+"");

        int imgsSizes = imgIdList.size();

       /* int[] imgsId=new int[imgsSizes];
        for(int i=0;i<imgsSizes;i++){
            imgsId[i] = Integer.parseInt(imgIdList.get(i));
        }*/

        pBean.setSort(imgIdList);

        PushInfoBean mBeans = new PushInfoBean();
        mBeans.setInfo(pBean);
        Log.e("kankanzhi",new Gson().toJson(pBean));

//        imgIdList


        Map<String,String> headers = new HashMap();
        Map<String,String> hdList = new HashMap();


        headers.put("token",MyApplication.getToken());
        headers.put("info",new Gson().toJson(pBean));

//
//        hdList.put("title",pBean.getTitle());
//        hdList.put("content",pBean.getContent());
//        hdList.put("cover",pBean.getCover());
//

/*
        for(int i = 0;i<imgIdList.size();i++){

//                int values[] = {Integer.parseInt(check.getId())};
                headers.put("sort"+"["+i+"]",imgIdList.get(i));

        }

*/

/*
        OkHttpUtils
                .post()
                .url(WebUri.UPDATAPLAYBACKLIST)
                .params(maps)
                .build()
                .execute(new StringCallback() {
        */

        Log.e("respppptoken",headers.toString()+"");

  /*      OkHttpUtils
                .post()
                .url(WebUri.PUSHCREATIONSAVE)
                .params(headers)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("resppppeee",e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("respppp",response);
                    }
                });
*/

        OkHttpUtils
                .post()
                .url(WebUri.PUSHCREATIONSAVE)
                .addHeader("User-Agent","android")
                .addParams("token",MyApplication.getToken())
                .addParams("info",new Gson().toJson(pBean))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("resppp22222p",e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("resppp111111111p",response);
//                        Log.e("resppp22222p",new Gson().toJson(pBean));
//                        if(re)
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("errcode").equals("200")){

                                finish();

                                MessageEvent event = new MessageEvent("");
                                event.setStatus("gx");
                                EventBus.getDefault().post(event);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

  /*      // 构造请求体
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/xhtml+xml;charset=UTF-8"), new Gson().toJson(pBean));

        Log.e("RequestBodypBean" +
                "",new Gson().toJson(pBean));
        Log.e("RequestBody",body.toString());
        Request request = new Request.Builder()
                .url(WebUri.PUSHCREATIONSAVE)
                .post(body)
                .build();
        // 向服务器异步请求数据
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
//                LogUtils.i(TAG, "失败");
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                ResponseBody body = response.body();
//                LogUtils.i(TAG, "返回数据：" + body.string());
                Log.e("resppp111111111p",body.string());
            }
        });

*/

      /*  OkHttpUtils
                .post()
                .url(WebUri.PUSHCREATIONSAVE)
                .addHeader("token",MyApplication.getToken())
                .addHeader("info",new Gson().toJson(pBean))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("respppp",response);
                    }
                });
*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            if (false) {
                mPhotosSnpl.setData(BGAPhotoPickerActivity.getSelectedPhotos(data));
            } else {
                mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
            }
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }
    }



    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {


            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                    .cameraFileDir(true ? takePhotoDir : null) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount()) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);


        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {
        Log.e("0","排序发生变化");
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PRC_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }
}
