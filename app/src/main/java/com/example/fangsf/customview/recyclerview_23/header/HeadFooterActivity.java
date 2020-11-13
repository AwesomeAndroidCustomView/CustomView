package com.example.fangsf.customview.recyclerview_23.header;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.fangsf.customview.R;
import com.example.fangsf.customview.recyclerview_23.ChatData;
import com.example.fangsf.customview.recyclerview_23.ItemDecoration1.adapter.RecylcerAdapter;

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

        mRcView.setLayoutManager(new LinearLayoutManager(this));
        mRcView.setAdapter(recyclerAdapter);


        View headerView = inflater.inflate(R.layout.header_view, mRcView, false);
        View footerView = inflater.inflate(R.layout.footer_view, mRcView, false);


        recyclerAdapter.addHeaderView(headerView);
        recyclerAdapter.addFooterView(footerView);
    }


}
