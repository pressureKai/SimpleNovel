package com.simplynovel.zekai.simplynovel.ui;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

/**
 * Created by 15082 on 2018/7/25.
 */

public class BookConversionDialog extends Dialog {
    private TextView confirm;
    private View view;
    public BookConversionDialog(Context context) {
        this(context, 0);
    }

    public BookConversionDialog(final Context context, int themeResId) {
        super(context, themeResId);
        getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER);
        getWindow().setBackgroundDrawable(UIUtils.getDrawable(R.drawable.conversiondialogrectangle));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        view = UIUtils.inflate(R.layout.dialog_conversion);
        this.setContentView(view);
    }
    public TextView getConfirm(){
        confirm = (TextView) view.findViewById(R.id.conversion_dialog_confirm);
        return confirm;
    }
}
