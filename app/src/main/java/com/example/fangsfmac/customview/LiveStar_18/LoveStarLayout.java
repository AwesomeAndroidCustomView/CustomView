package com.example.fangsfmac.customview.LiveStar_18;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.fangsfmac.customview.R;

import java.util.Random;

/**
 * Created by Femto-iMac-003 on 2018/6/9.
 */

public class LoveStarLayout extends RelativeLayout {

    private Drawable mRed, mYellow, mBlue;
    private Drawable[] mDrawables;
    private Random mRandom = new Random();
    private int mDrawableWidth, mDrawableHeight;

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

    private void init() {
        initDrawable();


        // 设置宽高
        //添加到 最底部的位置, 添加规则
        mParams = new LayoutParams(mDrawableWidth, mDrawableHeight);
        mParams.addRule(CENTER_HORIZONTAL, TRUE);
        mParams.addRule(ALIGN_PARENT_BOTTOM, TRUE);
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

        // 开启alpha 动画
        ObjectAnimator alphaAni = ObjectAnimator.ofFloat(loveIv, "alpha", 0.3f, 1f);
        // 开启x,y 轴的缩放动画
        ObjectAnimator scaleXAni = ObjectAnimator.ofFloat(loveIv, "scaleX", 0.2f, 1f);
        ObjectAnimator scaleYAni = ObjectAnimator.ofFloat(loveIv, "scaleY", 0.2f, 1f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.setTarget(loveIv);
        set.playTogether(alphaAni, scaleXAni, scaleYAni);

        return set;
    }
}
