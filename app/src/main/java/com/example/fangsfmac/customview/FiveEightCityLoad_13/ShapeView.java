package com.example.fangsfmac.customview.FiveEightCityLoad_13;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fangsfmac on 2018/4/23.
 */

public class ShapeView extends View {

    private Paint mPaint;

    private Path mPath;

    //默认的形状是 圆形
    private Shape mCurrentShape = Shape.CIRCLE;

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();

        mPaint.setAntiAlias(false);

    }

    public void exchangeView() {
        switch (mCurrentShape) {
            case CIRCLE: // 画圆形
                mCurrentShape = Shape.SQUARE;

                break;

            case SQUARE: // 画正方形
                mCurrentShape = Shape.TRIANGLE;

                break;

            case TRIANGLE:  // 画 等边三角形
                mCurrentShape = Shape.CIRCLE;

                break;
        }

        invalidate();
    }


    public enum Shape {
        SQUARE, TRIANGLE, CIRCLE
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 只保证是正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        switch (mCurrentShape) {
            case CIRCLE: // 画圆形
                mPaint.setColor(Color.BLUE);
                // 圆点中心x轴,圆点中心y轴,半径,画笔
                canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, mPaint);

                break;

            case SQUARE: // 画正方形
                mPaint.setColor(Color.YELLOW);
                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

                break;

            case TRIANGLE:  // 画 等边三角形
                mPaint.setColor(Color.RED);

                if (mPath == null) {
                    mPath = new Path();
                    mPath.moveTo(getWidth() / 2, 0);
                    // (getWidth()/2 * Math.sqrt(3)) 求出y的位置, 是一个 等边三角形
                    mPath.lineTo(0, (float) (getWidth() / 2 * Math.sqrt(3)));
                    mPath.lineTo(getWidth(), (float) (getWidth() / 2 * Math.sqrt(3)));
                    mPath.close();
                }

                canvas.drawPath(mPath, mPaint);

                break;
        }
    }

    public Shape getCurrentShape() {
        return mCurrentShape;
    }
}
