package com.example.fangsf.customview.DropDownView_14;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by fangsf on 2018/5/19.
 * Useful:
 */

public class ListDataScreenView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = ListDataScreenView.class.getSimpleName();

    private MenuAdapterObserver mObserver;

    private Context mContext;
    // tabView, 条目选择的view
    private LinearLayout mTabMenuView;
    // (内容的view + 底部阴影的view)
    private FrameLayout mMiddleMenuView;
    // 阴影的view
    private View mShadowView;
    // 内容的view
    private FrameLayout mMenuContainerView;
    // 阴影的颜色
    private int mShadowColor = Color.parseColor("#0D2b2b2b");
    private final int DURATION_ANI = 200;

    private BaseMenuAdapter mAdapter;
    // 菜单内容的高度
    private int mMenuContainerHeight;

    private int mCurrentPosition = -1;

    // 动画是否正在执行
    private boolean mAnimatorExecute;

    public ListDataScreenView(Context context) {
        this(context, null);
    }

    public ListDataScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListDataScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        initLayout();
    }

    private void initLayout() {
        setOrientation(VERTICAL);

        //创建菜单view
        mTabMenuView = new LinearLayout(mContext);
       // mTabMenuView.setBackgroundColor(Color.WHITE);
        mTabMenuView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mTabMenuView);

        // 创建阴影+内容容器的view
        mMiddleMenuView = new FrameLayout(mContext);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        mMiddleMenuView.setLayoutParams(params);
        addView(mMiddleMenuView);

        // 创建阴影的view, 默认是match_parent
        mShadowView = new View(mContext);
        mShadowView.setBackgroundColor(mShadowColor);
        mShadowView.setAlpha(0);
        mShadowView.setVisibility(GONE);
        mShadowView.setOnClickListener(this);
        mMiddleMenuView.addView(mShadowView);

        // 创建内容的view
        mMenuContainerView = new FrameLayout(mContext);
        mMenuContainerView.setBackgroundColor(Color.YELLOW);
        mMiddleMenuView.addView(mMenuContainerView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: ");

        int height = MeasureSpec.getSize(heightMeasureSpec);
        // 防止多次重新测量
        if (mMenuContainerHeight == 0) {
            ViewGroup.LayoutParams containerViewParams = mMenuContainerView.getLayoutParams();
            mMenuContainerHeight = (int) (height * 75f / 100);
            containerViewParams.height = mMenuContainerHeight;  // 设置内容显示的布局是 屏幕高度的 75%,
            mMenuContainerView.setLayoutParams(containerViewParams);
            // 首次进来应该是没有打开菜单的
            mMenuContainerView.setTranslationY(-mMenuContainerHeight);
        }
    }

    private class MenuAdapterObserver extends MenuObserver {

        @Override
        public void closeMenu() {
            ListDataScreenView.this.closeMenu(); // 关闭菜单
        }
    }

    public void setAdapter(BaseMenuAdapter adapter) {

        // 观察者
        if (mAdapter != null && mObserver != null) {
            mAdapter.unregisterDataSetObserver(mObserver);
        }
        this.mAdapter = adapter;
        mObserver = new MenuAdapterObserver();
        mAdapter.registerDataSetObserver(mObserver);

        int childViewCount = mAdapter.getCount();
        for (int i = 0; i < childViewCount; i++) {
            //tab选择布局
            View tabView = mAdapter.getTabMenuView(i, mTabMenuView);
            LinearLayout.LayoutParams layoutParams = (LayoutParams) tabView.getLayoutParams();
            layoutParams.weight = 1;
            tabView.setLayoutParams(layoutParams);
            mTabMenuView.addView(tabView);

            // 设置 tab的点击事件
            setTabClick(i, tabView);

            // 菜单布局
//            View menuView = mAdapter.getMenuView(i, mMenuContainerView);
            List<View> menuViewList = mAdapter.getMenuView(i, mMenuContainerView);
            View menuView = menuViewList.get(i);
            mMenuContainerView.addView(menuView);
            menuView.setVisibility(GONE);  // 没有选择的时候,不打开布局

        }
    }

    private void setTabClick(final int position, View tabView) {

        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPosition == -1) {
                    openMenu(position);
                } else {
                    if (mCurrentPosition == position) {
                        // 如果是相同的条目,则关闭,否则切换条目
                        closeMenu();
                    } else {
                        // 菜单的view
                        View currentMenu = mMenuContainerView.getChildAt(mCurrentPosition);
                        currentMenu.setVisibility(View.GONE); //注意切换布局的时候,这里设置为gone, 会重新执行onMeasure()方法
                        mAdapter.menuClose(mTabMenuView.getChildAt(mCurrentPosition));
                        mCurrentPosition = position;
                        currentMenu = mMenuContainerView.getChildAt(mCurrentPosition);
                        currentMenu.setVisibility(View.VISIBLE);
                        mAdapter.menuOpen(mTabMenuView.getChildAt(mCurrentPosition));
                    }
                }

            }
        });
    }

    private void openMenu(int position) {
        if (mAnimatorExecute) {
            return;
        }
        // 打开菜单的动画
        mCurrentPosition = position;

        mShadowView.setVisibility(View.VISIBLE);
        mShadowView.setVisibility(View.VISIBLE);
        // 获取当前位置显示当前菜单，菜单是加到了菜单容器
        View menuView = mMenuContainerView.getChildAt(position);
        menuView.setVisibility(View.VISIBLE);

        ObjectAnimator middleMenuAni = ObjectAnimator.ofFloat(mMenuContainerView, "translationY", -mMenuContainerHeight, 0);
        middleMenuAni.setDuration(DURATION_ANI);
        middleMenuAni.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimatorExecute = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mAdapter.menuOpen(mTabMenuView.getChildAt(mCurrentPosition));
                mAnimatorExecute = true;
            }
        });
        middleMenuAni.start();

        //开启阴影的动画
        ObjectAnimator shadowViewAni = ObjectAnimator.ofFloat(mShadowView, "alpha", 0, 0.5f);
        shadowViewAni.setDuration(DURATION_ANI);
        shadowViewAni.start();
    }

    @Override
    public void onClick(View v) {
        // 关闭menu
        if (mCurrentPosition != -1) {
            closeMenu();
        }
    }


    private void closeMenu() {
        if (mAnimatorExecute) {
            return;
        }

        // close菜单的动画
        ObjectAnimator middleMenuAni = ObjectAnimator.ofFloat(mMenuContainerView, "translationY", 0, -mMenuContainerHeight);
        middleMenuAni.setDuration(DURATION_ANI);
        middleMenuAni.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                View menuView = mMenuContainerView.getChildAt(mCurrentPosition);
                menuView.setVisibility(View.GONE);
                mAnimatorExecute = false;
                mCurrentPosition = -1;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mAdapter.menuClose(mTabMenuView.getChildAt(mCurrentPosition));
                mAnimatorExecute = true;

            }
        });
        middleMenuAni.start();

        //close阴影的动画
        ObjectAnimator shadowViewAni = ObjectAnimator.ofFloat(mShadowView, "alpha", 0.5f, 0f);
        shadowViewAni.setDuration(DURATION_ANI);
        shadowViewAni.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mShadowView.setVisibility(GONE); // 当关闭菜单的时候,需要gone,否则底部的view会不响应事件
            }
        });
        shadowViewAni.start();
    }
}
