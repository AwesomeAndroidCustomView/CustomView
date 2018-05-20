package com.example.fangsfmac.customview.loadingView_15;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by fangsf on 2018/5/20.
 * Useful:
 */

public class LoadingView extends RelativeLayout {

    private static final String TAG = LoadingView.class.getSimpleName();

    // 3个圆
    private CircleView mLeftView, mRightView, mMiddleView;

    //  动画是否 暂停
    private boolean mIsStopAni = false;

    // 位移的距离
    private int TRANSLATION_DISTANCE = 23;
    // 位移的动画
    private final int ANIMATION_DURATION = 300;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {
        setBackgroundColor(Color.WHITE);

        TRANSLATION_DISTANCE = dip2px(TRANSLATION_DISTANCE);

        mLeftView = getCircleView(context);
        mLeftView.exchangeColor(Color.RED);
        mRightView = getCircleView(context);
        mRightView.exchangeColor(Color.GREEN);
        mMiddleView = getCircleView(context);
        mMiddleView.exchangeColor(Color.BLUE);

        addView(mLeftView);
        addView(mRightView);
        addView(mMiddleView);


        // leftView, 和rightView, 往两边位移的动画
        expandAni();
    }

    private void expandAni() {
        if (mIsStopAni) {
            return;
        }
        Log.i(TAG, "expandAni: ");
        ObjectAnimator leftViewAni = ObjectAnimator.ofFloat(mLeftView, "translationX", 0, -TRANSLATION_DISTANCE);
        ObjectAnimator rightViewAni = ObjectAnimator.ofFloat(mRightView, "translationX", 0, TRANSLATION_DISTANCE);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION);
        set.playTogether(leftViewAni, rightViewAni);
        // 外两边位移,越来越慢
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                innerAni();
            }
        });
        set.start();

    }

    private void innerAni() {
        if (mIsStopAni) {
            return;
        }
        ObjectAnimator leftViewAni = ObjectAnimator.ofFloat(mLeftView, "translationX", -TRANSLATION_DISTANCE, 0);
        ObjectAnimator rightViewAni = ObjectAnimator.ofFloat(mRightView, "translationX", TRANSLATION_DISTANCE, 0);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION);
        set.playTogether(leftViewAni, rightViewAni);
        // 往里位移越来越快
        set.setInterpolator(new AccelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                // 切换颜色, 左边颜色给中间的,中间的颜色给右边的,右边的颜色给左边的
                int leftColor = mLeftView.getColor();
                int rightColor = mRightView.getColor();
                int middleColor = mMiddleView.getColor();

                mLeftView.exchangeColor(middleColor);
                mMiddleView.exchangeColor(rightColor);
                mRightView.exchangeColor(leftColor);

                expandAni();
            }
        });
        set.start();
    }

    private CircleView getCircleView(Context c) {
        CircleView circleView = new CircleView(c);
        // 将圆放在中心的位置, 设置圆的大小
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dip2px(12), dip2px(12));
        params.addRule(CENTER_IN_PARENT);  // 垂直于屏幕中间

        circleView.setLayoutParams(params);
        return circleView;
    }

    private int dip2px(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, getResources().getDisplayMetrics());
    }

    /**
     * 性能优化, 当 gone,INVISIBLE 的时候, 不要再执行动画了,VISIBLE 再次执行动画
     *
     * @param visibility
     */
    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);

        if (visibility == GONE || visibility == INVISIBLE) {
            mIsStopAni = true;
        }

        if (visibility == VISIBLE) {
            mIsStopAni = false;
            expandAni();
        }
    }
}
