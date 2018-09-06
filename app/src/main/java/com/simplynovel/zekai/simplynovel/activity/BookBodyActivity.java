package com.simplynovel.zekai.simplynovel.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.simplynovel.zekai.simplynovel.R;

/**
 * Created by 15082 on 2018/8/16.
 */

public class BookBodyActivity extends AppCompatActivity implements View.OnClickListener  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookbody);
        initUI();
    }


    @SuppressLint("RestrictedApi")
    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.person_account_toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("我的账户");
        }
        toolbar.setOnClickListener(this);
        ViewPager text = (ViewPager) findViewById(R.id.book_body_text);
        text.setOnClickListener(this);
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
         switch (v.getId()){
             case R.id.book_body_text :
                 hideToolBar();
                 break;
         }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bookbody_top_toolbar,menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }
    private void hideToolBar() {
        if(this.getSupportActionBar().isShowing()){
            this.getSupportActionBar().hide();
        }else{
            this.getSupportActionBar().show();
        }
    }
}
