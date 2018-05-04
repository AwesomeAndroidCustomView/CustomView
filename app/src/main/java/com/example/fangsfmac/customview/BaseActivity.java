package com.example.fangsfmac.customview;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fangsfmac on 2018/4/22.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();

    private Unbinder mBind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());

        mBind = ButterKnife.bind(this);

        init();
    }

    protected abstract int bindLayout();

    protected abstract void init();

    public void jumpAct(Class clz) {

        startActivity(new Intent(this, clz));

    }

    public void toast(String mes) {
        Toast.makeText(this, "" + mes, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBind != null) {
            mBind.unbind();
        }
    }
}
