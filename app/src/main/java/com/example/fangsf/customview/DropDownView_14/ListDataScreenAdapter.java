package com.example.fangsf.customview.DropDownView_14;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fangsf.customview.R;

import java.util.List;

/**
 * Created by fangsf on 2018/5/19.
 * Useful:
 */

public class ListDataScreenAdapter extends BaseMenuAdapter {

    private Context mContext;
    private List<View> mViewList;

    private String[] mTabItems ;


    public ListDataScreenAdapter(Context context, String[] tabItems, List<View> viewList) {
        mContext = context;
        this.mViewList = viewList;
        mTabItems = tabItems;
    }

    @Override
    public View getTabMenuView(int position, ViewGroup parent) {

        TextView view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.text_view, parent, false);
        view.setText(mTabItems[position]);
        view.setTextColor(Color.BLACK);
        return view;
    }

    @Override
    public List<View> getMenuView(int position, ViewGroup parent) {

        return mViewList;
    }

    @Override
    public void menuClose(View tabView) {
    }

    @Override
    public void menuOpen(View tabView) {
    }

    @Override
    public int getCount() {
        return mTabItems.length;
    }

}
