package com.example.fangsf.customview.yahuLoadingView_20;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;

import com.example.fangsf.customview.R;

/**
 * Created by fangsfmac on 2018/6/14.
 */

public class LoadingView extends View {

    private static final String TAG = "LoadingView";
    // 旋转动画执行的时间
    private final long ROTATION_ANIMATION_TIME = 1400;
    // 小圆的颜色列表
    private int[] mCircleColors;

    // 空心圆初始半径
    private float mHoleRadius = 0F;

    // 初始化参数设置
    private boolean mInitParams = false;
    private Paint mPaint;

    // 大圆里面包含很多小圆的半径 - 整宽度的 1/4
    private float mRotationRadius;
    // 每个小圆的半径 - 大圆半径的 1/8
    private float mCircleRadius;
    // 中心点
    private int mCenterX, mCenterY;
    // 整体的颜色背景
    private int mSplashColor = Color.WHITE;
    // 屏幕对角线的一半
    private float mDiagonalDist;
    // 当前大圆旋转的角度（弧度）
    private float mCurrentRotationAngle = 0F;

    // 3 中状态的动画
    private LoadingState mLoadingStatus;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取 colors 中 小圆 颜色数组
        mCircleColors = getResources().getIntArray(R.array.splash_circle_colors);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (!mInitParams) {
            initParams();
            mInitParams = true;
        }


        // 开始动画, 旋转
        if (mLoadingStatus == null) {
            mLoadingStatus = new RotationState();
        }


        mLoadingStatus.draw(canvas); // 重复执行画圆的动作
    }

    /**
     * 结束动画
     */
    public void disappear() {
        // 关闭旋转动画,
        // 开始聚合动画
        if (mLoadingStatus instanceof RotationState) {
            ((RotationState) mLoadingStatus).cancel();
        }

        // 开始聚合动画
        mLoadingStatus = new MergeState();
    }

    /**
     * 聚合动画
     */
    class MergeState extends LoadingState {
        private ValueAnimator mAnimator;

        // 和旋转动画, 基本一致, 只需要改变 r (半径的值就可以了), 不需要改变角度
        public MergeState() {
            mAnimator = ObjectAnimator.ofFloat(mRotationRadius, 0);
            mAnimator.setDuration(ROTATION_ANIMATION_TIME / 2);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotationAngle = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            // 开始的时候向后然后向前甩
            mAnimator.setInterpolator(new AnticipateInterpolator(3f));

            // 动画结束的时候开启另外一个 展开动画
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                     mLoadingStatus = new ExpendState();
                }
            });


            mAnimator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(mSplashColor); // 画白色的背景

            // 开始画 6 个小圆
            double percentAngle = Math.PI * 2 / mCircleColors.length; // 每分小圆 角度
            for (int i = 0; i < mCircleColors.length; i++) {
                mPaint.setColor(mCircleColors[i]);
                // 当前的角度 = 初始角度 + 旋转的角度
                double currentAngle = percentAngle * i;
                int cx = (int) (mCenterX + mCurrentRotationAngle * Math.cos(currentAngle));
                int cy = (int) (mCenterY + mCurrentRotationAngle * Math.sin(currentAngle));
                canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
            }
        }
    }

    class ExpendState extends LoadingState {
        private ValueAnimator mAnimator;

        // 开启展开的动画的圆, 是一个 空心圆
        public ExpendState() {
            mAnimator = ObjectAnimator.ofFloat(0, mDiagonalDist);
            mAnimator.setDuration(ROTATION_ANIMATION_TIME / 2);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mHoleRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            mAnimator.start();
        }

        @Override
        public void draw(Canvas canvas) {
             // 画一个 展开 的空心圆  (strokeWidth 刚一开始的时候, 全是strokeWidth宽度值, 为白色)
            float strokeWidth = mDiagonalDist  - mHoleRadius;// 画笔的宽度
            mPaint.setStrokeWidth(strokeWidth);
            mPaint.setColor(mSplashColor);
            mPaint.setStyle(Paint.Style.STROKE);

            // strokeWidth 慢慢减小  (radius  计算 要加上 strokeWidth 距离)
            float radius = strokeWidth / 2 + mHoleRadius; // 慢慢增大
            canvas.drawCircle(mCenterX, mCenterY, radius, mPaint);

        }
    }

    /**
     * 旋转动画
     */
    public class RotationState extends LoadingState {
        private ValueAnimator mAnimator;

        public RotationState() {
            mAnimator = ObjectAnimator.ofFloat(0f, 2 * (float) Math.PI); // 计算的是弧度
            mAnimator.setDuration(ROTATION_ANIMATION_TIME);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotationAngle = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            mAnimator.setInterpolator(new LinearInterpolator()); // 线性差值器
            mAnimator.setRepeatCount(-1); // 无限制执行
            mAnimator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(mSplashColor); // 画白色的背景

            // 开始画 6 个小圆
            double percentAngle = Math.PI * 2 / mCircleColors.length; // 每分小圆 角度
            for (int i = 0; i < mCircleColors.length; i++) {
                mPaint.setColor(mCircleColors[i]);
                // 当前的角度 = 初始角度 + 旋转的角度
                double currentAngle = percentAngle * i + mCurrentRotationAngle;
                int cx = (int) (mCenterX + mRotationRadius * Math.cos(currentAngle));
                int cy = (int) (mCenterY + mRotationRadius * Math.sin(currentAngle));
                canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
            }
        }

        public void cancel() {
            mAnimator.cancel();
        }
    }

    /**
     * 初始化参数
     */
    private void initParams() {
        Log.i(TAG, " getMeasuredWidth: " + getMeasuredWidth() + " getMeasuredHeight " + getMeasuredHeight());

        mRotationRadius = getMeasuredWidth() / 4;
        mCircleRadius = mRotationRadius / 8; //相当于大圆的 1/8
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mCenterX = getMeasuredWidth() / 2;
        mCenterY = getMeasuredHeight() / 2;

        mDiagonalDist = (float) Math.sqrt(mCenterX * mCenterX + mCenterY * mCenterY); // x的平方 + y 的平方, 开根号
    }


    abstract class LoadingState {
        public abstract void draw(Canvas canvas);
    }

}
