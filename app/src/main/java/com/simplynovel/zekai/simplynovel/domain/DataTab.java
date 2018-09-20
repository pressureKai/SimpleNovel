package com.simplynovel.zekai.simplynovel.domain;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

/**
 * Created by 15082 on 2018/6/17.
 */

public class DataTab {
    // R.mipmap.baseline_message_black_24,论坛图标
    public static final int[]  mTabIconPress = new int[]{
            R.mipmap.baseline_book_black_24,
            R.mipmap.baseline_shopping_basket_black_24,
            R.mipmap.round_youtube_searched_for_black_24,
            R.mipmap.baseline_account_box_black_24};
    //  R.mipmap.outline_message_black_24, 论坛图标
    public static final int[] mTabIcon = new int[]{
            R.mipmap.outline_book_black_24,
            R.mipmap.outline_shopping_basket_black_24,
            R.mipmap.baseline_search_black_24,
            R.mipmap.outline_account_box_black_24
    };
    public static String[] mTabTitle = new String[]{
            "书架",
            "书城",
            "发现",
            "我的"
    };
    public static View getTabView(int position){
        View tab_item = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.tab_item, null);
        ImageView icon = (ImageView) tab_item.findViewById(R.id.tab_icon);
        icon.setImageResource(mTabIcon[position]);
        TextView textView = (TextView) tab_item.findViewById(R.id.tab_des);
        textView.setText(mTabTitle[position]);
        textView.setTextColor(UIUtils.getContext().getResources().getColor(android.R.color.darker_gray));
        return tab_item;
    }
}
