package com.example.fangsfmac.customview.DropDownView_14;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fangsfmac.customview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DropDownActivity extends AppCompatActivity {

    @BindView(R.id.listDataScreen)
    ListDataScreenView mListDataScreen;
    private List<View> popupViews = new ArrayList<>();

    private String[] mItems = {"类型", "品牌", "价格", "更多"};

    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州",
            "深圳", "重庆", "天津", "西安", "南京", "杭州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_down);
        ButterKnife.bind(this);

        ListView mListView = new ListView(this);
        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return citys.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view, parent, false);
                view.setText(citys[position]);

                return view;
            }
        });
        popupViews.add(mListView);
        TextView textView = new TextView(this);
        textView.setText("品牌");
        TextView textView2 = new TextView(this);
        textView2.setText("价格");
        TextView textView3 = new TextView(this);
        textView3.setText("更多");
        popupViews.add(textView);
        popupViews.add(textView2);
        popupViews.add(textView3);

        mListDataScreen.setAdapter(new ListDataScreenAdapter(DropDownActivity.this,mItems ,popupViews));

    }
}
