package com.simplynovel.zekai.simplynovel.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

/**
 * Created by 15082 on 2018/9/14.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ed_register_user;
    private EditText ed_register_password;
    private EditText ed_register_confirm_password;
    private Button bt_register;
    private Button dialog_negative;
    private Button dialog_positive;
    private AlertDialog dialog;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    showDialog();
                    break;
            }
        }
    };

    private void showDialog() {
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        dialog = builder.create();
        View view = UIUtils.inflate(R.layout.register_dialog);
        dialog_negative = view.findViewById(R.id.dialog_negative);
        dialog_positive = view.findViewById(R.id.dialog_positive);
        dialog_positive.setOnClickListener(this);
        dialog_negative.setOnClickListener(this);
        dialog.setView(view);
        dialog.show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.person_account_toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("用户注册");
        }
        toolbar.setOnClickListener(this);
        ed_register_user = findViewById(R.id.ed_register_user);
        ed_register_password = findViewById(R.id.ed_register_password);
        ed_register_confirm_password = findViewById(R.id.ed_register_confirm_password);
        bt_register = findViewById(R.id.bt_register);
        bt_register.setOnClickListener(this);
    }

    private void ComeBack() {
        Intent intent = new Intent();
        intent.putExtra("user","wo");
        setResult(1,intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_register:
                Connect();
                break;
            case R.id.dialog_positive:
                ComeBack();
                overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
                break;
            case R.id.dialog_negative:
                dialog.dismiss();
                break;
        }
    }

    private void Connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
              handler.sendEmptyMessage(0);
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
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
}
