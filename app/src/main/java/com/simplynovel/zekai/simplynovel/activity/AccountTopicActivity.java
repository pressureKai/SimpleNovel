package com.simplynovel.zekai.simplynovel.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.ui.BookListFragment;
import com.simplynovel.zekai.simplynovel.ui.Adapter.BookListViewPagerAdapter;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15082 on 2018/7/25.
 */

public class AccountTopicActivity extends AppCompatActivity implements View.OnClickListener {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> strings = new ArrayList<>();
    private List<BookListFragment> fragments = new ArrayList<>();
    private String[] tabTitles = {"发布的话题", "收藏的话题"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_topic);
        initData();
        initUI();
    }

    private void initData() {
        for (String tabTitle : tabTitles) {
            BookListFragment fragment = new BookListFragment();
            fragments.add(fragment);
            strings.add(tabTitle);
        }

    }


    @SuppressLint("RestrictedApi")
    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.person_account_toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("话题");
        }
        toolbar.setOnClickListener(this);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new BookListViewPagerAdapter(getSupportFragmentManager(), strings, fragments));
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
                if (i == 0) {
                    View tabView = (View) tab.getCustomView().getParent();
                    TextView title = (TextView) tabView.findViewById(R.id.tv_tab_title);
                    title.setTextColor(getResources().getColor(R.color.book_list_select));
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
                break;
        }
        return true;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }
    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.book_topic_tab_fragment, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_tab_title);
        tv.setText(tabTitles[position]);
        return view;
    }
}
