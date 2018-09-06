package com.simplynovel.zekai.simplynovel.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.simplynovel.zekai.simplynovel.R;

/**
 * Created by 15082 on 2018/6/26.
 */

public class MyRefreshHeader extends FrameLayout implements RefreshHeader{
    private Animation rotate_up;
    private Animation rotate_down;
    private Animation rotate_infinite;
    private TextView  refresh_text;
    private ImageView pull_icon;
    private ImageView refresh_icon;
    public MyRefreshHeader( Context context) {
        this(context,null);
    }

    public MyRefreshHeader( Context context, AttributeSet attrs) {
        super(context, attrs);
        rotate_up = AnimationUtils.loadAnimation(context, R.anim.rotate_up);
        rotate_down = AnimationUtils.loadAnimation(context, R.anim.rotate_down);
        rotate_infinite = AnimationUtils.loadAnimation(context, R.anim.rotate_infinite);
        inflate(context, R.layout.header_my, this);

        refresh_text = (TextView) findViewById(R.id.refresh_text);
        pull_icon = (ImageView) findViewById(R.id.pull_icon);
        refresh_icon = (ImageView)findViewById(R.id.refresh_icon);

    }

    @Override
    public void reset() {
        refresh_text.setText("小说");
        pull_icon.setVisibility(INVISIBLE);
        pull_icon.clearAnimation();
        refresh_icon.setVisibility(VISIBLE);
        refresh_icon.clearAnimation();
    }

    @Override
    public void pull() {
        refresh_text.setText("下拉刷新");
        pull_icon.setVisibility(INVISIBLE);
        pull_icon.clearAnimation();
        refresh_icon.setVisibility(VISIBLE);
        refresh_icon.startAnimation(rotate_infinite);
    }

    @Override
    public void refreshing() {
        refresh_text.setText("正在刷新");
        pull_icon.setVisibility(VISIBLE);
        pull_icon.startAnimation(rotate_infinite);
        refresh_icon.setVisibility(INVISIBLE);
        refresh_icon.clearAnimation();
    }

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, State state) {
        // 往上拉
        if (currentPos < refreshPos && lastPos >= refreshPos) {


        }
        // 往下拉
        else if (currentPos > refreshPos && lastPos <= refreshPos) {
            if (isTouch && state == State.PULL) {

            }
        }
    }

    @Override
    public void complete() {
        refresh_text.setText("刷新成功");
        pull_icon.setVisibility(INVISIBLE);
        pull_icon.clearAnimation();
        refresh_icon.setVisibility(VISIBLE);
        refresh_icon.clearAnimation();
    }
}
