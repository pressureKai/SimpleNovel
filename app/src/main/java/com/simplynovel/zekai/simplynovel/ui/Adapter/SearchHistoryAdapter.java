package com.simplynovel.zekai.simplynovel.ui.Adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.activity.SearchActivity;
import com.simplynovel.zekai.simplynovel.activity.SearchResultActivity;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by 15082 on 2018/9/5.
 */

public class SearchHistoryAdapter extends BaseAdapter implements View.OnClickListener {
    private ArrayList<String> history;
    private SearchActivity searchActivity;

    public SearchHistoryAdapter(ArrayList<String> history, SearchActivity searchActivity){
        this.history = history;
        this.searchActivity = searchActivity;
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
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = UIUtils.inflate(R.layout.search_history_item);
            viewHolder = new ViewHolder();
            viewHolder.tx_search_history = convertView.findViewById(R.id.tx_search_history);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tx_search_history.setText(history.get(getCount()-1-position));
        viewHolder.tx_search_history.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tx_search_history:
                TextView textView = (TextView) v;
                String text = textView.getText().toString();
                Intent intent = new Intent(searchActivity, SearchResultActivity.class);
                intent.putExtra("word", text);
                searchActivity.startActivity(intent);
                break;
        }
    }

    public static class ViewHolder{
        TextView tx_search_history;
    }
}
