package com.simplynovel.zekai.simplynovel.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.domain.BookRankMsg;
import com.simplynovel.zekai.simplynovel.domain.SearchBookMsg;
import com.simplynovel.zekai.simplynovel.ui.Adapter.SearchResultAdapter;
import com.simplynovel.zekai.simplynovel.utils.HttpUtils;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 15082 on 2018/9/5.
 */

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private RequestBody requestBody;
    private EditText search_ed;
    private  String word;
    private ListView lt_search_result;
    private List<SearchBookMsg> searchBookMsgs;
    private ProgressDialog progressDialog;
    private TextView tv_search_result;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0 :
                    progressDialog.dismiss();
                    if(searchBookMsgs != null){
                        lt_search_result.setAdapter(new SearchResultAdapter(searchBookMsgs));
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initUI();
        initData();
    }

    private void initUI() {
        search_ed = (EditText) findViewById(R.id.search_ed);
        search_ed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                search_ed.setFocusable(true);
                search_ed.setFocusableInTouchMode(true);
                return false;
            }
        });
        lt_search_result = (ListView) findViewById(R.id.lt_search_result);
        lt_search_result.setOnItemClickListener(this);
        tv_search_result = (TextView)findViewById(R.id.tv_search_result);
        tv_search_result.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        word = extras.getString("word");
        search_ed.setText(word);
        requestBody = new FormBody.Builder()
                .add("bookName", word)
                .build();
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        HttpUtils.sendOkHttpRequest("http://10.0.3.2:8888/SimpleNovel/SearchForQidian", requestBody,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<SearchBookMsg>>() {
                        }.getType();
                        searchBookMsgs = new ArrayList<>();
                        searchBookMsgs = gson.fromJson(string,listType);
                        handler.sendEmptyMessage(0);
                    }
                });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search_result:


                String text = search_ed.getText().toString();
                requestBody = new FormBody.Builder()
                        .add("bookName", text)
                        .build();
                progressDialog = new ProgressDialog(this);
                progressDialog.show();
                HttpUtils.sendOkHttpRequest("http://10.0.3.2:8888/SimpleNovel/SearchForQidian", requestBody,
                        new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String string = response.body().string();
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<SearchBookMsg>>() {
                                }.getType();
                                searchBookMsgs = new ArrayList<>();
                                searchBookMsgs = gson.fromJson(string,listType);
                                handler.sendEmptyMessage(0);
                            }
                        });

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          Intent intent = new Intent(this,BookDetailActivity.class);
          intent.putExtra("BookMsg", (Serializable) searchBookMsgs.get(position));
          startActivity(intent);
    }
}
