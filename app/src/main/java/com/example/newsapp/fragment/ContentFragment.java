package com.example.newsapp.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.newsapp.R;
import com.example.newsapp.base.BaseFragment;

/**
 * Created by chenyuelun on 2017/6/2.
 */

public class ContentFragment extends BaseFragment {
    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_content,null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
