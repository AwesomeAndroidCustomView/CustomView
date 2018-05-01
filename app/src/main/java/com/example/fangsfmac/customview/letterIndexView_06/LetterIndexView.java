package com.example.fangsfmac.customview.letterIndexView_06;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.fangsfmac.customview.R;

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



    }
}
