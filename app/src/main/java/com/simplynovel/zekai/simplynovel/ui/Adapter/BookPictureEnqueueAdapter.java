package com.simplynovel.zekai.simplynovel.ui.Adapter;



import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.simplynovel.zekai.simplynovel.utils.UIUtils;

import java.util.List;


/**
 * Created by 15082 on 2018/8/2.
 */

public class BookPictureEnqueueAdapter extends PagerAdapter {
    private List<ImageView>  imageViews;
    public BookPictureEnqueueAdapter(List<ImageView> imageViews){
        this.imageViews = imageViews;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int newPosition = position % imageViews.size();
        ImageView imageView = imageViews.get(newPosition);
        LinearLayout.LayoutParams  layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,50);
        ViewGroup parent = (ViewGroup) imageView.getParent();
        if(parent != null){
            parent.removeView(imageView);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UIUtils.getContext(),"click",Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(imageView,layoutParams1);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
