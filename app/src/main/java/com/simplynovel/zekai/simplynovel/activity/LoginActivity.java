package com.simplynovel.zekai.simplynovel.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.ui.Adapter.LoginBgAdapter;
import com.simplynovel.zekai.simplynovel.ui.ScrollLinearLayoutManager;
import com.simplynovel.zekai.simplynovel.utils.ConstantValues;
import com.simplynovel.zekai.simplynovel.utils.SharedPreferenceUtils;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

/**
 * Created by 15082 on 2018/9/13.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private EditText ed_user;
    private EditText ed_password;
    private Button bt_login;
    private Button bt_register;
    private TextView tv_forget;
    private CheckBox cb_auto_login;
    private CheckBox cb_remember_password;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    startActivity();
                    break;
                case 1:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void initUI() {
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setAdapter(new LoginBgAdapter(LoginActivity.this));
        ScrollLinearLayoutManager scrollLinearLayoutManager = new ScrollLinearLayoutManager(LoginActivity.this);
        float speed = (float) 50;
        //设置滚动速度
        scrollLinearLayoutManager.setSpeedSlow(speed);
        mRecyclerView.setLayoutManager(scrollLinearLayoutManager);
        //smoothScrollToPosition滚动到某个位置（有滚动效果）
        mRecyclerView.smoothScrollToPosition(Integer.MAX_VALUE / 2);


        ed_user = findViewById(R.id.ed_user);
        ed_password = findViewById(R.id.ed_password);
        bt_login = findViewById(R.id.bt_login);
        bt_register = findViewById(R.id.bt_register);
        tv_forget = findViewById(R.id.tv_forget);
        cb_auto_login = findViewById(R.id.cb_auto_login);
        cb_remember_password = findViewById(R.id.cb_remember_password);
        cb_remember_password.setOnClickListener(this);
        cb_auto_login.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                String user = ed_user.getText().toString();
                String password = ed_password.getText().toString();
                if(user.isEmpty() || password.isEmpty()) {
                    Toast.makeText(UIUtils.getContext(), "用户名或密码，不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    CheckLogin(user,password);
                }

                break;
            case R.id.bt_register:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivityForResult(intent,0);
                break;
            case R.id.tv_forget:

                break;
            case R.id.cb_auto_login:
                if(!cb_remember_password.isChecked()){
                    Toast.makeText(UIUtils.getContext(), "请先勾选记住密码", Toast.LENGTH_SHORT).show();
                    cb_auto_login.setChecked(false);
                }else{
                    SharedPreferenceUtils.putBoolean(UIUtils.getContext(), ConstantValues.ISAUTOLOGIN,true);
                }
                break;
            case R.id.cb_remember_password:
                if(!cb_remember_password.isChecked() && cb_auto_login.isChecked()){
                    cb_auto_login.setChecked(false);
                }
                break;
        }
    }

    private void CheckLogin(String user, String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private void startActivity() {
        Intent intent = new Intent(this,AccountPersonMessageActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode ==1){
            Toast.makeText(UIUtils.getContext(),data.getStringExtra("user"),Toast.LENGTH_SHORT).show();
        }
    }
}
