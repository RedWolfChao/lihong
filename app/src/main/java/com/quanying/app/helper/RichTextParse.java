package com.quanying.app.helper;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.quanying.app.R;
import com.quanying.danmu.RichMessage;

import java.util.ArrayList;


/**
 * 解析富文本
 * <p>
 * Created by android_ls on 2016/11/25.
 */

public class RichTextParse {

    public static SpannableStringBuilder parse(final Context context, ArrayList<RichMessage> richText,
                                               int textSize, boolean isChatList) {
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (isChatList) {
            String name = "直播消息：";
            spannableStringBuilder.append(name);

            int nameColor = ContextCompat.getColor(context, R.color.live_yellow);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(nameColor),
                    0,
                    name.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        for (RichMessage message : richText) {
            final int length = spannableStringBuilder.length();
            if ("text".equals(message.getType())) {
                String content = message.getContent();
                spannableStringBuilder.append(content);

                String textColor = message.getColor();
                if (TextUtils.isEmpty(textColor)) {
                    textColor = "FFFFFF";
                }

                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#" + textColor)),
                        length,
                        length + content.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                String content = message.getContent();
                spannableStringBuilder.append(content);

                spannableStringBuilder.setSpan(
                        new ForegroundColorSpan(ContextCompat.getColor(context,
                                R.color.light_green)),
                        length,
                        length + content.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return spannableStringBuilder;
    }

}
