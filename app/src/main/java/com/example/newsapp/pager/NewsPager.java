package com.example.newsapp.pager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.example.newsapp.MenuDetailPager.InteractDetailPager;
import com.example.newsapp.MenuDetailPager.NewsDetailPager;
import com.example.newsapp.MenuDetailPager.PictureDetailPager;
import com.example.newsapp.MenuDetailPager.TopicDetailPager;
import com.example.newsapp.MenuDetailPager.VoteDetailPager;
import com.example.newsapp.Utils.ConstantUtils;
import com.example.newsapp.acitivyty.MainActivity;
import com.example.newsapp.base.BasePager;
import com.example.newsapp.base.MenuDetailBasePager;
import com.example.newsapp.domain.NewsControlBean;
import com.example.newsapp.fragment.LeftMenuFragment;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static android.R.attr.data;

/**
 * Created by chenyuelun on 2017/6/2.
 */

public class NewsPager extends BasePager {

    private List<NewsControlBean.DataBean> datas;
    private ArrayList<MenuDetailBasePager> basePagers;

    public NewsPager(Context context) {
        super(context);


    }

    @Override
    public void initData() {
        Log.e("TAG","NewsPager,initData");
        super.initData();
        basePagers = new ArrayList<>();
        basePagers.add(new NewsDetailPager(context));
        basePagers.add(new TopicDetailPager(context));
        basePagers.add(new PictureDetailPager(context));
        basePagers.add(new InteractDetailPager(context));
        basePagers.add(new VoteDetailPager(context));
//        tv_title.setText("主页");
//        TextView textView = new TextView(context);
//        textView.setText("NewsPager");
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextColor(Color.RED);
//




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


    public void switchPager(int position) {
        fl_content.removeAllViews();
        fl_content.addView(basePagers.get(position).rootView);
        MenuDetailBasePager menuDetailBasePager = basePagers.get(position);
        menuDetailBasePager.initData();
    }
}
