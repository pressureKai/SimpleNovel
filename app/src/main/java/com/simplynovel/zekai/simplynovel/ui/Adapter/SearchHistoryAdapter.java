package com.simplynovel.zekai.simplynovel.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by 15082 on 2018/9/5.
 */

public class SearchHistoryAdapter extends BaseAdapter {
    private ArrayList<String> history;

    public SearchHistoryAdapter(ArrayList<String> history){
        this.history = history;
    }
    @Override
    public int getCount() {
        return history.size();
    }

    @Override
    public String getItem(int position) {
        return history.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
