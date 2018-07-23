package com.test.test.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.test.R;

/**
 *
 *
 */
public class InputEdit extends LinearLayout {

    private boolean hasTextInput;
    private boolean editTrandparent;
    private int paddingLeft;
    private int textPaddingRight;
    private String inputText;
    private int textColor;
    private int textSize;
    private String inputExtraText;
    private int inputExtraPaddingLeft;

    private TextView textView;
    private TextInputLayout textInputLayout;
    private XEditText editText;
    private TextView extraTextView;

    public InputEdit(Context context) {
        this(context, null);
    }

    public InputEdit(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InputEdit(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.InputEdit);

        hasTextInput = array.getBoolean(R.styleable.InputEdit_inputHasTextInput, false);
        paddingLeft = array.getDimensionPixelSize(R.styleable.InputEdit_inputPaddingLeft, 0);
        textPaddingRight = array.getDimensionPixelSize(R.styleable.InputEdit_inputTextPaddingRight, 0);
        inputText = array.getString(R.styleable.InputEdit_inputText);
        textColor = array.getColor(R.styleable.InputEdit_inputTextColor, Color.parseColor("#000000"));
        textSize = array.getDimensionPixelSize(R.styleable.InputEdit_inputTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, context.getResources().getDisplayMetrics()));
        inputExtraText = array.getString(R.styleable.InputEdit_inputExtraText);
        inputExtraPaddingLeft = array.getDimensionPixelSize(R.styleable.InputEdit_inputExtraPaddingLeft, 0);
        final int paddingRight = array.getDimensionPixelSize(R.styleable.InputEdit_inputPaddingRight, 0);
        editTrandparent = array.getBoolean(R.styleable.InputEdit_inputEditTrandparent, false);

        array.recycle();


        setPadding(paddingLeft, 0, paddingRight, 0);
        addTextView(context);
        addEditText(context, attrs);
        if (!TextUtils.isEmpty(inputExtraText))
            addExtraTextView(context);
    }

    public void UpperCase() {
        if (editText != null)
            editText.UpperCase();//digits
    }

    public void setOnXTextChangeListener(XEditText.OnXTextChangeListener listener) {
        if (editText != null)
            editText.setOnXTextChangeListener(listener);
    }

    public void setInputType(int type) {
        if (editText != null)
            editText.setInputType(type);
    }

    public void setFilters(InputFilter[] filters) {
        if (editText != null)
            editText.setFilters(filters);
    }

    public void setMaxLength(int maxLength) {
        if (editText != null)
            editText.setMaxLength(maxLength);
    }

    private void addTextView(Context context) {
        textView = new TextView(context);
        textView.setId(TextUtils.isEmpty(inputText) ? "@".hashCode() : inputText.hashCode());
        textView.setPadding(0, 0, textPaddingRight, 0);
        textView.setTextColor(textColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setGravity(Gravity.CENTER);
        textView.setText(inputText);
        addView(textView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
    }

    public final void setTitle(@StringRes int resid) {
        if (textView != null)
            textView.setText(resid);
    }

    public void setTitle(CharSequence text) {
        if (textView != null)
            textView.setText(text);
    }

    public void setText(String inputText) {
        if (textView != null)
            textView.setText(Html.fromHtml(inputText));
    }

    public final void setTitleVisibility(int visibility) {
        if (textView != null)
            textView.setVisibility(visibility);
    }

    public final void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        setPadding(paddingLeft, 0, 0, 0);
    }

    public final void setPaddingRight(int paddingRight) {
        this.textPaddingRight = paddingRight;
        if (textView != null)
            textView.setPadding(0, 0, paddingRight, 0);
    }

    private void addEditText(Context context, AttributeSet attrs) {
        editText = new XEditText(context, attrs);
        editText.setId((inputText + "_" + attrs.toString()).hashCode());
        if (editTrandparent)
            editText.setBackgroundColor(Color.TRANSPARENT);
        if (hasTextInput) {
            editText.setHint(null);
            editText.setGravity(Gravity.CENTER_VERTICAL);
            textInputLayout = new TextInputLayout(context, attrs);
            textInputLayout.setId((inputText + "__" + attrs.toString()).hashCode());
            textInputLayout.setPadding(0, 0, 0, context.getResources().getDimensionPixelSize(R.dimen.input_paddingBottom));
            textInputLayout.setGravity(Gravity.CENTER_VERTICAL);
            textInputLayout.setHintAnimationEnabled(true);
            textInputLayout.addView(editText, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            addView(textInputLayout, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
        } else {
            addView(editText, new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        }
    }

    private void addExtraTextView(Context context) {
        extraTextView = new TextView(context);
        extraTextView.setId(TextUtils.isEmpty(inputExtraText) ? "@_@".hashCode() : inputExtraText.hashCode());
        extraTextView.setPadding(inputExtraPaddingLeft, 0, 0, 0);
        extraTextView.setTextColor(textColor);
        extraTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        extraTextView.setGravity(Gravity.CENTER);
        extraTextView.setText(inputExtraText);
        addView(extraTextView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (editText != null)
            editText.setVisibility(visibility);
    }

    public XEditText getEditText() {
        return editText;
    }

    public String getText() {
        if (editText == null) return null;
        return editText.getText().toString().trim();
    }

    public String getNonSeparatorText() {
        if (editText == null) return null;
        return editText.getNonSeparatorText();
    }

    public void setText(CharSequence text) {
        setText(text, true);
    }

    public void setText(CharSequence text, boolean isSetSelection) {
        if (editText != null) {
            editText.setText(text);
            if (isSetSelection && !TextUtils.isEmpty(text))
                editText.setSelection(text.length() > editText.getMaxLength() ? editText.getMaxLength() : text.length());
        }
    }

    public void setSelection(int index) {
        if (editText != null)
            editText.setSelection(index > editText.getMaxLength() ? editText.getMaxLength() : index);
    }

    public void setTextToSeparate(CharSequence text) {
        if (editText != null) {
            editText.setTextToSeparate(text);
            if (!TextUtils.isEmpty(text))
                editText.setSelection(text.length() > editText.getMaxLength() ? editText.getMaxLength() : text.length());
        }
    }

    public void setHintText(CharSequence text) {
        if (editText != null)
            editText.setHint(text);
    }

    public CharSequence getHintText() {
        if (editText == null) return null;
        return editText.getHint();
    }

    public void setError(CharSequence error) {
        if (textInputLayout != null) {
            textInputLayout.setError(error);
        } else if (editText != null){
            editText.setError(error);
        }
    }

    public void editRequestFocus() {
        if (editText != null)
            editText.requestFocus();
    }
}
