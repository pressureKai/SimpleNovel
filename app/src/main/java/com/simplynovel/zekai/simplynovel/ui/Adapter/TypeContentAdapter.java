package com.simplynovel.zekai.simplynovel.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15082 on 2018/9/11.
 */

public class TypeContentAdapter extends BaseAdapter {
    private List<String> menus;

    public TypeContentAdapter(){
        menus = new ArrayList<>();
        menus.add("圣墟");
        menus.add("圣墟");
        menus.add("圣墟");
        menus.add("圣墟");
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
        if(convertView == null){
            convertView = UIUtils.inflate(R.layout.type_content_item);
            viewHolder = new ViewHolder();
            viewHolder.tx_search_history = convertView.findViewById(R.id.tx_search_history);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tx_search_history.setText(menus.get(position) +"" +position);
        return convertView;
    }
    public static class ViewHolder{
        TextView tx_search_history;
    }
}
