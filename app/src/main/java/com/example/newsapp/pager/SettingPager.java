package com.example.newsapp.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.example.newsapp.base.BasePager;

/**
 * Created by chenyuelun on 2017/6/2.
 */

public class SettingPager extends BasePager {

    public SettingPager(Context context) {
        super(context);


    }

    @Override
    public void initData() {
        Log.e("TAG","SettingPager,initData");
        super.initData();

        tv_title.setText("主页");
        TextView textView = new TextView(context);
        textView.setText("SettingPager");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);

        fl_content.addView(textView);

    }
}
