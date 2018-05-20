package com.example.fangsfmac.customview.DropDownView_14;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fangsf on 2018/5/19.
 * Useful: Adapter 设计模式,
 */

public abstract class BaseMenuAdapter {

    // 获取条目筛选的条目
    public abstract View getTabMenuView(int position, ViewGroup parent);

    // 获取菜单的view
    public abstract View getMenuView(int position, ViewGroup parent);


    // 获取view 的个数
    public abstract int getCount();

    public void menuClose(View tabView) {


    }

    public void menuOpen(View tabView) {

    }
}
