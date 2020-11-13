package com.example.fangsf.customview.recyclerview_23.commonAdapter2.common;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by fangsf on 2019/2/22.
 * Useful:
 */
public abstract class RecyclerCommonAdapter<DATA> extends RecyclerView.Adapter<ViewHolder> {

    public List<DATA> mData;
    private int mLayoutRes;
    public Context mContext;

    //兼容多布局
    protected MultiTypeSupport<DATA> mTypeSupport;

    public RecyclerCommonAdapter(List<DATA> data, MultiTypeSupport<DATA> typeSupport) {
        this(-1, data);
        mData = data;
        this.mTypeSupport = typeSupport;
    }


    public RecyclerCommonAdapter(int layoutRes, List<DATA> data) {
        mData = data;
        this.mLayoutRes = layoutRes;
    }

    public List<DATA> getData() {
        return mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();


        if (mTypeSupport != null) {  // 兼容多布局
            mLayoutRes = viewType;
        }


        View view = LayoutInflater.from(mContext).inflate(mLayoutRes, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {

        if (mTypeSupport != null) { // 兼容多布局
            return mTypeSupport.getLayoutId(mData.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        convert(holder, mData.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(RecyclerCommonAdapter.this, v, position);
                }
            }
        });

        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mLongClickListener.onLongClick(v, position);
                }
            });
        }
    }

    protected abstract void convert(ViewHolder holder, DATA item);

    @Override
    public int getItemCount() {
        return mData.size();
    }


    private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerCommonAdapter adapter, View view, int position);
    }

    private OnLongClickListener mLongClickListener;

    public void setOnLongClickListener(OnLongClickListener longClickListener) {
        mLongClickListener = longClickListener;
    }

    public interface OnLongClickListener {
        boolean onLongClick(View view, int position);
    }
}
