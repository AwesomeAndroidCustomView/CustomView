package com.example.fangsfmac.customview;

import android.view.View;

import com.example.fangsfmac.customview.colorTrackTextView_02.ColorTrackTextViewActivity;
import com.example.fangsfmac.customview.kuguo_slideMenu_08.KuGuoSlideMenuActivity;
import com.example.fangsfmac.customview.letterIndexView_06.LetterIndexActivity;
import com.example.fangsfmac.customview.loadingView_03.LoadingViewActivity;
import com.example.fangsfmac.customview.powerLoadingView_05.PowerBatteryLevelActivity;
import com.example.fangsfmac.customview.progressLoading_01.ProgressLoadingActivity;
import com.example.fangsfmac.customview.qqSlideMenu_09.QQSlideMenuActivity;
import com.example.fangsfmac.customview.ratingbarView_04.RatingBarViewActivity;
import com.example.fangsfmac.customview.tagLayout_07.TagLayoutActivity;

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

    public void click3(View view) {
        jumpAct(LoadingViewActivity.class);
    }

    public void click4(View view) {
        jumpAct(RatingBarViewActivity.class);
    }


    public void click5(View view) {
        jumpAct(PowerBatteryLevelActivity.class);
    }

    public void click6(View view) {
        jumpAct(LetterIndexActivity.class);
    }

    public void click7(View view) {
        jumpAct(TagLayoutActivity.class);
    }

    public void click8(View view) {
        jumpAct(KuGuoSlideMenuActivity.class);
    }

    public void click9(View view) {
        jumpAct(QQSlideMenuActivity.class);
    }
}
