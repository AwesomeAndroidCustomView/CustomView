package com.example.fangsfmac.customview.custom_Behavior_12;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.fangsfmac.customview.R;

/**
 * Created by fangsf on 2018/5/17.
 * Useful:
 */

public class CustomBehavior extends CoordinatorLayout.Behavior {

    private static final String TAG = CustomBehavior.class.getSimpleName();

    // 悬浮按钮是否显示出来
    private boolean mIsShow = false;
    private LinearLayout mBottomTabView;

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 摆放子view的时候调用
    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        mBottomTabView = parent.findViewById(R.id.bottom_tab_layout);
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type) {

        // 只处理垂直滑动的事件
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed,
                               int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        Log.i(TAG, "onNestedScroll: dyConsumed " + dyConsumed);
        if (dyConsumed > 0) {
            // 列表往下滑动
            // 开启动画栏 悬浮按钮,移出屏幕
            if (!mIsShow) {
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
                child.animate().translationY(child.getMeasuredHeight() + params.bottomMargin).setDuration(400).start(); // 直接操作的是这个子控件
                mBottomTabView.animate().translationY(mBottomTabView.getMeasuredHeight()).setDuration(400).start();
                mIsShow = !mIsShow;
            }

        } else {
            // 列表往下滑动
            // 在屏幕显示出来
            if (mIsShow) {
                child.animate().translationY(0).setDuration(400).start(); //恢复到原来的位置
                mBottomTabView.animate().translationY(0).setDuration(200).start();
                mIsShow = !mIsShow;
            }
        }

    }


}
