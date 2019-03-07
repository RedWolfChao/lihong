package com.quanying.app.ui.user;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.quanying.app.R;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.utils.AppKeyBoardMgr;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;

public class EditorUI extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    private TextView tvCenter;
    private Intent intent;
    public final static String TITLE = "title";
    public final static String LIMIT = "limit";
    public final static String EDITCONTEXT = "context";

    @BindView(R.id.edit)
    EditText editText;
    @BindView(R.id.textsum)
    TextView editSum;
    private  int nubLimit;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_editor_ui;
    }

    @Override
    protected void initView() {

        intent = getIntent();
        nubLimit = intent.getIntExtra(LIMIT,0);
        tvCenter = new TextView(context);
        tvCenter.setText(intent.getStringExtra(TITLE));
        tvCenter.setTextColor(getResources().getColor(R.color.white));
        tvCenter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tvCenter.setGravity(Gravity.CENTER);
        tvCenter.setSingleLine(true);
        titlebar.setCenterView(tvCenter);

        titlebar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
                if (action == CommonTitleBar.ACTION_RIGHT_TEXT) {
                    String edit = editText.getText().toString();
                    if(edit.length()>nubLimit){
                        showBaseDialog("输入长度超出范围","好");
                        return;
                    }

                    Intent intent1 = new Intent();
                    intent1.putExtra(EDITCONTEXT, edit);
                    EditorUI.this.setResult(RESULT_OK, intent1);
                    AppKeyBoardMgr.closeKeybord(editText,context);
                    finish();
                }


            }
        });
        editSum.setText("0/"+nubLimit);
        editText.addTextChangedListener(mTextWatcher);

    }
    @Override
    protected void initData() {



    }

    TextWatcher mTextWatcher = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            temp = charSequence;
        }

        @Override
        public void afterTextChanged(Editable editable) {

            editSum.setText(temp.length() + "/"+nubLimit);

        }
    };



    }
