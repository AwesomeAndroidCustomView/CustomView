package com.example.fangsfmac.customview.LiveStar_18;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.fangsfmac.customview.R;

public class LiveStarActivity extends AppCompatActivity {

    private LoveStarLayout mStarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_star);

        mStarLayout = findViewById(R.id.star);
    }

    public void click1(View view) {
        mStarLayout.addLove();
    }
}
