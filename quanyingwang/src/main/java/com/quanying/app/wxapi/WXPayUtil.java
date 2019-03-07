package com.quanying.app.wxapi;

import org.json.JSONObject;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.Context;
import android.widget.Toast;

public class WXPayUtil {

	public static final String APPID = "wx8d8c81355a0869cd";
	public static final String PARID = "1307638701";
	public static final String NOTIFY_URL = "";
	private static IWXAPI api;
	public static final int WX_PAY_STATUS = 0x100000;

	public WXPayUtil() {
		// TODO Auto-generated constructor stub
	}

	public static void doPay(final Context context, final String order) {
		try {
			api = WXAPIFactory.createWXAPI(context, null);
			api.registerApp(APPID);
			JSONObject json = new JSONObject(order);
			PayReq req = new PayReq();
			req.appId = json.getString("appid");
			req.partnerId = json.getString("partnerid");
			req.prepayId = json.getString("prepayid");
			req.nonceStr = json.getString("noncestr");
			req.timeStamp = json.getString("timestamp");
			req.packageValue = json.getString("package");
			req.sign = json.getString("sign");
			Toast.makeText(context, "正常调起支付", Toast.LENGTH_SHORT).show();
			boolean ret = api.sendReq(req);
			System.out.println("微信支付结果=》" + ret);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
