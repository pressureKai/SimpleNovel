package com.simplynovel.zekai.simplynovel.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.domain.DataTab;
import com.simplynovel.zekai.simplynovel.ui.BookAccountFragment;
import com.simplynovel.zekai.simplynovel.ui.BookCityFragment;
import com.simplynovel.zekai.simplynovel.ui.BookCommunityFragment;
import com.simplynovel.zekai.simplynovel.ui.BookFindFragment;
import com.simplynovel.zekai.simplynovel.ui.BookRackMoreItem;
import com.simplynovel.zekai.simplynovel.ui.BookshelfFragment;
import com.simplynovel.zekai.simplynovel.utils.ConstantValues;
import com.simplynovel.zekai.simplynovel.utils.SharedPreferenceUtils;


/**
      *  应用主界面
 */
public class BookRackActivity extends AppCompatActivity {
    private int lastPosition = 5;
    private Toolbar toolbar;
    private TabLayout bottom_tab_bookshelf_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        SharedPreferenceUtils.putBoolean(getApplicationContext(), ConstantValues.ISAUTOREFRESH, false);
        initUI();
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.bookrack_toolbar);
        setSupportActionBar(toolbar);
        bottom_tab_bookshelf_layout = (TabLayout) findViewById(R.id.bottom_tab_bookshelf_layout);
        bottom_tab_bookshelf_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int count = bottom_tab_bookshelf_layout.getTabCount();
                for (int i = 0; i < count; i++) {
                    View customView = bottom_tab_bookshelf_layout.getTabAt(i).getCustomView();
                    ImageView icon = (ImageView) customView.findViewById(R.id.tab_icon);
                    TextView des = (TextView) customView.findViewById(R.id.tab_des);
                    if (i == tab.getPosition()) {
                        icon.setImageResource(DataTab.mTabIconPress[i]);
                        des.setTextColor(getResources().getColor(android.R.color.black));
                        onItemSelected(i);
                    } else {
                        icon.setImageResource(DataTab.mTabIcon[i]);
                        des.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i = 0; i < 5; i++) {
            bottom_tab_bookshelf_layout.addTab(bottom_tab_bookshelf_layout.newTab().setCustomView(DataTab.getTabView(i)));
        }
    }

    private void onItemSelected(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new BookshelfFragment();
                break;
            case 1:
                fragment = new BookCityFragment(this);
                break;
            case 2:
                fragment = new BookCommunityFragment();
                break;
            case 3:
                fragment = new BookFindFragment();
                break;
            case 4:
                fragment = new BookAccountFragment();
                break;

        }


        if (fragment != null) {

            if (lastPosition == 5 || position == 0) {
                getSupportFragmentManager().beginTransaction().replace(R.id.bookshelf_contain, fragment).commit();
                lastPosition = position;
            } else if (position > lastPosition) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.next_in_anim,
                                R.anim.next_out_anim
                        ).replace(R.id.bookshelf_contain, fragment).commit();
                lastPosition = position;
            } else {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.pre_in_anim,
                                R.anim.pre_out_anim
                        ).replace(R.id.bookshelf_contain, fragment).commit();
                lastPosition = position;
            }


        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bookrack_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gift:

                break;
            case R.id.search:

                break;
            case R.id.history:
                Intent intent = new Intent(this, AccountReadHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.more:
                showMore();
                break;
            default:
        }
        return true;
    }

    private void showMore() {
        BookRackMoreItem bookrackMoreItem = new BookRackMoreItem(BookRackActivity.this);
        Window dialogWindow = bookrackMoreItem.getWindow();
        int dialogHeight = toolbar.getHeight();
        WindowManager.LayoutParams layoutParams = bookrackMoreItem.getWindow().getAttributes();
        layoutParams.y = dialogHeight;
        layoutParams.alpha = 1;
        dialogWindow.setAttributes(layoutParams);
        bookrackMoreItem.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
