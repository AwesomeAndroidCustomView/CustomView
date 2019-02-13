package com.example.fangsf.customview.foldView_10;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * Created by fangsf on 2018/5/14.
 * Useful:
 */

public class VerticalDragListView extends FrameLayout {

    private String TAG = getClass().getSimpleName();

    private ViewDragHelper mViewDragHelper;

    // 菜单的高度
    private int mMenuHeight;
    // 第二个view,  可拖动的 列表
    private View mDragListView;

    // view 是否已经折叠开了
    private boolean mViewIsOpen = false;

    // 按下的y的位置
    private float mActionDownY;

    public VerticalDragListView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelpCallback());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            View menuView = getChildAt(0);
            mMenuHeight = menuView.getMeasuredHeight();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        if (childCount != 2) {
            throw new RuntimeException(TAG + " 只能有2个子view ");
        }

        mDragListView = getChildAt(1);

    }

    private class ViewDragHelpCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {

            return mDragListView == child;  // 返回true, 让所有的view, 可以拖动
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (top < 0) {
                top = 0;
            }
            if (top > mMenuHeight) {
                top = mMenuHeight;
            }
            return top;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.i(TAG, "onViewReleased: yvel " + yvel + " top " + mDragListView.getTop());

            if (releasedChild == mDragListView) {
                if (mDragListView.getTop() > mMenuHeight / 2) { // 打开折叠的view
                    mViewDragHelper.settleCapturedViewAt(0, mMenuHeight);
                    mViewIsOpen = true;
                } else {
                    // 关闭 view
                    mViewDragHelper.settleCapturedViewAt(0, 0);
                    mViewIsOpen = false;
                }

                invalidate();  // 需要重绘 , 才能有效果, computeScroll ,也需要调用
            }
        }
    }

    /**
     * 响应滚动
     */
    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    /**
     * 当第二个view 是listview, 或者是Recycleview 的时候,需要处理事件拦截
     * 1, 当istView往下拉的时候, 并且是顶部的位置的时候,需要拦截事件,不要让listview 做处理
     * 2, 当菜单打开的时候, 往上拖动, 可以关闭,拦截listview 的事件
     * @param
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        //当菜单打开的时候, 往上拖动, 可以关闭,拦截listview 的事件
        if (mViewIsOpen) {
            return true;
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mActionDownY = ev.getY();

                //Ignoring pointerId=0 because ACTION_DOWN was not received for this pointer before ACTION_MOVE. It likely happened because  ViewDragHelper did not receive all the events in the event stream.
                // 需要响应一次完整的事件,
                mViewDragHelper.processTouchEvent(ev);

                break;

            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                if ((moveY - mActionDownY > 0) && !canChildScrollUp()) {
                    // 可以下拉, 拦截listview的事件
                    return true;
                }
                break;


            default:
                break;
        }


        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event); // 给ViewDragHelper 处理事件

        return true;
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     * 判断View是否滚动到了最顶部,还能不能向上滚
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mDragListView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mDragListView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mDragListView, -1) || mDragListView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mDragListView, -1);
        }
    }
}
