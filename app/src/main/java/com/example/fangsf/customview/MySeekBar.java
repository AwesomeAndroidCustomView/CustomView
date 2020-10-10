package com.example.fangsf.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.SeekBar;

/**
 * Created by fangsf on 2019-09-05
 * Useful:
 */
@SuppressLint("AppCompatCustomView")
public class MySeekBar extends SeekBar {

    private Paint mTextPaint;
    private int mTextMarginRight;
    private Bitmap mLeftIconBitmap;

    /**
     * 左边Icon
     */
    private int mLeftIconRes;
    private String mRightText;

    public MySeekBar(Context context) {
        this(context, null);
    }

    public MySeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MySeekBar);
        mLeftIconRes = array.getResourceId(R.styleable.MySeekBar_leftIcon, 0);
        mRightText = array.getString(R.styleable.MySeekBar_rightText);
        mTextMarginRight = array.getDimensionPixelOffset(R.styleable.MySeekBar_textMarginRight, dip2px(30));

        mLeftIconBitmap = BitmapFactory.decodeResource(context.getResources(), mLeftIconRes);

        mTextPaint = new Paint();
        mTextPaint.setColor(getResources().getColor(R.color.blue));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(33);
        mTextPaint.setStyle(Paint.Style.STROKE);

        array.recycle();
    }

    private int dip2px(int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLeftIcon(canvas);

        drawRightText(canvas);

    }

    private void drawLeftIcon(Canvas canvas) {
        if (mLeftIconBitmap != null) {
            canvas.drawBitmap(mLeftIconBitmap, 20, 0, null);
        }
    }

    private void drawRightText(Canvas canvas) {
        String text = mRightText + "";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), textBounds);
        int dx = (getWidth() - mTextMarginRight) - textBounds.width() / 2;
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        // 基线
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, dx, baseLine, mTextPaint);
    }


    public void setRightText(String text) {
        this.mRightText = text;
        invalidate();
    }

}
