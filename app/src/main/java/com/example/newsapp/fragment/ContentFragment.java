package com.example.newsapp.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.newsapp.base.BaseFragment;

/**
 * Created by chenyuelun on 2017/6/2.
 */

public class ContentFragment extends BaseFragment {
    TextView textView;
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
        textView.setText("ContentFragment");
    }
}
