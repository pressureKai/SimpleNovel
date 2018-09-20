package com.simplynovel.zekai.simplynovel.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.domain.DataAccount;
import com.simplynovel.zekai.simplynovel.ui.Adapter.BookAccountAdapter;
import com.simplynovel.zekai.simplynovel.utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by 15082 on 2018/6/19.
 */

public class BookAccountFragment extends Fragment {

    private LinearLayoutManager linearLayoutManager;
    private BookAccountAdapter bookaccountAdapter;
    private DataAccount dataAccount = new DataAccount();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hideToolBar();
        initData();
        View view = inflater.inflate(R.layout.bookaccount_fragment, container, false);
        setRecyclerView(view);
        return view;
    }

    private void setRecyclerView(View view) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        bookaccountAdapter = new BookAccountAdapter(dataAccount,getContext());
        RecyclerView recyclerView = view.findViewById(R.id.book_account_rv);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(bookaccountAdapter);
    }

    private void initData() {
        String[] functionDes = new String[]{
                "AccountName",
                "99",
                "99",
        };
        String[]  rightDes = new String[]{
                "已保护",
                "99级"
        };
        dataAccount.setRightDes(rightDes);
        dataAccount.setFunctionDes(functionDes);
        dataAccount.setAccountImgRes(R.mipmap.baseline_account_box_black_24);
    }


    private void hideToolBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity.getSupportActionBar().isShowing()) {
            activity.getSupportActionBar().hide();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            connect();
        }else{

        }
    }

    private void connect() {

        RequestBody requestBody = new FormBody.Builder()
                .add("type", "")
                .add("state","0")
                .build();
        HttpUtils.sendOkHttpRequest("http://10.0.3.2:8888/SimpleNovel/SelectBookFromDb", requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

}
