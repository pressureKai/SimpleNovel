package com.simplynovel.zekai.simplynovel.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.domain.BookRankMsg;
import com.simplynovel.zekai.simplynovel.ui.Adapter.BookRankAdapter;
import com.simplynovel.zekai.simplynovel.ui.Adapter.BookRankContentAdapter;
import com.simplynovel.zekai.simplynovel.utils.HttpUtils;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 15082 on 2018/8/2.
 */

@SuppressLint("ValidFragment")
public class BookRankFragment extends Fragment implements AdapterView.OnItemClickListener {
    private String bookType;
    private List<List<BookRankMsg>>  bookRankMsgRanks;
    private ListView lv_book_rank;
    private ListView lv_book_rank_content;
    private List<String> rankTypeNames;
    private List<Integer> rankTypes;
    private BookRankAdapter bookRankAdapter;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    rankTypeNames = new ArrayList<>();
                    rankTypes = new ArrayList<>();
                    for(List<BookRankMsg> bookRankMsgs : bookRankMsgRanks){
                        int ranktype = bookRankMsgs.get(0).getRanktype();
                        switch (ranktype){
                            case 1:
                                rankTypeNames.add("原创榜");
                                rankTypes.add(1);
                                break;
                            case 2:
                                rankTypeNames.add("热销榜");
                                rankTypes.add(2);
                                break;
                            case 3:
                                rankTypeNames.add("点击榜");
                                rankTypes.add(3);
                                break;
                            case 4:
                                rankTypeNames.add("推荐榜");
                                rankTypes.add(4);
                                break;
                            case 5:
                                rankTypeNames.add("收藏榜");
                                rankTypes.add(5);
                                break;
                            case 6:
                                rankTypeNames.add("完本榜");
                                rankTypes.add(6);
                                break;
                            case 7:
                                rankTypeNames.add("签约新书榜");
                                rankTypes.add(7);
                                break;
                            case 8:
                                rankTypeNames.add("公众新书榜");
                                rankTypes.add(8);
                                break;
                        }
                    }
                    if(rankTypeNames.size() > 0){
                        bookRankAdapter = new BookRankAdapter(rankTypeNames);
                        lv_book_rank.setAdapter(bookRankAdapter);
                    }
                    lv_book_rank_content.setAdapter(new BookRankContentAdapter(bookRankMsgRanks.get(0)));
                    break;
                case 1:
                    Toast.makeText(UIUtils.getContext(),"网络连接错误",Toast.LENGTH_SHORT).show();
                    break;
            }

            return false;
        }
    });


    @SuppressLint("ValidFragment")
    public BookRankFragment(String bookType){
        this.bookType = bookType;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_rank_fragment, container, false);
        initUI(view);
        return view;
    }


    private void initUI(View view) {
        lv_book_rank_content = (ListView) view.findViewById(R.id.lv_book_rank_content);
        lv_book_rank = (ListView) view.findViewById(R.id.lv_book_rank);
        lv_book_rank.setOnItemClickListener(this);
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
                .add("type",bookType)
                .add("state","1")
                .build();
        HttpUtils.sendOkHttpRequest("http://10.0.3.2:8888/SimpleNovel/SelectBookFromDb", requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(1);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<List<BookRankMsg>>>() {
                }.getType();
                bookRankMsgRanks = new ArrayList<>();
                bookRankMsgRanks = gson.fromJson(string, listType);
                if (bookRankMsgRanks != null && bookRankMsgRanks.size() > 0) {
                    handler.sendEmptyMessage(0);
                }
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(bookRankAdapter != null){
            bookRankAdapter.setSelectItem(position);
            bookRankAdapter.notifyDataSetInvalidated();
        }
        if(bookRankMsgRanks.size() > 0 && rankTypes.size() > 0){
            List<BookRankMsg> currentBookRankMsgs = new ArrayList<>();
            for(List<BookRankMsg> bookRankMsgs : bookRankMsgRanks){
                   int ranktype = bookRankMsgs.get(0).getRanktype();
                   if(ranktype == rankTypes.get(position)){
                       currentBookRankMsgs = bookRankMsgs;
                   }
            }
            if(currentBookRankMsgs != null){
                if(currentBookRankMsgs.size() > 0){
                    lv_book_rank_content.setAdapter(new BookRankContentAdapter(currentBookRankMsgs));
                }
            }
        }
    }
}
