package com.example.fangsf.customview.yahuLoadingView_20;

import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fangsf.customview.R;

public class YahuLoadingActivity extends AppCompatActivity {

    private LoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yahu_loading);


        mLoadingView = findViewById(R.id.loadingView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingView.disappear();
            }
        }, 2000);
    }
}
