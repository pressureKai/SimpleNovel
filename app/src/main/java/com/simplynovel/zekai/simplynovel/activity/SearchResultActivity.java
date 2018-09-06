package com.simplynovel.zekai.simplynovel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.simplynovel.zekai.simplynovel.R;

/**
 * Created by 15082 on 2018/9/5.
 */

public class SearchResultActivity extends AppCompatActivity {
    private EditText search_ed;
    private  String word;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        initData();
        initUI();
    }

    private void initUI() {
        search_ed = (EditText) findViewById(R.id.search_ed);
        search_ed.setText(word);
    }

    private void initData() {

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        word = extras.getString("word");

    }
}
