package com.example.fangsfmac.customview.progressLoading_01;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.fangsfmac.customview.BaseActivity;
import com.example.fangsfmac.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressLoadingActivity extends BaseActivity {

    @BindView(R.id.progressView)
    ProgressLoadView mProgressView;
    private ValueAnimator mValueAnimator;

    @Override
    protected int bindLayout() {
        return R.layout.activity_progress_loading;
    }

    @Override
    protected void init() {
        mProgressView.setMaxProgress(4000);
        mValueAnimator = ObjectAnimator.ofFloat(0, 3500);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currenStep = (float) animation.getAnimatedValue();
                mProgressView.setCurrentProgress((int) currenStep);
            }
        });
        mValueAnimator.start();
    }

    public void click1(View view) {
        mValueAnimator.start();
    }

}
