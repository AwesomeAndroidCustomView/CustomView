package com.example.fangsfmac.customview.tagLayout_07;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fangsf on 2018/5/4.
 * Useful:
 */

public class TagLayoutView extends ViewGroup {

    // 存储标签的所有view
    private List<List<View>> mChildViews = new ArrayList<>();

    public TagLayoutView(Context context) {
        super(context);
    }

    public TagLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 指定view的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //清空view的集合, 可能onMeasure 会调用多次
        mChildViews.clear();

        int width = MeasureSpec.getSize(widthMeasureSpec);
        // 高度需要计算
        int height = getPaddingTop() + getPaddingBottom();
        // 一行的宽度
        int lineWidht = getPaddingLeft();

        // 存储该行标签的view
        List<View> childViews = new ArrayList<>();
        mChildViews.add(childViews);  //why first add()
        // 获取 子view 的个数
        int childViewCount = getChildCount();

        // 子view 的高度可能不一致的情况下, 取最大值的view 的高度
        int maxHeight = 0;

        // 遍历设置 计算所有子view 的宽高, 设置viewGroup的宽高
        for (int i = 0; i < childViewCount; i++) {
            View childView = getChildAt(i);
            // 测量子view 的宽高,  会自动调用子view 的onMeasure()
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            // 获取子view 的margin值
            ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

            if ((lineWidht + params.leftMargin + params.rightMargin) > width) {
                // 下一行, 高度一直累加, 设置width,不累加
                height += childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                lineWidht = childView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                //下一行, 也就是另外一行的view
                childViews = new ArrayList<>();
                mChildViews.add(childViews);
            } else {
                // 还是原来的那一行, 宽度一直累加,设置高度,不累加
                lineWidht += params.leftMargin + params.rightMargin + childView.getMeasuredWidth();
                // 取出子view 最大的高度
                maxHeight = Math.max(childView.getMeasuredHeight() + params.topMargin + params.bottomMargin, maxHeight);
            }

            //集合中添加view 标签...
            childViews.add(childView);

        }

        height += maxHeight;
        // 根据计算所有view 的 宽高,设置给viewGroup的宽高
        setMeasuredDimension(width, height);
    }


    // 摆放view 的位置
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = getPaddingTop(), left, right, bottom;

        // 遍历整个view的集合
        for (List<View> childViews : mChildViews) {
            left = getPaddingLeft();

            for (View childView : childViews) {     //遍历该行的view, 摆放他的位置
                ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

                left += params.leftMargin;
                int childTop = top + params.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = childTop + childView.getMeasuredHeight();

                // 摆放位置
                childView.layout(left, childTop, right, bottom);  // 注: left ,top 一直要进行累加
                // left 叠加
                left += childView.getMeasuredWidth() + params.rightMargin;
            }

            // 下一行view 的 高度累加
            //累加高度,摆放位置,不断叠加top值  ()
            ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childViews.get(0).getLayoutParams();
            top += childViews.get(0).getMeasuredHeight() + params.topMargin + params.bottomMargin;
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
