package com.example.fangsfmac.customview.kuguo_parallax_19;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.fangsfmac.customview.R;
import com.example.fangsfmac.customview.kuguo_slideMenu_08.ParallaxTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Femto-iMac-003 on 2018/6/11.
 */

public class ParallaxViewPager extends ViewPager {

    private List<ParallaxFragment> mFragmentList;

    public ParallaxViewPager(Context context) {
        this(context, null);
    }

    public ParallaxViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        mFragmentList = new ArrayList<>();
    }

    public void setLayout(FragmentManager fm, int[] res) {
        mFragmentList.clear();


        // viewPager -> adapter  设置fragment
        for (int layout : res) {

            ParallaxFragment fragment = new ParallaxFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ParallaxFragment.LAYOUT_ID_KEY, layout);
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
        }

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("TAG", "onPageScrolled: " + position);

                //解析 布局信息
                analysisAttrs(position, positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        setAdapter(new ParallaxAdapter(fm));
    }

    private void analysisAttrs(int position, int positionOffsetPixels) {
        // 左边的fragment
        ParallaxFragment outFragment = mFragmentList.get(position);
        Log.i("TAG", " outFragment: " + outFragment.toString() + " positionOffsetPixels " + positionOffsetPixels);
        // 获取该view 的 属性值
        List<View> parallaxViews = outFragment.getParallaxViews();
        for (View leftView : parallaxViews) {
            ParallaxTag tag = (ParallaxTag) leftView.getTag(R.id.parallax_tag);

            leftView.setTranslationX(-positionOffsetPixels * tag.translationXOut);
            leftView.setTranslationY(-positionOffsetPixels * tag.translationYOut);
        }


        try {
            // 右边的fragment
            ParallaxFragment inFragment = mFragmentList.get(position + 1);
            Log.i("TAG", " inFragment: " + inFragment.toString());
            parallaxViews = outFragment.getParallaxViews();
            for (View rightView : parallaxViews) {
                ParallaxTag tag = (ParallaxTag) rightView.getTag(R.id.parallax_tag);

                rightView.setTranslationX(getMeasuredWidth() - positionOffsetPixels * tag.translationXIn);
                rightView.setTranslationY(getMeasuredWidth() - positionOffsetPixels * tag.translationYIn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class ParallaxAdapter extends FragmentPagerAdapter {
        public ParallaxAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }


}
