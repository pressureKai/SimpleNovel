package com.simplynovel.zekai.simplynovel.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplynovel.zekai.simplynovel.R;

/**
 * Created by 15082 on 2018/9/6.
 */

public class BookPictureEnqueueFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_picture_fragment, container, false);
        return view;
    }
}
