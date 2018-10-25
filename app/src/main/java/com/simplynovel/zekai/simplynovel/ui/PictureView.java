package com.simplynovel.zekai.simplynovel.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.utils.UIUtils;

/**
 * Created by 15082 on 2018/9/21.
 */

public class PictureView extends RelativeLayout {

    private ImageView img_left;
    private ImageView img_right;
    private ImageView img_center;
    public PictureView(Context context) {
        this(context,null);
    }

    public PictureView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.picture_item,this);
        img_left =  this.findViewById(R.id.img_left);
        img_right =  this.findViewById(R.id.img_right);
        img_center =  this.findViewById(R.id.img_center);
    }

    public void setLeftBackground(String href){
        if(img_left != null){
            Glide.with(UIUtils.getContext()).load(href).error(R.mipmap.login_bg).into(img_left);
        }

    }
    public void setRightBackground(String href){
        if(img_right != null){
            Glide.with(UIUtils.getContext()).load(href).error(R.mipmap.login_bg).into(img_right);
        }

    }
    public void setCenterBackground(String href){
        if(img_center != null){
            Glide.with(UIUtils.getContext()).load(href).error(R.mipmap.login_bg).into(img_center);
        }
    }
}
