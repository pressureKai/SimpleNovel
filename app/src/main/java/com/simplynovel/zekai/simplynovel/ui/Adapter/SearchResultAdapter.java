package com.simplynovel.zekai.simplynovel.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.domain.SearchBookMsg;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.List;

/**
 * Created by 15082 on 2018/9/10.
 */

public class SearchResultAdapter extends BaseAdapter {
    private List<SearchBookMsg> searchBookMsgs;
    public SearchResultAdapter(List<SearchBookMsg> searchBookMsgs){
        this.searchBookMsgs = searchBookMsgs;
    }

    @Override
    public int getCount() {
        return searchBookMsgs.size();
    }

    @Override
    public SearchBookMsg getItem(int position) {
        return searchBookMsgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = UIUtils.inflate(R.layout.search_result_item);
            viewHolder = new ViewHolder();
            viewHolder.img_search_result = convertView.findViewById(R.id.img_search_result);
            viewHolder.tv_result_bookname = convertView.findViewById(R.id.tv_result_bookname);
            viewHolder.tv_result_author = convertView.findViewById(R.id.tv_result_author);
            viewHolder.tv_result_type = convertView.findViewById(R.id.tv_result_type);
            viewHolder.tv_result_state = convertView.findViewById(R.id.tv_result_state);
            viewHolder.tv_result_des = convertView.findViewById(R.id.tv_result_des);
            viewHolder.tv_result_updatedes = convertView.findViewById(R.id.tv_result_updatedes);
            viewHolder.tv_result_updatetime = convertView.findViewById(R.id.tv_result_updatetime);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(UIUtils.getContext()).load(searchBookMsgs.get(position).getImg()).error(R.drawable.back).into( viewHolder.img_search_result);
        viewHolder.tv_result_bookname.setText(searchBookMsgs.get(position).getBookName());
        viewHolder.tv_result_author.setText(searchBookMsgs.get(position).getAuthor());
        viewHolder.tv_result_type.setText(searchBookMsgs.get(position).getType());
        viewHolder.tv_result_state.setText(searchBookMsgs.get(position).getState());
        viewHolder.tv_result_des.setText(searchBookMsgs.get(position).getDes());
        viewHolder.tv_result_updatedes.setText(searchBookMsgs.get(position).getUpdatedes());
        viewHolder.tv_result_updatetime.setText(searchBookMsgs.get(position).getUpdatetime());

        return convertView;
    }
    public static class ViewHolder{
        ImageView img_search_result;
        TextView tv_result_bookname;
        TextView tv_result_author;
        TextView tv_result_type;
        TextView tv_result_state;
        TextView tv_result_des;
        TextView tv_result_updatedes;
        TextView tv_result_updatetime;
    }
}
