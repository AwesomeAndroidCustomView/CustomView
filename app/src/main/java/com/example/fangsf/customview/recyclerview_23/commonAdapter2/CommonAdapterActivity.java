package com.example.fangsf.customview.recyclerview_23.commonAdapter2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.fangsf.customview.R;
import com.example.fangsf.customview.recyclerview_23.commonAdapter2.common.RecyclerCommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonAdapterActivity extends AppCompatActivity {

    @BindView(R.id.rcView)
    RecyclerView mRcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_adapter);
        ButterKnife.bind(this);
    }

    public void click1(View view) { //commom

        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            strings.add("" + i);
        }
        CommonAdapter commonAdapter = new CommonAdapter(R.layout.item_textview, strings);
        mRcView.setLayoutManager(new LinearLayoutManager(this));
        mRcView.setAdapter(commonAdapter);

//        commonAdapter.setOnItemClickListener(new RecyclerCommonAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerCommonAdapter adapter, View view, int position) {
//                Toast.makeText(CommonAdapterActivity.this, "" + position, Toast.LENGTH_SHORT).show();
//            }
//        });

        commonAdapter.setOnLongClickListener(new RecyclerCommonAdapter.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view, int position) {
                Toast.makeText(CommonAdapterActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    public void click2(View view) { //

    }


}
