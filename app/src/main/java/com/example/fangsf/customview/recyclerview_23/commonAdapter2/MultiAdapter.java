package com.example.fangsf.customview.recyclerview_23.commonAdapter2;

import android.view.View;
import android.widget.Toast;

import com.example.fangsf.customview.R;
import com.example.fangsf.customview.recyclerview_23.ChatData;
import com.example.fangsf.customview.recyclerview_23.commonAdapter2.common.MultiTypeSupport;
import com.example.fangsf.customview.recyclerview_23.commonAdapter2.common.RecyclerCommonAdapter;
import com.example.fangsf.customview.recyclerview_23.commonAdapter2.common.ViewHolder;

import java.util.List;

/**
 * Created by fangsf on 2019/2/24
 * Useful:
 */
public class MultiAdapter extends RecyclerCommonAdapter<ChatData> {

    public MultiAdapter(List<ChatData> chatData, MultiTypeSupport typeSupport) {
        super(chatData, typeSupport);
    }

    @Override
    protected void convert(ViewHolder holder,ChatData item) {

        if (item.isMe) {

            holder.setText(R.id.tv_me, item.content);

            holder.setOnClickListener(R.id.tv_me, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "me", Toast.LENGTH_SHORT).show();
                }
            });

        }else {

            holder.setOnClickListener(R.id.tv_friend, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "friend", Toast.LENGTH_SHORT).show();
                }
            });

            holder.setText(R.id.tv_friend, item.content);


        }

    }
}
