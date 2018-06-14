package com.example.fangsfmac.customview.yahuLoadingView_20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fangsfmac.customview.R;

public class YahuLoadingActivity extends AppCompatActivity {

    private LoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yahu_loading);


        mLoadingView = findViewById(R.id.loadingView);

    }
}
