package com.simplynovel.zekai.simplynovel.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.domain.BookRankMsg;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15082 on 2018/9/11.
 */

public class BookTypeContentAdapter extends BaseAdapter {
    private List<List<BookRankMsg>> listCurrentBookRankMsgs;
    private List<BookRankMsg> currentBookRankMsgs;
    public BookTypeContentAdapter(List<List<BookRankMsg>> listCurrentBookRankMsgs){
        this.listCurrentBookRankMsgs = listCurrentBookRankMsgs;
        currentBookRankMsgs = new ArrayList<>();
        for(int i =0; i< listCurrentBookRankMsgs.size(); i++){
            for(int index = 0; index < listCurrentBookRankMsgs.get(i).size(); index++){
                currentBookRankMsgs.add( listCurrentBookRankMsgs.get(i).get(index));
            }
        }

    }
    @Override
    public int getCount() {
        return currentBookRankMsgs.size();
    }

    @Override
    public BookRankMsg getItem(int position) {
        return currentBookRankMsgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = UIUtils.inflate(R.layout.book_type_content_item);
            viewHolder = new ViewHolder();
            viewHolder.img_search_result = convertView.findViewById(R.id.img_search_result);
            viewHolder.tv_result_bookname = convertView.findViewById(R.id.tv_result_bookname);
            viewHolder.tv_result_author = convertView.findViewById(R.id.tv_result_author);
            viewHolder.tv_result_type = convertView.findViewById(R.id.tv_result_type);
            viewHolder.tv_result_state = convertView.findViewById(R.id.tv_result_state);
            viewHolder.tv_result_des = convertView.findViewById(R.id.tv_result_des);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(UIUtils.getContext()).load(currentBookRankMsgs.get(position).getImg()).error(R.drawable.back).into( viewHolder.img_search_result);
        viewHolder.tv_result_bookname.setText(currentBookRankMsgs.get(position).getBookName());
        viewHolder.tv_result_author.setText(currentBookRankMsgs.get(position).getAuthor());
        viewHolder.tv_result_type.setText(currentBookRankMsgs.get(position).getType());
        viewHolder.tv_result_state.setText(currentBookRankMsgs.get(position).getState());
        viewHolder.tv_result_des.setText(currentBookRankMsgs.get(position).getDes());

        return convertView;
    }
    public static class ViewHolder{
        ImageView img_search_result;
        TextView tv_result_bookname;
        TextView tv_result_author;
        TextView tv_result_type;
        TextView tv_result_state;
        TextView tv_result_des;
    }
}
