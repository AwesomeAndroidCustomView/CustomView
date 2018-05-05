package com.example.fangsfmac.customview.tagLayout_07;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fangsf on 2018/5/5.
 * Useful:
 */

public abstract class TagAdapter {


    //参考 Listview 的 adapter 设计模式, 增加扩展性,
    //子view 的个数
    public abstract int getCount();


    public abstract View getView(int position, ViewGroup parent);


    //todo 通知 适配器刷新, 拖动事件
}
