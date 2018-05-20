package com.example.fangsfmac.customview.DropDownView_14;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fangsf on 2018/5/19.
 * Useful: Adapter 设计模式,
 */

public abstract class BaseMenuAdapter {

    private MenuObserver mObserver;

    // 使用观察者设计模式, 参考baseAdapter
    public void registerDataSetObserver(MenuObserver observer) {
        mObserver = observer;
    }

    public void unregisterDataSetObserver(MenuObserver observer) {
        mObserver = null;
    }

    public void closeMenu() {
        if (mObserver != null) {
            mObserver.closeMenu();
        }
    }

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
