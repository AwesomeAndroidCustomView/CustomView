package com.example.fangsf.customview.letterIndexView_06;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.fangsf.customview.R;

/**
 * Created by fangsfmac on 2018/5/1.
 */

public class LetterIndexView extends View {

    private String mLetters[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    // 默认颜色的画笔, 和高亮颜色文字的画笔
    private Paint mDefaultPaint;
    private Paint mSelectedPaint;

    // 未选中文字的颜色,和选中文字的颜色
    private int mDefaultColor = Color.GRAY;
    private int mSelectedColor = Color.BLUE;

    private int mLetterTextSize = 12;
    private int mLetterWidth;
    private int mLetterHeight;
    private String mCurrentSelectedLetter = "";

    public LetterIndexView(Context context) {
        this(context, null);
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LetterIndexView);
        mDefaultColor = array.getColor(R.styleable.LetterIndexView_letterDefaultColor, mDefaultColor);
        mSelectedColor = array.getColor(R.styleable.LetterIndexView_letterSelectedColor, mSelectedColor);
        // 画笔 的 size, 和直接 设置 textsize 好像不同
        mLetterTextSize = array.getDimensionPixelSize(R.styleable.LetterIndexView_letterTextSize, dip2px(mLetterTextSize));


        mDefaultPaint = new Paint();
        mDefaultPaint.setColor(mDefaultColor);
        mDefaultPaint.setAntiAlias(true);
        mDefaultPaint.setTextSize(mLetterTextSize);

        mSelectedPaint = new Paint();
        mSelectedPaint.setColor(mSelectedColor);
        mSelectedPaint.setAntiAlias(true);
        mSelectedPaint.setTextSize(mLetterTextSize);

        array.recycle();
    }

    private int dip2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 宽度 = paddingleft + paddingRight + 一个字母的宽度
        mLetterWidth = (int) mDefaultPaint.measureText("A");

        int width = mLetterWidth + getPaddingLeft() + getPaddingRight();

        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //一个字母所占的高度
        mLetterHeight = getHeight() / mLetters.length;

        for (int i = 0; i < mLetters.length; i++) {

            // 每个 字母的width 可能会不一样, 需要重新计算
            int letterX = (int) (getWidth() / 2 - mDefaultPaint.measureText(mLetters[i]) / 2);

            Paint.FontMetrics fontMetrics = mDefaultPaint.getFontMetrics();
            int dy = (int) (((fontMetrics.bottom - fontMetrics.bottom) / 2) - fontMetrics.bottom);

            // 每个字母当前位置的 所在的高度
            int itemHeight = mLetterHeight + (mLetterHeight * i);
            int baseLine = itemHeight + dy;

            if (mCurrentSelectedLetter.equals(mLetters[i])) {
                // 高亮
                canvas.drawText(mLetters[i], letterX, baseLine, mSelectedPaint);
            } else {
                // 动态改变,x,y 的位置
                canvas.drawText(mLetters[i], letterX, baseLine, mDefaultPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            //case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                // 判断所选中的位置, 当前位置高亮显示
                float moveY = event.getY();

                //当前选中 字母的位置
                int touchIndex = (int) (moveY / mLetterHeight);


                if (mSideBarTouchListener != null) {
                    mSideBarTouchListener.onTouch(mCurrentSelectedLetter, true);
                }

                if (touchIndex < 0) {
                    touchIndex = 0;
                }

                if (touchIndex > mLetters.length - 1) {
                    touchIndex = mLetters.length - 1;
                }

                if (mCurrentSelectedLetter.equals(mLetters[touchIndex])) {
                    return true;
                }

                mCurrentSelectedLetter = mLetters[touchIndex];
                invalidate();  // 注: 需要减少 onDraw() 的次数

                break;

            case MotionEvent.ACTION_UP:
                if (mSideBarTouchListener != null) {
                    mSideBarTouchListener.onTouch(mCurrentSelectedLetter, false);
                }

                break;

        }

        return true;
    }

    private SideBarTouchListener mSideBarTouchListener;

    public void setOnSideTouchListener(SideBarTouchListener listener) {
        this.mSideBarTouchListener = listener;
    }

    public interface SideBarTouchListener {
        // 当前的文字, 文字是否按下了
        void onTouch(String letter, boolean isTouch);
    }

}
