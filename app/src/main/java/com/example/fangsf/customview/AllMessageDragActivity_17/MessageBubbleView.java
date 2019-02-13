package com.example.fangsf.customview.AllMessageDragActivity_17;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by fangsf on 2018/6/3.
 * Useful:
 */

public class MessageBubbleView extends View {

    // 两个圆的圆形
    private PointF mFixationPoint, mDragPoint;
    // 拖拽圆的半径
    private int mDragRadius = 10;
    // 画笔
    private Paint mPaint;
    // 固定圆的最大半径（初始半径）
    private int mFixationRadiusMax = 7;
    private int mFixationRadiusMin = 3;
    private int mFixationRadius;

    private Bitmap mDragBitmap;

    public MessageBubbleView(Context context) {
        this(context, null);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDragRadius = dip2px(mDragRadius);
        mFixationRadiusMax = dip2px(mFixationRadiusMax);
        mFixationRadiusMin = dip2px(mFixationRadiusMin);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    private int dip2px(int dragRadius) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dragRadius, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 初始化的时候, 没有按下的时候, 不执行方法
        if (mDragPoint == null || mFixationPoint == null) {
            return;
        }

        // 画拖动的圆
        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDragRadius, mPaint);

        // 画贝塞尔 曲线
        Path Bezier = getBezierPath();

        if (Bezier != null) {
            canvas.drawCircle(mFixationPoint.x, mFixationPoint.y, mFixationRadius, mPaint);

            canvas.drawPath(Bezier, mPaint);
        }

        // 将缓存的view, drawBitmap
        if (mDragBitmap != null) {
            canvas.drawBitmap(mDragBitmap, mDragPoint.x - mDragBitmap.getWidth() / 2, mDragPoint.y - mDragBitmap.getHeight() / 2, null);
        }
    }

    /**
     * 画bezier曲线
     *
     * @return
     */
    private Path getBezierPath() {

        // 计算两圆心之间的距离, 当mFixationRadius小于mFixationRadiusMin, 就不再画bezier和固定的圆
        float distance = getPointDistance(mFixationPoint, mDragPoint);
        mFixationRadius = (int) (mFixationRadiusMax - (distance / 14));
        if (mFixationRadius < mFixationRadiusMin) {
            // 不画
            return null;
        }

        Path bezierPath = new Path();

        // 需要计算的点是: 起始点, 终点, 控制点

        //求角a, 求斜率
        float dy = mDragPoint.y - mFixationPoint.y;
        float dx = mDragPoint.x - mFixationPoint.x;
        float tanA = dy / dx; // 斜率
        // 求出角a
        double arcTanA = Math.atan(tanA);

        // 求出 4个点(起始点, 终点)
        float p0x = (float) (mFixationPoint.x + mFixationRadius * Math.sin(arcTanA));
        float p0y = (float) (mFixationPoint.y - mFixationRadius * Math.cos(arcTanA));

        float p1x = (float) (mDragPoint.x + mDragRadius * Math.sin(arcTanA));
        float p1y = (float) (mDragPoint.y - mDragRadius * Math.cos(arcTanA));

        float p2x = (float) (mDragPoint.x - mDragRadius * Math.sin(arcTanA));
        float p2y = (float) (mDragPoint.y + mDragRadius * Math.cos(arcTanA));

        float p3x = (float) (mFixationPoint.x - mFixationRadius * Math.sin(arcTanA));
        float p3y = (float) (mFixationPoint.y + mFixationRadius * Math.cos(arcTanA));

        //求控制点的位置, 画出bezier 曲线
        bezierPath.moveTo(p0x, p0y);

        PointF controlPoint = getControlPoint();
        //控制点, 终点
        bezierPath.quadTo(controlPoint.x, controlPoint.y, p1x, p1y);


        // 第二条 bezier 曲线
        bezierPath.lineTo(p2x, p2y);
        bezierPath.quadTo(controlPoint.x, controlPoint.y, p3x, p3y);
        bezierPath.close();


        return bezierPath;
    }

    private float getPointDistance(PointF point1, PointF point2) {

        return (float) Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) + (point1.y - point2.y) * (point1.y - point2.y));
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                float downX = event.getX();
//                float downY = event.getY();
//
//                // 当在屏幕按下的时候画圆
//                initPoint(downX, downY);
//
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                float moveX = event.getX();
//                float moveY = event.getY();
//                updatePoint(moveX, moveY);
//                break;
//
//            case MotionEvent.ACTION_UP:
//                break;
//
//            default:
//                break;
//        }
//
//        invalidate();
//        return true;
//    }

    public void updatePoint(float moveX, float moveY) {
        mDragPoint.x = moveX;
        mDragPoint.y = moveY;
        invalidate();
    }

    public void initPoint(float downX, float downY) {
        mFixationPoint = new PointF(downX, downY);
        mDragPoint = new PointF(downX, downY);
        invalidate();
    }

    /**
     * 控制点
     *
     * @return
     */
    public PointF getControlPoint() {
        return new PointF((mDragPoint.x + mFixationPoint.x) / 2, (mDragPoint.y + mFixationPoint.y) / 2);
    }

    public static void attach(View view, BubbleMessageTouchListener.BubbleDisappearListener listener) {
        // 设置当前的 的点击事件
        view.setOnTouchListener(new BubbleMessageTouchListener(view, view.getContext(), listener));
    }

    public void setDragBitmap(Bitmap bitmap) {
        this.mDragBitmap = bitmap;
    }

    public void handleActionUp() {

        if (mFixationRadius > mFixationRadiusMin) {
            // 回弹
            ValueAnimator valueAnimator = ObjectAnimator.ofFloat(1);  // 0 -> 1
            valueAnimator.setDuration(250);
            final PointF start = new PointF(mDragPoint.x, mDragPoint.y);
            final PointF end = new PointF(mFixationPoint.x, mFixationPoint.y);

            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float prcent = (float) animation.getAnimatedValue();
                    PointF pointF = BubbleUtils.getPointByPercent(start, end, prcent);// 0 -> 1 ,百分百,求出x,y的pointf

                    updatePoint(pointF.x, pointF.y);
                }
            });
            valueAnimator.setInterpolator(new OvershootInterpolator(3f));
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    // 动画结束的时候, 恢复原来的view
                    if (mListener != null) {
                        mListener.restore();
                    }
                }
            });
            valueAnimator.start();

        } else {
            // 爆炸消失
            if(mListener != null){
                mListener.dismiss(mDragPoint);
            }
        }


    }


    // 气泡拖拽的监听
    public interface MessageBubbleListener {
        // 气泡消失后
        void dismiss(PointF dragPoint);

        void restore();
    }

    private MessageBubbleListener mListener;

    public void setOnMessgeBubbleListener(MessageBubbleListener listener) {
        this.mListener = listener;
    }

}
