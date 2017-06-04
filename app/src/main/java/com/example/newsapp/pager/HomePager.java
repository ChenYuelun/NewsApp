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

public class HomePager extends BasePager {

    public HomePager(Context context) {
        super(context);


    }

    @Override
    public void initData() {
        Log.e("TAG","HomePager,initData");
        super.initData();
        tv_title.setText("主页");
        TextView textView = new TextView(context);
        textView.setText("HomePager");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);

        fl_content.addView(textView);

    }
}
