package com.example.fangsf.customview.loadingView_15;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.fangsf.customview.R;

public class LoadingActivity extends AppCompatActivity {

    private LoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        mLoadingView = findViewById(R.id.loadingView);
    }

    public void click1(View view) {
        mLoadingView.setVisibility(View.GONE);
    }
    public void click2(View view) {
        mLoadingView.setVisibility(View.VISIBLE);
    }
}
