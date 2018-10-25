package com.simplynovel.zekai.simplynovel.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.utils.ConstantValues;

/**
 * Created by 15082 on 2018/7/24.
 */

public class MyFindItemView extends RelativeLayout {
    private ImageView mIcon;
    private  TextView mDes;
    public MyFindItemView(Context context) {
        this(context,null);
    }

    public MyFindItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyFindItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.bookfind_item, this);
        mIcon =(ImageView) this.findViewById(R.id.find_function_icon);
        mDes = (TextView)this.findViewById(R.id.find_function_des);
        String my_icon = attrs.getAttributeValue(ConstantValues.NAMESPACE, "my_icon");
        int my_img_id = Integer.parseInt(my_icon.replace("@",""));
        String my_des = attrs.getAttributeValue(ConstantValues.NAMESPACE, "my_des");
        mIcon.setImageResource(my_img_id);
        mDes.setText(my_des);
    }
}
