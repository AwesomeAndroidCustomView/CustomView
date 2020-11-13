package com.example.fangsf.customview.colorTrackTextView_02;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fangsf.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fangsfmac on 2018/4/22.
 */

public class ItemFragment extends Fragment {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    Unbinder unbinder;

    public static ItemFragment newInstance(String item) {
        ItemFragment fragment = new ItemFragment();

        Bundle bundle = new Bundle();
        bundle.putString("title", item);

        fragment.setArguments(bundle);


        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, null, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        String title = bundle.getString("title");

        mTvTitle.setText(title + "");

        return view;
    }


}
