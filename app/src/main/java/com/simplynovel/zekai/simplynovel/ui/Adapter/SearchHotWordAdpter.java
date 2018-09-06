package com.simplynovel.zekai.simplynovel.ui.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.activity.SearchActivity;
import com.simplynovel.zekai.simplynovel.activity.SearchResultActivity;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by 15082 on 2018/9/4.
 */

public class SearchHotWordAdpter extends RecyclerView.Adapter<SearchHotWordAdpter.ViewHolder> {
    private  ArrayList<String> searchhotword;
    private  SearchActivity searchActivity;

    public SearchHotWordAdpter(ArrayList<String> searchhotword, SearchActivity searchActivity){
         this.searchhotword = searchhotword;
         this.searchActivity = searchActivity;
    }
    @Override
    public SearchHotWordAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = UIUtils.inflate(R.layout.searchhotword_item);
        final ViewHolder holder = new ViewHolder(view);
        holder.word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchword = holder.word.getText().toString();
                Intent intent = new Intent(UIUtils.getContext(),SearchResultActivity.class);
                intent.putExtra("word", searchword);
                searchActivity.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.word.setText(searchhotword.get(position));
    }


    @Override
    public int getItemCount() {
        return searchhotword.size();
    }

    static  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView word;
        public ViewHolder(View itemView) {
            super(itemView);
            word = (TextView) itemView.findViewById(R.id.word);
        }
    }
}
