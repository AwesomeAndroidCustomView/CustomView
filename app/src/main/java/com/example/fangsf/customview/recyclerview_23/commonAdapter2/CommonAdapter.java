package com.example.fangsf.customview.recyclerview_23.commonAdapter2;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fangsf.customview.R;
import com.example.fangsf.customview.recyclerview_23.commonAdapter2.common.RecyclerCommonAdapter;
import com.example.fangsf.customview.recyclerview_23.commonAdapter2.common.ViewHolder;

import java.util.List;

/**
 * Created by fangsf on 2019/2/22.
 * Useful:
 */
public class CommonAdapter extends RecyclerCommonAdapter<String> {

    public CommonAdapter(int layoutRes, List<String> strings) {
        super(layoutRes, strings);
    }

    @Override
    protected void convert(ViewHolder holder, final String item) {

        holder.setText(R.id.tvTitle, item);

//        holder.setOnClickListener(R.id.tvTitle, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "" + item, Toast.LENGTH_SHORT).show();
//            }
//        });

    }

}
