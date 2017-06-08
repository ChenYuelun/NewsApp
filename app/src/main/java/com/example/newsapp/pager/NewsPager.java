package com.example.newsapp.pager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myutils_library.Utils.CacheUtils;
import com.example.myutils_library.Utils.ConstantUtils;
import com.example.newsapp.MenuDetailPager.InteractDetailPager;
import com.example.newsapp.MenuDetailPager.NewsDetailPager;
import com.example.newsapp.MenuDetailPager.PictureDetailPager;
import com.example.newsapp.MenuDetailPager.TopicDetailPager;
import com.example.newsapp.MenuDetailPager.VoteDetailPager;
import com.example.newsapp.acitivyty.MainActivity;
import com.example.newsapp.base.BasePager;
import com.example.newsapp.base.MenuDetailBasePager;
import com.example.newsapp.domain.NewsControlBean;
import com.example.newsapp.fragment.LeftMenuFragment;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static android.media.CamcorderProfile.get;


/**
 * Created by chenyuelun on 2017/6/2.
 */

public class NewsPager extends BasePager {

    private List<NewsControlBean.DataBean> datas;
    private ArrayList<MenuDetailBasePager> basePagers;
    private String url;

    public NewsPager(Context context) {
        super(context);


    }

    @Override
    public void initData() {
        Log.e("TAG", "NewsPager,initData");
        super.initData();
        ib_menu.setVisibility(View.VISIBLE);
        tv_title.setText("新闻");
        String stringData = CacheUtils.getStringData(context, ConstantUtils.NEWSCENTER_PAGER_URL);
        if(!TextUtils.isEmpty(stringData)) {
           processData(stringData);
        }


        getDataFromNet();

    }

    private void initMenuDetailPagers() {
        basePagers = new ArrayList<>();
        basePagers.add(new NewsDetailPager(context,datas.get(0).getChildren()));
        basePagers.add(new TopicDetailPager(context,datas.get(0).getChildren()));
        basePagers.add(new PictureDetailPager(context,datas.get(2)));
        basePagers.add(new InteractDetailPager(context));
        basePagers.add(new VoteDetailPager(context));
    }

    private void getDataFromNet() {
        url = ConstantUtils.NEWSCENTER_PAGER_URL;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "请求失败==" + e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "请求成功==" + response);
                        processData(response);
                        CacheUtils.putStringData(context,ConstantUtils.NEWSCENTER_PAGER_URL,response);
                    }


                });
    }

    private void processData(String json) {
        NewsControlBean newsControlBean = new Gson().fromJson(json, NewsControlBean.class);
        //NewsControlBean newsControlBean = parseJson(json);
        datas = newsControlBean.getData();
        Log.e("TAG", "数据解析成功:+" + datas.get(0).getTitle());
        initMenuDetailPagers();
        MainActivity mainActivity = (MainActivity) context;
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
        leftMenuFragment.setData(datas);
    }

    private NewsControlBean parseJson(String json) {
        NewsControlBean newsControlBean = new NewsControlBean();
        try {
            JSONObject jsonObject = new JSONObject(json);
            int retcode = jsonObject.optInt("retcode");
            Log.e("TAG","retcode:"+retcode);
            newsControlBean.setRetcode(retcode);
            JSONArray data = jsonObject.optJSONArray("data");
            if (data != null) {
                List<NewsControlBean.DataBean> dataBeanList = new ArrayList<>();
                newsControlBean.setData(dataBeanList);

                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) data.get(i);
                    NewsControlBean.DataBean dataBean = new NewsControlBean.DataBean();
                    dataBean.setId(jsonObject1.optInt("id"));
                    dataBean.setTitle(jsonObject1.optString("title"));
                    dataBean.setType(jsonObject1.optInt("type"));
                    dataBean.setUrl(jsonObject1.optString("url"));

                    JSONArray children = jsonObject1.optJSONArray("children");
                    if (children != null) {
                        List<NewsControlBean.DataBean.ChildrenBean> childrenBeanList = new ArrayList<>();
                        dataBean.setChildren(childrenBeanList);
                        NewsControlBean.DataBean.ChildrenBean childrenBean = new NewsControlBean.DataBean.ChildrenBean();
                        for(int j = 0; j < children.length(); j++) {
                            JSONObject jsonObject2 = (JSONObject) children.get(j);
                            childrenBean.setId(jsonObject2.optInt("id"));
                            childrenBean.setTitle(jsonObject2.optString("title"));
                            childrenBean.setType(jsonObject2.optInt("type"));
                            childrenBean.setUrl(jsonObject2.optString("url"));
                            childrenBeanList.add(childrenBean);
                        }

                    }

                    dataBeanList.add(dataBean);
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsControlBean;
    }


    public void switchPager(int position) {
        tv_title.setText(datas.get(position).getTitle());
        fl_content.removeAllViews();
        fl_content.addView(basePagers.get(position).rootView);
        MenuDetailBasePager menuDetailBasePager = basePagers.get(position);
        menuDetailBasePager.initData();
        if(position == 2) {
            ib_switch_list_grid.setVisibility(View.VISIBLE);
            ib_switch_list_grid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PictureDetailPager pictureDetailPager = (com.example.newsapp.MenuDetailPager.PictureDetailPager) basePagers.get(2);
                    pictureDetailPager.switchListOrGrid(ib_switch_list_grid);
                }
            });

        }else {
            ib_switch_list_grid.setVisibility(View.GONE);
        }
    }


}
