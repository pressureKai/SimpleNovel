package com.simplynovel.zekai.simplynovel.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.ui.Adapter.BookMenuAdapter;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.Config;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.PageWidget;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.db.BookList;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.dialog.PageModeDialog;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.dialog.SettingDialog;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.util.BrightnessUtil;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.util.FileUtils;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.util.PageFactory;
import com.simplynovel.zekai.simplynovel.utils.ConstantValues;
import com.simplynovel.zekai.simplynovel.utils.SharedPreferenceUtils;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15082 on 2018/8/16.
 */

public class BookBodyActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ArrayList<String> arrayList;
    private PageWidget mPageWidget;
    private PageFactory mPageFactory;
    private Config mConfig;
    private Boolean mDayOrNight;
    private TextView tv_dayornight;
    private RelativeLayout rl_bottom;
    private RelativeLayout rl_top;
    private DrawerLayout menu_draw_layout;
    private SettingDialog mSettingDialog;
    private PageModeDialog mPageModeDialog;
    private TextView tv_pagemode;
    private TextView tv_setting;
    private TextView tv_directory;
    private ListView book_menu;
    private boolean isSpeaking = false;
    private boolean isShow;
    private boolean isSuccess;
    private String fileName = "";
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0 :
                    initData(0,fileName);
                    break;
                case 1 :
                    initData(1,fileName);
                    break;
            }
        }
    };
    private List<String> fileNames = new ArrayList<>();

    // 接收电池信息更新的广播
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int level = intent.getIntExtra("level", 0);
                mPageFactory.updateBattery(level);
            } else if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                mPageFactory.updateTime();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookbody);
        initUI();
        fileName = SharedPreferenceUtils.getString(this,ConstantValues.FILENAME);
        if(fileName.equals(" ")){
            if(FileUtils.write2files("",fileName)){
                handler.sendEmptyMessage(0);
            }
        }else{
            handler.sendEmptyMessage(0);
        }

        initListener();
    }


    /**
     * 初始化界面监听
     */
    private void initListener() {
        mPageWidget.setTouchListener(new PageWidget.TouchListener() {
            @Override
            public void center() {
                if (isShow) {
                    hideReadSetting();
                } else {
                    showReadSetting();
                }
            }

            @Override
            public Boolean prePage() {
                if (isShow || isSpeaking) {
                    return false;
                }
                if (isSuccess) {
                    mPageFactory.prePage();
                    if (mPageFactory.isfirstPage()) {
                        return false;
                    }
                }


                return true;
            }

            @Override
            public Boolean nextPage() {

                if (isShow || isSpeaking) {
                    return false;
                }

                if (isSuccess) {
                    mPageFactory.nextPage();
                    if (mPageFactory.islastPage()) {
                        return false;
                    }
                }

                return true;
            }

            @Override
            public void cancel() {

            }
        });
        mPageModeDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                hideSystemUI();
            }
        });

        mPageModeDialog.setPageModeListener(new PageModeDialog.PageModeListener() {
            @Override
            public void changePageMode(int pageMode) {
                mPageWidget.setPageMode(pageMode);
            }
        });

        mSettingDialog.setSettingListener(new SettingDialog.SettingListener() {
            @Override
            public void changeSystemBright(Boolean isSystem, float brightness) {
                if (!isSystem) {
                    BrightnessUtil.setBrightness(BookBodyActivity.this, brightness);
                } else {
                    int bh = BrightnessUtil.getScreenBrightness(BookBodyActivity.this);
                    BrightnessUtil.setBrightness(BookBodyActivity.this, bh);
                }
            }

            @Override
            public void changeFontSize(int fontSize) {

                mPageFactory.changeFontSize(fontSize);

            }

            @Override
            public void changeTypeFace(Typeface typeface) {
                mPageFactory.changeTypeface(typeface);

            }

            @Override
            public void changeBookBg(int type) {
                mPageFactory.changeBookBg(type);
            }
        });
    }

    private void initData(long begin,String fileName) {

        arrayList = new ArrayList<>();
//        for (int i = 0; i < 200; i++) {
//            arrayList.add("章节" + i);
//        }
        book_menu.setAdapter(new BookMenuAdapter(arrayList));

        mConfig = Config.createConfig(this);
        mPageFactory = PageFactory.createPageFactory(this);

        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 19) {
            mPageWidget.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        IntentFilter mfilter = new IntentFilter();
        mfilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        mfilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(myReceiver, mfilter);


        // pageFactory and pageWidget connection
        mPageWidget.setPageMode(mConfig.getPageMode());
        mPageFactory.setPageWidget(mPageWidget);



        BookList bookList = new BookList();
        bookList.setCharset("UTF-8");
        String path = this.getFilesDir().getAbsolutePath();
        bookList.setBookpath(path + "/zekai"+ fileName + ".txt");
        bookList.setId(1);
        bookList.setBegin(0);
        bookList.setBookname("wo");
        bookList.setPageindex(1);
        bookList.save();

        List<BookList> beginList =  DataSupport.findAll(BookList.class);
        if(beginList != null){
            bookList.setBegin(beginList.get(0).getBegin());
            bookList.setPageindex(beginList.get(0).getPageindex());
        }

        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            // program entrance
            mPageFactory.openBook(bookList,begin);
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
            isSuccess = false;
            Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show();
        }
        initDayOrNight();
    }



    private void initUI() {
        book_menu = findViewById(R.id.book_menu);
        menu_draw_layout = findViewById(R.id.menu_draw_layout);
        mPageWidget = findViewById(R.id.my_page);
        tv_dayornight = findViewById(R.id.tv_dayornight);
        rl_bottom = findViewById(R.id.rl_bottom);
        rl_top = findViewById(R.id.rl_top);
        tv_pagemode = findViewById(R.id.tv_pagemode);
        tv_setting = findViewById(R.id.tv_setting);
        tv_directory = findViewById(R.id.tv_directory);
        book_menu.setDividerHeight(0);
        mSettingDialog = new SettingDialog(this);
        mPageModeDialog = new PageModeDialog(this);
        tv_pagemode.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        tv_dayornight.setOnClickListener(this);
        tv_directory.setOnClickListener(this);
        book_menu.setOnItemClickListener(this);
        hideSystemUI();
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

    /**
     * 隐藏底部设置页面
     */
    private void hideReadSetting() {
        isShow = false;
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.dialog_top_exit);
        if (rl_bottom.getVisibility() == View.VISIBLE) {
            rl_bottom.startAnimation(topAnim);
        }
        rl_bottom.setVisibility(View.GONE);
        rl_top.setVisibility(View.GONE);
        hideSystemUI();
    }

    /**
     * 显示底部设置页面
     */
    private void showReadSetting() {
        isShow = true;
        showSystemUI();
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.dialog_top_enter);
        rl_bottom.startAnimation(topAnim);
        rl_bottom.setVisibility(View.VISIBLE);
        rl_top.startAnimation(topAnim);
        rl_top.setVisibility(View.VISIBLE);
        mSettingDialog.setSettingListener(new SettingDialog.SettingListener() {
            @Override
            public void changeSystemBright(Boolean isSystem, float brightness) {
                if (!isSystem) {
                    BrightnessUtil.setBrightness(BookBodyActivity.this, brightness);
                } else {
                    int bh = BrightnessUtil.getScreenBrightness(BookBodyActivity.this);
                    BrightnessUtil.setBrightness(BookBodyActivity.this, bh);
                }
            }

            @Override
            public void changeFontSize(int fontSize) {
                mPageFactory.changeFontSize(fontSize);
            }

            @Override
            public void changeTypeFace(Typeface typeface) {
                mPageFactory.changeTypeface(typeface);
            }

            @Override
            public void changeBookBg(int type) {
                mPageFactory.changeBookBg(type);
            }
        });
    }

    /**
     * 隐藏菜单。沉浸式阅读
     */
    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    public void initDayOrNight() {
        mDayOrNight = mConfig.getDayOrNight();
        if (mDayOrNight) {
            tv_dayornight.setText(getResources().getString(R.string.read_setting_night));
        } else {
            tv_dayornight.setText(getResources().getString(R.string.read_setting_day));
        }
    }

    //改变显示模式
    public void changeDayOrNight() {
        if (mDayOrNight) {
            mDayOrNight = false;
            tv_dayornight.setText(getResources().getString(R.string.read_setting_day));
        } else {
            mDayOrNight = true;
            tv_dayornight.setText(getResources().getString(R.string.read_setting_night));
        }
        mConfig.setDayOrNight(mDayOrNight);
        mPageFactory.setDayOrNight(mDayOrNight);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = view.findViewById(R.id.chapter_name);
        fileName = textView.getText().toString();
        if(FileUtils.write2files(textView.getText() + "",fileName)){
            handler.sendEmptyMessage(1);
            fileNames.add(fileName);
            SharedPreferenceUtils.putString(this, ConstantValues.FILENAME,fileName);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_directory:
                menu_draw_layout.openDrawer(GravityCompat.START);
                hideSystemUI();
                hideReadSetting();
                break;
            case R.id.tv_dayornight:
                changeDayOrNight();
                break;
            case R.id.tv_pagemode:
                hideReadSetting();
                mPageModeDialog.show();
                break;
            case R.id.tv_setting:
                hideReadSetting();
                mSettingDialog.show();
                break;
            case R.id.bookpop_bottom:
                break;
            case R.id.rl_bottom:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bookbody_top_toolbar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isShow) {
            hideSystemUI();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPageFactory.clear();
        mPageWidget = null;
        FileUtils.deleteSingleFile(fileNames);
        unregisterReceiver(myReceiver);
    }

}
