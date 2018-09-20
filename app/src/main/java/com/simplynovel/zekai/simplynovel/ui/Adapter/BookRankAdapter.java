package com.simplynovel.zekai.simplynovel.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.List;

/**
 * Created by 15082 on 2018/9/12.
 */

public class BookRankAdapter extends BaseAdapter {
    private List<String> rankTypeNames;
    private int selectItem;
    public BookRankAdapter(List<String> rankTypeNames){
        this.rankTypeNames = rankTypeNames;
    }
    @Override
    public int getCount() {
        return rankTypeNames.size();
    }

    @Override
    public String getItem(int position) {
        return rankTypeNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = UIUtils.inflate(R.layout.book_rank_item);
            viewHolder = new ViewHolder();
            viewHolder.tx_search_history = convertView.findViewById(R.id.tx_search_history);
            viewHolder.red_line = convertView.findViewById(R.id.red_line);
            viewHolder.ll_book_rank = convertView.findViewById(R.id.ll_book_rank);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(selectItem == position){
            viewHolder.red_line.setVisibility(View.VISIBLE);
            viewHolder.ll_book_rank.setBackgroundColor(UIUtils.getColor(R.color.book_rank_tv));
        }else{
            viewHolder.red_line.setVisibility(View.INVISIBLE);
            viewHolder.ll_book_rank.setBackgroundColor(UIUtils.getColor(R.color.book_rank_tv_un_select));
        }
        viewHolder.tx_search_history.setText(rankTypeNames.get(position));
        return convertView;
    }

    public void setSelectItem(int newPosition) {
        selectItem = newPosition;
    }

    public static class ViewHolder {
        TextView tx_search_history;
        TextView red_line;
        LinearLayout ll_book_rank;
    }
}
