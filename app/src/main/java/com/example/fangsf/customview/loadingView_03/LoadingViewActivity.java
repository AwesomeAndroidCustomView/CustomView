package com.example.fangsf.customview.loadingView_03;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.example.fangsf.customview.BaseActivity;
import com.example.fangsf.customview.R;

import butterknife.BindView;

public class LoadingViewActivity extends BaseActivity {


    @BindView(R.id.progressView)
    ProgressView mProgressView;
    private ValueAnimator mValueAnimator;
    private ValueAnimator mValueAnimator2;

    @BindView(R.id.shapeView)
    ShapeView mShapeView;
    @BindView(R.id.progressView2)
    ProgressView2 mProgressView2;

    private int count = 0;


    private Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            ++count;
            if (count > 3) {
                return;
            }

            Toast.makeText(LoadingViewActivity.this, ""+count, Toast.LENGTH_SHORT).show();

            mHandler.sendEmptyMessageDelayed(0, 1000);
        }
    };


    private void startAnim2() {
        mProgressView2.setMaxProgress(300);

        mValueAnimator2 = ObjectAnimator.ofFloat(0, 300);

        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgressView2.setCurrentProgress((Float) animation.getAnimatedValue());
            }
        });
        mValueAnimator2.setDuration(3000);
        mValueAnimator2.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_loading_view;
    }

    @Override
    protected void init() {
        initListener();

        mProgressView.setMaxProgress(3);
        mValueAnimator = ObjectAnimator.ofFloat(1, 3);
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                mProgressView.setCurrentProgress((Float) animation.getAnimatedValue());

            }
        });
        mValueAnimator.setDuration(2000);
        mValueAnimator.start();
    }

    private void initListener() {

        mProgressView2.setCurrentProgress(0);
        findViewById(R.id.btn_test2)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count = 0;
                        mProgressView2.setCurrentProgress(0);
                        mHandler.sendEmptyMessage(0);
                        startAnim2();
                    }
                });
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
