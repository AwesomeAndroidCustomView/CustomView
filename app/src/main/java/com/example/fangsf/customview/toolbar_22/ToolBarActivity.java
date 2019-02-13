package com.example.fangsf.customview.toolbar_22;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.fangsf.customview.BaseActivity;
import com.example.fangsf.customview.R;
import com.example.fangsf.customview.ToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ToolBarActivity extends BaseActivity {

    @BindView(R.id.toolBar2)
    ToolBar mToolBar2;
    @BindView(R.id.toolBar3)
    Toolbar mToolBar3;

    @Override
    protected int bindLayout() {
        return R.layout.activity_tool_bar;
    }

    @Override
    protected void init() {
        mToolBar2.inflateMenu(R.menu.menu);
        mToolBar3.inflateMenu(R.menu.menu);
    }

}
