package com.test.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.test.widget.XEditText;

/**
 *
 *
 */

public class CheckInputView extends RelativeLayout {

    private CheckBox mTitle;
    private TextView mSubTitle;
    private TextView mTip;
    private XEditText mEdit;
    private TextView mTipBelow;
    private XEditText mEditBelow;
    private CheckBox mCheck;

    private OnTitleCheckedListener listener;

    public CheckInputView(Context context) {
        this(context, null);
    }

    public CheckInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CheckInputView);

        final String title = array.getString(R.styleable.CheckInputView_title);
        final boolean titleCheckOn = array.getBoolean(R.styleable.CheckInputView_title_check_on, false);
        final String subTitle = array.getString(R.styleable.CheckInputView_sub_title);
        final String tip = array.getString(R.styleable.CheckInputView_tip);
        final boolean showTip = array.getBoolean(R.styleable.CheckInputView_show_tip, false);
        final String tipBelow = array.getString(R.styleable.CheckInputView_tip_below);
        final boolean showTipBelow = array.getBoolean(R.styleable.CheckInputView_show_tip_below, false);
        final String check = array.getString(R.styleable.CheckInputView_check_text);
        final boolean checkOn = array.getBoolean(R.styleable.CheckInputView_check_on, false);

        array.recycle();

        inflate(context, R.layout.check_input_layout, this);

        mTitle = findViewById(R.id.title);
        mSubTitle = findViewById(R.id.subtitle);
        mTip = findViewById(R.id.tip);
        mEdit = findViewById(R.id.edit);
        mTipBelow = findViewById(R.id.tipBelow);
        mEditBelow = findViewById(R.id.editBelow);
        mCheck = findViewById(R.id.check);

        mEdit.setFilters(new InputFilter[]{new DecimalFormatInputFilter()});

        mEdit.setOnXTextChangeListener(new XEditText.OnXTextChangeListenerAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!TextUtils.isEmpty(s.toString()))
                    mTitle.setChecked(true);
            }
        });

        mTitle.setText(title);
        setSubTitle(subTitle);

        mTip.setText(tip);
        showTip(showTip);

        mTipBelow.setText(tipBelow);
        showTipBelow(showTipBelow);

        if (TextUtils.isEmpty(check)) {
            mCheck.setVisibility(GONE);
        } else {
            mCheck.setText(check);
            mCheck.setVisibility(VISIBLE);
        }

        mTitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(checkOn) mCheck.setChecked(true);
                }
                if(listener != null) listener.titleCheck(isChecked);
            }
        });
        mTitle.setChecked(titleCheckOn);
    }

    public void showTip(boolean showTip) {
        mTip.setVisibility(showTip ? View.VISIBLE : View.GONE);
        mEdit.setVisibility(showTip ? View.VISIBLE : View.GONE);
    }

    public void showTipBelow(boolean showTipBelow) {
        mTipBelow.setVisibility(showTipBelow ? View.VISIBLE : View.GONE);
        mEditBelow.setVisibility(showTipBelow ? View.VISIBLE : View.GONE);
    }

    public void showCheck(boolean showCheck) {
        mCheck.setVisibility(showCheck ? View.VISIBLE : View.GONE);
    }

    public void setSubTitle(String subTitle) {
        if (TextUtils.isEmpty(subTitle)) {
            mSubTitle.setVisibility(GONE);
        } else {
            mSubTitle.setText(subTitle);
            mSubTitle.setVisibility(VISIBLE);
        }
    }

    public void setTitleCheck(boolean check){
        mTitle.setChecked(check);
    }

    public boolean isCheckedItem() {
        return mTitle.isChecked();
    }

    public void setCheck(boolean check){
        mCheck.setChecked(check);
    }

    public boolean isCheckedCheck() {
        return mCheck.isChecked();
    }

    public String getText() {
        return mEdit.getText().toString().trim();
    }

    public void setText(String text){
        mEdit.setText(text);
    }

    public void setBelowText(String text){
        mEditBelow.setText(text);
    }

    public String getBelowText() {
        return mEditBelow.getText().toString().trim();
    }

    public CheckBox getTitleCheckBox(){ return mTitle; }

    public XEditText getInputView(){
        return mEdit;
    }

    public XEditText getBelowInputView(){
        return mEditBelow;
    }

    public void setTitleCheckListener(OnTitleCheckedListener listener){
        this.listener = listener;
    }

    public interface OnTitleCheckedListener{
        void titleCheck(boolean isChecked);
    }

}
