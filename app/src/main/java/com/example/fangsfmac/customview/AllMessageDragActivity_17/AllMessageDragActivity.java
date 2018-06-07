package com.example.fangsfmac.customview.AllMessageDragActivity_17;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fangsfmac.customview.R;

public class AllMessageDragActivity extends AppCompatActivity {

    private Button mButton;
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_message_drag);

        mButton = findViewById(R.id.btn);
        mTextView = findViewById(R.id.tvMes);



        MessageBubbleView.attach(mButton, new BubbleMessageTouchListener.BubbleDisappearListener() {
            @Override
            public void dismiss(View view) {

            }
        });

        MessageBubbleView.attach(mTextView, new BubbleMessageTouchListener.BubbleDisappearListener() {
            @Override
            public void dismiss(View view) {

            }
        });
        // view 拖拽消失后的 监听回掉

    }
}
