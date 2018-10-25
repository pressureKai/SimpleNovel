package com.simplynovel.zekai.simplynovel.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.domain.ForumData;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.CircleImageView;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.List;

/**
 * Created by 15082 on 2018/10/16.
 */

public class ForumAdapter extends BaseAdapter {
    private  List<ForumData> forumDatas;
    public ForumAdapter(List<ForumData> forumDatas){
        this.forumDatas = forumDatas;
    }
    @Override
    public int getCount() {
        return forumDatas.size();
    }

    @Override
    public ForumData getItem(int position) {
        return forumDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = UIUtils.inflate(R.layout.forum_item);
            viewHolder = new ViewHolder();
            viewHolder.img_forum = convertView.findViewById(R.id.img_forum);
            viewHolder.tv_forum_name = convertView.findViewById(R.id.tv_forum_name);
            viewHolder.tv_forum_content = convertView.findViewById(R.id.tv_forum_content);
            viewHolder.tv_forum_updatetime = convertView.findViewById(R.id.tv_forum_updatetime);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        Glide.with(UIUtils.getContext()).load(getItem(position).getImg()).error(R.drawable.back).into(viewHolder.img_forum);
        viewHolder.tv_forum_name.setText(getItem(position).getName());
        viewHolder.tv_forum_content.setText(getItem(position).getContent());
        viewHolder.tv_forum_updatetime.setText(getItem(position).getUpdateTime());
        return convertView;
    }
    public class ViewHolder{
        ImageView img_forum;
        TextView tv_forum_name;
        TextView tv_forum_content;
        TextView tv_forum_updatetime;
    }
}
