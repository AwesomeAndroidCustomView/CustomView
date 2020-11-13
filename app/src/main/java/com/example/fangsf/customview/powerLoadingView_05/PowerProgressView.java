package com.example.fangsf.customview.powerLoadingView_05;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.fangsf.customview.R;


/**
 * Created by fangsfmac on 2018/4/23.
 */

public class PowerProgressView extends View {

    // 内圆的颜色
    private int mInnerColor;
    // 外圆的颜色
    private int mOutColor;
    //外部轮廓线的颜色
    private int mOutlineColor;

    private int mOutlineWidth = 3;

    // 距离外圆的距离
    private int mDrawArcPadding = 13;

    // 默认的圆环的 宽度
    private int mBorderWidth = 20;

    //渐变数组 , 将最后一个颜色值==第一个颜色值,过渡的会更自然一点
    private int[] arcColors = new int[]{
            Color.parseColor("#E7B34A"),
            Color.parseColor("#E3B34C"),
            Color.parseColor("#BDB663"),
            Color.parseColor("#78BD8D"),
            Color.parseColor("#57C1A1"),
            Color.parseColor("#49C2AA"),
            Color.parseColor("#E7B34A")
    };

    // 文字之前的间隔
    private int mTextPadding = 40;

    // 圆环 中间进度的文字
    private int mCenterTextSize;
    private int mDesTextSize;
    // 圆环 中间进度文字的颜色
    private int mCenterTextColor = Color.BLACK;
    // 描述文字的颜色
    private int mDesTextColor = Color.GREEN;

    // 内圆, 外圆, 进度文字的画笔, 描述文字, 轮廓线的颜色
    private Paint mInnerPaint, mOutPaint, mTextPaint, mDesTextPaint, mOutlinePaint;

    // 设置最大进度,和当前的进度
    private float mMaxProgress = 1f;
    private float mCurrentProgress = 0.75f;

    private String mDesText = "默认的描述文字";


    public PowerProgressView(Context context) {
        this(context, null);
    }

    public PowerProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PowerProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PowerProgressView);
        mInnerColor = array.getColor(R.styleable.PowerProgressView_powerInnerColor, mInnerColor);
        mOutColor = array.getColor(R.styleable.PowerProgressView_powerOutColor, mOutColor);
        mBorderWidth = (int) array.getDimension(R.styleable.PowerProgressView_powerBorderWidth, dip2px(mBorderWidth));
        mCenterTextSize = array.getDimensionPixelSize(R.styleable.PowerProgressView_powerCenterTextSize, sp2px(mCenterTextSize));
        mCenterTextColor = array.getColor(R.styleable.PowerProgressView_powerCenterTextColor, mCenterTextColor);
        mOutlineColor = array.getColor(R.styleable.PowerProgressView_powerOutlineColor, mOutlineColor);
        mDesTextSize = array.getDimensionPixelSize(R.styleable.PowerProgressView_powerDesTextSize, sp2px(mDesTextSize));
        mDesTextColor = array.getColor(R.styleable.PowerProgressView_powerDesTextColor, mDesTextColor);
        mTextPadding = array.getDimensionPixelOffset(R.styleable.PowerProgressView_powerTextPadding, (int) dip2px(mTextPadding));
        mDesText = array.getString(R.styleable.PowerProgressView_powerDesText);

        initPaint();

        array.recycle();
    }

    private void initPaint() {
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

        //外圆轮廓线
        mOutlinePaint = new Paint();
        mOutlinePaint.setColor(mOutlineColor);
        mOutlinePaint.setAntiAlias(true);
        mOutlinePaint.setStrokeWidth(mOutlineWidth);
        mOutlinePaint.setStrokeCap(Paint.Cap.ROUND);  // 圆弧
        mOutlinePaint.setStyle(Paint.Style.STROKE);  // 画笔实心


        mTextPaint = new Paint();
        mTextPaint.setColor(mCenterTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mCenterTextSize);
        mTextPaint.setStyle(Paint.Style.STROKE);

        mDesTextPaint = new Paint();
        mDesTextPaint.setColor(mDesTextColor);
        mDesTextPaint.setAntiAlias(true);
        mDesTextPaint.setTextSize(mDesTextSize);
        mDesTextPaint.setStyle(Paint.Style.STROKE);
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

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制圆形
        RectF rectF = new RectF(mBorderWidth / 2 + mDrawArcPadding, mBorderWidth / 2 + mDrawArcPadding,
                getWidth() - mBorderWidth / 2 - mDrawArcPadding, getHeight() - mBorderWidth / 2 - mDrawArcPadding);
        canvas.drawArc(rectF, 0, 360, false, mInnerPaint);

        if (mCurrentProgress == 0) {
            return;
        }

        // 绘制外圆
        // 环形颜色填充
        SweepGradient sweepGradient = new SweepGradient(getWidth() / 2, getHeight() / 2, arcColors, null);
        mOutPaint.setShader(sweepGradient);

        float sweepAngle = mCurrentProgress / mMaxProgress;
        canvas.drawArc(rectF, -90, 360 * sweepAngle, false, mOutPaint);

        //绘制外圆的轮廓
        RectF outlineRectF = new RectF(mOutlineWidth / 2, mOutlineWidth / 2, getWidth() - mOutlineWidth / 2,
                getHeight() - mOutlineWidth / 2);
        canvas.drawArc(outlineRectF, 0, 360, false, mOutlinePaint);

        // 绘制文字
        String text = (int) mCurrentProgress + "%";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), textBounds);
        int dx = getWidth() / 2 - textBounds.width() / 2;

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);

        // 基线
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, dx, baseLine + mTextPadding, mTextPaint);


        // 描述文字
        Rect desBounds = new Rect();
        mDesTextPaint.getTextBounds(mDesText, 0, mDesText.length(), desBounds);
        int desDx = getWidth() / 2 - desBounds.width() / 2;
        canvas.drawText(mDesText, desDx, baseLine - mTextPadding, mDesTextPaint);

    }

    public synchronized void setMaxProgress(float maxProgress) {
        mMaxProgress = maxProgress;
    }

    public synchronized void setCurrentProgress(float currentProgress) {
        mCurrentProgress = currentProgress;
        invalidate();
    }

}
