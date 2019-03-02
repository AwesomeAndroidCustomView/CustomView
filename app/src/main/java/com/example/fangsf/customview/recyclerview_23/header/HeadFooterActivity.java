package com.example.fangsf.customview.recyclerview_23.header;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.fangsf.customview.R;
import com.example.fangsf.customview.recyclerview_23.ChatData;
import com.example.fangsf.customview.recyclerview_23.ItemDecoration1.adapter.RecylcerAdapter;
import com.example.fangsf.customview.recyclerview_23.commonAdapter2.CommonAdapter;
import com.example.fangsf.customview.recyclerview_23.commonAdapter2.MultiAdapter;
import com.example.fangsf.customview.recyclerview_23.commonAdapter2.common.MultiTypeSupport;

import java.util.ArrayList;
import java.util.List;

public class HeadFooterActivity extends AppCompatActivity {

    private RecyclerView mRcView;
    List<ChatData> mChatData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_footer);


        mRcView = findViewById(R.id.rcView);

        List<String> strings = new ArrayList<>();

        for (int i = 0; i < 39; i++) {
            strings.add(String.valueOf(i));
        }

        RecylcerAdapter mRecylcerAdapter = new RecylcerAdapter(this, strings);


        WrapRecyclerAdapter recyclerAdapter = new WrapRecyclerAdapter(mRecylcerAdapter);
        LayoutInflater inflater = LayoutInflater.from(this);
        View headerView = inflater.inflate(R.layout.header_view, null, false);
        View footerView = inflater.inflate(R.layout.footer_view, null, false);



        recyclerAdapter.addHeaderView(headerView);
        recyclerAdapter.addFooterView(footerView);

        mRcView.setLayoutManager(new LinearLayoutManager(this));
        mRcView.setAdapter(recyclerAdapter);


    }


}
