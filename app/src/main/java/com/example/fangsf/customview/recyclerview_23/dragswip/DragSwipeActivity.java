package com.example.fangsf.customview.recyclerview_23.dragswip;

import android.graphics.Color;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fangsf.customview.R;

import java.util.ArrayList;
import java.util.Collections;

public class DragSwipeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<String> mDatas;
    private DragAdapter mDragAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_swip);

        mRecyclerView = findViewById(R.id.rcView);

        init();

    }

    public void click1(View view) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void click2(View view) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    class DragAdapter extends RecyclerView.Adapter<DragAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.text_view, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextView.setText(mDatas.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView mTextView;

            public ViewHolder(View itemView) {
                super(itemView);

                mTextView = itemView.findViewById(R.id.textView);
            }
        }

    }


    private void init() {

        mDatas = new ArrayList<>();

        for (int i = 0; i < 22; i++) {
            mDatas.add(i + "");
        }

        mDragAdapter = new DragAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(mDragAdapter);

        //添加拖拽删除功能
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                // 设置可以拖动的方向

                int swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

                int dragFlags =0;

                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    swipeFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                } else {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                }


                return makeMovementFlags(dragFlags, swipeFlag);
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);

                // 选择的时候高亮
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) { //空闲的状态
                    viewHolder.itemView.setBackgroundColor(Color.GRAY);
                }

            }
//
//            /**
//             * 是否开启item长按拖拽功能
//             */
//            @Override
//            public boolean isLongPressDragEnabled() {
//                return true;
//            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // 在移动的时候,替换位置, 移动数据的位置

                // 获取原来的位置
                int fromPosition = viewHolder.getAdapterPosition();

                // 获取目标的位置
                int targetPosition = target.getAdapterPosition();
                // 替换位置
                mDragAdapter.notifyItemMoved(fromPosition, targetPosition);

                if (fromPosition < targetPosition) {
                    for (int i = fromPosition; i < targetPosition; i++) {
                        Collections.swap(mDatas, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > targetPosition; i--) {
                        Collections.swap(mDatas, i, i - 1);
                    }
                }

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 侧滑删除后执行方法
                int currentSwipe = viewHolder.getAdapterPosition();
                mDatas.remove(currentSwipe);
                mDragAdapter.notifyItemRemoved(currentSwipe);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                // 动画执行完毕,
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#D6D5D5"));
                // 由于recyclerView 复用的问题, 需要将view恢复回来
                ViewCompat.setTranslationX(viewHolder.itemView, 0);

            }
        });


        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }


}
