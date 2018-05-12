package com.example.fangsfmac.customview.kuguo_slideMenu_08;

import android.content.Context;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

import com.example.fangsfmac.customview.R;

/**
 * Created by fangsf on 2018/5/9.
 * Useful:
 */

public class SlideMenuLayout extends HorizontalScrollView {

    private View mMenuView, mContentView;

    private int mMenuWidth;

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

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.i("TAG", "onScrollChanged: left = " + l);  // 侧滑,往右边滑动,是 menuWidth -> 0 的范围

        // 算出,梯度值, 往右边滑动, contentView,缩放,
        float scale = 1f * l / mMenuWidth;  // 梯度值的变化是 1 -> 0
        // 往右边滑动,contentView, 需要缩放的范围是 最大是1f,最小是0.7f
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

        // 设置右边平移的文字
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                int upX = (int) ev.getX();
                if (upX > mMenuWidth) {
                    //关闭
                    closeMenu();
                } else {
                    // 打开
                    openMenu();
                }
                return true;

            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            default:
                break;
        }

        return super.onTouchEvent(ev);
    }

    private void openMenu() {
        smoothScrollTo(mMenuWidth, 0);
    }

    private void closeMenu() {
        smoothScrollTo(0, 0);
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
