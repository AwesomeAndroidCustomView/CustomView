package com.example.fangsf.customview.colorTrackTextView_02;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fangsf.customview.BaseActivity;
import com.example.fangsf.customview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ColorTrackTextViewActivity extends BaseActivity {

    @BindView(R.id.llIndicator)
    LinearLayout mIndicatorContainer;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华"};

    private List<ColorTrackTextView> mIndicatorList;

    @Override
    protected int bindLayout() {
        return R.layout.activity_color_track_text_view;
    }

    @Override
    protected void init() {
        mIndicatorList = new ArrayList<>();

        initIndicator();

        initViewPager();
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }
        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (positionOffset > 0) {

                    // 左边的文字
                    ColorTrackTextView left = mIndicatorList.get(position);
                    left.setDirection(ColorTrackTextView.Direction.DIRECTION_LEFT_RIGHT);
                    left.setCurrentProgress(1 - positionOffset);


                    //右边的文字
                    try {
                        ColorTrackTextView right = mIndicatorList.get(position + 1);
                        right.setDirection(ColorTrackTextView.Direction.DIRECTION_RIGHT_LEFT);
                        right.setCurrentProgress(positionOffset);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 默认选择第一个
        ColorTrackTextView colorTrackTextView = mIndicatorList.get(0);
        colorTrackTextView.setDirection(ColorTrackTextView.Direction.DIRECTION_LEFT_RIGHT);
        colorTrackTextView.setCurrentProgress(1);

    }


    /**
     * 初始化可变色的指示器
     */
    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            // 动态添加颜色跟踪的TextView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            // 设置两种颜色
            colorTrackTextView.setOriginColor(Color.BLACK);
            colorTrackTextView.setChangeColor(Color.RED);
            colorTrackTextView.setText(items[i]);
            colorTrackTextView.setLayoutParams(params);
            // 把新的加入LinearLayout容器
            mIndicatorContainer.addView(colorTrackTextView);
            // 加入集合
            mIndicatorList.add(colorTrackTextView);
        }
    }

}
