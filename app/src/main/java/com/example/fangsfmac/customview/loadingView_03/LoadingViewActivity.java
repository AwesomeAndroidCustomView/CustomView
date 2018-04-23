package com.example.fangsfmac.customview.loadingView_03;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.fangsfmac.customview.BaseActivity;
import com.example.fangsfmac.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingViewActivity extends BaseActivity {


    @BindView(R.id.progressView)
    ProgressView mProgressView;
    private ValueAnimator mValueAnimator;

    @BindView(R.id.shapeView)
    ShapeView mShapeView;

    @Override
    protected int bindLayout() {

        return R.layout.activity_loading_view;
    }

    @Override
    protected void init() {
        mProgressView.setMaxProgress(100);
        mValueAnimator = ObjectAnimator.ofFloat(1, 80);

        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                mProgressView.setCurrentProgress((Float) animation.getAnimatedValue());

            }
        });
        mValueAnimator.setDuration(1500);
        mValueAnimator.start();
    }

    public void click1(View view) {
        mValueAnimator.start();
    }

    public void click2(View view) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mShapeView.exchangeView();
                                }
                            });

                            SystemClock.sleep(800);

                        }
                    }
                }
        ).start();
    }
}
