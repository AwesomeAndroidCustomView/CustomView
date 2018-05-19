package com.example.fangsfmac.customview.DropDownView_14;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by fangsf on 2018/5/19.
 * Useful:
 */

public class ListDataScreenView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    // tabView, 条目选择的view
    private LinearLayout mTabMenuView;
    // (内容的view + 底部阴影的view)
    private RelativeLayout mMiddleMenuView;
    // 阴影的view
    private View mShadowView;
    // 内容的view
    private FrameLayout mMenuContainerView;
    // 阴影的颜色
    private int mShadowColor = Color.parseColor("#0D2b2b2b");
    private final int DURATION_ANI = 350;

    private BaseMenuAdapter mAdapter;
    private int mMenuContainerHeight;

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
        mTabMenuView.setBackgroundColor(Color.WHITE);
        mTabMenuView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mTabMenuView);

        // 创建阴影+内容容器的view
        mMiddleMenuView = new RelativeLayout(mContext);
        mMiddleMenuView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mMiddleMenuView);

        // 创建内容的view
        mMenuContainerView = new FrameLayout(mContext);
        mMenuContainerView.setBackgroundColor(Color.YELLOW);
        mMenuContainerView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mMiddleMenuView.addView(mMenuContainerView);

        // 创建阴影的view
        mShadowView = new View(mContext);
        mShadowView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mShadowView.setBackgroundColor(mShadowColor);
        mShadowView.setAlpha(0);
        mShadowView.setVisibility(GONE);
        mShadowView.setOnClickListener(this);
        mMiddleMenuView.addView(mShadowView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);

        ViewGroup.LayoutParams containerViewParams = mMenuContainerView.getLayoutParams();
        mMenuContainerHeight = (int) (height * 75f / 100);
        containerViewParams.height = mMenuContainerHeight;  // 设置内容显示的布局是 屏幕高度的 75%,
        mMenuContainerView.setLayoutParams(containerViewParams);
        // 首次进来应该是没有打开菜单的
        mMenuContainerView.setTranslationY(-mMenuContainerHeight);

    }

    public void setAdapter(BaseMenuAdapter adapter) {
        this.mAdapter = adapter;

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
            View menuView = mAdapter.getMenuView(i, mMenuContainerView);
            menuView.setVisibility(GONE);  // 没有选择的时候,不打开布局
            mMenuContainerView.addView(menuView);


        }
    }

    private void setTabClick(final int position, View tabView) {

        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                openMenu();
            }
        });
    }

    private void openMenu() {
        // 打开菜单的动画
        ObjectAnimator middleMenuAni = ObjectAnimator.ofFloat(mMenuContainerView, "translationY", -mMenuContainerHeight, 0);
        middleMenuAni.setDuration(DURATION_ANI);
        middleMenuAni.start();

        //开启阴影的动画
        mShadowView.setVisibility(VISIBLE);
        ObjectAnimator shadowViewAni = ObjectAnimator.ofFloat(mShadowView, "alpha", 0, 1f);
        shadowViewAni.setDuration(DURATION_ANI);
        shadowViewAni.start();
    }

    @Override
    public void onClick(View v) {
        // 关闭menu
        closeMenu();
    }


    private void closeMenu() {
        // close菜单的动画
        ObjectAnimator middleMenuAni = ObjectAnimator.ofFloat(mMenuContainerView, "translationY", 0, -mMenuContainerHeight);
        middleMenuAni.setDuration(DURATION_ANI);
        middleMenuAni.start();

        //close阴影的动画
        ObjectAnimator shadowViewAni = ObjectAnimator.ofFloat(mShadowView, "alpha", 1f, 0f);
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
