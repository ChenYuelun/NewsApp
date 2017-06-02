package com.example.newsapp.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.newsapp.base.BasePager;

/**
 * Created by chenyuelun on 2017/6/2.
 */

public class NewsPager extends BasePager {

    public NewsPager(Context context) {
        super(context);


    }

    @Override
    public void initData() {
        super.initData();

        tv_title.setText("主页");
        TextView textView = new TextView(context);
        textView.setText("NewsPager");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);

        fl_content.addView(textView);

    }
}
