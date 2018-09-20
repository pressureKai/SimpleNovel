package com.simplynovel.zekai.simplynovel.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.activity.BookRackActivity;
import com.simplynovel.zekai.simplynovel.activity.BookRankActivity;
import com.simplynovel.zekai.simplynovel.activity.BookTypeActivity;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

/**
 * Created by 15082 on 2018/6/19.
 */

@SuppressLint("ValidFragment")
public class BookFindFragment extends Fragment implements View.OnClickListener {
    private TextView tv_book_find_type;
    private TextView tv_book_find_rank;
    private BookRackActivity bookRackActivity;

    public BookFindFragment(BookRackActivity bookRackActivity) {
        this.bookRackActivity = bookRackActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hideToolBar();
        View view = inflater.inflate(R.layout.bookfind_fragment, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        tv_book_find_type = view.findViewById(R.id.tv_book_find_type);
        tv_book_find_rank = view.findViewById(R.id.tv_book_find_rank);
        tv_book_find_type.setOnClickListener(this);
        tv_book_find_rank.setOnClickListener(this);
    }

    private void hideToolBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity.getSupportActionBar().isShowing()) {
            activity.getSupportActionBar().hide();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_book_find_type:
                Intent intent = new Intent(bookRackActivity, BookTypeActivity.class);
                bookRackActivity.startActivity(intent);
                break;
            case R.id.tv_book_find_rank:
                Intent intent_rank = new Intent(bookRackActivity, BookRankActivity.class);
                bookRackActivity.startActivity(intent_rank);
                break;
        }
    }
}
