package com.example.fangsf.customview.recyclerview_23.header;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fangsf on 2019/3/2
 * Useful:  像listView 一样, 兼容 添加头部和底部,
 * 装饰者设计模式
 */
public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private RecyclerView.Adapter mAdapter;

    //头部和底部的view
    private SparseArray<View> mFooterViews, mHeaderViews;

    private static final int HEADER_KEY_TYPE = 1000000;
    private static final int FOOTER_KEY_TYPE = 2000000;


    public WrapRecyclerAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        mFooterViews = new SparseArray<>();
        mHeaderViews = new SparseArray<>();
    }

    public void addHeaderView(View view) {
        // 防止添加重复的view
        if (mHeaderViews.indexOfValue(view) == -1) {
            mHeaderViews.put(HEADER_KEY_TYPE, view);

            notifyDataSetChanged();
        }
    }


    public void removeHeaderView(View view) {

        if (mHeaderViews.indexOfValue(view) > 0) {
            mHeaderViews.removeAt(mHeaderViews.indexOfValue(view));

            notifyDataSetChanged();
        }

    }

    public void removeFooterView(View view) {
        if (mFooterViews.indexOfValue(view) > 0) {
            mFooterViews.removeAt(mFooterViews.indexOfValue(view));

            notifyDataSetChanged();
        }
    }


    public void addFooterView(View view) {
        // 防止添加重复的view
        if (mFooterViews.indexOfValue(view) == -1) {
            mFooterViews.put(FOOTER_KEY_TYPE, view);

            notifyDataSetChanged();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 根据viewType 创建不同的ViewHolder

        if (mHeaderViews.indexOfKey(viewType) > 0) {  // 头部
            return createHeaderFooterViewHolder(mHeaderViews.get(viewType));
        } else if (mFooterViews.indexOfKey(viewType) > 0) {
            return createHeaderFooterViewHolder(mFooterViews.get(viewType));
        }

        // 中间的部分
        return mAdapter.createViewHolder(parent, viewType);
    }

    /**
     * 创建头部或者底部的ViewHolder
     */
    private RecyclerView.ViewHolder createHeaderFooterViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {

        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //头部
        int numHeaders = mHeaderViews.size();
        int adjPosition = position - numHeaders; // 实际的中部的位置

        if (position < numHeaders ||
                position >= (mHeaderViews.size() +mAdapter.getItemCount())) { // 头部,尾部不绑定数据
            return;
        }

        // 中部

        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, adjPosition);
            }
        }


    }

    @Override
    public int getItemViewType(int position) {
        //根据相应的的position, 返回相应的viewType

        //头部
        int numHeaders = mHeaderViews.size();
        if (position < numHeaders) {
            return mHeaderViews.keyAt(position);
        }

        // 中部
        int adjPosition = position - numHeaders; // 实际的中部的位置
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }

        int footerPosition = adjPosition - adapterCount;

        // 底部
        return mFooterViews.keyAt(footerPosition);
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + mFooterViews.size() + mHeaderViews.size();
    }
}
