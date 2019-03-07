package com.quanying.app.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsGranted;
import com.joker.annotation.PermissionsRequestSync;
import com.joker.api.Permissions4M;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.quanying.app.MyApplication;
import com.quanying.app.R;
import com.quanying.app.alipay.AlipayUtil;
import com.quanying.app.alipay.PayResult;
import com.quanying.app.utils.LoadingDialog;
import com.quanying.app.wxapi.WXPayUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import static com.quanying.app.activity.MainActivity.CALLCODE;
import static com.quanying.app.activity.MainActivity.CAMERACODE;
import static com.quanying.app.activity.MainActivity.READCODE;
import static com.quanying.app.activity.MainActivity.WRITECODE;

//@PermissionsRequestSync(value = {Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, permission={CAMERACODE,CALLCODE})


@PermissionsRequestSync(permission = {Manifest.permission.CAMERA, Manifest.permission
		.CALL_PHONE,Manifest.permission
		.READ_EXTERNAL_STORAGE,Manifest.permission
		.WRITE_EXTERNAL_STORAGE},
		value = {CAMERACODE, CALLCODE,READCODE,WRITECODE})
public class MainActivity extends Activity implements View.OnClickListener {
	private static int ACTIVITY_IMAGE_CAPTURE = 1;
	private static int ACTIVITY_GET_IMAGE = 2;
	private static int ACTIVITY_CUT_IMAGE = 3;
	private static String IMAGE_CACHE_DIR = "qyapp/files/imgcache";
	private static String IMAGE_CACHE_DIR_1 = "qyapp/files/imgcache1";
	private String CAHCEDIR = null;
	private WebView webView;
	private View vTitle;
	private TextView tvTitle;
	private ImageView ivLogo;
	private View vBack;
	private View vShare;
	private String shareInfo = "";
	private long lastTime = 0;
	private JSInterface jsInterface = new JSInterface();
	private String URL = "";
	private String extraURL = null;
	private boolean isMain = true;
	private Dialog imagePickDialog;
	private boolean needCut = false;
	private String[] outSize = null;
	private String cutRate = "1:1";
	private String currentFile = null;
	private String idStr = null;
	//private ValueCallback<Uri> uploadInfo;
	public static MainActivity instance;
	// private LoadingDialog loadingDialog;
	private View llLoading;
	private View errPage;
	private String errUrl;
	private ProgressBar progressBar;
	boolean isFromPush = false;;
	public final static int FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5 = 5;
	public final static int CAMERACODE = 101;
	public final static int CALLCODE = 102;
	public final static int READCODE = 103;
	public final static int WRITECODE = 104;
	private Button button_;

	private ValueCallback<Uri[]> mUploadCallbackAboveL;
	private ValueCallback<Uri> mUploadMessage;// 表单的数据信息


	@Override
	protected void onCreate(Bundle savedInstanceState) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		instance = this;
		URL = getIntent().getStringExtra("URL");
		extraURL = getIntent().getStringExtra("extraURL");
		isMain = getIntent().getBooleanExtra("isMain", true);
		init();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
		Permissions4M
				.get(MainActivity.this)
				.requestSync();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}

	public void init() {
		tvTitle = (TextView) findViewById(R.id.tv_title_text);
		vTitle = findViewById(R.id.ll_title);
		ivLogo = (ImageView) findViewById(R.id.iv_title_logo);
		vBack = findViewById(R.id.iv_title_back);
		vShare = findViewById(R.id.iv_title_share);
		errPage = findViewById(R.id.err_page);
		progressBar = (ProgressBar) findViewById(R.id.pb_progress);
		vBack.setOnClickListener(this);
		vShare.setOnClickListener(this);
		errPage.setOnClickListener(this);
		button_ = (Button) findViewById(R.id.button_);
		button_.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,DbUIActivity.class));
			}
		});
		webView = (WebView) findViewById(R.id.wv_main);
		webView.getSettings().setDefaultFontSize(16);
		llLoading = findViewById(R.id.ll_loading);
		GifView gf1 = (GifView) findViewById(R.id.gif);
		// 设置Gif图片源
		gf1.setGifImage(R.drawable.loading);
		// 设置显示的大小，拉伸或者压缩
		gf1.setShowDimension(LoadingDialog.dp2px(this, 100), LoadingDialog.dp2px(this, 100));
		// 设置加载方式：先加载后显示、边加载边显示、只显示第一帧再显示
		gf1.setGifImageType(GifImageType.COVER);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() + "qyApp");
		webView.addJavascriptInterface(jsInterface, "qyapp");
		webView.setWebViewClient(client);
		webView.setWebChromeClient(chromeClient);


		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);

		CAHCEDIR = StorageUtils.getOwnCacheDirectory(this, IMAGE_CACHE_DIR_1).getAbsolutePath();
		initImageLoader(this);
		//initPickerDialog();
		// loadingDialog = new LoadingDialog(this);
		if (extraURL != null && !extraURL.equals(URL)) {
			isFromPush = true;
			webView.loadUrl(extraURL);
		} else {
			webView.loadUrl(URL);
		}
	}

	public void loadUrl(String url) {
		webView.loadUrl(url);
	}

/*	public void initPickerDialog() {
		View v = View.inflate(this, R.layout.get_image_dialog, null);
		imagePickDialog = new Dialog(this, android.R.style.Theme_Wallpaper_NoTitleBar_Fullscreen);
		imagePickDialog.setContentView(v);
		// imagePickDialog = new AlertDialog.Builder(this,
		// android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen)
		// .setView(v).create();
		v.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				imagePickDialog.dismiss();
				uploadInfo.onReceiveValue(null);
			}
		});
		v.findViewById(R.id.bt_camera).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				imagePickDialog.dismiss();
				takePhoto();

			}
		});
		v.findViewById(R.id.bt_gallery).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				imagePickDialog.dismiss();
				pickAlbum();
			}
		});
	}*/

	/**
	 * 调用系统拍照方法
	 */
	private void takePhoto() {
		currentFile = CAHCEDIR + "/CAMERA_" + System.currentTimeMillis();
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(currentFile);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		startActivityForResult(cameraIntent, ACTIVITY_IMAGE_CAPTURE);
	}

	/**
	 * 调用系统相册方法
	 */
	private void pickAlbum() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(intent, ACTIVITY_GET_IMAGE);
	}

	public void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, IMAGE_CACHE_DIR);
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.diskCache(new UnlimitedDiskCache(cacheDir));// 自定义缓存路径
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app
		ImageLoader.getInstance().init(config.build());
	}

	private WebChromeClient chromeClient = new WebChromeClient() {
		// Android > 4.1.1 调用这个方法
		public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
			/*uploadInfo = uploadMsg;
			imagePickDialog.show();*/
			mUploadMessage=uploadMsg;
			take();
		}

		// 3.0 + 调用这个方法
		public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
/*			uploadInfo = uploadMsg;
			imagePickDialog.show();*/
			mUploadMessage=uploadMsg;
			take();

		}
		/**
		 * alert弹框
		 *
		 * @return
		 */
		@Override
		public boolean onJsAlert(WebView view, String url, final String message, JsResult result) {
			Log.d("main", "onJsAlert:" + message);
			runOnUiThread(new Runnable() {
				@Override
				public void run() {

					new AlertDialog.Builder(MainActivity.this)
							.setTitle("全影提示")
							.setMessage(message)
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									webView.reload();//重写刷新页面
								}
							})
							.setNegativeButton("取消",null)
							.show();

				}
			});
			result.confirm();//这里必须调用，否则页面会阻塞造成假死
			return true;
		}


		// Android < 3.0 调用这个方法
		public void openFileChooser(ValueCallback<Uri> uploadMsg) {
			/*uploadInfo = uploadMsg;
			imagePickDialog.show();*/
			mUploadMessage=uploadMsg;
			take();
		}

	/*	@Override
		@SuppressLint("NewApi")
		public boolean onShowFileChooser (WebView webView, ValueCallback<Uri[]> uploadMsg,
				WebChromeClient.FileChooserParams fileChooserParams) {
			onenFileChooseImpleForAndroid(uploadMsg);
			return true;
		}*/

		@Override
		public boolean onShowFileChooser(WebView webView,
				ValueCallback<Uri[]> filePathCallback,
				FileChooserParams fileChooserParams) {
			mUploadCallbackAboveL=filePathCallback;
			take();
			return true;
		}



		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progressBar.setVisibility(View.GONE);
			} else {
				if (View.GONE == progressBar.getVisibility()) {
					progressBar.setVisibility(View.VISIBLE);
				}
				progressBar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}
	};
	private WebViewClient client = new WebViewClient() {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.e("kevin","url:"+url);
			if (url.toLowerCase().startsWith("tel:")) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
				startActivity(intent);
			} else {
				view.loadUrl(url);
			}
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			// loadingDialog.show();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			// loadingDialog.dismiss();
			if (llLoading.getVisibility() == View.VISIBLE) {
				llLoading.setVisibility(View.GONE);
			}
			// if (extraURL != null && !extraURL.equals(URL)) {
			// view.loadUrl(extraURL);
			// }
			// extraURL = null;
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			// loadingDialog.dismiss();
			if (llLoading.getVisibility() == View.VISIBLE) {
				llLoading.setVisibility(View.GONE);
			}
			// if (extraURL != null && !extraURL.equals(URL)) {
			// view.loadUrl(extraURL);
			// }
			// extraURL = null;
			view.stopLoading();
			errUrl = failingUrl;
			errPage.setVisibility(View.VISIBLE);
			vShare.setVisibility(View.GONE);
		}

		@Override
		public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
			super.onReceivedHttpAuthRequest(view, handler, host, realm);
		}

	};

	final class InJavaScriptLocalObj {
		public void showSource(String html) {
			System.out.println("====>html=" + html);
		}
	}

	private Uri imageUri;
	private void take(){
		File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
		// Create the storage directory if it does not exist
		if (! imageStorageDir.exists()){
			imageStorageDir.mkdirs();
		}
		File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
			imageUri = Uri.fromFile(file);

		final List<Intent> cameraIntents = new ArrayList<Intent>();
		final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		final PackageManager packageManager = getPackageManager();
		final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
		for(ResolveInfo res : listCam) {
			final String packageName = res.activityInfo.packageName;
			final Intent i = new Intent(captureIntent);
			i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
			i.setPackage(packageName);
			i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			cameraIntents.add(i);

		}
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("image/*");
		Intent chooserIntent = Intent.createChooser(i,"Image Chooser");
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
		MainActivity.this.startActivityForResult(chooserIntent,  FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView.canGoBack()) {
				webView.goBack();
				errPage.setVisibility(View.GONE);
				return true;
			} else {
				if (isFromPush) {
					isFromPush = false;
					webView.loadUrl(URL);
					return true;
				}
				long now = System.currentTimeMillis();
				if (now - lastTime <= 2000) {
					return super.onKeyDown(keyCode, event);
				} else {
					if (isMain) {
						lastTime = now;
						Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
						return true;
					}
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_back:
			if (webView.canGoBack()) {
				webView.goBack();
				errPage.setVisibility(View.GONE);
			} else {
				if (isFromPush) {
					isFromPush = false;
					webView.loadUrl(URL);
				}
			}
			break;
		case R.id.iv_title_share:
			jsInterface.openshare(shareInfo);
			break;
		case R.id.err_page:
			errPage.setVisibility(View.GONE);
			webView.reload();// .loadUrl(errUrl);
			break;
		}

	}

	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AlipayUtil.SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
					// webView.loadUrl(AlipayUtil.RETURN_URL);
					webView.loadUrl("javascript:pay_sta('success','ali')");
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(MainActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
						webView.loadUrl("javascript:pay_sta('other','ali')");
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
						webView.loadUrl("javascript:pay_sta('fail','ali')");
					}
				}
				break;
			}
			case WXPayUtil.WX_PAY_STATUS: {
				int code = (Integer) msg.obj;
				if (code == 0) {
					webView.loadUrl("javascript:pay_sta('success','wx')");
				} else if (code == -1) {
					webView.loadUrl("javascript:pay_sta('fail','wx')");
				} else if (code == -2) {
					webView.loadUrl("javascript:pay_sta('cancel','wx')");
				}
				break;
			}
			default:
				break;
			}
		};
	};

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		String cutRates[] = cutRate.split(":");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", Integer.valueOf(cutRates[0]));
		intent.putExtra("aspectY", Integer.valueOf(cutRates[1]));
		// // outputX outputY 是裁剪图片宽高
		// intent.putExtra("outputX", 320);
		// intent.putExtra("outputY", 320);

		intent.putExtra("return-data", false);
		currentFile = CAHCEDIR + "/CUT_" + System.currentTimeMillis();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(currentFile)));
		startActivityForResult(intent, ACTIVITY_CUT_IMAGE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//Uri result = (data == null || resultCode != Activity.RESULT_OK) ? null: data.getData();

		if (resultCode != Activity.RESULT_OK) {
			//uploadInfo.onReceiveValue(null);
			if(mUploadCallbackAboveL !=null){
				mUploadCallbackAboveL.onReceiveValue(null);
				return;
			}
			if(mUploadMessage!=null){
				mUploadMessage.onReceiveValue(null);
				return;
			}
		}

		if(requestCode==FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5)
		{
			if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
			Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
			if (mUploadCallbackAboveL != null) {
				onActivityResultAboveL(requestCode, resultCode, data);
			}
			else  if (mUploadMessage != null) {

				if(result==null){
					//                   mUploadMessage.onReceiveValue(imageUri);
					mUploadMessage.onReceiveValue(imageUri);
					mUploadMessage = null;

					Log.e("imageUri",imageUri+"");
				}else {
					mUploadMessage.onReceiveValue(result);
					mUploadMessage = null;
				}
			}
		}

	}

	@SuppressWarnings("null")
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
		if (requestCode != FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5
				|| mUploadCallbackAboveL == null) {
			return;
		}

		Uri[] results = null;
		if (resultCode == Activity.RESULT_OK) {
			if (data == null) {
				results = new Uri[]{imageUri};
			} else {
				String dataString = data.getDataString();
				ClipData clipData = data.getClipData();

				if (clipData != null) {
					results = new Uri[clipData.getItemCount()];
					for (int i = 0; i < clipData.getItemCount(); i++) {
						ClipData.Item item = clipData.getItemAt(i);
						results[i] = item.getUri();
					}
				}

				if (dataString != null)
					results = new Uri[]{Uri.parse(dataString)};
			}
		}
		if(results!=null){
			mUploadCallbackAboveL.onReceiveValue(results);
			mUploadCallbackAboveL = null;
		}else{
			results = new Uri[]{imageUri};
			mUploadCallbackAboveL.onReceiveValue(results);
			mUploadCallbackAboveL = null;
		}

		return;
	}


	class JSInterface {
		public JSInterface() {

		}

		@JavascriptInterface
		public void setUser(String user) {
			Log.e("kevins",user);
			JPushInterface.setAliasAndTags(MainActivity.this, user, null);
		}

	@JavascriptInterface
		public void playlive(String id) {
			Log.e("playlive",id);
		Intent intent = new
				Intent(MainActivity.this,PlayUIActivity.class);
		//在Intent对象当中添加一个键值对
		MyApplication.PLAYID = id;
		MyApplication.PLAYSTATUS = 1+"";
		intent.putExtra("play_id",id);
		startActivity(intent);
		}

		/**
		 * 设置顶部标题函数 图片类型的参数 titleJson={'isShow':true,//是否显示顶部nav
		 * 'backAble':false,//是否显示后退按钮 'rightBtn':'share',//右侧显示分享按钮
		 * 'type':'img',
		 * 'content':'http://m.7192.com/static/img/quanying_hr.png' }
		 */
		@JavascriptInterface
		public void setTitle(final String titleJson) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						JSONObject o = new JSONObject(titleJson);
						boolean isShow = o.getBoolean("isShow");
						boolean backAble = o.getBoolean("backAble");
						String rightBtn = o.getString("rightBtn");
						String type = o.getString("type");
						String content = o.getString("content");
						//vTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
						vBack.setVisibility(backAble ? View.VISIBLE : View.GONE);
						if (rightBtn != null && rightBtn.equals("share")) {
							vShare.setVisibility(View.VISIBLE);
						} else {
							vShare.setVisibility(View.GONE);
						}
						if (type != null && type.equals("img")) {
							ivLogo.setVisibility(View.VISIBLE);
							tvTitle.setVisibility(View.GONE);
							ImageLoader.getInstance().displayImage(content, ivLogo);
						} else {
							tvTitle.setVisibility(View.VISIBLE);
							ivLogo.setVisibility(View.GONE);
							tvTitle.setText(content);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

		}

		@JavascriptInterface
		public void logOut() {
//			Intent in = new Intent(MainActivity.this, LoginActivity.class);
//			startActivity(in);
//			getSharedPreferences("config", 0).edit().remove("user").remove("pass").remove("access_token").commit();
//			finish();
//			JPushInterface.setAlias(MainActivity.this, null, null);
		}

		@JavascriptInterface
		public void logIn() {
//			Intent in = new Intent(MainActivity.this, LoginActivity.class);
//			startActivity(in);
//			getSharedPreferences("config", 0).edit().remove("user").remove("pass").remove("access_token").commit();
//			finish();
//			JPushInterface.setAlias(MainActivity.this, null, null);
		}

		@JavascriptInterface
		public void aliPay(String orderJson) {
			Log.e("orderjson",orderJson);
			Toast.makeText(MainActivity.this, ""+orderJson, Toast.LENGTH_SHORT).show();
			try {
				AlipayUtil.doPay(MainActivity.this, handler, orderJson);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
				webView.loadUrl("javascript:pay_sta('fail','ali')");
			}
		}

		@JavascriptInterface
		public void wxPay(String orderJson) {
			Toast.makeText(MainActivity.this, ""+orderJson, Toast.LENGTH_SHORT).show();
			Log.e("orderjson",orderJson);
			WXPayUtil.doPay(MainActivity.this, orderJson);
		}
		@JavascriptInterface
		public void logIn(String urls) {
			webView.loadUrl(urls);
		}

		@JavascriptInterface
		public void uploadImage(final String setting) {
			System.out.println(setting);
			// { "needCut": true, "needZip": true, "outSize": "1000x1000",
			// "cutRate": "1:1"}
			try {
				JSONObject o = new JSONObject(setting);
				if (o.has("needCut")) {
					needCut = o.getBoolean("needCut");
				}
				if (o.has("outSize")) {
					String s = o.getString("outSize");
					if (s != null) {
						String ss[] = s.split("x");
						if (ss != null && ss.length == 2) {
							outSize = ss;
						}
					}
				}
				if (o.has("cutRate")) {
					cutRate = o.getString("cutRate");
				}
			} catch (Exception e) {
			}
		}

		@JavascriptInterface
		public void openshare(String config) {
			try {
				JSONObject o = new JSONObject(config);
				String title = o.getString("title");
				String link = o.getString("link");
				String thumb = o.getString("thumb");
				String dsp = o.getString("dsp");

				OnekeyShare oks = new OnekeyShare();
				// 关闭sso授权
				oks.disableSSOWhenAuthorize();

				// oks.setNotification(R.drawable.ic_launcher,
				// getString(R.string.app_name));
				// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
				oks.setTitle(title);
				// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
				oks.setTitleUrl(link);
				// text是分享文本，所有平台都需要这个字段
				oks.setText(dsp);
				// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
				oks.setImageUrl(thumb);
				// url仅在微信（包括好友和朋友圈）中使用
				oks.setUrl(link);
				// comment是我对这条分享的评论，仅在人人网和QQ空间使用
				// oks.setComment("我是测试评论文本");
				// site是分享此内容的网站名称，仅在QQ空间使用
				oks.setSite(getString(R.string.app_name));
				// siteUrl是分享此内容的网站地址，仅在QQ空间使用
				oks.setSiteUrl(link);

				// 启动分享GUI
				oks.show(MainActivity.this);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		@JavascriptInterface
		public void setshare(String config) {
			shareInfo = config;
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
			grantResults) {
		Permissions4M.onRequestPermissionsResult(MainActivity.this, requestCode, grantResults);
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
	@PermissionsGranted({CAMERACODE, CALLCODE,READCODE,WRITECODE})
	public void granted(int code) {
		switch (code) {
			case CAMERACODE:
				//ToastUtil.show("地理位置权限授权成功");
				break;
			case CALLCODE:
				//ToastUtil.show("传感器权限授权成功");
				break;
		}
	}
	@PermissionsDenied({CAMERACODE, CALLCODE,READCODE,WRITECODE})
	public void denied(int code) {
		switch (code) {
			case CAMERACODE:
				//ToastUtil.show("地理位置权限授权成功");
				Toast.makeText(instance, "相机权限获取失败", Toast.LENGTH_SHORT).show();
				break;
			case CALLCODE:
				//ToastUtil.show("传感器权限授权成功");
				Toast.makeText(instance, "拨打电话权限获取失败", Toast.LENGTH_SHORT).show();
				break;
			case READCODE:
				//ToastUtil.show("传感器权限授权成功");
				Toast.makeText(instance, "读取存储空间权限", Toast.LENGTH_SHORT).show();
				break;
			case WRITECODE:
				//ToastUtil.show("传感器权限授权成功");
				Toast.makeText(instance, "写入存储空间权限", Toast.LENGTH_SHORT).show();
				break;

		}
	}


	}
