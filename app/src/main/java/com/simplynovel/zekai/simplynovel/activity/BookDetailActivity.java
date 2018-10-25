package com.simplynovel.zekai.simplynovel.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.domain.BookBodyUri;
import com.simplynovel.zekai.simplynovel.domain.ForumData;
import com.simplynovel.zekai.simplynovel.domain.SearchBookMsg;
import com.simplynovel.zekai.simplynovel.ui.Adapter.ForumAdapter;
import com.simplynovel.zekai.simplynovel.utils.HttpUtils;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

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
 * Created by 15082 on 2018/10/15.
 */

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_bookdetail_result;
    private TextView tv_bookdetail_author;
    private TextView tv_bookdetail_bookname;
    private TextView tv_bookdetail_state;
    private TextView tv_resultbookdetail_type;
    private TextView tv_bookdetail_des;
    private ListView lt_bookdetail_forum;
    private RequestBody requestBody;
    private ProgressDialog progressDialog;
    private SearchBookMsg searchBookMsg;
    private List<ForumData> forumDatas;
    private List<BookBodyUri> bookBodyUris;
    private Button read;
    private Button add;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0 :
                    progressDialog.dismiss();
                    if(forumDatas != null){
                        lt_bookdetail_forum.setAdapter(new ForumAdapter(forumDatas));
                    }
                    break;
                case 1:
                    if(bookBodyUris != null){
                        read.setText("开始阅读");
                        Intent intent = new Intent(UIUtils.getContext(),BookBodyActivity.class);
                        intent.putExtra("bookChapterList", (Serializable) bookBodyUris);
                        startActivity(intent);
                    }
                    break;
                case 3:
//                    if(bookBodyUris != null){
                        read.setText("开始阅读");
//                        Intent intent = new Intent(UIUtils.getContext(),BookBodyActivity.class);
//                        intent.putExtra("bookChapterList", (Serializable) bookBodyUris);
//                        startActivity(intent);
//                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detial);
        initUI();
        iniData();
    }

    private void iniData() {
        requestBody = new FormBody.Builder()
                .add("bookName", searchBookMsg.getBookName())
                .add("bookUrl", searchBookMsg.getHref())
                .build();
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        HttpUtils.sendOkHttpRequest("http://10.0.3.2:8888/SimpleNovel/SelectFromQiDianForum", requestBody,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<ForumData>>() {
                        }.getType();
                        forumDatas = new ArrayList<>();
                        forumDatas = gson.fromJson(string,listType);
                        handler.sendEmptyMessage(0);
                    }
                });

    }

    @SuppressLint("RestrictedApi")
    private void initUI() {
        Toolbar toolbar = findViewById(R.id.person_account_toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        Serializable bookMsg = extras.getSerializable("BookMsg");
        searchBookMsg = (SearchBookMsg) bookMsg;
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(searchBookMsg.getBookName());
        }
        img_bookdetail_result = findViewById(R.id.img_bookdetail_result);
        tv_bookdetail_author = findViewById(R.id.tv_bookdetail_author);
        tv_bookdetail_bookname = findViewById(R.id.tv_bookdetail_bookname);
        tv_bookdetail_state = findViewById(R.id.tv_bookdetail_state);
        tv_resultbookdetail_type = findViewById(R.id.tv_resultbookdetail_type);
        tv_bookdetail_des = findViewById(R.id.tv_bookdetail_des);
        lt_bookdetail_forum = findViewById(R.id.lt_bookdetail_forum);
        read = findViewById(R.id.read);
        add = findViewById(R.id.add);
        read.setOnClickListener(this);
        add.setOnClickListener(this);
        Glide.with(this).load(searchBookMsg.getImg()).error(R.drawable.back).into(img_bookdetail_result);
        tv_bookdetail_author.setText(searchBookMsg.getAuthor());
        tv_bookdetail_bookname.setText(searchBookMsg.getBookName());
        tv_bookdetail_state.setText(searchBookMsg.getState());
        tv_resultbookdetail_type.setText(searchBookMsg.getType());
        tv_bookdetail_des.setText(searchBookMsg.getDes());
        toolbar.setOnClickListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.history_clear, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.read :
                 read.setText("正在打开...");
                 Thread thread = new Thread(new Runnable() {
                     @Override
                     public void run() {
                         Looper.myLooper().prepare();
                         getBookMsg();
                     }
                 });
                 thread.start();
                 break;
             case R.id.add:
                 Toast.makeText(UIUtils.getContext(),"添加成功",Toast.LENGTH_SHORT).show();
                 break;
         }
    }

    private void getBookMsg() {
        bookBodyUris = new ArrayList<>();
        requestBody = new FormBody.Builder()
                .add("bookName", searchBookMsg.getBookName())
                .add("bookAuthor", searchBookMsg.getAuthor())
                .build();
        progressDialog = new ProgressDialog(this);
        HttpUtils.sendOkHttpRequest("http://10.0.3.2:8888/SimpleNovel/search", requestBody,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<BookBodyUri>>() {
                        }.getType();
                        bookBodyUris = new ArrayList<>();
                        bookBodyUris = gson.fromJson(string,listType);
                        if(bookBodyUris != null){
                            for(int i =0; i < bookBodyUris.size(); i++){
                                Log.i("currentPosition",bookBodyUris.get(i).getTitle());
                            }
                            handler.sendEmptyMessage(1);
                        }else{
                            handler.sendEmptyMessage(3);
                        }

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }
}
