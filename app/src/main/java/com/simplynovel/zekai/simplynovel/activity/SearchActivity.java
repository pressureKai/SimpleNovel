package com.simplynovel.zekai.simplynovel.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ListView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.ui.Adapter.SearchHistoryAdapter;
import com.simplynovel.zekai.simplynovel.ui.Adapter.SearchHotWordAdpter;

import java.util.ArrayList;

/**
 * Created by 15082 on 2018/9/4.
 */

public class SearchActivity extends AppCompatActivity {
    private  RecyclerView search_rcy;
    private ListView search_history;
    private ArrayList<String> searchhotword;
    private ArrayList<String> searchHistory;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initData();
        initUI();
    }

    private void initData() {
       searchhotword = new ArrayList<String>();
       searchhotword.add("...");
       searchHistory = new ArrayList<String>();
    }

    private void initUI() {
        search_rcy = (RecyclerView) findViewById(R.id.search_rcy);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL);
        search_rcy.setLayoutManager(layoutManager);
        search_rcy.setAdapter(new SearchHotWordAdpter(searchhotword,this));


        search_history = (ListView) findViewById(R.id.search_history);
        search_history.setAdapter(new SearchHistoryAdapter(searchHistory));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }
}
