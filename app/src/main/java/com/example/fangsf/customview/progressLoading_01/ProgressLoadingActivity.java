package com.example.fangsf.customview.progressLoading_01;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.fangsf.customview.BaseActivity;
import com.example.fangsf.customview.R;

import butterknife.BindView;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mValueAnimator.cancel();
    }
}
