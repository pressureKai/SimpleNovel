package com.simplynovel.zekai.simplynovel.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.domain.BookRankMsg;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.List;

/**
 * Created by 15082 on 2018/9/8.
 */

public class pictureListViewAdapter extends BaseAdapter implements View.OnClickListener {
    private List<String> rankTypeName;
    private List<List<BookRankMsg>> bookRankMagRanks;
    public pictureListViewAdapter(List<List<BookRankMsg>> bookRankMagRanks, List<String> rankTypeName){
        this.rankTypeName = rankTypeName;
        this.bookRankMagRanks = bookRankMagRanks;
    }
    @Override
    public int getCount() {
        return rankTypeName.size();
    }

    @Override
    public String getItem(int position) {
        return rankTypeName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if(convertView == null){
            convertView = UIUtils.inflate(R.layout.book_picture_listview_item);
            viewHolder = new ViewHolder();
            viewHolder.book_img_main = convertView.findViewById(R.id.book_img_main);
            viewHolder.book_img_1 = convertView.findViewById(R.id.book_img_1);
            viewHolder.book_img_2 = convertView.findViewById(R.id.book_img_2);
            viewHolder.book_img_3 = convertView.findViewById(R.id.book_img_3);
            viewHolder.book_img_4 = convertView.findViewById(R.id.book_img_4);
            viewHolder.book_img_5 = convertView.findViewById(R.id.book_img_5);
            viewHolder.book_rank_type_name = convertView.findViewById(R.id.book_rank_type_name);
            viewHolder.book_name = convertView.findViewById(R.id.book_name);
            viewHolder.book_des = convertView.findViewById(R.id.book_des);
            viewHolder.book_author = convertView.findViewById(R.id.book_author);
            viewHolder.book_name_1 = convertView.findViewById(R.id.book_name_1);
            viewHolder.book_name_2 = convertView.findViewById(R.id.book_name_2);
            viewHolder.book_name_3 = convertView.findViewById(R.id.book_name_3);
            viewHolder.book_name_4 = convertView.findViewById(R.id.book_name_4);
            viewHolder.book_name_5 = convertView.findViewById(R.id.book_name_5);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.book_rank_type_name.setText(rankTypeName.get(position));
        Glide.with(UIUtils.getContext()).load(bookRankMagRanks.get(position).get(0).getImg()).error(R.drawable.back).into(viewHolder.book_img_main);
        Glide.with(UIUtils.getContext()).load(bookRankMagRanks.get(position).get(0).getImg()).error(R.drawable.back).into(viewHolder.book_img_1);
        Glide.with(UIUtils.getContext()).load(bookRankMagRanks.get(position).get(1).getImg()).error(R.drawable.back).into(viewHolder.book_img_2);
        Glide.with(UIUtils.getContext()).load(bookRankMagRanks.get(position).get(2).getImg()).error(R.drawable.back).into(viewHolder.book_img_3);
        Glide.with(UIUtils.getContext()).load(bookRankMagRanks.get(position).get(3).getImg()).error(R.drawable.back).into(viewHolder.book_img_4);
        Glide.with(UIUtils.getContext()).load(bookRankMagRanks.get(position).get(4).getImg()).error(R.drawable.back).into(viewHolder.book_img_5);

        viewHolder.book_img_main.setOnClickListener(this);
        viewHolder.book_img_1.setOnClickListener(this);
        viewHolder.book_img_2.setOnClickListener(this);
        viewHolder.book_img_3.setOnClickListener(this);
        viewHolder.book_img_4.setOnClickListener(this);
        viewHolder.book_img_5.setOnClickListener(this);


        viewHolder.book_name.setText(bookRankMagRanks.get(position).get(0).getBookName());
        viewHolder.book_des.setText(bookRankMagRanks.get(position).get(0).getDes());
        viewHolder.book_author.setText(bookRankMagRanks.get(position).get(0).getAuthor());

        viewHolder.book_name_1.setText(bookRankMagRanks.get(position).get(0).getBookName());
        viewHolder.book_name_2.setText(bookRankMagRanks.get(position).get(1).getBookName());
        viewHolder.book_name_3.setText(bookRankMagRanks.get(position).get(2).getBookName());
        viewHolder.book_name_4.setText(bookRankMagRanks.get(position).get(3).getBookName());
        viewHolder.book_name_5.setText(bookRankMagRanks.get(position).get(4).getBookName());
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.book_img_main:

                Toast.makeText(UIUtils.getContext(),"click",Toast.LENGTH_SHORT).show();
                break;
            case R.id.book_img_1:
                break;
            case R.id.book_img_2:
                break;
            case R.id.book_img_3:
                break;
            case R.id.book_img_4:
                break;
            case R.id.book_img_5:
                break;
        }
    }

    public static class ViewHolder{
        ImageView book_img_main;
        ImageView book_img_1;
        ImageView book_img_2;
        ImageView book_img_3;
        ImageView book_img_4;
        ImageView book_img_5;
        TextView book_rank_type_name;
        TextView book_name;
        TextView book_des;
        TextView book_author;
        TextView book_name_1;
        TextView book_name_2;
        TextView book_name_3;
        TextView book_name_4;
        TextView book_name_5;

    }
}
