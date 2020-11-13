package com.example.fangsf.customview.recyclerview_23.ItemDecoration1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

/**
 * Created by fangsf on 2019/2/19.
 * Useful:  设置分割线
 * 1, 预留出位置来
 * 2, draw 绘制
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "LinearItemDecoration";

    private Context mContext;
    private Drawable mDrawable;

    public GridItemDecoration(Context context, int drawableRes) {
        this.mContext = context;
        mDrawable = ContextCompat.getDrawable(context, drawableRes);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // 源码中顺序, 先在measureChildWithMargins 中, 测量recyclerView的子view的大小, 然后加上间隙分割线,
        // 然后 再重新绘制   requestLayout(); --> 调用onDraw方法, 开始绘制分割线的onDraw方法
        /**
         *      @Override
         *     public void onDraw(Canvas c) {
         *         super.onDraw(c);
         *
         *         final int count = mItemDecorations.size();
         *         for (int i = 0; i < count; i++) {
         *             mItemDecorations.get(i).onDraw(c, this, mState);
         *         }
         *     }
         */

        int right = mDrawable.getIntrinsicWidth();
        int bottom = mDrawable.getIntrinsicHeight();

        //假如有3列,
        // 最右边那一列不预留出分割线的位置
        if (isLastColumn(view, parent)) {
            right = 0;
        }

        if (isLastRow(view, parent)) {  //最后一行
            bottom = 0;
        }


        outRect.right = right;
        outRect.bottom = bottom;


    }

    /**
     * 是否是最后一行
     */
    private boolean isLastRow(View view, RecyclerView parent) {

        //列数
        int spanCount = getSpanCount(parent);

        // 当前的位置
        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

        // 行数 = 总条目 / 列数 (除不尽的时候,多加1)
        int rowNum = parent.getAdapter().getItemCount() % spanCount == 0 ?
                parent.getAdapter().getItemCount() / spanCount : (parent.getAdapter().getItemCount() / spanCount) + 1;


        // 最后一行: (当前的位置+1) > (行数-1) * 列数
        return (currentPosition + 1) > (rowNum - 1) * spanCount;
    }

    /**
     * 获取列数
     */
    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            return gridLayoutManager.getSpanCount();
        }

        return 1;
    }

    /**
     * 是否是最后一列
     *
     * @param view
     * @param parent
     */
    private boolean isLastColumn(View view, RecyclerView parent) {
        // 当前view的位置 %  列数 , 为0说明是时候一列

        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition(); //源码中getItemOffsets()

        // 获取列数, 只有gridLayoutManager,有这个方法

        int spanCount = getSpanCount(parent);

        return (currentPosition + 1) % spanCount == 0;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        // 绘制每一个item

        drawHorizontal(c, parent, state);

        drawVertical(c, parent, state);

    }

    /**
     * 绘制垂直方向
     */
    private void drawVertical(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();

        Rect rect = new Rect();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();

            rect.left = childView.getRight() + params.rightMargin;
            rect.right = rect.left + mDrawable.getIntrinsicWidth();
            rect.top = childView.getTop() - params.topMargin;
            rect.bottom = childView.getBottom() + mDrawable.getIntrinsicHeight() + params.bottomMargin;

            mDrawable.setBounds(rect);
            mDrawable.draw(canvas);
        }

    }

    /**
     * 绘制水平方向
     */
    private void drawHorizontal(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int childCount = parent.getChildCount();

        Rect rect = new Rect();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();

            rect.left = childView.getLeft() - params.leftMargin;
            rect.right = childView.getRight() + mDrawable.getIntrinsicWidth() + params.rightMargin;
            rect.top = childView.getBottom() + params.bottomMargin;
            rect.bottom = rect.top + mDrawable.getIntrinsicHeight();

            mDrawable.setBounds(rect);
            mDrawable.draw(c);
        }
    }
}
