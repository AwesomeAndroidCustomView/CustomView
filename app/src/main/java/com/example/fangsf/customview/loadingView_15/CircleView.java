package com.example.fangsf.customview.loadingView_15;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fangsf on 2018/5/20.
 * Useful:
 */

public class CircleView extends View {

    private Paint mPaint;

    private int mColor;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 设置抗锯齿
        mPaint.setDither(true);  // 设置仿抖动
    }

    @Override
    protected void onDraw(Canvas canvas) {


        // 求出 圆的中心点
        int cx = getWidth() /2;
        int cy = getHeight() / 2;
        int radius = getWidth() /2;

        canvas.drawCircle(cx, cy, radius, mPaint);

    }

    public void exchangeColor(int color) {
        this.mColor = color;

        mPaint.setColor(color);
        invalidate();
    }

    public int getColor() {
        return mColor;
    }
}
