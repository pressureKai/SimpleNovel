package com.simplynovel.zekai.simplynovel.ui.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.simplynovel.zekai.simplynovel.ui.BookBaseFragment;

import java.util.List;


/**
 * Created by 15082 on 2018/8/2.
 */

public class BookRankTypeAdapter extends FragmentStatePagerAdapter {
    private List<BookBaseFragment> fragments;
    private List<String> strings;

    public BookRankTypeAdapter(FragmentManager fm, List<String> strings, List<BookBaseFragment> fragments) {
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
