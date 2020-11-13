package com.example.fangsf.customview.colorTrackTextView_02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.fangsf.customview.R;

/**
 * Created by fangsfmac on 2018/4/22.
 */

@SuppressLint("AppCompatCustomView")
public class ColorTrackTextView extends TextView {


    private int mOriginColor = Color.BLACK;
    private int mChangeColor = Color.RED;

    private Paint mOriginPaint, mChangePaint;

    private String mText;

    private float mCurrentProgress = 0.0f;

    private Direction mDirection = Direction.DIRECTION_RIGHT_LEFT;

    public enum Direction {
        //从左往右滑动              // 从右往右滑动
        DIRECTION_LEFT_RIGHT, DIRECTION_RIGHT_LEFT
    }

    public void setOriginColor(int originColor) {
        mOriginColor = originColor;
    }

    public void setChangeColor(int changeColor) {
        mChangeColor = changeColor;
    }

    public void setCurrentProgress(float currentProgress) {
        mCurrentProgress = currentProgress;
        invalidate();
    }

    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);

        mOriginColor = array.getColor(R.styleable.ColorTrackTextView_originColor, mOriginColor);
        mChangeColor = array.getColor(R.styleable.ColorTrackTextView_changeColor, mChangeColor);

        mOriginPaint = getPaintByColor(mOriginColor);
        mChangePaint = getPaintByColor(mChangeColor);

        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);  // 注意 这里是继承的textView, 我们需要自定义变换颜色, 不需要父类的

        mText = getText().toString();

        // 切割两部分
        int middle = (int) (getWidth() * mCurrentProgress);

        if (!TextUtils.isEmpty(mText)) {

            if (mDirection == Direction.DIRECTION_RIGHT_LEFT) { // 从右往左滑
                // 其实 是两个文字,重叠在一起了
                // 变色的文字
                drawText(canvas, 0, middle, mChangePaint);
                // 不变色的文字
                drawText(canvas, middle, getWidth(), mOriginPaint);

            } else if (mDirection == Direction.DIRECTION_LEFT_RIGHT) {  // 从左往右边滑动
                // 其实 是两个文字,重叠在一起了
                // 变色的文字
                drawText(canvas, getWidth() - middle, getWidth(), mChangePaint);
                // 不变色的文字
                drawText(canvas, 0, getWidth() - middle, mOriginPaint);
            }
        }
    }

    private void drawText(Canvas canvas, int startX, int endY, Paint paint) {
        // 保存画笔的状态
        canvas.save();
        // 裁剪区域
        canvas.clipRect(startX, 0, endY, getHeight());

        Rect textRect = new Rect();
        paint.getTextBounds(mText, 0, mText.length(), textRect);
        int dx = getWidth() / 2 - textRect.width() / 2;

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom); // 是一个负值
        int baseLine = getHeight() / 2 + dy;

        canvas.drawText(mText, dx, baseLine, paint);
        // 释放画笔状态
        canvas.restore();
    }

    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();

        // 设置抗锯齿
        paint.setAntiAlias(true);

        //防止抖动
        paint.setDither(true);
        paint.setColor(color);
        paint.setTextSize(getTextSize());

        return paint;
    }


}
