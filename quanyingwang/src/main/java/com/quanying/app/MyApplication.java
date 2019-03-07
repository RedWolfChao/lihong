package com.quanying.app;

import android.app.Application;
import cn.jpush.android.api.JPushInterface;
import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.VcPlayerLog;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.zhy.http.okhttp.OkHttpUtils;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

public class MyApplication extends Application {
	private AliVcMediaPlayer mPlayer;
	public static OkHttpUtils ohti;
	public static String Cookies;

	public static String PLAYID ="" ;
	public static String PLAYCALLBACKID ="" ;
	public static String PLAYSTATUS ="" ;
	public static String PLAYCID ="" ;

	public MyApplication() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);

		VcPlayerLog.enableLog();
//初始化播放器（只需调用一次即可，建议在application中初始化）
		final String businessId = "";
		AliVcMediaPlayer.init(getApplicationContext(), businessId);
//创建播放器的实例

		//new SetCookieCache().
		//new SharedPrefsCookiePersistor(getApplicationContext()).s
		ClearableCookieJar cookieJar =
				new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				//                .addInterceptor(new LoggerInterceptor("TAG"))
				.connectTimeout(10000L, TimeUnit.MILLISECONDS)
				.readTimeout(10000L, TimeUnit.MILLISECONDS)
				//其他配置
				.build();

		OkHttpUtils.initClient(okHttpClient);



	}

}
