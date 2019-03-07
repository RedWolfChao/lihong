package com.quanying.app.ui.showroominsidepage;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.quanying.app.R;
import com.quanying.app.adapter.SRLatestAdapter;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.CityJsonBean;
import com.quanying.app.bean.ShowRoomBean;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.ui.base.BaseFragment;
import com.quanying.app.utils.GetJsonDataUtil;
import com.quanying.app.view.RecycleViewDivider;
import com.quanying.app.weburl.WebUri;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author ChayChan
 * @date 2017/6/23  11:22
 */
public class OurCityFragment extends BaseFragment {


    @BindView(R.id.recyclerview)
    LRecyclerView recyclerview;

    @BindView(R.id.city_text)
    TextView city_text;
    @BindView(R.id.opencity)
    LinearLayout opencity;
    private ArrayList<CityJsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    @BindView(R.id.gank_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static final String CONTENT = "content";
    private TextView mTextView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    private String lastId="";
    private SRLatestAdapter mAdapter;

    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private Thread thread;
    private String cityId = "" ;
    private String cityName = "" ;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.f_ourcity;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        cityId = MyApplication.getCityId();
        cityName = MyApplication.getCity();
        if(!TextUtils.isEmpty(cityId)){
            city_text.setText(cityName);
        }

        recyclerview.setLayoutManager(new LinearLayoutManager(getMContext(),LinearLayoutManager.VERTICAL,false));
        recyclerview.addItemDecoration(new RecycleViewDivider(getMContext(), LinearLayoutManager.VERTICAL, 6, getResources().getColor(R.color.gray_bkg)));
        recyclerview.setPullRefreshEnabled(false);
//add a FooterView
        recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                Toast.makeText(getMContext(), "gahsjiashasij", Toast.LENGTH_SHORT).show();

                loadMoreData("");

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {

//                Toast.makeText(getMContext(), "shuaxin", Toast.LENGTH_SHORT).show();
                cityId = MyApplication.getCityId();
                cityName = MyApplication.getCity();
                if(!TextUtils.isEmpty(cityId)){
                    city_text.setText(cityName);
                }
                initData();
            }
        });

//        opencity.
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);



    }

    @OnClick(R.id.opencity)
    public void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getMContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                city_text.setText(options2Items.get(options1).get(options2));
                cityId = getCityID(options1,options2,true);
                initData();


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

//                    isLoaded = true;
                    break;

            }
        }
    };


    private void loadMoreData(String pageId) {

        OkHttpUtils
                .post()
                .url(WebUri.GETOURCITYROOM)
                .addParams("token", MyApplication.getToken())
                .addParams("page",lastId)
                .addParams("areaid",cityId)
                .addParams("offset","3")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {
//                        LogUtils.json(response);
//                        Toast.makeText(getMContext(), ""+response, Toast.LENGTH_SHORT).show();

//                        Log.e("h")


                        try {
                            ShowRoomBean showRoomBean = new
                                    ShowRoomBean();
                            JSONObject jsonObject = new JSONObject(response);
                            showRoomBean.setErrcode
                                    (jsonObject.getString("errcode"));
                            showRoomBean.setUid
                                    (jsonObject.getInt("uid"));
                            showRoomBean.setHrtype
                                    (jsonObject.getString("hrtype"));
                            showRoomBean.setErrmsg
                                    (jsonObject.getString("errmsg"));
                            if(jsonObject.getString("errcode").equals("200")){


                            JSONArray dataArray =
                                    jsonObject.getJSONArray("data");
                            List<ShowRoomBean.DataBean> mList =
                                    new ArrayList<>();

                            for (int i = 0; i < dataArray.length(); i++
                                    ) {
                                Log.e
                                        ("shujuxinxi1", dataArray.toString());
                                ShowRoomBean.DataBean mDataBean =
                                        new ShowRoomBean.DataBean();
                                JSONObject jsonData
                                        = dataArray.getJSONObject(i);
                                mDataBean.setId
                                        (jsonData.getString("id"));
                                mDataBean.setUserid
                                        (jsonData.getString("userid"));
                                mDataBean.setAreaid
                                        (jsonData.getString("areaid"));
                                mDataBean.setTp
                                        (jsonData.getString("tp"));
                                mDataBean.setDataid
                                        (jsonData.getString("dataid"));
                                mDataBean.setTitle
                                        (jsonData.getString("title"));
                                mDataBean.setDsp
                                        (jsonData.getString("dsp"));
                                mDataBean.setThumb
                                        (jsonData.getString("thumb"));
                                mDataBean.setAddtime
                                        (jsonData.getString("addtime"));
                                mDataBean.setZaned
                                        (jsonData.getString("zaned"));
                                mDataBean.setZannum
                                        (jsonData.getString("zannum"));
                                mDataBean.setPlnum
                                        (jsonData.getString("plnum"));
                                mDataBean.setImgnum
                                        (jsonData.getInt("imgnum"));
                                mDataBean.setFace
                                        (jsonData.getString("face"));
                                mDataBean.setNickname
                                        (jsonData.getString("nickname"));
                                mDataBean.setHrtype
                                        (jsonData.getString("hrtype"));


                                Object jsons = new JSONTokener
                                        (jsonData.getString("images")).nextValue();


                                List<ShowRoomBean.DataBean.ImagesBean> imgList = new
                                        ArrayList<>();

                                if (jsons instanceof JSONObject) {
                                    JSONObject json =
                                            (JSONObject) jsons;
                                    ShowRoomBean.DataBean.ImagesBean mImgBean = new
                                            ShowRoomBean.DataBean.ImagesBean();
                                    mImgBean.setImgid("");
                                    mImgBean.setUserid("");
                                    mImgBean.setContentid("");
                                    mImgBean.setThumb("");
                                    imgList.add(mImgBean);

                                } else if (jsons instanceof JSONArray) {
                                    JSONArray jsonArray = (JSONArray) jsons;


                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        JSONObject jsonImg = jsonArray.getJSONObject(j);

                                        ShowRoomBean.DataBean.ImagesBean mImgBean = new ShowRoomBean.DataBean.ImagesBean();
                                        mImgBean.setImgid
                                                (jsonImg.getString("imgid"));
                                        mImgBean.setUserid
                                                (jsonImg.getString("userid"));
                                        mImgBean.setContentid
                                                (jsonImg.getString("contentid"));
                                        mImgBean.setThumb
                                                (jsonImg.getString("thumb"));

                                        Log.e("hahaha",jsonImg.toString());

                                        imgList.add(mImgBean);
//
                                    }

                                    mDataBean.setImages(imgList);
                                }

                                mList.add(mDataBean);
                            }

                            showRoomBean.setData(mList);

//                            SRLatestAdapter mAdapter = new SRLatestAdapter(showRoomBean,getMContext());
//                            recyclerview.setAdapter(mAdapter);
//                            mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
//                            recyclerview.setAdapter(mLRecyclerViewAdapter);
                                int size = showRoomBean.getData().size();
                                if(size>0) {
                                    lastId = showRoomBean.getData().get(size - 1).getId();
                                    mAdapter.addAll(showRoomBean.getData());
//                            mAdapter.notifyItemRangeInserted(0,size);
                                    recyclerview.refreshComplete(size);// REQUEST_COUNT为每页加载数量
                                }else{

                                    recyclerview.setNoMore(true);

                                }

                            }else{
                                recyclerview.setNoMore(true);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.e("testteste",response);
//                            Toast.makeText(getMContext(), "报错报错", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }

    @Override
    protected void initData() {

        OkHttpUtils
                .post()
                .url(WebUri.GETOURCITYROOM)
                .addParams("token", MyApplication.getToken())
                .addParams("areaid",cityId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("lalal",response);


                        try {
                            ShowRoomBean showRoomBean = new
                                    ShowRoomBean();
                            JSONObject jsonObject = new JSONObject(response);
                            showRoomBean.setErrcode
                                    (jsonObject.getString("errcode"));
                            showRoomBean.setUid
                                    (jsonObject.getInt("uid"));
                            showRoomBean.setHrtype
                                    (jsonObject.getString("hrtype"));
                            showRoomBean.setErrmsg
                                    (jsonObject.getString("errmsg"));

                            JSONArray dataArray =
                                    jsonObject.getJSONArray("data");
                            List<ShowRoomBean.DataBean> mList =
                                    new ArrayList<>();

                            for (int i = 0; i < dataArray.length(); i++
                                    ) {
                                Log.e
                                        ("shujuxinxi1", dataArray.toString());
                                ShowRoomBean.DataBean mDataBean =
                                        new ShowRoomBean.DataBean();
                                JSONObject jsonData
                                        = dataArray.getJSONObject(i);

                                mDataBean.setId
                                        (jsonData.getString("id"));
                                mDataBean.setUserid
                                        (jsonData.getString("userid"));
                                mDataBean.setAreaid
                                        (jsonData.getString("areaid"));
                                mDataBean.setTp
                                        (jsonData.getString("tp"));
                                mDataBean.setDataid
                                        (jsonData.getString("dataid"));
                                mDataBean.setTitle
                                        (jsonData.getString("title"));
                                mDataBean.setDsp
                                        (jsonData.getString("dsp"));
                                mDataBean.setThumb
                                        (jsonData.getString("thumb"));
                                mDataBean.setAddtime
                                        (jsonData.getString("addtime"));
                                mDataBean.setZaned
                                        (jsonData.getString("zaned"));
                                mDataBean.setZannum
                                        (jsonData.getString("zannum"));
                                mDataBean.setPlnum
                                        (jsonData.getString("plnum"));
                                mDataBean.setImgnum
                                        (jsonData.getInt("imgnum"));
                                mDataBean.setFace
                                        (jsonData.getString("face"));
                                mDataBean.setNickname
                                        (jsonData.getString("nickname"));
                                mDataBean.setHrtype
                                        (jsonData.getString("hrtype"));


                                Object jsons = new JSONTokener
                                        (jsonData.getString("images")).nextValue();


                                List<ShowRoomBean.DataBean.ImagesBean> imgList = new
                                        ArrayList<>();

                                if (jsons instanceof JSONObject) {
                                    JSONObject json =
                                            (JSONObject) jsons;
                                    ShowRoomBean.DataBean.ImagesBean mImgBean = new
                                            ShowRoomBean.DataBean.ImagesBean();
                                    mImgBean.setImgid("");
                                    mImgBean.setUserid("");
                                    mImgBean.setContentid("");
                                    mImgBean.setThumb("");
                                    imgList.add(mImgBean);

                                } else if (jsons instanceof JSONArray) {
                                    JSONArray jsonArray = (JSONArray) jsons;


                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        JSONObject jsonImg = jsonArray.getJSONObject(j);

                                        ShowRoomBean.DataBean.ImagesBean mImgBean = new ShowRoomBean.DataBean.ImagesBean();
                                        mImgBean.setImgid
                                                (jsonImg.getString("imgid"));
                                        mImgBean.setUserid
                                                (jsonImg.getString("userid"));
                                        mImgBean.setContentid
                                                (jsonImg.getString("contentid"));
                                        mImgBean.setThumb
                                                (jsonImg.getString("thumb"));

                                        Log.e("hahaha",jsonImg.toString());

                                        imgList.add(mImgBean);
//
                                    }

                                    mDataBean.setImages(imgList);
                                }

                                mList.add(mDataBean);
                            }

                            showRoomBean.setData(mList);

                        if(showRoomBean.getErrcode().equals("200")){
                            mAdapter = new SRLatestAdapter(showRoomBean,getMContext());
//                            recyclerview.setAdapter(mAdapter);
                            int size = showRoomBean.getData().size();
                            if(size>0){
                                lastId = showRoomBean.getData().get(size-1).getId();
                            }
                            swipeRefreshLayout.setRefreshing(false);
                            mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
                            recyclerview.setAdapter(mLRecyclerViewAdapter);

                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("testtesterrer",response);

//                            Toast.makeText(getMContext(), "报错报错", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(getMContext(), "province.json");//获取assets目录下的json文件数据
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
        String JsonData = new GetJsonDataUtil().getJson(getMContext(), "province.json");//获取assets目录下的json文件数据
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



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {

        if(messageEvent.getMessage().equals("updatacity")){

//                Toast.makeText(getMContext(), "shuaxin", Toast.LENGTH_SHORT).show();
            cityId = MyApplication.getCityId();
            cityName = MyApplication.getCity();
            if(!TextUtils.isEmpty(cityId)){
                city_text.setText(cityName);
            }
            initData();
        }


    }



}
