package com.example.fangsf.customview.recyclerview_23.ItemDecoration1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fangsf.customview.R;

import java.util.List;

/**
 * Created by fangsf on 2019/2/19.
 * Useful:
 */
public class RecylcerAdapter extends RecyclerView.Adapter<RecylcerAdapter.ViewHolder> {

    private final List<String> list;
    private Context mContext;

    public RecylcerAdapter(Context context, List<String> strings) {
        this.list = strings;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_recycler, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.textView);
        }
    }

}
