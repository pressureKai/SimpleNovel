package com.simplynovel.zekai.simplynovel.utils;

import android.content.Context;

import com.simplynovel.zekai.simplynovel.global.SimplyNovelApplication;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.util.DisplayMetrics;
import android.view.View;

/**
 *      UI操作的工具类
 * Created by 15082 on 2018/6/13.
 */

public class UIUtils {

    //获取全局上下文
    public static Context getContext(){
        return SimplyNovelApplication.getContext();
    }

    //获取一个Handler
    public static Handler getHandler(){
        return SimplyNovelApplication.getHandler();
    }

    //获取主线程id
    public static int getMainThreadId(){
        return SimplyNovelApplication.getMainThreadId();
    }

    //获取字符串
    public static String getString(int id){
        return getContext().getResources().getString(id);
    }

    //获取字符串数组
    public static String[] getStringArray(int id){
        return getContext().getResources().getStringArray(id);
    }

    //获取Drawable
    public static Drawable getDrawable(int id){
        return getContext().getResources().getDrawable(id);
    }

    //获取一个颜色
    public static int getColor(int id){
        return getContext().getResources().getColor(id);
    }

    //获取一个尺寸的像素值
    public static int getDimen(int id){
        return getContext().getResources().getDimensionPixelSize(id);
    }


    public static int getScreenWidth(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        return widthPixels;
    }

    public static int getScreenHeight(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.heightPixels;
        return widthPixels;
    }


    public static int dip2px(float dip){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    public static float px2dip(int px){
        float density = getContext().getResources().getDisplayMetrics().density;
        return px/density;
    }

    //加载一个布局文件
    public static View inflate(int id){
        return  View.inflate(getContext(),id,null);
    }

    //判断当前线程是否是主线程
    public static  boolean isRunOnUIThread(){
        int myTid = Process.myTid();
        if(myTid == getMainThreadId()){
            return  true;
        }
        return  false;
    }

    public static  void  runOnUIThread(Runnable r){
        if(isRunOnUIThread()){
            r.run();
        }else{
            getHandler().post(r);
        }
    }
}
