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

}
