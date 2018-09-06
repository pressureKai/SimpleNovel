package com.simplynovel.zekai.simplynovel.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplynovel.zekai.simplynovel.R;

/**
 * Created by 15082 on 2018/6/19.
 */

public class BookFindFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hideToolBar();
        return inflater.inflate(R.layout.bookfind_fragment,container,false);
    }
    private void hideToolBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity.getSupportActionBar().isShowing()){
            activity.getSupportActionBar().hide();
        }
    }
}
