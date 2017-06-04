package com.example.newsapp.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.example.newsapp.Utils.ConstantUtils;
import com.example.newsapp.acitivyty.MainActivity;
import com.example.newsapp.base.BasePager;
import com.example.newsapp.domain.NewsControlBean;
import com.example.newsapp.fragment.LeftMenuFragment;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static android.R.attr.data;

/**
 * Created by chenyuelun on 2017/6/2.
 */

public class NewsPager extends BasePager {

    private List<NewsControlBean.DataBean> datas;

    public NewsPager(Context context) {
        super(context);


    }

    @Override
    public void initData() {
        Log.e("TAG","NewsPager,initData");
        super.initData();

        tv_title.setText("主页");
        TextView textView = new TextView(context);
        textView.setText("NewsPager");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);

        fl_content.addView(textView);

        getDataFromNet();

    }

    private void getDataFromNet() {
        String url = ConstantUtils.NEWSCENTER_PAGER_URL;
        OkHttpUtils
                .get()
                .url(url)
//                .addParams("username", "hyman")
//                .addParams("password", "123")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG","请求失败=="+e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG","请求成功=="+response);
                        processData(response);
                    }


                });
    }

    private void processData(String json) {
        NewsControlBean newsControlBean = new Gson().fromJson(json, NewsControlBean.class);
        datas = newsControlBean.getData();
        Log.e("TAG","数据解析成功:+" + datas.get(0).getTitle());

        MainActivity mainActivity = (MainActivity) context;
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
        leftMenuFragment.setData(datas);
    }


}
