package com.simplynovel.zekai.simplynovel.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.domain.BookTypeData;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15082 on 2018/9/11.
 */

public class TypeMenuAdapter extends BaseAdapter {
    private int selectItem = 0;
    private List<String> menus;
    private BookTypeData bookTypeData;

    public TypeMenuAdapter(BookTypeData bookTypeData) {
        this.bookTypeData = bookTypeData;
        menus = new ArrayList<>();
        for(int i =0; i< bookTypeData.getTypeNames().size(); i++){
            menus.add(bookTypeData.getTypeNames().get(i));
        }
    }

    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public String getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = UIUtils.inflate(R.layout.type_menu_item);
            viewHolder = new ViewHolder();
            viewHolder.tx_search_history = convertView.findViewById(R.id.tx_search_history);
            viewHolder.red_line = convertView.findViewById(R.id.red_line);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(selectItem == position){
            viewHolder.red_line.setVisibility(View.VISIBLE);
        }else{
            viewHolder.red_line.setVisibility(View.INVISIBLE);
        }
        viewHolder.tx_search_history.setText(menus.get(position));
        return convertView;
    }

    public void setSelectItem(int newPosition) {
        selectItem = newPosition;
    }

    public static class ViewHolder {
        TextView tx_search_history;
        TextView red_line;
    }
}
