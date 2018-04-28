package com.example.fangsfmac.customview.powerLoadingView_05;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.fangsfmac.customview.BaseActivity;
import com.example.fangsfmac.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PowerBatteryLevelActivity extends BaseActivity {

    @BindView(R.id.powerView)
    PowerProgressView mPowerView;
    private ValueAnimator mValueAnimator;

    @Override
    protected int bindLayout() {
        return R.layout.activity_power_battery_level;
    }

    @Override
    protected void init() {
        mPowerView.setMaxProgress(100);
        mValueAnimator = ObjectAnimator.ofFloat(1, 80);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPowerView.setCurrentProgress((Float) animation.getAnimatedValue());
            }
        });
        mValueAnimator.start();
    }

    public void click6(View view) {
        mValueAnimator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mValueAnimator.cancel();
    }
}
