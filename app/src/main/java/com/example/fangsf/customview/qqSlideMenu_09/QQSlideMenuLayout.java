package com.example.fangsf.customview.qqSlideMenu_09;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import com.example.fangsf.customview.R;

import static android.content.ContentValues.TAG;

/**
 * Created by fangsf on 2018/5/9.
 * Useful:
 */

public class QQSlideMenuLayout extends HorizontalScrollView {

    private View mMenuView, mContentView, mShadeView;

    private int mMenuWidth;

    // 是否拦截 事件
    private boolean mIsIntercept;

    // 菜单是否已经打开
    private boolean mIsMenuOpen = false;

    // 处理 侧滑过程中的快速滑动
    private GestureDetector mGestureDetector;
    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            Log.i(TAG, "onFling: velocityX " + velocityX);
            if (mIsMenuOpen) {
                // 打开了, 正在快速滑动将他关闭
                if (velocityX < 0) {
                    closeMenu();
                    return true; // 快速关闭触发时, 返回true.在onTouchEvent() 处理
                }
            } else {
                if (velocityX > 0) {
                    openMenu();
                    return true;
                }
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    public QQSlideMenuLayout(Context context) {
        this(context, null);
    }

    public QQSlideMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQSlideMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQSlideMenuLayout);

        int mMenuRightMargin = array.getDimensionPixelSize(R.styleable.QQSlideMenuLayout_QQmenuRightMargin, dip2px(60));

        mMenuWidth = getScreenWidth(context) - mMenuRightMargin;

        array.recycle();

        // 处理快速滑动
        mGestureDetector = new GestureDetector(context, mGestureListener);

    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.i("TAG", "onScrollChanged: left = " + l);  // 侧滑,往右边滑动,是 menuWidth -> 0 的范围

        // 算出,梯度值, 往右边滑动, contentView,缩放,
        float scale = 1f * l / mMenuWidth;  // 梯度值的变化是 1 -> 0
        Log.i(TAG, "onScrollChanged: scale " + scale);

        // 设置右边的阴影的部分,
        float rightAlpha = 1 - scale; // 1 -> 0 的值变化
        // mShadeView.setAlpha(rightAlpha); // 和下面是一样的
        ViewCompat.setAlpha(mShadeView, rightAlpha);


        // 设置左边边平移的文字
        ViewCompat.setTranslationX(mMenuView, 0.6f * l);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollTo(mMenuWidth, 0);
    }

    // 布局解析完毕
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ViewGroup container = (ViewGroup) getChildAt(0);

        if (container.getChildCount() != 2) {
            throw new RuntimeException("" + this.getClass().getSimpleName() + " 只能包含两个布局 ");
        }

        mMenuView = container.getChildAt(0);
        ViewGroup.LayoutParams menuViewParams = mMenuView.getLayoutParams();
        menuViewParams.width = mMenuWidth;
        mMenuView.setLayoutParams(menuViewParams);  // android 7.0 必须这样方式,才能有效果

        mContentView = container.getChildAt(1);



        ViewGroup.LayoutParams contentViewParams = mContentView.getLayoutParams();
        contentViewParams.width = getScreenWidth(getContext());
        mContentView.setLayoutParams(contentViewParams);

        //先将 需要添加阴影的view, 抽离出来, 在这个view的最外层添加一个view, 这个view将原来需要的操作的view 添加进去, 最外层的view, add 一个阴影的view
        container.removeView(mContentView);
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        relativeLayout.addView(mContentView);
        mShadeView = new View(getContext());
        mShadeView.setBackgroundColor(Color.parseColor("#99000000"));
        relativeLayout.addView(mShadeView);
        container.addView(relativeLayout);

        relativeLayout.getLayoutParams().width = getScreenWidth(getContext());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mIsIntercept = false;

        Log.i(TAG, "onInterceptTouchEvent: ev " + ev.getAction());
        if (mIsMenuOpen) {
            float x = ev.getX();
            // 菜单是打开的状态, 点击右边contentView, 需要关闭, 并且子view,不响应事件
            if (x > mMenuWidth) {
                mIsIntercept = true;
                return true;  // 拦截子view的事件, 子view 不响应任何的事件, -> 注: 会走viewGroup 的TouchEvent(),
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onTouchEvent: " + ev.getAction());
        if (mIsIntercept) {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                closeMenu();  // 当按下了右边的内容的contentview 的时候, 如果是手指抬起的时候,消费事件, 关闭侧滑
                return true;  // 拦截了事件, 不调用自身的viewGroup的onTouchEvent() 方法
            }
        }

        if (mGestureDetector.onTouchEvent(ev)) {
            return true; // 当快速 滑动触发了的时候,消费事件
        }


        if (ev.getAction() == MotionEvent.ACTION_UP) {
            int currentScrollX = getScrollX(); // 获取滑动的x 的位置
            if (currentScrollX > mMenuWidth / 2) {
                //关闭
                closeMenu();
            } else {
                // 打开
                openMenu();
            }
            return true;
        }


        return super.onTouchEvent(ev);
    }


    private void openMenu() {
        smoothScrollTo(0, 0);
        mIsMenuOpen = true;
    }

    private void closeMenu() {
        smoothScrollTo(mMenuWidth, 0);
        mIsMenuOpen = false;
    }

    private int dip2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

}
