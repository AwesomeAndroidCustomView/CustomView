package com.example.fangsfmac.customview.loadingView_03;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.fangsfmac.customview.R;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Created by fangsfmac on 2018/4/23.
 */

public class ProgressView extends View {

    // 内圆的颜色
    private int mInnerColor;
    // 外圆的颜色
    private int mOutColor;

    // 默认的圆环的 宽度
    private int mBorderWidth = 20;

    // 圆环 中间进度的文字
    private int mCenterTextSize ;
    // 圆环 中间进度文字的颜色
    private int mCenterTextColor = Color.BLACK;

    // 内圆, 外圆, 进度文字的画笔
    private Paint mInnerPaint, mOutPaint, mTextPaint;

    // 设置最大进度,和当前的进度
    private float mMaxProgress = 1f;
    private float mCurrentProgress = 0.5f;


    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        mInnerColor = array.getColor(R.styleable.ProgressView_innerColor1, mInnerColor);
        mOutColor = array.getColor(R.styleable.ProgressView_outColor1, mOutColor);
        mBorderWidth = (int) array.getDimension(R.styleable.ProgressView_borderWidth1, dip2px(mBorderWidth));
        mCenterTextSize = array.getDimensionPixelSize(R.styleable.ProgressView_centerTextSize1, sp2px(mCenterTextSize));
        mCenterTextColor = array.getColor(R.styleable.ProgressView_centerTextColor1, mCenterTextColor);

        // 绘制内圆的画笔
        mInnerPaint = new Paint();
        mInnerPaint.setColor(mInnerColor);
        // 设置抗锯齿
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        //圆弧
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        // 画笔实心
        mInnerPaint.setStyle(Paint.Style.STROKE);

        // 绘制外圆的画笔
        mOutPaint = new Paint();
        mOutPaint.setColor(mOutColor);
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);  // 圆弧
        mOutPaint.setStyle(Paint.Style.STROKE);  // 画笔实心

        mTextPaint = new Paint();
        mTextPaint.setColor(mCenterTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mCenterTextSize);
        mTextPaint.setStyle(Paint.Style.STROKE);


        array.recycle();
    }

    private float dip2px(int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    private int sp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        // 确保是一个正圆
        setMeasuredDimension(width > height ? width : height, width > height ? width : height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制圆形
        RectF rectF = new RectF(mBorderWidth / 2, mBorderWidth / 2, getWidth() - mBorderWidth / 2, getHeight() - mBorderWidth / 2);
        canvas.drawArc(rectF, 0, 360, false, mInnerPaint);

        // 绘制外圆
        float sweepAngle = mCurrentProgress / mMaxProgress;
        canvas.drawArc(rectF, 0, 360 * sweepAngle, false, mOutPaint);

        // 绘制文字
        String text = (int)mCurrentProgress + "";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), textBounds);
        int dx = getWidth() / 2 - textBounds.width() / 2;

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);

        // 基线
        int baseLine = getHeight() / 2 + dy;

        canvas.drawText(text, dx, baseLine, mTextPaint);
    }

    public void setMaxProgress(float maxProgress) {
        mMaxProgress = maxProgress;
    }

    public void setCurrentProgress(float currentProgress) {
        mCurrentProgress = currentProgress;
        invalidate();
    }

}
