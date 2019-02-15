package com.example.fangsf.customview.recyclerview_23.ItemDecoration;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.fangsf.customview.BaseActivity;
import com.example.fangsf.customview.R;

import butterknife.BindView;

public class ItemDecorationActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.rcView)
    RecyclerView mRcView;

    @Override
    protected int bindLayout() {
        return R.layout.activity_item_decoration;
    }

    @Override
    protected void init() {

        initMenu();

        initData();
    }

    private void initData() {



    }





    private void initMenu() {
        mToolBar.inflateMenu(R.menu.item_dec);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                        case R.id.lin :
                    break;

                        case R.id.grid:
                    break;

                    default:
                        break;
                }


                return false;
            }
        });
    }
}
