package com.simplynovel.zekai.simplynovel.ui.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.simplynovel.zekai.simplynovel.ui.BookListFragment;
import com.simplynovel.zekai.simplynovel.ui.BookRankFragment;

import java.util.List;


/**
 * Created by 15082 on 2018/8/2.
 */

public class BookRankViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<BookRankFragment> fragments;
    private List<String> strings;

    public BookRankViewPagerAdapter(FragmentManager fm, List<String> strings, List<BookRankFragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.strings = strings;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }
}
