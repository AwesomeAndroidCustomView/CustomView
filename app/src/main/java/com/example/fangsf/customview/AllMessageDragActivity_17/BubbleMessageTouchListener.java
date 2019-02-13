package com.example.fangsf.customview.AllMessageDragActivity_17;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.fangsf.customview.R;
import com.example.fangsf.customview.statusBar_11.SystemBarHelper;

/**
 * Created by fangsf on 2018/6/7.
 * Useful:
 */

public class BubbleMessageTouchListener implements View.OnTouchListener, MessageBubbleView.MessageBubbleListener {

    // 原始需要拖动爆炸的view
    private View mStaticView;
    private Context mContext;
    private BubbleDisappearListener mListener;

    // 手动创建  消息气泡view
    private MessageBubbleView mMessageBubbleView;


    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;

    // 爆炸动画 (容器view)
    private FrameLayout mBombFrame;
    private ImageView mBombImage;

    // 通知外面的listener

    public BubbleMessageTouchListener(View view, Context context, BubbleDisappearListener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mStaticView = view;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        // 手动创建view, 添加到windowManger 中, 并设置事件
        mMessageBubbleView = new MessageBubbleView(context);
        mMessageBubbleView.setOnMessgeBubbleListener(this);

        mParams = new WindowManager.LayoutParams();
        mParams.format = PixelFormat.TRANSPARENT; // 设置 windowManger 透明

        mBombFrame = new FrameLayout(context);
        mBombImage = new ImageView(context);
        mBombImage.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mBombFrame.addView(mBombImage);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        // 1, 按下,将当前的view隐藏起来, 复制一份view在windowManager上 (这样才可以拖动到状态栏中)
        // 2, 拖动的时候 更新位置
        // 3, 抬起的时候, 如果固定的view,小于一定的值,  则开启消失动画, 否则开启回弹动画,(将原来的view 再次显示出来)

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mWindowManager.addView(mMessageBubbleView, mParams);

                Bitmap bitmap = getBitmapByView(mStaticView);

                int[] location = new int[2];
                mStaticView.getLocationOnScreen(location);
                mMessageBubbleView.initPoint(location[0] + mStaticView.getWidth() / 2, location[1] + mStaticView.getHeight() / 2 - SystemBarHelper.getBarHeight((Activity) mContext));

                mMessageBubbleView.setDragBitmap(bitmap);  // 将缓存的view, 画一个bitmap(其实就是将拖动的圆, 转换成了一个bitmap了)

                // 将原始的 气泡view 隐藏
                mStaticView.setVisibility(View.INVISIBLE);
                break;

            case MotionEvent.ACTION_MOVE:
                mMessageBubbleView.updatePoint(event.getRawX(), event.getRawY() - SystemBarHelper.getBarHeight((Activity) mContext));
                break;

            case MotionEvent.ACTION_UP:
                //  3, 处理抬起的事件
                mMessageBubbleView.handleActionUp();
                break;

            default:
                break;
        }

        return true;
    }

    private Bitmap getBitmapByView(View view) {
        // 得到缓存的view, 转换成bitmap
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    @Override
    public void dismiss(PointF pointF) {
        // 爆炸消失,
        mWindowManager.removeView(mMessageBubbleView);

        // windowManager 中添加爆炸动画
        mWindowManager.addView(mBombFrame, mParams);
        mBombImage.setBackgroundResource(R.drawable.anim_bubble_pop);
        AnimationDrawable drawableAni = (AnimationDrawable) mBombImage.getBackground();

        // 设置view 消失的位置
        mBombImage.setX(pointF.x - drawableAni.getIntrinsicWidth() / 2);
        mBombImage.setY(pointF.y - drawableAni.getIntrinsicHeight() / 2);

        drawableAni.start();

        // 动画结束后,通知外面
        mBombImage.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWindowManager.removeView(mBombFrame);

                if (mListener != null) {
                    mListener.dismiss(mStaticView);
                }
            }
        }, getAnimationDrawableTime(drawableAni));

    }

    private long getAnimationDrawableTime(AnimationDrawable drawable) {
        int numberOfFrames = drawable.getNumberOfFrames();
        long time = 0;
        for (int i = 0; i < numberOfFrames; i++) {
            time += drawable.getDuration(i);
        }

        return time;
    }

    @Override
    public void restore() {
        // 移除拖动的view, 获取原来的view显示
        mWindowManager.removeView(mMessageBubbleView);

        mStaticView.setVisibility(View.VISIBLE);
    }

    /**
     * 通知的外面的view
     */
    public interface BubbleDisappearListener {
        void dismiss(View view);
    }

}
