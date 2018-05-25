package com.example.fangsfmac.customview.kuguo_slideMenu_08;

import android.content.Context;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
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

import com.example.fangsfmac.customview.R;

import static android.content.ContentValues.TAG;

/**
 * Created by fangsf on 2018/5/9.
 * Useful:
 */

public class SlideMenuLayout extends HorizontalScrollView {

    private View mMenuView, mContentView;

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

    public SlideMenuLayout(Context context) {
        this(context, null);
    }

    public SlideMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlideMenuLayout);

        int mMenuRightMargin = array.getDimensionPixelSize(R.styleable.SlideMenuLayout_menuRightMargin, dip2px(60));

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
        // 往右边滑动,contentView, 需要缩放的范围是 最大是1f,最小是0.7f
        Log.i(TAG, "onScrollChanged: scale " + scale + " l ");
        float rightScale = 0.7f + (scale * 0.3f);
        Log.i("TAG", "onScrollChanged: rightScale " + rightScale);
        ViewCompat.setPivotX(mContentView, 0);
        ViewCompat.setPivotY(mContentView, getScreenWidth(getContext()) / 2);
        ViewCompat.setScaleX(mContentView, rightScale);
        ViewCompat.setScaleY(mContentView, rightScale);

        //设置 左边menuView的透明度, 0.5f -> 1f ,半透明到完全透明
        float leftAlpha = 0.5f + (1 - scale) * 0.5f;
        Log.i("TAG", "onScrollChanged: leftAlpha " + leftAlpha);
        ViewCompat.setAlpha(mMenuView, leftAlpha);

        //左边的缩放文字 0.7 -> 1
        float leftScale = 0.7f + (1 - scale) * 0.3f;
        ViewCompat.setScaleY(mMenuView, leftScale);
        ViewCompat.setScaleX(mMenuView, leftScale);

        // 设置左边平移的文字
        ViewCompat.setTranslationX(mMenuView, 0.25f * l);
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


        // 在这里调用不响应, 因为view的绘制流程中, onFinishInflate(), 这个方法是在setContentView() 解析后,后面还会再调用onLayout() 方法,再一次摆放了布局
        //        scrollTo(mMenuWidth, 0);
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
                // 判断滑动的 距离 有没有超过 menuWidth
                if (getScaleX() > mMenuWidth /2) {
                    closeMenu();  // 当按下了右边的内容的contentview 的时候, 如果是手指抬起的时候,消费事件, 关闭侧滑
                    return true;  // 拦截了事件, 不调用自身的viewGroup的onTouchEvent() 方法
                }
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
