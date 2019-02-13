package com.example.fangsf.customview.custom_Behavior_12;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fangsf.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomBehaviorActivity extends AppCompatActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.rcView)
    RecyclerView mRcView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.bottom_tab_layout)
    LinearLayout mBottomTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_behavior);
        ButterKnife.bind(this);

        mRcView.setLayoutManager(new LinearLayoutManager(this));
        mRcView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_textview, parent, false);

                ViewHolder viewHolder = new ViewHolder(view);

                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 50;
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
