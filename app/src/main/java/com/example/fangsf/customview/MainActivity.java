package com.example.fangsf.customview;

import android.view.View;

import com.example.fangsf.customview.AllMessageDragActivity_17.AllMessageDragActivity;
import com.example.fangsf.customview.DropDownView_14.DropDownActivity;
import com.example.fangsf.customview.FiveEightCityLoad_13.LoadViewActivity;
import com.example.fangsf.customview.LiveStar_18.LiveStarActivity;
import com.example.fangsf.customview.colorTrackTextView_02.ColorTrackTextViewActivity;
import com.example.fangsf.customview.coordinatorlayout25.CoorMainActivity;
import com.example.fangsf.customview.coordinatorlayout25.CoordinatorScrollActivity;
import com.example.fangsf.customview.custom_Behavior_12.CustomBehaviorActivity;
import com.example.fangsf.customview.foldView_10.FoldViewActivity;
import com.example.fangsf.customview.kuguo_parallax_19.KuGouParallaxActivity;
import com.example.fangsf.customview.kuguo_slideMenu_08.KuGuoSlideMenuActivity;
import com.example.fangsf.customview.letterIndexView_06.LetterIndexActivity;
import com.example.fangsf.customview.loadingView_03.LoadingViewActivity;
import com.example.fangsf.customview.loadingView_15.LoadingActivity;
import com.example.fangsf.customview.messageDragView_16.MessageDragActivity;
import com.example.fangsf.customview.powerLoadingView_05.PowerBatteryLevelActivity;
import com.example.fangsf.customview.progressLoading_01.ProgressLoadingActivity;
import com.example.fangsf.customview.qqSlideMenu_09.QQSlideMenuActivity;
import com.example.fangsf.customview.ratingbarView_04.RatingBarViewActivity;
import com.example.fangsf.customview.recyclerview_23.RecyclerViewAnalysisActivity;
import com.example.fangsf.customview.statusBar_11.StatusActivity;
import com.example.fangsf.customview.tagLayout_07.TagLayoutActivity;
import com.example.fangsf.customview.timeControl_21.TimeControlActivity;
import com.example.fangsf.customview.toolbar_22.ToolBarActivity;
import com.example.fangsf.customview.yahuLoadingView_20.YahuLoadingActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

    }

    public void click1(View view) {
        startActivity(ProgressLoadingActivity.class);
    }

    public void click2(View view) {
        startActivity(ColorTrackTextViewActivity.class);
    }

    public void click3(View view) {
        startActivity(LoadingViewActivity.class);
    }

    public void click4(View view) {
        startActivity(RatingBarViewActivity.class);
    }


    public void click5(View view) {
        startActivity(PowerBatteryLevelActivity.class);
    }

    public void click6(View view) {
        startActivity(LetterIndexActivity.class);
    }

    public void click7(View view) {
        startActivity(TagLayoutActivity.class);
    }

    public void click8(View view) {
        startActivity(KuGuoSlideMenuActivity.class);
    }

    public void click9(View view) {
        startActivity(QQSlideMenuActivity.class);
    }

    public void click10(View view) {
        startActivity(FoldViewActivity.class);
    }

    public void click11(View view) {
        startActivity(StatusActivity.class);
    }

    public void click12(View view) {
        startActivity(CustomBehaviorActivity.class);
    }

    public void click13(View view) {
        startActivity(LoadViewActivity.class);
    }

    public void click14(View view) {
        startActivity(DropDownActivity.class);

    }

    public void click15(View view) {
        startActivity(LoadingActivity.class);
    }

    public void click16(View view) {
        startActivity(MessageDragActivity.class);
    }

    public void click17(View view) {
        startActivity(AllMessageDragActivity.class);
    }

    public void click18(View view) {
        startActivity(LiveStarActivity.class);
    }

    public void click19(View view) {
        startActivity(KuGouParallaxActivity.class);
    }

    public void click21(View view) {
        startActivity(TimeControlActivity.class);
    }

    public void click20(View view) {
        startActivity(YahuLoadingActivity.class);
    }

    public void click22(View view) {
        startActivity(ToolBarActivity.class);
    }

    public void click23(View view) {
        startActivity(RecyclerViewAnalysisActivity.class);
    }

    public void coordinatorlayout(View view) {
        startActivity(CoorMainActivity.class);
    }
}
