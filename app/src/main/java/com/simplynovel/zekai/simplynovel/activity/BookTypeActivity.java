package com.simplynovel.zekai.simplynovel.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.ui.Adapter.TypeContentAdapter;
import com.simplynovel.zekai.simplynovel.ui.Adapter.TypeMenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15082 on 2018/9/11.
 */

public class BookTypeActivity extends AppCompatActivity {
    private ListView lv_menu;
    private ListView lv_content;
    private TypeMenuAdapter typeMenuAdapter;
    private TypeContentAdapter typeContentAdapter;
    private List<Integer> tag;
    private int newPosition = 0;
    private int currentMenuItem = 0;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    typeMenuAdapter.setSelectItem(newPosition);
                    typeMenuAdapter.notifyDataSetInvalidated();

                    lv_content.setAdapter(typeContentAdapter);
                    lv_content.setSelection(newPosition);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_type);
        tag = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            tag.add(i);
        }
        initUI();
        initAdapter();
    }

    private void initAdapter() {

        typeMenuAdapter = new TypeMenuAdapter();
        typeContentAdapter = new TypeContentAdapter();
        lv_menu.setAdapter(typeMenuAdapter);
        lv_content.setAdapter(typeContentAdapter);
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.person_account_toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("分类");
        }
        lv_menu = findViewById(R.id.lv_menu);
        lv_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView red_line = view.findViewById(R.id.red_line);
                red_line.setVisibility(View.VISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                TextView red_line = parent.findViewById(R.id.red_line);
                red_line.setVisibility(View.VISIBLE);
            }
        });
        lv_content = findViewById(R.id.lv_content);
        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newPosition = position;
                handler.sendEmptyMessage(0);
            }
        });
        lv_content.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int current = tag.indexOf(firstVisibleItem);
                if (currentMenuItem != current && current >= 0) {
                    // 联动  menuListView
                    currentMenuItem = current;
                    typeMenuAdapter.setSelectItem(currentMenuItem);
                    typeMenuAdapter.notifyDataSetInvalidated();
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }


}
