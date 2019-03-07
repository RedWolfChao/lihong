package com.quanying.app.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.VcPlayerLog;
import com.com.sky.downloader.greendao.DaoMaster;
import com.com.sky.downloader.greendao.DaoSession;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.quanying.app.utils.AppSharePreferenceMgr;
import com.tsy.sdk.social.PlatformConfig;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;


public class MyApplication extends Application {

    public static String Cookies;
    public static String PLAYID ="" ;
    public static String PLAYCALLBACKID ="" ;
    public static String PLAYSTATUS ="" ;
    public static String PLAYCID ="" ;
	public static Context context;
    public static String WX_SECRET ="42f862ca6ba6b4c794cac0c0af8c02a4";
    // 记录是否已经初始化
    private boolean isInit = false;

    public static String BACKURL = "";
    public static boolean DOREFRESH = false;
    private static DaoSession daoSession;

    public static final String WX_APPID = "wx8d8c81355a0869cd";    //申请的wx appid
    private static final String QQ_APPID = "1105061245";    //申请的qq appid

    public static final String RSA_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCYd0iuhhBZIpV2o/yfHkUXYklbzrC9qu6AQMzrRv0MKnAZRzK+PM5TGPWRbqRj4JchoKADKZNyRhGyARd+5+icHbBWCtqQ1U0ci891sCWs7wGbSJ8NgnJ8//S1T7kwJXKTvzfkJg9pM9OwieKG9Ed6yaV3ombTf3VNj+9AlG24nvFLev5aGWqMG4KYkM38Nj8SBI8kd8O2FKFMUsjVN6ZdhWnuYMVJb5CbZLudVummbT4Oc5uCl2LqJY74qWvlCAZUPIWRnVGRK8TgN5qL2woh4/T0wNnWEn+/VwG7CO/iBQEENdcBa8KUSLmh2S8XCy29iE19+P561iEwaRtmSbwvAgMBAAECggEAesPBkFV+oGcKdkdAyFa1R0jqzLO2+GHEPRzwY5n7c2pQbOo5X5jSgXKdeQbf81QKHRebADDf+qmU4gGjC5psYBg/vmhq6Sf3OT8SQy8chwzD/GcTOuuIiEIIpl2VPcFJatITjO9ghVMn5wR/lC7px2LKi3QG29HLgDKfcR6S4o5Jagt9dtUw78cKuGSx6CUDRKztGngqf/886LCwKc5RxiOLCf1SSlo5zbvaX5Ce5hgZK17aNBDOXo2b5NWW1EB8cluTYcyDTSuiG+BF2FYjUaGGNK6+B1V++c8WuU5mgish0V1lUsG7P/h7Bn29EFpnaeL2XcZChEZidl1Wyl7ccQKBgQDXEuQ4QhkFZJyMVjyog30MvKJPT8bSbepgtxzmQfCkbD3qKkJSeZwfmfATe+LvIm1ITPEQUZ/GSE3bB70F/SyBV1YQ88RqrZDm9XTxYHj4zoRwYluIwTA4isBDJxOvFHveqjdEYfyBG54gyDi4/Qy+B87KYStOt2fmD4xkmCzvLQKBgQC1eoNQISDe1o7VMh5iKo5Gkw1EMK8YJjUnSfDKH2X15kUxol84jMx27yEeLITM6RXNyMRs9q31S+1dymW6r89AjVxBL8/aln6qTV1oNVj84jf7JRe7cxPxkx6sc6mm/JWy7358p9duZ628YDnDQVcM3ogOsFiYeY+r/mPkppiSSwKBgAKuYJv27vrrOyxgstbi9L8g5uEYPIFJD3/vKHQSZF6YXtk5d6Qxkemu8Jg9liGnCze4hdTPP6+oHRha61dUxaZDIXaKo2s1CLZoXvHXYwqvAGWqRDvbNK+vwx/TL+Kh3b1z38aBdjVh08HS3SopNWx1CnXpajHMBYaPsQ9b+kttAoGAbRutWz2vr4o78b3iOo7vX0sGDNtgShqelnKEDGF4QbEiJMdEjJFKVK9MsAGADlqMu15bik99Ix91JJCNPQJ6joruzlpak0cr/GPrJr38NH4lvh7897wmX3VeWbxoYXc24Y/P8k5O3ZgEAl+wGs5r8dbQ4lPv0Cw1zuhxUoU+8aUCgYAYBgeHN6BRiJc13V0Gt6auWUCOC/ugikOzjGuQeBA1S/fjz0UOCTUb7lzKMUuOGMibFVvX1wKaFoIEro5DgVRmnROq3NsK3tnzhxy2JONG7s5q46VqjyEs0AoAMwQSDGx3Gz9ak9JV9cUs/yps8diZHgrh0G+7Ux5m/Miqc+RlBg==";
    public static final String APPID = "2015122301030727";

    public static PersistentCookieJar cookieJar;

//    private IWX

    public MyApplication() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context = this;
		Fresco.initialize(this);
//		CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));

        cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.cookieJar(cookieJar)
				//其他配置
				.build();

		OkHttpUtils.initClient(okHttpClient);

        JPushInterface.init(this);
//		EMOptions options = new EMOptions();
//// 默认添加好友时，是不需要验证的，改成需要验证
//		options.setAcceptInvitationAlways(false);
//
//		EaseUI.getInstance().init(context, options);

//        options = new EMOptions();
//// 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);

//        setupDatabase();
//        EaseUI.getInstance().init(context, options);
        // 初始化环信SDK
//        initEasemob();
        PlatformConfig.setWeixin(WX_APPID);
        PlatformConfig.setQQ(QQ_APPID);

//        initHuanXin();


        VcPlayerLog.enableLog();
//初始化播放器（只需调用一次即可，建议在application中初始化）
        final String businessId = "";
        AliVcMediaPlayer.init(getApplicationContext(), businessId);


	}
/*
* 关闭极光推送服务
* */
	public static void stopJpush(){

        JPushInterface.stopPush(context);
        AppSharePreferenceMgr.put(context,"jpushstatus","0");
    }

    /*
    * 开启极光推送服务
    * */

	public static void openJpush(){

        JPushInterface.resumePush(context);
        AppSharePreferenceMgr.put(context,"jpushstatus","1");

    }
    /*
    * 获取极光推送服务状态
    * */

	public static boolean getJpushStatus(){

        String status = (String) AppSharePreferenceMgr.get(context,"jpushstatus","");
        if(status.equals("0")){

            return false;

        }else{

            return true;
        }
    }


	public static String getToken(){

		String token = (String) AppSharePreferenceMgr.get(context,"token","");
		return token;

	}	public static String getFaceUrl(){

		String token = (String) AppSharePreferenceMgr.get(context,"faceurl","");
		return token;

	}	public static String getUserID(){

		String token = (String) AppSharePreferenceMgr.get(context,"userid","");
		return token;

	}public static String getUserPhone(){

		String token = (String) AppSharePreferenceMgr.get(context,"qyphone","");
		return token;

	}public static String getUserCode(){

		String token = (String) AppSharePreferenceMgr.get(context,"qycode","");
		return token;

	}
public static String getCity(){

		String city = (String) AppSharePreferenceMgr.get(context,"cityname","");
		return city;

	}
public static String getCityId(){

		String city = (String) AppSharePreferenceMgr.get(context,"cityid","");
		return city;

	}


	public static void loginBack(){

		AppSharePreferenceMgr.put(context,"qycode","");
		AppSharePreferenceMgr.put(context,"token","");
		AppSharePreferenceMgr.put(context,"faceurl","");
		AppSharePreferenceMgr.put(context,"userid","");
		AppSharePreferenceMgr.put(context,"qyphone","");

        cleanHc();
	}


	public static void cleanHc(){

        cookieJar.clear();

    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }


//    EMConnectionListener connectionListener;

    /**
     * 设置一个全局的监听
     */
    protected void setGlobalListeners(){


  /*      // create the global connection listener
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
                EMLog.d("global listener", "onDisconnect" + error);
                if (error == EMError.USER_REMOVED) {// 显示帐号已经被移除
                    onUserException(Constant.ACCOUNT_REMOVED);
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {// 显示帐号在其他设备登录
                    onUserException(Constant.ACCOUNT_CONFLICT);
                    EMClient.getInstance().logout(true);//退出登录
//                    Toast.makeText(getApplicationContext(),"退出成功",Toast.LENGTH_SHORT).show();

                } else if (error == EMError.SERVER_SERVICE_RESTRICTED) {//
                    onUserException( Constant.ACCOUNT_FORBIDDEN);
                }
            }

            @Override
            public void onConnected() {
                // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events

            }
        };
*/
        //register connection listener


//        EMClient.getInstance().addConnectionListener(connectionListener);
    }


    /**
     * user met some exception: conflict, removed or forbidden
     */


}
