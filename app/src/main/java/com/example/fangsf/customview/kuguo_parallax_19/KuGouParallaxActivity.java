package com.example.fangsf.customview.kuguo_parallax_19;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.fangsf.customview.R;
import com.example.fangsf.customview.statusBar_11.SystemBarHelper;

/**
 * Created by Femto-iMac-003 on 2018/6/11.
 */

public class KuGouParallaxActivity extends AppCompatActivity {

    private ParallaxViewPager mParallaxVP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ku_gou_pararllax);

        mParallaxVP = findViewById(R.id.parallaxVP);
        SystemBarHelper.setBarColor(this, Color.parseColor("#ffff8800"));
        SystemBarHelper.setNavigationBarColor(this, Color.parseColor("#ffff8800"));


        mParallaxVP.setLayout(getSupportFragmentManager(),
                new int[]{R.layout.fragment_page_first, R.layout.fragment_page_second,
                        R.layout.fragment_page_third});

    }
}
