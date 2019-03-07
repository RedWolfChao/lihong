package com.quanying.app.ui.message;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.quanying.app.R;
import com.quanying.app.adapter.ZpInteractiveAdapter;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.ZpInteractiveBean;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.ui.user.LoginActivity;
import com.quanying.app.utils.AppKeyBoardMgr;
import com.quanying.app.weburl.WebUri;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
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

public class ZpInteractiveActivity extends BaseActivity {


    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;

    @BindView(R.id.recyclerview)
    LRecyclerView recyclerview;
    @BindView(R.id.details_msg_send)
    TextView details_msg_send;
    @BindView(R.id.details_msg_edit)
    EditText details_msg_edit;
    @BindView(R.id.edit_ll)
    LinearLayout edit_ll;
    @BindView(R.id.hd_ll)
    LinearLayout hd_ll;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ZpInteractiveBean mBean ;
    private String zpId;
    private String plrId;
    private String lastId;
    private ZpInteractiveAdapter myAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_zp_interactive;
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
        EventBus.getDefault().register(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
//        recyclerview.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 6, getResources().getColor(R.color.gray_bkg)));
        recyclerview.setPullRefreshEnabled(false);
//add a FooterView
        recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                Toast.makeText(getMContext(), "gahsjiashasij", Toast.LENGTH_SHORT).show();

                loadMoreData();


            }
        });

     recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
         @Override
         public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
             super.onScrollStateChanged(recyclerView, newState);

             if(newState>0&&AppKeyBoardMgr.KeyBoard(details_msg_edit)){

                 AppKeyBoardMgr.closeKeybord(details_msg_edit,context);

             }

         }
     });

        autoScrollView(hd_ll, edit_ll);//弹出软键盘时滚动视图
    }
    @Override
    protected void initData() {

        OkHttpUtils
                .post()
                .url(WebUri.GETZPHD)
                .addParams("token", MyApplication.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("zphd",response);

                        mBean = new Gson().fromJson(response,ZpInteractiveBean.class);



                        if(mBean.getErrcode().equals("200")){

                            int size = mBean.getData().size();
                            if (size > 0) {
                                lastId = mBean.getData().get(size - 1).getId();
                            }

                            myAdapter = new ZpInteractiveAdapter(mBean,context);
                            mLRecyclerViewAdapter = new LRecyclerViewAdapter(myAdapter);
                            recyclerview.setAdapter(mLRecyclerViewAdapter);

                        }

                    }
                });


    }
    private void loadMoreData() {

        OkHttpUtils
                .post()
                .url(WebUri.GETZPHD)
                .addParams("token", MyApplication.getToken())
                .addParams("page", lastId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mBean = new Gson().fromJson(response,ZpInteractiveBean.class);
                        if (mBean.getErrcode().equals("200")) {

//                            recyclerview.setAdapter(mAdapter);
                            int size = mBean.getData().size();
                            if (size > 0) {
                                lastId = mBean.getData().get(size - 1).getId();
                            }
//                            mAdapter.addAll(uBean.getData());
//                            mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
//                            recyclerview.setAdapter(mLRecyclerViewAdapter);

//                        Log.e("collection",response);
                            if(size>0) {
                                lastId = mBean.getData().get(size - 1).getId();
                                myAdapter.addAll(mBean.getData());
//                            mAdapter.notifyItemRangeInserted(0,size);
                                recyclerview.refreshComplete(size);// REQUEST_COUNT为每页加载数量
                            }else{

                                recyclerview.setNoMore(true);

                            }

                        }else{
                            recyclerview.setNoMore(true);
                        }

                    }
                });

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
        edit_ll.setVisibility(View.GONE);

        AppKeyBoardMgr.closeKeybord(details_msg_edit,context);
        OkHttpUtils
                .post()
                .url(WebUri.PL)
                .addParams("token", MyApplication.getToken())
                .addParams("id", zpId)
                .addParams("pid",plrId+"")
                .addParams("content",msg+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(context, "回复失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("errcode").equals("200")){
                                Toast.makeText(context, "回复成功！", Toast.LENGTH_SHORT).show();
                                edit_ll.setVisibility(View.GONE);
                                plrId = "";
                                zpId = "";
                                details_msg_edit.setText("");
                                details_msg_edit.setHint("添加评论");

                            }else{

                                Toast.makeText(context, "回复失败，请检查网络", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {

        if(messageEvent.getMessage().equals("plzp")){
            zpId = messageEvent.getContext();
            plrId = messageEvent.getTitle();
            details_msg_edit.setHint("回复　"+messageEvent.getStatus()+"　:");

            edit_ll.setVisibility(View.VISIBLE);

            AppKeyBoardMgr.openKeybord(details_msg_edit,context);

        }

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }}
}
