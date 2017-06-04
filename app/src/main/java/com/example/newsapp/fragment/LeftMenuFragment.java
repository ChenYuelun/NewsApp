package com.example.newsapp.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.newsapp.base.BaseFragment;
import com.example.newsapp.domain.NewsControlBean;

import java.util.List;

/**
 * Created by chenyuelun on 2017/6/2.
 */

public class LeftMenuFragment extends BaseFragment {
    TextView textView;
    private List<NewsControlBean.DataBean> datas;

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
        textView.setText("LeftMenuFragment");
    }

    public void setData(List<NewsControlBean.DataBean> datas) {
        this.datas = datas;
        Log.e("TAG","数据已传递到侧滑菜单");
    }
}
