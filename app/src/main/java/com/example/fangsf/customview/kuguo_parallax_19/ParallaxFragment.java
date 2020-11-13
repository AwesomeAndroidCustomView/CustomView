package com.example.fangsf.customview.kuguo_parallax_19;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.LayoutInflaterFactory;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.fangsf.customview.R;
import com.example.fangsf.customview.kuguo_slideMenu_08.CompatViewInflater;
import com.example.fangsf.customview.kuguo_slideMenu_08.ParallaxTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Femto-iMac-003 on 2018/6/11.
 */

public class ParallaxFragment extends Fragment implements LayoutInflaterFactory {

    public static final String LAYOUT_ID_KEY = "LAYOUT_ID_KEY";
    private CompatViewInflater mCompatViewInflater;

    // 存放 所有需要 位移的view
    private List<View> mParallaxViews = new ArrayList<>();

    // 位移的属性
    private int[] mParallaxAttrs = new int[]{R.attr.translationXIn,
            R.attr.translationXOut, R.attr.translationYIn, R.attr.translationYOut};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int layout = bundle.getInt(LAYOUT_ID_KEY);


        // 这里 需要解析 布局中 自定义的属性, 需要拦截系统的创建, 自己手动解析布局, 将属性值取出来
        inflater = inflater.cloneInContext(getActivity());  // LayoutInflater 是一个单例设计模式, 所有view的创建都会是该fragment 创建的,所以克隆一个
        LayoutInflaterCompat.setFactory(inflater, this);


        return inflater.inflate(layout, container, false);

    }


    /**
     * 拦截系统解析布局, 自动解析 布局
     *
     * @param parent
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        // If the Factory didn't handle it, let our createView() method try
        View view = createView(parent, name, context, attrs);


        if (view != null) {

            // 解析 需要的属性
            analysisAttrs(view, context, attrs);
        }


        return view;  // 需要返回自己解析的view
    }

    private void analysisAttrs(View view, Context context, AttributeSet attrs) {
        // 解析属性
        TypedArray array = context.obtainStyledAttributes(attrs, mParallaxAttrs); // 解析数组

        if (array != null && array.getIndexCount() != 0) {
            int n = array.getIndexCount();
            ParallaxTag tag = new ParallaxTag();
            for (int i = 0; i < n; i++) {
                int attr = array.getIndex(i);
                switch (attr) {
                    case 0:
                        tag.translationXIn = array.getFloat(attr, 0f);
                        break;
                    case 1:
                        tag.translationXOut = array.getFloat(attr, 0f);
                        break;
                    case 2:
                        tag.translationYIn = array.getFloat(attr, 0f);
                        break;
                    case 3:
                        tag.translationYOut = array.getFloat(attr, 0f);
                        break;
                }
                // 自定义属性, 自定义tag, 一一绑定
                view.setTag(R.id.parallax_tag, tag);
                mParallaxViews.add(view);
            }
        }

        array.recycle();
    }

    /**
     * @return 位移的view
     */
    public List<View> getParallaxViews() {
        return mParallaxViews;
    }

    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;

        if (mCompatViewInflater == null) {
            mCompatViewInflater = new CompatViewInflater();
        }

        // We only want the View to inherit it's context if we're running pre-v21
        final boolean inheritContext = isPre21 && true
                && shouldInheritContext((ViewParent) parent);

        return mCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                isPre21, /* Only read android:theme pre-L (L+ handles this anyway) */
                true /* Read read app:theme as a fallback at all times for legacy reasons */
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (!(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }
}
