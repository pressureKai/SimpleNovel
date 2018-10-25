package com.simplynovel.zekai.simplynovel.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.domain.BookRankMsg;
import com.simplynovel.zekai.simplynovel.ui.Adapter.BookTypeContentAdapter;
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
 * Created by 15082 on 2018/9/22.
 */

public class TypeContentActivity extends AppCompatActivity {
    private String title;
    private String contentHref;
    private ListView lt_search_result;
    private List<List<BookRankMsg>> bookRankMsgs;
    private Toolbar toolbar;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if(bookRankMsgs != null) {
                        lt_search_result.setAdapter(new BookTypeContentAdapter(bookRankMsgs));
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_content);
        iniUI();
        initData();

    }

    private void initData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(title);
        }
        contentHref = intent.getStringExtra("contentHref");
        connect();
    }

    private void connect() {
        RequestBody requestBody = new FormBody.Builder()
                .add("bookUrl",contentHref)
                .build();
        HttpUtils.sendOkHttpRequest("http://10.0.3.2:8888/SimpleNovel/GetBookFromType", requestBody,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<List<BookRankMsg>>>() {
                        }.getType();
                        bookRankMsgs = new ArrayList<>();
                        bookRankMsgs = gson.fromJson(string,listType);
                        handler.sendEmptyMessage(0);
                    }
                });
    }

    private void iniUI() {
        toolbar = findViewById(R.id.person_account_toolbar);
        lt_search_result = findViewById(R.id.lt_search_result);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
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
}
