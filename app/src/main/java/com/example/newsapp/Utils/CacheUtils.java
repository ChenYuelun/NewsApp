package com.example.newsapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.animation.Animation;

/**
 * Created by chenyuelun on 2017/6/2.
 */

public class CacheUtils {

    public static void setBooleanForGuide(Context context, String key,boolean value) {
        SharedPreferences news = context.getSharedPreferences("news", Context.MODE_PRIVATE);
        news.edit().putBoolean(key,value).commit();
    }

    public static boolean getBooleanForGuide(Context context, String key) {
        SharedPreferences news = context.getSharedPreferences("news", Context.MODE_PRIVATE);
        return news.getBoolean(key,false);

    }
}
