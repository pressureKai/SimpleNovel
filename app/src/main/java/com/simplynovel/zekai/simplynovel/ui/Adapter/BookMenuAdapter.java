package com.simplynovel.zekai.simplynovel.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by 15082 on 2018/8/21.
 */

public class BookMenuAdapter extends BaseAdapter {
    private  ArrayList<String> ChapterName;
    public BookMenuAdapter(ArrayList<String> ChapterName){
        this.ChapterName = ChapterName;
    }
    @Override
    public int getCount() {
        return ChapterName.size();
    }

    @Override
    public String getItem(int position) {
        return ChapterName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(UIUtils.getContext(), R.layout.menu_item,null);
            viewHolder = new ViewHolder();
            viewHolder.chapter_id = convertView.findViewById(R.id.chapter_id);
            viewHolder.chapter_name = convertView.findViewById(R.id.chapter_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
           viewHolder.chapter_id.setText(position + 1 +"");
           viewHolder.chapter_name.setText(ChapterName.get(position));
        return convertView;
    }
    public class ViewHolder{
        TextView chapter_id;
        TextView chapter_name;
    }
}
