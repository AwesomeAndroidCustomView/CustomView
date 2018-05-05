package com.example.fangsfmac.customview.tagLayout_07;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fangsfmac.customview.BaseActivity;
import com.example.fangsfmac.customview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagLayoutActivity extends BaseActivity {


    @BindView(R.id.tagLayout)
    TagLayoutView mTagLayout;

    private List<String> mStrings;

    @Override
    protected int bindLayout() {
        return R.layout.activity_tag_layout;
    }

    @Override
    protected void init() {

        mStrings = new ArrayList<>();
        mStrings.add("新闻001");
        mStrings.add("新闻03412342101");
        mStrings.add("新闻0123401");
        mStrings.add("新闻00121");
        mStrings.add("新21");
        mStrings.add("新闻1");
        mStrings.add("新闻0123401");
        mStrings.add("新0121");
        mStrings.add("新闻001sadf231");

        mTagLayout.setAdapter(new TagAdapter() {
            @Override
            public int getCount() {
                return mStrings.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {

                //parent 不能为空
                Log.i(TAG, "getView: parent " + parent);

                TextView view = (TextView) LayoutInflater.from(TagLayoutActivity.this).inflate(R.layout.item_tag, parent, false);

                view.setText(mStrings.get(position));


                return view;
            }
        });
    }
}
