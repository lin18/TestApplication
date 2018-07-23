package com.test.test.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.test.R;

/**
 *
 *
 */
public class CellView extends RelativeLayout {

    private LinearLayout content;
    private ImageView iconIv;
//    private AutoResizeTextView titleTv;
    private TextView titleTv;
    private TextView subTitleTv;
    private TextView tipTv;
    private ImageView tipIv;

    private Drawable icon;
    private int iconWidth;
    private int marginLeft;
    private int marginRight;
    private int arrowsMarginRight;
    private String title;
    private float titleSizeMutli;
    private float titleSizeSimple;
    private int titleColor;
    private String subTitle;
    private float subTitleSize;
    private int subTitleColor;
    private String tip;
    private float tipSize;
    private int tipColor;
    private int tipMarginLeft;
    private Drawable tipBackGround;
    private int arrowVisibility;

    public CellView(Context context) {
        this(context, null);
    }

    public CellView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initData(context, attrs, defStyleAttr);
        initView(context);

        addIcon(context);
        addContent(context);
        addArrows(context);
        addTip(context);

    }

    private void initData(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CellView, defStyleAttr, 0);


        marginLeft = array.getDimensionPixelSize(R.styleable.CellView_cellMarginLeft,
                context.getResources().getDimensionPixelSize(R.dimen.cell_marginLeft));
        icon = array.getDrawable(R.styleable.CellView_cellIcon);
        iconWidth = array.getDimensionPixelSize(R.styleable.CellView_cellIconWidth, 0);
        marginRight = array.getDimensionPixelSize(R.styleable.CellView_cellMarginRight,
                context.getResources().getDimensionPixelSize(R.dimen.marginRight));

        arrowsMarginRight = array.getDimensionPixelSize(R.styleable.CellView_cellArrowsMarginRight,
                context.getResources().getDimensionPixelSize(R.dimen.arrowsMarginRight));

        title = array.getString(R.styleable.CellView_cellTitleText);
        titleSizeMutli = array.getDimensionPixelSize(R.styleable.CellView_cellTitleTextSizeMutli,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 19, context.getResources().getDisplayMetrics()));
        titleSizeSimple = array.getDimensionPixelSize(R.styleable.CellView_cellTitleTextSizeSimple,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, context.getResources().getDisplayMetrics()));
        titleColor = array.getColor(R.styleable.CellView_cellTitleTextColor, Color.parseColor("#000000"));

        subTitle = array.getString(R.styleable.CellView_cellSubTitleText);
        subTitleSize = array.getDimensionPixelSize(R.styleable.CellView_cellSubTitleTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics()));
        subTitleColor = array.getColor(R.styleable.CellView_cellSubTitleTextColor, context.getResources().getColor(R.color.sub_title_color));

        tip = array.getString(R.styleable.CellView_cellTipText);
        tipSize = array.getDimensionPixelSize(R.styleable.CellView_cellTipTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, context.getResources().getDisplayMetrics()));
        tipColor = array.getColor(R.styleable.CellView_cellTipTextColor, Color.parseColor("#888888"));
        tipBackGround = array.getDrawable(R.styleable.CellView_cellTipBackGround);
        tipMarginLeft = array.getDimensionPixelSize(R.styleable.CellView_cellTipMarginLeft, 0);

        arrowVisibility = array.getInt(R.styleable.CellView_cellArrowsVisibility, 0);

        array.recycle();
    }

    private void initView(Context context) {
        content = new LinearLayout(context);
        content.setId(android.R.id.content);
        iconIv = new ImageView(context);
        iconIv.setId(android.R.id.icon);
//        titleTv = new AutoResizeTextView(context);
        titleTv = new TextView(context);
        subTitleTv = new TextView(context);
        tipTv = new TextView(context);
        tipIv = new ImageView(context);
        tipIv.setId(android.R.id.icon1);
    }

    private void addTip(Context context) {
        if (tipBackGround != null)
            tipTv.setBackgroundDrawable(tipBackGround);
        tipTv.setText(tip);
        tipTv.setTextColor(tipColor);
        tipTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tipSize);
        tipTv.setGravity(Gravity.RIGHT);
        tipTv.setMaxLines(1);
        tipTv.setEllipsize(TextUtils.TruncateAt.END);
        RelativeLayout.LayoutParams tipTvParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tipTvParams.addRule(RelativeLayout.RIGHT_OF, content.getId());
        if (tipIv.getVisibility() == VISIBLE) {
            tipTvParams.addRule(RelativeLayout.LEFT_OF, tipIv.getId());
            tipTvParams.leftMargin = tipMarginLeft;
            tipTvParams.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.marginRight);
        } else {
            tipTvParams.leftMargin = tipMarginLeft;
            tipTvParams.rightMargin = arrowsMarginRight;
            tipTvParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        tipTvParams.addRule(RelativeLayout.CENTER_VERTICAL);
        addView(tipTv, tipTvParams);
    }

    private void addArrows(Context context) {
        tipIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow));
        tipIv.setVisibility(arrowVisibility == 0 ? VISIBLE : GONE);
        RelativeLayout.LayoutParams tipIvParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tipIvParams.rightMargin = arrowsMarginRight;
        tipIvParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tipIvParams.addRule(RelativeLayout.CENTER_VERTICAL);
        addView(tipIv, tipIvParams);
    }

    private void addContent(Context context) {
        content.setOrientation(LinearLayout.VERTICAL);
        titleTv.setMaxLines(1);
        titleTv.setText(title);
        titleTv.setTextColor(titleColor);
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextUtils.isEmpty(subTitle) ? titleSizeSimple : titleSizeMutli);


        subTitleTv.setText(subTitle);
        subTitleTv.setTextColor(subTitleColor);
        subTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, subTitleSize);
        subTitleTv.setVisibility(TextUtils.isEmpty(subTitle) ? GONE : VISIBLE);

        titleTv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        content.addView(titleTv, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams subTitleParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        subTitleParams.topMargin = 4;
        content.addView(subTitleTv, subTitleParams);

        RelativeLayout.LayoutParams contentParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        contentParams.addRule(RelativeLayout.RIGHT_OF, iconIv.getId());
        contentParams.addRule(RelativeLayout.CENTER_VERTICAL);
        if (icon == null) contentParams.leftMargin = marginLeft;
        addView(content, contentParams);
    }

    private void addIcon(Context context) {
        RelativeLayout.LayoutParams iconLayoutParams = new RelativeLayout.LayoutParams(
                iconWidth==0 ? LayoutParams.WRAP_CONTENT : context.getResources().getDimensionPixelSize(R.dimen.icon_width),
                iconWidth==0 ? LayoutParams.WRAP_CONTENT : context.getResources().getDimensionPixelSize(R.dimen.icon_width));
        iconLayoutParams.setMargins(marginLeft, 12, marginRight, 12);
        iconLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        iconIv.setImageDrawable(icon);
        iconIv.setVisibility(icon==null ? GONE : VISIBLE);
        addView(iconIv, iconLayoutParams);
    }

    public ImageView getIconIv() {
        return iconIv;
    }

    public String getTitleText() {
        return titleTv.getText().toString();
    }

    public void setTitleText(CharSequence text) {
        titleTv.setText(text);
    }

    public void setTitleText(final String text) {
        titleTv.setText(Html.fromHtml(text));
    }

    public void setSubTitleText(CharSequence text) {
        subTitleTv.setText(text);
        subTitleTv.setVisibility(View.VISIBLE);
    }

    public void setTitleTextColor(@ColorInt int color){
        titleTv.setTextColor(color);
    }

    public void setTipColor(@ColorInt int color) {
        tipTv.setTextColor(color);
    }

    public void setTipText(int resId) {
        tipTv.setText(resId);
    }

    public void setTipText(CharSequence text) {
        tipTv.setText(text);
    }

    public void setTipText(final String text) {
        tipTv.setText(text);
    }

    public void setTipMaxLines(int maxLines) {
        tipTv.setMaxLines(maxLines);
    }

    public void setTipTextExceptNull(CharSequence text) {
        if (TextUtils.isEmpty(text)) return;
        tipTv.setText(text);
    }

    public void setArrowsVisibility(boolean visibility) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (visibility) {
            tipIv.setVisibility(VISIBLE);
            layoutParams.rightMargin = marginRight;
            layoutParams.addRule(RelativeLayout.LEFT_OF, tipIv.getId());
        } else {
            tipIv.setVisibility(GONE);
            layoutParams.rightMargin = getContext().getResources().getDimensionPixelSize(R.dimen.arrowsMarginRight);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        tipTv.setLayoutParams(layoutParams);
    }

    public String getTipText() {
        return tipTv.getText().toString().trim();
    }

    public void setMarginLeft(int marginLeft) {
        ((LayoutParams) content.getLayoutParams()).leftMargin = marginLeft;
    }

}
