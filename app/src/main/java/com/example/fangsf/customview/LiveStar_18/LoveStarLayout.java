package com.example.fangsf.customview.LiveStar_18;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.fangsf.customview.R;

import java.util.Random;

/**
 * Created by Femto-iMac-003 on 2018/6/9.
 */

public class LoveStarLayout extends RelativeLayout {

    private int mWidth, mHeight;
    private Drawable mRed, mYellow, mBlue;
    private Drawable[] mDrawables;
    private Random mRandom = new Random();
    private int mDrawableWidth, mDrawableHeight;

    private Interpolator[] mInterpolator;
    private LayoutParams mParams;

    public LoveStarLayout(Context context) {
        this(context, null);
    }

    public LoveStarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveStarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    private void init() {
        initDrawable();

        initIntercept();

        // 设置宽高
        //添加到 最底部的位置, 添加规则
        mParams = new LayoutParams(mDrawableWidth, mDrawableHeight);
        mParams.addRule(CENTER_HORIZONTAL, TRUE);
        mParams.addRule(ALIGN_PARENT_BOTTOM, TRUE);
    }

    private void initIntercept() {
        mInterpolator = new Interpolator[]{new AccelerateDecelerateInterpolator(), new AccelerateInterpolator(),
                new DecelerateInterpolator(), new LinearInterpolator()};
    }

    private void initDrawable() {
        mRed = getResources().getDrawable(R.drawable.pl_red);
        mBlue = getResources().getDrawable(R.drawable.pl_blue);
        mYellow = getResources().getDrawable(R.drawable.pl_yellow);

        mDrawables = new Drawable[]{mRed, mBlue, mYellow};

        mDrawableWidth = mRed.getIntrinsicWidth();
        mDrawableHeight = mRed.getIntrinsicHeight();
    }

    public void addLove() {
        ImageView loveIv = new ImageView(getContext());
        loveIv.setImageDrawable(mDrawables[mRandom.nextInt(mDrawables.length - 1)]);
        loveIv.setLayoutParams(mParams);

        // 添加到布局当中
        addView(loveIv);

        // 开启动画 集合
        AnimatorSet animatorSet = getAnimatorSet(loveIv);
        animatorSet.start();
    }

    private AnimatorSet getAnimatorSet(ImageView loveIv) {
        AnimatorSet allAnimatorSet = new AnimatorSet();

        // 开启alpha 动画
        ObjectAnimator alphaAni = ObjectAnimator.ofFloat(loveIv, "alpha", 0.3f, 1f);
        // 开启x,y 轴的缩放动画
        ObjectAnimator scaleXAni = ObjectAnimator.ofFloat(loveIv, "scaleX", 0.2f, 1f);
        ObjectAnimator scaleYAni = ObjectAnimator.ofFloat(loveIv, "scaleY", 0.2f, 1f);

        // 设置动画集合
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.setTarget(loveIv);
        set.playTogether(alphaAni, scaleXAni, scaleYAni);

        // 动画集合执行完毕后,执行运动路径动画
        allAnimatorSet.playSequentially(set, getBezierAnimator(loveIv));

        return allAnimatorSet;
    }

    /**
     * 路径动画
     *
     * @param loveIv
     * @return
     */
    private Animator getBezierAnimator(final ImageView loveIv) {

        final PointF point0 = new PointF(mWidth / 2 - mDrawableWidth / 2, mHeight - mDrawableHeight);
        PointF point1 = getPoint(1); // 是一个随机的数, 确保p2的y值, 要大于p1 的y 值
        PointF point2 = getPoint(2);
        PointF point3 = new PointF(mRandom.nextInt(mWidth - mDrawableWidth), 0);

        LoveTypeEvaluator loveTypeEvaluator = new LoveTypeEvaluator(point1, point2);

        ValueAnimator bezierAnimator = ObjectAnimator.ofObject(loveTypeEvaluator, point0, point3);

        //随机差值器
        bezierAnimator.setInterpolator(mInterpolator[mRandom.nextInt(mInterpolator.length - 1)]);
        bezierAnimator.setDuration(3000);
        bezierAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF updatePointf = (PointF) animation.getAnimatedValue();

                // 更新图片的x,y 的值
                loveIv.setX(updatePointf.x);
                loveIv.setY(updatePointf.y);

                // 设置透明度
                float t = animation.getAnimatedFraction();  // 也就是 t 的值, 0 -> 1,
                loveIv.setAlpha(1 - (t + 0.1f));
            }
        });

        return bezierAnimator;
    }

    private PointF getPoint(int index) {
        return new PointF(mRandom.nextInt(mWidth - mDrawableWidth),
                mRandom.nextInt(mHeight / 2) + (index - 1) * mHeight / 2);
    }
}
