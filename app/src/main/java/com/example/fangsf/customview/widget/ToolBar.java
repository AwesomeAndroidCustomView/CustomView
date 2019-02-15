package com.example.fangsf.customview.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fangsf.customview.R;


/**
 * Created by fangsf on 2018/7/2.
 * Useful:
 */
public class ToolBar extends Toolbar {

    private String mLeftText, mRightText, mCenterText;
    private int mLeftIcon, mRightIcon;
    private int mCenterTextSize, mLeftTextSize, mRightTextSize;
    private int mCenterTextColor, mLeftTextColor, mRightTextColor;
    private boolean mIsFinshActivity = true;
    private boolean mShowLeftIcon = true;

    private boolean mIsLeftTextFinishActivity = false;

    private int mLeftIconMarginLeft, mRightIconMarginRight;
    private int mLeftTextMarginLeft, mRightTextMarginRight;

    private ImageView mIvLeft, mIvRight;
    private TextView mTvTitle, mTvRight, mTvLeftText;

    public ToolBar(Context context) {
        this(context, null);
    }

    public ToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ToolBar);
        mLeftIcon = array.getResourceId(R.styleable.ToolBar_ToolBarLeftIcon, mLeftIcon);
        mLeftText = array.getString(R.styleable.ToolBar_ToolBarLeftText);
        mCenterText = array.getString(R.styleable.ToolBar_ToolBarCenterText);
        mRightIcon = array.getResourceId(R.styleable.ToolBar_ToolBarRightIcon, mRightIcon);
        mRightText = array.getString(R.styleable.ToolBar_ToolBarRightText);
        mIsFinshActivity = array.getBoolean(R.styleable.ToolBar_ToolBarFinishActivity, true);
        mCenterTextSize = array.getDimensionPixelSize(R.styleable.ToolBar_ToolBarCenterTextSize, dip2px(15));
        mLeftTextSize = array.getDimensionPixelSize(R.styleable.ToolBar_ToolBarLeftTextSize, dip2px(15));
        mRightTextSize = array.getDimensionPixelSize(R.styleable.ToolBar_ToolBarRightTextSize, dip2px(15));
        mCenterTextColor = array.getColor(R.styleable.ToolBar_ToolBarCenterTextColor, Color.parseColor("#333333"));
        mLeftTextColor = array.getColor(R.styleable.ToolBar_ToolBarLeftTextColor, Color.parseColor("#333333"));
        mRightTextColor = array.getColor(R.styleable.ToolBar_ToolBarRightTextColor, Color.parseColor("#333333"));
        mLeftIconMarginLeft = array.getDimensionPixelOffset(R.styleable.ToolBar_ToolBarLeftIconMarginLeft, 0);
        mRightIconMarginRight = array.getDimensionPixelOffset(R.styleable.ToolBar_ToolBarRightIconMarginRight, 0);
        mLeftTextMarginLeft = array.getDimensionPixelOffset(R.styleable.ToolBar_ToolBarLeftTextMarginLeft, 0);
        mRightTextMarginRight = array.getDimensionPixelOffset(R.styleable.ToolBar_ToolBarRightTextMarginRight, 0);
        mIsLeftTextFinishActivity = array.getBoolean(R.styleable.ToolBar_ToolBarLeftTextFinishActivity, false);

        mShowLeftIcon = array.getBoolean(R.styleable.ToolBar_ToolBarShowLeftIcon, mShowLeftIcon);

        array.recycle();

        setViewData();
    }

    private int dip2px(int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
    }

    private void setViewData() {

        if (mLeftIcon != 0 && mLeftIcon > 0) { // 设置了左边的图片, 就默认显示左边的图片
            mIvLeft.setImageResource(mLeftIcon);
        } else {// 没有设置左边的图片, 默认显示返回的箭头
            mIvLeft.setImageResource(R.drawable.ic_back);
            mIvLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }
            });
        }

        // 设置左边图片距离左边的位置
        if (mLeftIconMarginLeft != 0) {
            RelativeLayout.LayoutParams leftIconParams = (RelativeLayout.LayoutParams) mIvLeft.getLayoutParams();
            leftIconParams.leftMargin = mLeftIconMarginLeft;
            mIvLeft.setLayoutParams(leftIconParams);
        }

        if (!mShowLeftIcon) { // 不显示
            mIvLeft.setVisibility(GONE);
        }

        if (mRightIcon != 0) {
            mIvRight.setVisibility(VISIBLE);
            mIvRight.setImageResource(mRightIcon);
        }

        // 设置左边图片距离左边的位置
        if(mRightIconMarginRight != 0){
            RelativeLayout.LayoutParams rightIconParams = (RelativeLayout.LayoutParams) mIvRight.getLayoutParams();
            rightIconParams.rightMargin = mRightIconMarginRight;
            mIvRight.setLayoutParams(rightIconParams);
        }

        if (!TextUtils.isEmpty(mCenterText)) {
            mTvTitle.setVisibility(VISIBLE);
            mTvTitle.setTextColor(mCenterTextColor);
            //mCenterTextSize 获取的是像素值, 而setTextSize(value) 获取是sp的值
            mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCenterTextSize);
            mTvTitle.setText(mCenterText);
        }

        if (!TextUtils.isEmpty(mRightText)) {
            mTvRight.setVisibility(VISIBLE);
            mTvRight.setTextColor(mRightTextColor);
            mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
            mTvRight.setText(mRightText);
        }

        if (mRightTextMarginRight != 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mTvRight.getLayoutParams();
            layoutParams.rightMargin = mRightTextMarginRight;
            mTvRight.setLayoutParams(layoutParams);
        }

        if (!TextUtils.isEmpty(mLeftText)) {
            mTvLeftText.setVisibility(VISIBLE);
            mTvLeftText.setTextColor(mLeftTextColor);
            mTvLeftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);
            mTvLeftText.setText(mLeftText);
        }

        if (mIsLeftTextFinishActivity) {
            mTvLeftText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }
            });
        }

        if (mLeftTextMarginLeft != 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mTvLeftText.getLayoutParams();
            layoutParams.leftMargin = mLeftTextMarginLeft;
            mTvLeftText.setLayoutParams(layoutParams);
        }

    }

    private void initView() {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.title_bar, null, false);

        mIvLeft = (ImageView) view.findViewById(R.id.ivBack);
        mIvRight = (ImageView) view.findViewById(R.id.ivRight);
        mTvTitle = (TextView) view.findViewById(R.id.tvTitle);
        mTvRight = (TextView) view.findViewById(R.id.tvRight);
        mTvLeftText = (TextView) view.findViewById(R.id.tv_left_text);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
        addView(view, lp);
    }
}
