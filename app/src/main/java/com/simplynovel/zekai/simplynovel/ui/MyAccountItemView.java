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
 * Created by 15082 on 2018/7/25.
 */

public class MyAccountItemView extends RelativeLayout {
    private ImageView mIcon;
    private TextView mDes;
    private static TextView mData;
    private ImageView mNext;

    public MyAccountItemView(Context context) {
        this(context, null);
    }

    public MyAccountItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAccountItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.my_account_item, this);
        mIcon = (ImageView) this.findViewById(R.id.my_account_function_icon);
        mDes = (TextView) this.findViewById(R.id.my_account_des);
        mData = (TextView) this.findViewById(R.id.my_account_data);
        mNext = (ImageView) this.findViewById(R.id.my_account_next_img);
        String my_icon = attrs.getAttributeValue(ConstantValues.NAMESPACE, "my_account_icon");
        int my_img_id = Integer.parseInt(my_icon.replace("@",""));
        mIcon.setImageResource(my_img_id);
        String my_des = attrs.getAttributeValue(ConstantValues.NAMESPACE, "my_account_des");
        mDes.setText(my_des);
        String my_data = attrs.getAttributeValue(ConstantValues.NAMESPACE, "my_account_data");
        mData.setText(my_data);
        String my_img = attrs.getAttributeValue(ConstantValues.NAMESPACE, "my_account_img");
        Log.i("woooo",my_img);
        if(my_img.equals("0")){
            mNext.setVisibility(View.INVISIBLE);
        }else{
            int my_account_img_id = Integer.parseInt(my_img.replace("@",""));
            mNext.setImageResource(my_account_img_id);
        }


    }
    public static void setData(String val){
        mData.setText(val);
    }
}
