package com.example.newsapp.MenuDetailPager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.newsapp.base.MenuDetailBasePager;

/**
 * Created by chenyuelun on 2017/6/4.
 */

public class VoteDetailPager extends MenuDetailBasePager {
    private TextView textView;
    public VoteDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setTextColor(Color.RED);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("VoteDetailPager");
    }
}
