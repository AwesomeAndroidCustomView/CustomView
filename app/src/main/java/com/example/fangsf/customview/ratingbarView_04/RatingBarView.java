package com.example.fangsf.customview.ratingbarView_04;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.fangsf.customview.R;

/**
 * Created by Femto-iMac-003 on 2018/4/26.
 */

public class RatingBarView extends View {

    private Bitmap mStarNormalBitmap, mStarFocusBitmap;

    private int mGradeNumber;

    //当前被选中的星星的个数
    private int mCurrentGrade = 3;

    public RatingBarView(Context context) {
        this(context, null);
    }

    public RatingBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatingBarView);

        int starNormal = array.getResourceId(R.styleable.RatingBarView_starNormalBg, 0);
        if (starNormal == 0) {
            throw new RuntimeException("RatingBarView 请设置starNormalBg");
        }

        // 只能是图片, svg的图片不能读取出来
        mStarNormalBitmap = BitmapFactory.decodeResource(context.getResources(), starNormal);

        int starFocus = array.getResourceId(R.styleable.RatingBarView_starFocusBg, 0);
        if (starFocus == 0) {
            throw new RuntimeException("RatingBarView 请设置starNormalBg");
        }

        mStarFocusBitmap = BitmapFactory.decodeResource(context.getResources(), starFocus);

        mGradeNumber = array.getInt(R.styleable.RatingBarView_gradeNumber, mGradeNumber);

        array.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 高度,和宽度,就是 图片的宽高
        int height = mStarNormalBitmap.getHeight() + getPaddingTop() + getBottom();
        int width = mStarNormalBitmap.getWidth() * mGradeNumber + (getPaddingLeft() * (mGradeNumber + 1)); // 每个星星之间有空隙,需要计算
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mGradeNumber; i++) {
            int left = mStarNormalBitmap.getWidth() * i;

            if (mCurrentGrade > i) { // 选中的个数
                // 绘制 间距和 , 信息在中间的位置
                canvas.drawBitmap(mStarFocusBitmap, left + (getPaddingLeft() * (i + 1)), (getPaddingTop()) / 2, null);
            } else {
                // 绘制 间距和 , 信息在中间的位置
                canvas.drawBitmap(mStarNormalBitmap, left + (getPaddingLeft() * (i + 1)), (getPaddingTop()) / 2, null);
            }

        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            // 此处要减少onDraw() 的次数

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                int currentGrade = (int) (event.getX() / (mStarNormalBitmap.getWidth() + getPaddingLeft()) + 1);
                Log.i("tag", "onTouchEvent: currentGrade  " + currentGrade);

                // 判断触摸的范围
                if (currentGrade < 0) {
                    mCurrentGrade = 0;
                }
                if (currentGrade > mGradeNumber) {
                    mCurrentGrade = mGradeNumber;
                }

                //减少ondraw() 的次数
                if (currentGrade == mCurrentGrade) {
                    return true;
                }

                mCurrentGrade = currentGrade;

                invalidate();

                break;
        }


        return true;  // 消费action_down的事件
    }
}
