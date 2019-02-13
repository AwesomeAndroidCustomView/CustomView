package com.example.fangsf.customview.letterIndexView_06;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.fangsf.customview.BaseActivity;
import com.example.fangsf.customview.R;

import butterknife.BindView;

public class LetterIndexActivity extends BaseActivity implements LetterIndexView.SideBarTouchListener {


    @BindView(R.id.tvLetter)
    TextView mTvLetter;
    @BindView(R.id.letterIndex)
    LetterIndexView mLetterIndexView;

    @Override
    protected int bindLayout() {
        return R.layout.activity_letter_index;
    }

    @Override
    protected void init() {

        mLetterIndexView.setOnSideTouchListener(this);

    }

    @Override
    public void onTouch(final String letter, boolean isTouch) {
        if (isTouch) {
            mTvLetter.setVisibility(View.VISIBLE);
            mTvLetter.setText(letter);
        } else {
            // 延迟消失
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTvLetter.setVisibility(View.GONE);
                    mTvLetter.setText(letter);
                }
            }, 180);

        }
    }
}
