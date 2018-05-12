package com.example.fangsfmac.customview.progressLoading_01;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.example.fangsfmac.customview.R;

/**
 * Created by fangsfmac on 2018/4/22.
 */

public class ProgressLoadView extends View {

    // 内圆的颜色
    private int mInnerColor = Color.DKGRAY;
    // 外圆的颜色
    private int mOutColor = Color.RED;
    // 中间进度文字的颜色
    private int mCenterTextColor = Color.BLACK;
    private int mCenterTextSize = 12;

    // 圆形的宽度
    private int mBorderWidth = 20;
    private Paint mCenterTextPaint, mInnerPaint, mOutPaint;

    private float mCurrentProgress = 0;
    private float mMaxProgress = 0;

    public synchronized void setCurrentProgress(int currentProgress) {
        this.mCurrentProgress = currentProgress;
        invalidate();
    }

    public synchronized void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
        invalidate();
    }

    public ProgressLoadView(Context context) {
        this(context, null);
    }

    public ProgressLoadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressLoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAtts(context, attrs);
    }

    private void initAtts(Context context, AttributeSet attrs) {

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressLoadView);

        mBorderWidth = (int) array.getDimension(R.styleable.ProgressLoadView_borderSize, mBorderWidth);
        mCenterTextColor = array.getColor(R.styleable.ProgressLoadView_centerTextColor, mCenterTextColor);
        mInnerColor = array.getColor(R.styleable.ProgressLoadView_innerColor, mInnerColor);
        mOutColor = array.getColor(R.styleable.ProgressLoadView_outColor, mOutColor);
        mCenterTextSize = array.getDimensionPixelSize(R.styleable.ProgressLoadView_centerTextSize, mCenterTextSize);

        mInnerPaint = new Paint();
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);  // 圆弧
        mInnerPaint.setStyle(Paint.Style.STROKE); // 画笔实心

        mOutPaint = new Paint();
        mOutPaint.setColor(mOutColor);
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);  // 圆弧
        mOutPaint.setStyle(Paint.Style.STROKE); // 画笔实心


        mCenterTextPaint = new Paint();
        mCenterTextPaint.setTextSize(mCenterTextSize);
        mCenterTextPaint.setColor(mCenterTextColor);
        mCenterTextPaint.setAntiAlias(true);
        mCenterTextPaint.setStyle(Paint.Style.STROKE);


        array.recycle();
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

        //1, 画内圆形
        RectF rect = new RectF(mBorderWidth / 2, mBorderWidth / 2, getWidth() - mBorderWidth / 2, getWidth() - mBorderWidth - 2);
        canvas.drawArc(rect, 135, 270, false, mInnerPaint);


        //2, 画外圆形
        if (mMaxProgress == 0) return;

        float sweepAngle = mCurrentProgress / mMaxProgress;
        System.out.println("currentProgress == " + mCurrentProgress + " mMaxProgress == " + mMaxProgress + " sweep == " + sweepAngle * 270);
        canvas.drawArc(rect, 135, sweepAngle * 270, false, mOutPaint);

        //3, 画中间的文字
        String text = mCurrentProgress + "";
        Rect textBounds = new Rect();
        mCenterTextPaint.getTextBounds(text, 0, text.length(), textBounds);
        int dx = getWidth() / 2 - textBounds.width() / 2;

        //基线
        Paint.FontMetrics fontMetrics = mCenterTextPaint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom); // 是一个负值
        int baseLine = getHeight() / 2 + dy;

        canvas.drawText(text, dx, baseLine, mCenterTextPaint);
    }



}
