package com.quanying.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.event.MessageEvent;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, MyApplication.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if(resp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
			if(resp.errCode==0){
				Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
				MessageEvent msg = new MessageEvent("pay_sta");
				msg.setStatus("success");
				EventBus.getDefault().post(msg);
			}
			else {
				Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
				MessageEvent msg = new MessageEvent("pay_sta");
				msg.setStatus("error");
				EventBus.getDefault().post(msg);
			}
			finish();
		}
	}
}

