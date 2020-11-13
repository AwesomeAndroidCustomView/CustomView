package com.example.fangsf.customview.recyclerview_23.ItemDecoration1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by fangsf on 2019/2/19.
 * Useful:  设置分割线
 * 1, 预留出位置来
 * 2, draw 绘制
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "LinearItemDecoration";

    private Drawable mDrawable;

    public LinearItemDecoration(Context context, int drawableRes) {
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

        if (childPosition != 0) {
            outRect.top = mDrawable.getIntrinsicHeight();
        }
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        // 绘制每一个item

        int childCount = parent.getChildCount();

        // 制定绘制的区域
        Rect rect = new Rect();
        for (int i = 1; i < childCount; i++) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) parent.getChildAt(i).getLayoutParams();
            rect.left = parent.getPaddingLeft() + params.leftMargin;
            rect.right = parent.getWidth() - parent.getPaddingRight() - params.rightMargin;
            rect.bottom = parent.getChildAt(i).getTop() - params.bottomMargin;
            rect.top = rect.bottom - mDrawable.getIntrinsicHeight();

            mDrawable.setBounds(rect);
            mDrawable.draw(c);
        }


    }
}
