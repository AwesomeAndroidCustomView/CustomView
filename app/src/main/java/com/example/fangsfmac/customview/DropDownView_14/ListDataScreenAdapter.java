package com.example.fangsfmac.customview.DropDownView_14;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fangsfmac.customview.R;

/**
 * Created by fangsf on 2018/5/19.
 * Useful:
 */

public class ListDataScreenAdapter extends BaseMenuAdapter {

    private Context mContext;

    private String[] mItems = {"类型", "品牌", "价格", "更多"};

    public ListDataScreenAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View getTabMenuView(int position, ViewGroup parent) {

        TextView view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.text_view, parent, false);
        view.setText(mItems[position]);
        view.setTextColor(Color.BLACK);
        return view;
    }

    @Override
    public View getMenuView(int position, ViewGroup parent) {
        TextView view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.text_view, parent, false);
        view.setText(mItems[position]);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "close", Toast.LENGTH_SHORT).show();
                closeMenu();
            }
        });

        return view;
    }

    @Override
    public void menuClose(View tabView) {
        TextView tabTv = (TextView) tabView;
        tabTv.setTextColor(Color.BLACK);
    }

    @Override
    public void menuOpen(View tabView) {
        TextView tabTv = (TextView) tabView;
        tabTv.setTextColor(Color.RED);
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

}
