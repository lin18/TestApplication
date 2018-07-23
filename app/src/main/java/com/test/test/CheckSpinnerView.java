package com.test.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.support.v7.app.AlertDialog;
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

public class CheckSpinnerView extends RelativeLayout {

    private CheckBox mTitle;
    private TextView mTip;
    private TextView mSpinner;
    private TextView mTipBelow;
    private XEditText mEditBelow;
    private CheckBox mCheck;

    private int checkedItem = -1;
    private int spinner;
    private int spinnerId;

    private OnTitleCheckedListener listener;

    public CheckSpinnerView(Context context) {
        this(context, null);
    }

    public CheckSpinnerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckSpinnerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(final Context context, AttributeSet attrs, int defStyle) {

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CheckSpinnerView);

        final String title = array.getString(R.styleable.CheckSpinnerView_title);
        final String tip = array.getString(R.styleable.CheckSpinnerView_tip);
        final String tipBelow = array.getString(R.styleable.CheckSpinnerView_tip_below);
        final boolean showTipBelow = array.getBoolean(R.styleable.CheckSpinnerView_show_below_tip, false);
        final String check = array.getString(R.styleable.CheckSpinnerView_check_text);
        final String prompt = array.getString(R.styleable.CheckSpinnerView_prompt);
        spinner = array.getResourceId(R.styleable.CheckSpinnerView_spinner, -1);
        spinnerId = array.getResourceId(R.styleable.CheckSpinnerView_spinner_id, -1);
        final boolean checkIn = array.getBoolean(R.styleable.CheckSpinnerView_check_in, false);

        array.recycle();

        inflate(context, R.layout.check_spinner_layout, this);

        mTitle = findViewById(R.id.title);
        mTip = findViewById(R.id.tip);
        mSpinner = findViewById(R.id.spinner);
        mTipBelow = findViewById(R.id.tipBelow);
        mEditBelow = findViewById(R.id.editBelow);
        mCheck = findViewById(R.id.check);

        mEditBelow.setFilters(new InputFilter[]{new DecimalFormatInputFilter()});

        mTitle.setText(title);
        mTip.setText(tip);

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
                    if(checkIn) mCheck.setChecked(true);
                }
                if(listener != null) listener.titleCheck(isChecked);
            }
        });

        mSpinner.setText(prompt);

        mSpinner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner != -1) {
                    final CharSequence[] items = context.getResources().getTextArray(spinner);
                    final AlertDialog dialog = new AlertDialog.Builder(context)
                            .setSingleChoiceItems(context.getResources().getTextArray(spinner), checkedItem, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    checkedItem = which;
                                    mSpinner.setText(items[which]);
                                    mTitle.setChecked(true);
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();
                }
            }
        });
    }

    public void showTipBelow(boolean showTipBelow) {
        mTipBelow.setVisibility(showTipBelow ? View.VISIBLE : View.GONE);
        mEditBelow.setVisibility(showTipBelow ? View.VISIBLE : View.GONE);
    }

    public void showCheck(boolean showCheck) {
        mCheck.setVisibility(showCheck ? View.VISIBLE : View.GONE);
    }

    public void setOnTouchListener(OnTouchListener l) {
        mSpinner.setOnTouchListener(l);
    }

    public CheckBox getTitleCheckBox(){ return mTitle; }

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
        return (String) mSpinner.getText();
    }

    public void setBelowText(String text){
        mEditBelow.setText(text);
    }

    public String getBelowText() {
        return mEditBelow.getText().toString().trim();
    }

    public XEditText getBelowInputView(){
        return mEditBelow;
    }

    public int getCheckedPosition() {
        return checkedItem;
    }

    public void setCheckPosition(int checkedItem){
        this.checkedItem = checkedItem;

        try {
            String spinnerText = getContext().getResources().getStringArray(spinner)[checkedItem];
            mSpinner.setText(spinnerText);
        } catch (Exception e) {
        }
    }

    public String getCheckedItemId() {
        String id = "";
        try {
            id = getContext().getResources().getStringArray(spinnerId)[checkedItem];
        } catch (Exception e) {
        }
        return id;
    }

    public void setTitleCheckListener(OnTitleCheckedListener listener){
        this.listener = listener;
    }

    public interface OnTitleCheckedListener{
        void titleCheck(boolean isChecked);
    }

}
