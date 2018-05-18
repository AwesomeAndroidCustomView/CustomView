package com.example.fangsfmac.customview.FiveEightCityLoad_13;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.example.fangsfmac.customview.R;

import static android.content.ContentValues.TAG;

/**
 * Created by fangsf on 2018/5/18.
 * Useful:
 */

public class LoadingView extends LinearLayout {

    // 改变形状的view
    private ShapeView mShapeView;
    // 底部阴影的view
    private View mShadowView;
    private int mTranslationY;

    private boolean mIsStopAni = false;

    // 动画执行的时间
    private final int ANIMATOR_DURATION = 950;


    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        mTranslationY = dip2px(80);
        initLayout();

    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    private void initLayout() {
        // 组合控件,
        inflate(getContext(), R.layout.loading_layout, this);
        mShapeView = findViewById(R.id.shapeView);
        mShadowView = findViewById(R.id.shadowView);


//        startAnimator();  // 默认是在onCreate() 方法中执行,在布局加载完就执行

        post(new Runnable() {
            @Override
            public void run() {
                startDownAnimator(); // 在onResume() 方法后执行
            }
        });


    }

    private void startDownAnimator() {
        if (mIsStopAni) {
            return;
        }
        // 开启形状的view往下位移的动画
        ObjectAnimator shapeTranslationYAni = ObjectAnimator.ofFloat(mShapeView, "translationY", 0, mTranslationY);

        //开启阴影缩放的动画,缩小的动画
        ObjectAnimator shadowScaleXAni = ObjectAnimator.ofFloat(mShadowView, "scaleX", 1f, 0.3f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(shapeTranslationYAni, shadowScaleXAni);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(ANIMATOR_DURATION);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 动画结束后开启往上移动的动画
                startUpAnimator();
            }

        });
        //需要在 addListener 后面执行
        animatorSet.start();

    }

    /**
     * 动画往上位移, 需要改变形状,并且需要旋转
     */
    private void startUpAnimator() {
        if (mIsStopAni) {
            return;
        }
        Log.i(TAG, "startUpAnimator");

        ObjectAnimator shapeTranslationYAni = ObjectAnimator.ofFloat(mShapeView, "translationY", mTranslationY, 0);
        ObjectAnimator shadowScaleXAni = ObjectAnimator.ofFloat(mShadowView, "scaleX", 0.3f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(shapeTranslationYAni, shadowScaleXAni);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.setDuration(ANIMATOR_DURATION);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 动画结束后开启往上移动的动画
                startDownAnimator();
            }


            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                // 当动画开始往上的时候,切换形状
                mShapeView.exchangeView();
                // 开启旋转的动画
                setRotationAnimator();
            }
        });
        //需要在 addListener 后面执行
        animatorSet.start();
    }

    /**
     * 设置形状的旋转动画
     */
    private void setRotationAnimator() {
        if (mIsStopAni) {
            return;
        }
        ObjectAnimator ShapeViewRotationAni = null;
        switch (mShapeView.getCurrentShape()) {
            case CIRCLE:
                break;

            case TRIANGLE:
                //三角形状
                ShapeViewRotationAni = ObjectAnimator.ofFloat(mShapeView, "rotation", 0, -120);
                ShapeViewRotationAni.setDuration(ANIMATOR_DURATION);
                ShapeViewRotationAni.start();
                break;

            case SQUARE:
                // 正方形
                ShapeViewRotationAni = ObjectAnimator.ofFloat(mShapeView, "rotation", 0, 90);
                ShapeViewRotationAni.setDuration(ANIMATOR_DURATION);
                ShapeViewRotationAni.start();
                break;

            default:
                break;
        }
    }

    /**
     * 性能优化
     *
     * @param visibility
     */
    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(View.VISIBLE);  // 性能优化: 每次GONE,的时候,都会重新计算布局, 直接给他返回VISIBLE, 不计算

        //在mainActivity,中销毁布局或者是setVisibility(不显示) , 动画并没有停止下来, 手动停止动画
        mShapeView.clearAnimation();
        mShadowView.clearAnimation();

        // 并且,把loadingView 移除
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
            removeAllViews();
        }

        mIsStopAni = true;  // 当activity 不显示的时候, 将不再执行动画,将动画停止
    }
}
