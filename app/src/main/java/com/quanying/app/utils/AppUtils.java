package com.quanying.app.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {
    public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /*当前做*/
    public static int CURR_COUNT = 60;
    /*设置提现账户  标识
      发送验证码*/

    private static Timer countdownTimer;
    private static TextView tvSendCode;
    private static LinearLayout lv_btn;


    /**
     * 开始倒计时
     *http://blog.csdn.net/qq_16965811
     * @param textView 控制倒计时的view
     */
    public static void startCountdown(TextView textView, LinearLayout lv) {
        lv_btn = lv;
        lv.setClickable(false);

        tvSendCode = textView;
        if (countdownTimer == null) {
            countdownTimer = new Timer();
            countdownTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = CURR_COUNT--;
                    handler.sendMessage(msg);
                }
            }, 0, 600);
        }
    }


    public static void stopTimer() {
        if(countdownTimer!=null) {


            CURR_COUNT= 60;
            lv_btn.setClickable(true);
            tvSendCode.setText("点击获取");
            tvSendCode.setEnabled(true);
            countdownTimer.cancel();
            countdownTimer = null;
        }
    }

    private static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (countdownTimer != null) {
                    countdownTimer.cancel();
                    countdownTimer = null;
                }
                CURR_COUNT= 60;
                lv_btn.setClickable(true);
                tvSendCode.setText("点击获取");
                tvSendCode.setEnabled(true);
            } else {
                tvSendCode.setText("("+msg.what + "s"+")");
                tvSendCode.setEnabled(false);
            }
            super.handleMessage(msg);
        }
    };


    /** 生成漂亮的颜色 */
    public static int generateBeautifulColor() {
        Random random = new Random();
        //为了让生成的颜色不至于太黑或者太白，所以对3个颜色的值进行限定
        int red = random.nextInt(150) + 50;//50-200
        int green = random.nextInt(150) + 50;//50-200
        int blue = random.nextInt(150) + 50;//50-200
        return Color.rgb(red, green, blue);//使用r,g,b混合生成一种新的颜色
    }

    /** 获得状态栏的高度 */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
