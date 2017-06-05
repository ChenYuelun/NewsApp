package com.example.newsapp.MenuDetailPager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.newsapp.base.MenuDetailBasePager;
import com.example.newsapp.domain.NewsControlBean;

/**
 * Created by chenyuelun on 2017/6/5.
 */

public class TabDetaiPager extends MenuDetailBasePager {
    private NewsControlBean.DataBean.ChildrenBean childrenBean;
    private TextView textView;

    public TabDetaiPager(Context context, NewsControlBean.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;
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
        textView.setText(childrenBean.getTitle());
    }


}
