package com.simplynovel.zekai.simplynovel.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.domain.BookRankMsg;
import com.simplynovel.zekai.simplynovel.domain.SearchHistory;
import com.simplynovel.zekai.simplynovel.ui.Adapter.SearchHistoryAdapter;
import com.simplynovel.zekai.simplynovel.ui.Adapter.SearchHotWordAdpter;
import com.simplynovel.zekai.simplynovel.utils.HttpUtils;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 15082 on 2018/9/4.
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView search_rcy;
    private ListView search_history;
    private ArrayList<String> searchhotword;
    private ArrayList<String> searchHistory;
    private RequestBody requestBody;
    private TextView bt_search;
    private EditText search_ed;
    private TextView del_history;
    private List<List<BookRankMsg>> bookRankMagRanks;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Random random = new Random();
                    int index = random.nextInt(bookRankMagRanks.size());
                    searchhotword = new ArrayList<>();
                    for (int i = 0; i < bookRankMagRanks.get(index).size(); i++) {
                        searchhotword.add(bookRankMagRanks.get(index).get(i).getBookName());
                    }
                    initAdapter();
                    break;
                case 1:
                    initHistoryAdapter();

                    break;
            }
        }
    };

    private void initHistoryAdapter() {
        search_history.setAdapter(new SearchHistoryAdapter(searchHistory,this));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initUI();
        initData();
    }

    private void initAdapter() {
        search_rcy.setAdapter(new SearchHotWordAdpter(searchhotword, this));
    }

    private void initData() {

        searchhotword = new ArrayList<>();
        searchHistory = new ArrayList<>();
        requestBody = new FormBody.Builder()
                .add("type", "玄幻")
                .add("state","0")
                .build();
        HttpUtils.sendOkHttpRequest("http://10.0.3.2:8888/SimpleNovel/SelectBookFromDb", requestBody,
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
                        bookRankMagRanks = new ArrayList<>();
                        bookRankMagRanks = gson.fromJson(string, listType);
                        if (bookRankMagRanks != null && bookRankMagRanks.size() > 0) {
                            handler.sendEmptyMessage(0);
                        }
                    }
                });

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<SearchHistory> searchAll = DataSupport.findAll(SearchHistory.class);
                for (SearchHistory searchHistory1 : searchAll) {
                    searchHistory.add(searchHistory1.getBookName());
                }
                handler.sendEmptyMessage(1);
            }
        }).start();


    }

    private void initUI() {
        search_rcy = (RecyclerView) findViewById(R.id.search_rcy);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        search_rcy.setLayoutManager(layoutManager);
        search_history = (ListView) findViewById(R.id.search_history);
        bt_search = (TextView) findViewById(R.id.bt_search);
        search_ed = (EditText) findViewById(R.id.search_ed);
        search_ed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                search_ed.setFocusableInTouchMode(true);
                search_ed.setFocusable(true);
                return false;
            }
        });
        bt_search.setOnClickListener(this);
        search_history.setFocusable(false);
        del_history = (TextView) findViewById(R.id.del_history);
        del_history.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_search:
                String searchword = search_ed.getText().toString();
                if (!searchword.equals("")) {
                    Intent intent = new Intent(this, SearchResultActivity.class);
                    intent.putExtra("word", searchword);
                    startActivity(intent);

                    List<SearchHistory> all = DataSupport.findAll(SearchHistory.class);
                    for (SearchHistory searchHistory1 : all) {
                        if (searchHistory1.getBookName().equals(searchword)) {
                            DataSupport.deleteAll(SearchHistory.class, "bookName = ?", searchword);
                        }
                    }

                    SearchHistory searchHistory = new SearchHistory();
                    searchHistory.setBookName(searchword);
                    searchHistory.save();
                } else {
                    Toast.makeText(UIUtils.getContext(), "输入内容不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.del_history:
                DataSupport.deleteAll(SearchHistory.class);
                searchHistory = new ArrayList<>();
                handler.sendEmptyMessage(1);
                break;
        }
    }

    @Override
    protected void onRestart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<SearchHistory> searchAll = DataSupport.findAll(SearchHistory.class);
                if (searchHistory != null) {
                    searchHistory = new ArrayList<>();
                    for (SearchHistory searchHistory1 : searchAll) {
                        String searchStr = searchHistory1.getBookName();
                        searchHistory.add(searchStr);
                    }
                    handler.sendEmptyMessage(1);
                }

            }
        }).start();


        if(bookRankMagRanks != null){
            handler.sendEmptyMessage(0);
        }


        super.onRestart();
    }
}
