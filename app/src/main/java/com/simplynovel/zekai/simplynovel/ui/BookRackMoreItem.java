package com.simplynovel.zekai.simplynovel.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

/**
 * Created by 15082 on 2018/6/15.
 */

public class BookRackMoreItem extends Dialog {
    public BookRackMoreItem(Context context) {
        this(context, R.style.MoreItemTheme);
    }

    public BookRackMoreItem(Context context, int themeResId) {
        super(context, themeResId);
        getWindow().setGravity(Gravity.TOP | Gravity.RIGHT);
        getWindow().getDecorView().setPadding(0,0,0,0);
        View view = UIUtils.inflate(R.layout.activity_main);
        this.setContentView(view);
    }

}
