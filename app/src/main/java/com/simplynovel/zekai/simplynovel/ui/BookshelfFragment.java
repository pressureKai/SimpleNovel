package com.simplynovel.zekai.simplynovel.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.activity.BookBodyActivity;
import com.simplynovel.zekai.simplynovel.ui.refresh.OnRefreshListener;
import com.simplynovel.zekai.simplynovel.ui.refresh.RefreshLayout;
import com.simplynovel.zekai.simplynovel.utils.ConstantValues;
import com.simplynovel.zekai.simplynovel.utils.SharedPreferenceUtils;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.ArrayList;


/**
 * Created by 15082 on 2018/6/19.
 */

public class BookshelfFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ArrayList<Drawable> bookImg;
    private ArrayList<String> bookName;
    private ArrayList<String> bookUpdate;
    private RefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        showToolBar();
        initData();
        View view = inflater.inflate(R.layout.bookshlf_fragment, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {

        refreshLayout = (RefreshLayout) view.findViewById(R.id.bookshelf_refresh);
        if (!SharedPreferenceUtils.getBoolean(getContext(), ConstantValues.ISAUTOREFRESH, false)) {
            refreshLayout.autoRefresh(500);
        }
        if (refreshLayout != null) {
            // 刷新状态的回调
            refreshLayout.setRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.refreshComplete();
                        }
                    }, 1000);
                }
            });
        }
        ListView bookshelf_content =(ListView) view.findViewById(R.id.bookshelf_content);
        bookshelf_content.setOnItemClickListener(this);
        bookshelf_content.setAdapter(new BookshelfAdapter(bookImg, bookName, bookUpdate));

    }

    private void showToolBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (!activity.getSupportActionBar().isShowing()) {
            activity.getSupportActionBar().show();
        }
    }
    private void initData() {
        bookImg = new ArrayList<Drawable>();
        bookName = new ArrayList<String>();
        bookUpdate = new ArrayList<String>();
        for(int i = 0; i< 10; i++){
            bookImg.add(UIUtils.getDrawable(R.mipmap.baseline_account_box_black_24));
            bookName.add("test");
            bookUpdate.add("now");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferenceUtils.putBoolean(getContext(), ConstantValues.ISAUTOREFRESH, true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(UIUtils.getContext(), BookBodyActivity.class);
        if(position < bookName.size()){
            intent.putExtra("bookName",bookName.get(position));
            startActivity(intent);
        }

    }
}
