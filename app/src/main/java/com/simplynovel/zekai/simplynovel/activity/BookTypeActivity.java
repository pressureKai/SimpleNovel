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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.domain.BookRankMsg;
import com.simplynovel.zekai.simplynovel.domain.BookTypeData;
import com.simplynovel.zekai.simplynovel.ui.Adapter.TypeContentAdapter;
import com.simplynovel.zekai.simplynovel.ui.Adapter.TypeMenuAdapter;
import com.simplynovel.zekai.simplynovel.utils.HttpUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private RequestBody requestBody;
    private List<BookTypeData> bookTypeDatas;
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
                case 1 :
                    tag = new ArrayList<>();
                    for (int i = 0; i < bookTypeDatas.size()-1; i++) {
                        tag.add(i);
                    }
                    initUI();
                    initAdapter(bookTypeDatas);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_type);
        connect();
    }

    private void connect() {
        requestBody = new FormBody.Builder()
                .build();
        HttpUtils.sendOkHttpRequest("http://10.0.3.2:8888/SimpleNovel/GetBookTypeFromQiDian", requestBody,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<BookTypeData>>() {
                        }.getType();
                        bookTypeDatas = new ArrayList<BookTypeData>();
                        bookTypeDatas = gson.fromJson(string, listType);
                        if (bookTypeDatas != null && bookTypeDatas.size() > 0) {
                            handler.sendEmptyMessage(1);
                        }
                    }
                });

    }

    private void initAdapter(List<BookTypeData> bookTypeDatas) {
        if(bookTypeDatas != null){
            if(bookTypeDatas.size() > 0){
                typeMenuAdapter = new TypeMenuAdapter(bookTypeDatas.get(0));
                typeContentAdapter = new TypeContentAdapter(this,bookTypeDatas);
                lv_menu.setAdapter(typeMenuAdapter);
                lv_content.setAdapter(typeContentAdapter);
            }
        }

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
