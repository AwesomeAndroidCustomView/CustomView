package com.example.fangsfmac.customview.ratingbarView_04;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.fangsfmac.customview.R;

/**
 * Created by Femto-iMac-003 on 2018/4/26.
 */

public class RatingBarView extends View {

    private Bitmap mStarNormalBitmap, mStarFocusBitmap;

    private int mGradeNumber ;

    public RatingBarView(Context context) {
        this(context,null);
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

        mStarNormalBitmap = BitmapFactory.decodeResource(getResources(), starNormal);

        int starFocus = array.getResourceId(R.styleable.RatingBarView_starFocusBg, 0);
        if (starFocus == 0) {
            throw new RuntimeException("RatingBarView 请设置starNormalBg");
        }

        mStarFocusBitmap = BitmapFactory.decodeResource(getResources(), starFocus);

        mGradeNumber = array.getInteger(R.styleable.RatingBarView_gradeNumber, mGradeNumber);

        array.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = mStarNormalBitmap.getWidth();

        for (int i = 0; i < mGradeNumber; i++) {

            int left = width * i;

            canvas.drawBitmap(mStarNormalBitmap, left, 0, null);
        }

    }
}
