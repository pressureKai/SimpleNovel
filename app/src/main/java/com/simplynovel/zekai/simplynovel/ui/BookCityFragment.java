package com.simplynovel.zekai.simplynovel.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.activity.BookRackActivity;
import com.simplynovel.zekai.simplynovel.activity.SearchActivity;
import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.ui.Adapter.BookListViewPagerAdapter;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15082 on 2018/6/19.
 */


@SuppressLint("ValidFragment")
public class BookCityFragment extends Fragment implements View.OnClickListener {
    private BookRackActivity bookRackActivity;
    private LinearLayout ll_search;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> strings = new ArrayList<>();
    private List<BookListFragment> fragments = new ArrayList<BookListFragment>();
    private String[] tabTitles = {"玄幻", "奇幻","武侠","仙侠","都市", "军事","其他"};



    @SuppressLint("ValidFragment")
    public BookCityFragment(BookRackActivity bookRackActivity){
         this.bookRackActivity = bookRackActivity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hideToolBar();
        View view = inflater.inflate(R.layout.bookcity_fragment,container,false);
        initData();
        initUI(view);
        return view;

    }

    private void initData() {
        for (String tabTitle : tabTitles) {
            BookListFragment fragment = new BookListFragment();
            fragments.add(fragment);
            strings.add(tabTitle);
        }

    }

    private void initUI(View view) {
        ll_search = view.findViewById(R.id.ll_search);
        ll_search.setOnClickListener(this);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new BookListViewPagerAdapter(bookRackActivity.getSupportFragmentManager(), strings, fragments));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View tabView = (View) tab.getCustomView().getParent();
                TextView title = (TextView) tabView.findViewById(R.id.tv_tab_title);
                title.setTextColor(getResources().getColor(R.color.book_list_select));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View tabView = (View) tab.getCustomView().getParent();
                TextView title = (TextView) tabView.findViewById(R.id.tv_tab_title);
                title.setTextColor(getResources().getColor(R.color.book_list_unselect));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
                View tabView = (View) tab.getCustomView().getParent();
                TextView title = (TextView) tabView.findViewById(R.id.tv_tab_title);
                if (i == 0) {
                    title.setTextColor(getResources().getColor(R.color.book_list_select));
                }else{
                    title.setTextColor(getResources().getColor(R.color.book_list_unselect));
                }
            }
        }
    }

    private void hideToolBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity.getSupportActionBar().isShowing()){
            activity.getSupportActionBar().hide();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_search:
                Intent intent = new Intent(UIUtils.getContext(),SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
    public View getTabView(int position) {
        View view =UIUtils.inflate(R.layout.book_city_fragment);
        TextView tv = (TextView) view.findViewById(R.id.tv_tab_title);
        tv.setText(tabTitles[position]);
        return view;
    }

}
