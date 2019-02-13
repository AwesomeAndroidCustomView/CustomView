package com.example.fangsf.customview.statusBar_11;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by fangsf on 2018/5/16.
 * Useful:  状态栏辅助类
 */

public class SystemBarHelper {

    private static final String TAG = SystemBarHelper.class.getSimpleName();

    /**
     * 设置沉浸式状态栏
     */
    public static void setBarColor(Activity activity, int statusColor) {

        // > 安卓5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            activity.getWindow().setStatusBarColor(statusColor);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 小于安卓5.0,大于安卓4.4
            // 透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View statusView = new View(activity);
            statusView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getBarHeight(activity)));
            statusView.setBackgroundColor(statusColor);

            // 安卓系统4.0 需要适配 FitsSystemWindows true
            // 从系统中, 获取根布局, 代码中设置根布局 为 true
            ViewGroup contentView = activity.findViewById(android.R.id.content);  // setContentView, 源码
            contentView.setPadding(0, getBarHeight(activity), 0, 0);

            decorView.addView(statusView);
        }

    }

    public static int getBarHeight(Activity activity) {
        int resId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            Log.i(TAG, "getBarHeight: " + activity.getResources().getDimensionPixelOffset(resId));
            return activity.getResources().getDimensionPixelOffset(resId);
        }
        return 0;
    }


    /**
     * 设置activity全屏, 让activity内容,可以填充至状态栏(但是状态栏要显示)
     *
     * @param activity
     */
    public static void setBarTransparent(Activity activity) {

        // > 安卓5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 1,设置activity全屏,
            // 2,设置状态栏透明
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);

            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 小于安卓5.0,大于安卓4.4
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置导航栏隐藏
     *
     * @param activity
     */
    public static void setNavigationBarHide(Activity activity) {

        View decorView = activity.getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(option);

    }

    /**
     * 设置导航栏的颜色
     */
    public static void setNavigationBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setNavigationBarColor(color);
        }
    }


    /**
     * 设置activity全屏, 内容填充至 状态栏和导航栏,状态栏和导航栏 透明
     *
     * @param activity
     */
    public static void setBarAndNavBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(option);

            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 设置activity全屏, 不显示状态栏和导航栏
     *
     * @param activitiy
     */
    public static void setFullActivity(Activity activitiy) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = activitiy.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }

}
