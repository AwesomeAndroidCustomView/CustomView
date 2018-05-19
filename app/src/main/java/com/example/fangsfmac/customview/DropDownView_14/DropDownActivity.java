package com.example.fangsfmac.customview.DropDownView_14;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.fangsfmac.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DropDownActivity extends AppCompatActivity {

    @BindView(R.id.listDataScreen)
    ListDataScreenView mListDataScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_down);
        ButterKnife.bind(this);

        mListDataScreen.setAdapter(new ListDataScreenAdapter(this));

    }
}
