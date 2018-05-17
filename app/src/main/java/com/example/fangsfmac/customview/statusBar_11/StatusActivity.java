package com.example.fangsfmac.customview.statusBar_11;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.fangsfmac.customview.R;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

    }

    public void click1(View view) {
        SystemBarHelper.setBarColor(this, Color.RED);
    }

    public void click2(View view) {
        SystemBarHelper.setBarColor(this, Color.YELLOW);
    }

    public void click3(View view) {
        SystemBarHelper.setActivityTranslucent(this);
    }
}
