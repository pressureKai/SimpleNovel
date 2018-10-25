package com.simplynovel.zekai.simplynovel.ui.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.activity.BookTypeActivity;
import com.simplynovel.zekai.simplynovel.activity.TypeContentActivity;
import com.simplynovel.zekai.simplynovel.domain.BookTypeData;
import com.simplynovel.zekai.simplynovel.ui.PictureView;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15082 on 2018/9/11.
 */

public class TypeContentAdapter extends BaseAdapter {
    private List<List<String>> menus;
    private List<BookTypeData> bookTypeDatas;
    private BookTypeActivity bookTypeActivity;
    private int currentPosition = 0;
    public TypeContentAdapter(BookTypeActivity bookTypeActivity,List<BookTypeData> bookTypeDatas){
        this.bookTypeActivity = bookTypeActivity;
        this.bookTypeDatas = bookTypeDatas;
        menus = new ArrayList<>();
        for(int i=1; i< bookTypeDatas.size(); i++){
            menus.add(bookTypeDatas.get(i).getChildTypeNames());
        }
    }
    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public List<String> getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        currentPosition = position;
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = UIUtils.inflate(R.layout.type_item);
            viewHolder = new ViewHolder();
            viewHolder.rv_type = convertView.findViewById(R.id.rv_type);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.rv_type.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = UIUtils.inflate(R.layout.type_content_item);
                RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
                return recyclerViewHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
                recyclerViewHolder.picture_view.setLeftBackground("https://bookcover.yuewen.com/qdbimg/349573/1011168558/180");
                recyclerViewHolder.picture_view.setRightBackground("https://bookcover.yuewen.com/qdbimg/349573/1010473379/180");
                recyclerViewHolder.picture_view.setCenterBackground("https://bookcover.yuewen.com/qdbimg/349573/1010601896/180");
                if(currentPosition == 0){
                    recyclerViewHolder.tx_type.setText(bookTypeDatas.get(1).getChildTypeNames().get(position));
                }
                if(currentPosition == 1){
                    recyclerViewHolder.tx_type.setText(bookTypeDatas.get(2).getChildTypeNames().get(position));
                }
                if(currentPosition == 2){
                    recyclerViewHolder.tx_type.setText(bookTypeDatas.get(3).getChildTypeNames().get(position));
                }
                recyclerViewHolder.tx_type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(bookTypeActivity,TypeContentActivity.class);
                        TextView clickView = (TextView) v;


                          for(int i =1; i < bookTypeDatas.size(); i++){
                               if(bookTypeDatas.get(i).getChildTypeNames().size() >= position +1){
                                   if(bookTypeDatas.get(i).getChildTypeNames().get(position).equals(clickView.getText().toString())){
                                       intent.putExtra("title",bookTypeDatas.get(i).getChildTypeNames().get(position));
                                       intent.putExtra("contentHref",bookTypeDatas.get(i).getChildTypes().get(position));
                                   }
                               }
                          }

                        bookTypeActivity.startActivity(intent);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bookTypeDatas.get(currentPosition + 1).getChildTypeNames().size();
            }
        });
        viewHolder.rv_type.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

        return convertView;
    }
    public static class ViewHolder{
       RecyclerView rv_type;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        PictureView picture_view;
        TextView tx_type;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            picture_view = itemView.findViewById(R.id.picture_view);
            tx_type = itemView.findViewById(R.id.tx_type);
        }
    }
}
