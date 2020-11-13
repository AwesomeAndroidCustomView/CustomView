package com.example.fangsf.customview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.SeekBar;

/**
 * Created by fangsf on 2019-09-05
 * Useful:
 */
public class SeekbarActivity extends AppCompatActivity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbar);

        SeekBar viewById = findViewById(R.id.seekbar);

        viewById.setProgressDrawableTiled(getResources().getDrawable(R.drawable.seekbar));

    }
}
