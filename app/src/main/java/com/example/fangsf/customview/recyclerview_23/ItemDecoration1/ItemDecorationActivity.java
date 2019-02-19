package com.example.fangsf.customview.recyclerview_23.ItemDecoration1;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.fangsf.customview.BaseActivity;
import com.example.fangsf.customview.R;
import com.example.fangsf.customview.recyclerview_23.ItemDecoration1.adapter.RecylcerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ItemDecorationActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.rcView)
    RecyclerView mRcView;

    private RecylcerAdapter mRecylcerAdapter;

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

        List<String> strings = new ArrayList<>();

        for (int i = 0; i < 39; i++) {
            strings.add(String.valueOf(i));
        }

        mRecylcerAdapter = new RecylcerAdapter(this, strings);
        mRcView.setLayoutManager(new LinearLayoutManager(this));
        mRcView.setAdapter(mRecylcerAdapter);

        // 添加分割线
        mRcView.addItemDecoration(new LinearItemDecoration(this, R.drawable.item_line_shape));

    }


    private void initMenu() {
        mToolBar.inflateMenu(R.menu.item_dec);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.lin:
                        mRcView.setLayoutManager(new LinearLayoutManager(ItemDecorationActivity.this));
                        break;

                    case R.id.grid:  // 网格
                        mRcView.setLayoutManager(new GridLayoutManager(ItemDecorationActivity.this, 3));
                        break;

                    default:
                        break;
                }


                return false;
            }
        });
    }
}
