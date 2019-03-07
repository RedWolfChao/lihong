package com.quanying.app.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.quanying.app.R;
import com.quanying.app.utils.AppKeyBoardMgr;
import com.xnumberkeyboard.android.XNumberKeyboardView;

public class PayUIActivity extends FragmentActivity implements XNumberKeyboardView.IOnKeyboardListener {

  @BindView(R.id.price_edit)
  EditText price_edit;
  @BindView(R.id.wxzf_btn)
  TextView wxzf_btn;
  @BindView(R.id.pay_main)
  RelativeLayout pay_main;
  @BindView(R.id.view_keyboard)
  XNumberKeyboardView keyboardView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_payui);
    ButterKnife.bind(this);
    keyboardView.setIOnKeyboardListener(this);
    keyboardView.setVisibility(View.GONE);
    price_edit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AppKeyBoardMgr.closeKeybord(price_edit,PayUIActivity.this);
        keyboardView.setVisibility(View.VISIBLE);
      }
    });
    pay_main.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        keyboardView.setVisibility(View.GONE);
      }
    });

    wxzf_btn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

      }
    });

  }

  @Override
  public void onInsertKeyEvent(String text) {
    price_edit.append(text);
  }

  @Override
  public void onDeleteKeyEvent() {
    int start = price_edit.length() - 1;
    if (start >= 0) {
      price_edit.getText().delete(start, start + 1);
    }
  }
}
