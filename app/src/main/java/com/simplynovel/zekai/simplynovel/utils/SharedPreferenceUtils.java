package com.simplynovel.zekai.simplynovel.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 15082 on 2018/5/28.
 */

public class SharedPreferenceUtils {
    private static SharedPreferences sharedPreferences;

    public static void putString(Context ctx,String key,String value){
        if(sharedPreferences == null){
            sharedPreferences = ctx.getSharedPreferences("update", Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key,value).commit();
    }
    public static String getString(Context ctx, String key){
        if(sharedPreferences == null){
            sharedPreferences = ctx.getSharedPreferences("update", Context.MODE_PRIVATE);
        }
        return  sharedPreferences.getString(key, " ");
    }

    public static void putInt(Context ctx,String key,int value){
        if(sharedPreferences == null){
            sharedPreferences = ctx.getSharedPreferences("update", Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putInt(key,value).commit();
    }
    public static int getInt(Context ctx, String key){
        if(sharedPreferences == null){
            sharedPreferences = ctx.getSharedPreferences("update", Context.MODE_PRIVATE);
        }
        return  sharedPreferences.getInt(key, 0);
    }
    public static int getInt(Context ctx, String key,int defValue){
        if(sharedPreferences == null){
            sharedPreferences = ctx.getSharedPreferences("update", Context.MODE_PRIVATE);
        }
        return  sharedPreferences.getInt(key, defValue);
    }
    public static void putBoolean(Context ctx,String key,boolean value){
        if(sharedPreferences == null){
            sharedPreferences = ctx.getSharedPreferences("update", Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putBoolean(key,value).commit();
    }
    public static boolean getBoolean(Context ctx, String key,boolean defValue){
        if(sharedPreferences == null){
            sharedPreferences = ctx.getSharedPreferences("update", Context.MODE_PRIVATE);
        }
        return  sharedPreferences.getBoolean(key, defValue);
    }
}
