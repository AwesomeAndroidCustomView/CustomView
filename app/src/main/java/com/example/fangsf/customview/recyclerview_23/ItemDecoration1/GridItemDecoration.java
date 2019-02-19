package com.example.fangsf.customview.recyclerview_23.ItemDecoration1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

        int childPosition = parent.getChildAdapterPosition(view);

        Log.i(TAG, "getItemOffsets: " + parent.getChildCount() + " -- " + childPosition);
        // parent.getChildCount() 是不断变化的, 可以第二个view  top 留出位置

        if (((childPosition + 1) % 3) != 0) {  // 当第三个的时候不预留出 right的间隔
            outRect.right = mDrawable.getIntrinsicWidth();
        }
        outRect.bottom = mDrawable.getIntrinsicHeight();

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
