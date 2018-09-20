package com.simplynovel.zekai.simplynovel.ui;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.utils.HttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 15082 on 2018/8/2.
 */

public class BookListFragment extends Fragment{
    private  String responseStr;
    private  TextView tv_tab_title;
    private ViewPager viewpager;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    tv_tab_title.setText(responseStr);
                    break;
            }
            return false;
        }
    });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_list_fragment, container, false);
        initUI(view);
        return view;
    }


    private void initUI(View view) {
        tv_tab_title = (TextView) view.findViewById(R.id.tv_tab_title);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
             connect();
        }else {

         }
    }
    private void connect() {
        RequestBody requestBody = new FormBody.Builder()
                .add("type","玄幻")
                .build();
        HttpUtils.sendOkHttpRequest("http://10.0.3.2:8888/SimpleNovel/SelectBookFromDb", requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                responseStr = "网络连接错误";
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                byte[] bytes = new byte[1024];
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder content = new StringBuilder();
                String line="";
                while ((line = reader.readLine())!= null){
                    content.append(line);
                }

                responseStr = content.toString();
                handler.sendEmptyMessage(0);
            }
        });
    }

}
