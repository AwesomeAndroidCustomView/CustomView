package com.example.fangsfmac.customview;

import android.view.View;

import com.example.fangsfmac.customview.colorTrackTextView_02.ColorTrackTextViewActivity;
import com.example.fangsfmac.customview.progressLoading_01.ProgressLoadingActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

    }

    public void click1(View view) {
        jumpAct(ProgressLoadingActivity.class);
}

    public void click2(View view) {
        jumpAct(ColorTrackTextViewActivity.class);
    }
}
