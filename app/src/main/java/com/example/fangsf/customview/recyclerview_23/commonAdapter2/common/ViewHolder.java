package com.example.fangsf.customview.recyclerview_23.commonAdapter2.common;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by fangsf on 2019/2/22.
 * Useful:
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    public ViewHolder(View itemView) {
        super(itemView);

        mViews = new SparseArray<>();
    }

    /**
     * 设置文本
     *
     * @return 返回本身, 让可以自生调用
     */
    public ViewHolder setText(@IdRes int viewId, CharSequence charSequence) {
        TextView textView = getView(viewId);
        textView.setText(charSequence);
        return this;
    }

    public ViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);

        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
