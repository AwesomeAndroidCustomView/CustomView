package com.example.fangsf.customview.foldView_10;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fangsf.customview.R;

import java.util.ArrayList;

public class FoldViewActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<String> mtitle = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fold_view);


        mListView = findViewById(R.id.listView);

        for (int i = 0; i < 30; i++) {
            mtitle.add("title " + i);
        }

        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mtitle.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_textview, parent, false);
                view.setText(mtitle.get(position));

                return view;
            }
        });
    }
}
