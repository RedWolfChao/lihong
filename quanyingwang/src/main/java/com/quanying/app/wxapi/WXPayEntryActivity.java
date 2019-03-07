package com.quanying.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.quanying.app.activity.MainActivity;
import com.quanying.app.bean.AnyEventType;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, WXPayUtil.APPID);
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
		Log.v("WXPAY", "onPayFinish, errCode = " + resp.transaction);
		Log.e("kevin", "onPayFinish, errCode = " + resp.errCode);
		if (resp.errCode == 0) {
			Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
      AnyEventType ma = new AnyEventType();
      ma.setStatus("success");
      EventBus.getDefault().post(ma);
		} else if (resp.errCode == -1) {
			Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
      AnyEventType ma = new AnyEventType();
      ma.setStatus("fail");
      EventBus.getDefault().post(ma);
		} else {
			Toast.makeText(this, "已取消支付", Toast.LENGTH_SHORT).show();
		}
		try {
			MainActivity.instance.handler.obtainMessage(WXPayUtil.WX_PAY_STATUS, resp.errCode).sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finish();
	}
}