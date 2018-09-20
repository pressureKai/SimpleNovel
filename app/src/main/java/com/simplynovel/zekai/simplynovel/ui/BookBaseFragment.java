package com.simplynovel.zekai.simplynovel.ui;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.activity.BookRackActivity;
import com.simplynovel.zekai.simplynovel.domain.BookRankMsg;
import com.simplynovel.zekai.simplynovel.ui.Adapter.BookPictureEnqueueAdapter;
import com.simplynovel.zekai.simplynovel.ui.Adapter.pictureListViewAdapter;
import com.simplynovel.zekai.simplynovel.utils.HttpUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;


@SuppressLint("ValidFragment")
public class BookBaseFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private ViewPager viewpager;
    private List<ImageView> imageViews;
    private int[] imageResIds;
    private BookRackActivity bookRackActivity;
    private String[] contentDescs;
    private TextView picture_bookname;
    private LinearLayout ll_point_container;
    private boolean isRunning;
    private String type;
    private int previousSelectedPosition = 0;
    private List<List<BookRankMsg>> bookRankMagRanks;
    private List<String> RankTypeName;
    private List<String> RankBookName;
    private List<String> RankBookImg;
    private List<Integer> ranktypes;
    private Thread thread;
    private ProgressDialog progressDialog;
    private ListView picture_listview;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                    break;
                case 1:
                    RankTypeName = new ArrayList<>();
                    RankBookName = new ArrayList<>();
                    ranktypes = new ArrayList<>();
                    RankBookImg = new ArrayList<>();
                    //bookRankMagRanks.size() 排行个数
                    for (List<BookRankMsg> bookRankMsgs : bookRankMagRanks) {


                        int ranktype = bookRankMsgs.get(0).getRanktype();
                        RankBookName.add(bookRankMsgs.get(0).getBookName());
                        RankBookImg.add(bookRankMsgs.get(0).getImg());
                        ranktypes.add(ranktype);
                        switch (ranktype) {
                            case 1:
                                RankTypeName.add("原创热推");
                                break;
                            case 2:
                                RankTypeName.add("今日热门");
                                break;
                            case 3:
                                RankTypeName.add("点击热门");
                                break;
                            case 4:
                                RankTypeName.add("热门推荐");
                                break;
                            case 5:
                                RankTypeName.add("热门收藏");
                                break;
                            case 6:
                                RankTypeName.add("完本推荐");
                                break;
                            case 7:
                                RankTypeName.add("新书推荐");
                                break;
                            case 8:
                                RankTypeName.add("网络新书");
                                break;
                        }
                    }
                    if (RankBookName.size() < 5) {
                        int more = 5 - RankBookName.size();
                        for (int i = 1; i <= more; i++) {
                            RankBookName.add(bookRankMagRanks.get(0).get(i).getBookName());
                            RankBookImg.add(bookRankMagRanks.get(0).get(i).getImg());
                        }
                    }
                    if (RankBookName.size() > 5) {
                        int size = RankBookName.size();
                        for (int i = 5; i < size; i++) {
                            RankBookName.remove(5);
                            RankBookImg.remove(5);
                        }
                    }

                    ImageView imageView;
                    if (RankBookImg.size() == 5) {

                        for (int i = 0; i < 5; i++) {
                            contentDescs[i] = RankBookName.get(i);
                            imageView = new ImageView(bookRackActivity);
                            Glide.with(bookRackActivity).load(RankBookImg.get(i)).error(R.drawable.back).into(imageView);
                            imageViews.remove(i);
                            imageViews.add(i, imageView);
                        }
                    }
                    progressDialog.dismiss();
                    handler.sendEmptyMessage(3);
                    break;
                case 3:
                    if(bookRankMagRanks != null && RankTypeName != null){
                        picture_listview.setAdapter(new pictureListViewAdapter(bookRankMagRanks,RankTypeName));
                    }
                    break;
            }
            return false;
        }
    });

    @SuppressLint("ValidFragment")
    public BookBaseFragment(BookRackActivity bookRackActivity, String type) {
        this.bookRackActivity = bookRackActivity;
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_picture_fragment, container, false);
        initUI(view);
        initData();
        initAdapter();
        thread = new Thread() {
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(3000);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
        return view;
    }

    private void initData() {
        imageResIds = new int[]{R.drawable.picture, R.drawable.picture,
                R.drawable.picture, R.drawable.picture, R.drawable.picture};
        contentDescs = new String[5];
        for (int i = 0; i < 5; i++) {
            contentDescs[i] = "";
        }
        // 初始化要展示的5个ImageView
        imageViews = new ArrayList<>();
        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {


            // 初始化要显示的图片对象
            imageView = new ImageView(bookRackActivity);
            imageView.setBackgroundResource(imageResIds[i]);
            imageViews.add(imageView);


            //加小白点, 指示器
            pointView = new View(bookRackActivity);
            pointView.setBackgroundResource(R.drawable.selector_bg_point);
            layoutParams = new LinearLayout.LayoutParams(5, 5);
            if (i != 0)
                layoutParams.leftMargin = 10;

            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }
    }

    private void initAdapter() {
        ll_point_container.getChildAt(0).setEnabled(true);
        picture_bookname.setText(contentDescs[0]);
        previousSelectedPosition = 0;
        //Fragment中 ViewPager轮播图的适配器
        viewpager.setAdapter(new BookPictureEnqueueAdapter(imageViews));
        viewpager.setCurrentItem(5000000);

    }


    private void initUI(View view) {
        viewpager = view.findViewById(R.id.picture_viewpager);
        picture_bookname = view.findViewById(R.id.picture_bookname);
        viewpager.addOnPageChangeListener(this);
        ll_point_container = view.findViewById(R.id.ll_point_container);
        picture_listview = view.findViewById(R.id.picture_listview);
        picture_listview.setFocusable(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            connect(type);
        }else{
            thread = null;
        }
    }


    private void connect(String type) {
        RequestBody requestBody = new FormBody.Builder()
                .add("type", type)
                .add("state","0")
                .build();
        progressDialog = new ProgressDialog(bookRackActivity);
        progressDialog.show();
        HttpUtils.sendOkHttpRequest("http://10.0.3.2:8888/SimpleNovel/SelectBookFromDb", requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<List<BookRankMsg>>>() {
                }.getType();
                bookRankMagRanks = new ArrayList<>();
                bookRankMagRanks = gson.fromJson(string, listType);
                if (bookRankMagRanks != null && bookRankMagRanks.size() > 0) {
                    handler.sendEmptyMessage(1);
                }
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int newPosition = position % imageViews.size();
        //设置文本
        picture_bookname.setText(contentDescs[newPosition]);
        ll_point_container.getChildAt(previousSelectedPosition).setEnabled(false);
        ll_point_container.getChildAt(newPosition).setEnabled(true);
        // 记录之前的位置
        previousSelectedPosition = newPosition;

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
