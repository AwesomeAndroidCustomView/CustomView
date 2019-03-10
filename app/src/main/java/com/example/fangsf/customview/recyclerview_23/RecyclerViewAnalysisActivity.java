package com.example.fangsf.customview.recyclerview_23;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.fangsf.customview.R;
import com.example.fangsf.customview.recyclerview_23.ItemDecoration1.ItemDecorationActivity;
import com.example.fangsf.customview.recyclerview_23.commonAdapter2.CommonAdapterActivity;
import com.example.fangsf.customview.recyclerview_23.dragswip.DragSwipeActivity;
import com.example.fangsf.customview.recyclerview_23.header.HeadFooterActivity;

public class RecyclerViewAnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_d);
    }

    public void click1(View view) {
        //RecyclerView 自定义分割线
        startActivity(new Intent(this, ItemDecorationActivity.class));

    }

    public void click2(View view) { //commonAdapter
        startActivity(new Intent(this, CommonAdapterActivity.class));
    }

    public void click3(View view) {
        startActivity(new Intent(this, HeadFooterActivity.class));
    }


    public void click4(View view) {
startActivity(new Intent(this, DragSwipeActivity.class));
    }


    public void click5(View view) {

    }
}
