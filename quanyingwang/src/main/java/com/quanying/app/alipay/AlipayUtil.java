package com.quanying.app.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;

import com.alipay.sdk.app.PayTask;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

public class AlipayUtil {
	// 商户PID
	public static final String PARTNER = "2088701591006294";
	// 商户收款账号
	public static final String SELLER = "vip@7192.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMsmiciiPtXRaIFlnm4YO0n9c35E+ExxKVZq2jRO1yVWBxR5w3zzgmUtqyw5RqQp1YBU91RgLL+/ZOUyNQZrvFVlUwFcvLGNwiQOuk+3KfnJ84ltDRB+ZKggfI5j8XkJ1g7QE0ZHvA3UJxLDNKkUqsYkZUSouObGGWnz9YFYqy0HAgMBAAECgYEAxkvY5HnSgd3Fd8Ecc3S9ssxuRsvF3r/6zLrCQZrPkUc24/qzIi2Dk4WPdzx1Qtr+5ySIw9iY9ZY6QyrN7XWOP8N8dWLLLkVUXqVm/COW0XdwjiWIE00yZwAuotqIu3IHZrkrbdbGYeTgV42as1j0Rrg+eIcQk/oLH0jBvv6kYbkCQQDtC7EuNBedqA9WSdbIccs2bcYGwzTganr1emYn+BEtGa52YJyaqBQncYY+pYViNN/z/BPeXSfbWlmtSfIuWgbFAkEA22UD4t1zliN4I2/zMpYdQWCQZ3O/4SMJxQYPDNi3CPsCflqZTs94NgCgDQYiMJdY/rtL9OWxLiYDToFt3FQBWwJBAOCljQPJkHv0EpznG2sZFMn/XIV6KDlqEB9mITxNxxFj3kgHpUKYeYkr+FqUiQUaMQfL5JR+2k/ynzwv8RlmbjUCQDrATFAeWWulR/DFc+ziAieVwlXRy1pw1ZbrTnB0QF+pSDup61ANgvWDKVkTgWhdvzlH0slcERx4lX4abt8Hp+0CQQDklPlFzJKjLuibI3xGt7fA53aKMQxx7nth0wxWOn/NyvItcY5RmHug4CkgUJ/4lUpeohbDQgDTJulzx9Bkpn3k";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDLJonIoj7V0WiBZZ5uGDtJ/XN+RPhMcSlWato0TtclVgcUecN884JlLassOUakKdWAVPdUYCy/v2TlMjUGa7xVZVMBXLyxjcIkDrpPtyn5yfOJbQ0QfmSoIHyOY/F5CdYO0BNGR7wN1CcSwzSpFKrGJGVEqLjmxhlp8/WBWKstBwIDAQAB";
	public static final int SDK_PAY_FLAG = 1;
	public static final int SDK_CHECK_FLAG = 2;
	public static String NOTIFY_URL = "";
	public static String RETURN_URL = "";

	public AlipayUtil() {
		// TODO Auto-generated constructor stub
	}

	public static void doPay(final Context context, final Handler handler, final String order) throws Exception {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
			Toast.makeText(context, "需要配置PARTNER | RSA_PRIVATE| SELLER", Toast.LENGTH_SHORT).show();
			return;
		}
		JSONObject o = new JSONObject(order);
		String id = o.getString("id");
		String userid = o.getString("userid");
		String subject = o.getString("subject");
		String orderno = o.getString("orderno");
		String fee = o.getString("fee");
		String status = o.getString("status");
		String addtime = o.getString("addtime");
		RETURN_URL = o.getString("return_url");
		NOTIFY_URL = o.getString("notify_url");
		String orderInfo = getOrderInfo(subject, subject, fee, orderno);

		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = sign(orderInfo);
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask((Activity) context);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				handler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	private static String getOrderInfo(String subject, String body, String price, String orderNo) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + orderNo + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + NOTIFY_URL + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"" + RETURN_URL + "\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private static String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	private static String getSignType() {
		return "sign_type=\"RSA\"";
	}
}
