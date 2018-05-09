package com.example.fangsfmac.customview.kuguo_slideMenu_08;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import com.example.fangsfmac.customview.R;

/**
 * Created by fangsf on 2018/5/9.
 * Useful:
 */

public class SlideMenuLayout extends HorizontalScrollView {

    private int mMenuRightMargin = 50;

    public SlideMenuLayout(Context context) {
        this(context,null);
    }

    public SlideMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlideMenuLayout);

        mMenuRightMargin = array.getDimensionPixelSize(R.styleable.SlideMenuLayout_menuRightMargin, mMenuRightMargin);

        array.recycle();

    }
}
