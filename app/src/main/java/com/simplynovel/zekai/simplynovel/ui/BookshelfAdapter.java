package com.simplynovel.zekai.simplynovel.ui;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by 15082 on 2018/8/16.
 */

public class BookshelfAdapter extends BaseAdapter {
    ArrayList<Drawable> bookImg;
    ArrayList<String> bookName;
    ArrayList<String> bookUpdate;

    public BookshelfAdapter(ArrayList<Drawable> bookImg, ArrayList<String> bookName, ArrayList<String> bookUpdate) {
        this.bookImg = bookImg;
        this.bookName = bookName;
        this.bookUpdate = bookUpdate;
    }

    @Override
    public int getCount() {
        return bookName.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if(position < bookName.size()){
            return bookName.get(position);
        }else{
            return null;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = (LinearLayout) View.inflate(UIUtils.getContext(), R.layout.bookshelf_content_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.content_img);
            viewHolder.bookName = convertView.findViewById(R.id.content_bookname);
            viewHolder.update = convertView.findViewById(R.id.content_update);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(position < bookName.size()){
            viewHolder.imageView.setImageDrawable(bookImg.get(position));
            viewHolder.bookName.setText(bookName.get(position));
            viewHolder.update.setText(bookUpdate.get(position));
        }else{
            viewHolder.imageView.setImageDrawable(bookImg.get(position -1));
            viewHolder.bookName.setText("添加你喜欢的小说");
            viewHolder.bookName.setTextSize(UIUtils.getDimen(R.dimen.book_shelf_tab));
            viewHolder.bookName.setTextColor(UIUtils.getColor(R.color.text_color));
            viewHolder.update.setText("");
        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView imageView;
        public TextView bookName;
        public TextView update;
    }
}
