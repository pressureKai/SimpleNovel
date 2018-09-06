package com.simplynovel.zekai.simplynovel.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.utils.ConstantValues;

/**
 * Created by 15082 on 2018/7/25.
 */

public class MyAccountOtherItemView extends RelativeLayout {
    private ImageView mIcon;
    private TextView mDes;

    public MyAccountOtherItemView(Context context) {
        this(context, null);
    }

    public MyAccountOtherItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAccountOtherItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.my_account_other_item, this);
        mIcon = (ImageView) this.findViewById(R.id.my_account_function_icon);
        mDes = (TextView) this.findViewById(R.id.my_account_des);
        String my_icon = attrs.getAttributeValue(ConstantValues.NAMESPACE, "my_account_other_icon");
        int my_img_id = Integer.parseInt(my_icon.replace("@",""));
        mIcon.setImageResource(my_img_id);
        String my_des = attrs.getAttributeValue(ConstantValues.NAMESPACE, "my_account_other_des");
        mDes.setText(my_des);
    }
}
