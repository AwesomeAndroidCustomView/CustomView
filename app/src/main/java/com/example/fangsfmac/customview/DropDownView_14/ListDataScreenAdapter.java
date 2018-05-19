package com.example.fangsfmac.customview.DropDownView_14;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fangsfmac.customview.R;

/**
 * Created by fangsf on 2018/5/19.
 * Useful:
 */

public class ListDataScreenAdapter extends BaseMenuAdapter {

    private Context mContext;

    private String[] mItems ={"类型","品牌","价格","更多"};

    public ListDataScreenAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View getTabMenuView(int position, ViewGroup parent) {

        TextView view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.text_view, parent, false);
        view.setText(mItems[position]);

        return view;
    }

    @Override
    public View getMenuView(int position, ViewGroup parent) {
        TextView view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.text_view, parent, false);
        view.setText(mItems[position]);

        return view;
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

}
