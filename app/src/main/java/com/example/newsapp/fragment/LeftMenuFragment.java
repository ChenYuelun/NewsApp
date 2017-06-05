package com.example.newsapp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.newsapp.R;
import com.example.newsapp.Utils.DensityUtil;
import com.example.newsapp.acitivyty.MainActivity;
import com.example.newsapp.base.BaseFragment;
import com.example.newsapp.base.BasePager;
import com.example.newsapp.domain.NewsControlBean;
import com.example.newsapp.pager.NewsPager;

import java.util.List;

/**
 * Created by chenyuelun on 2017/6/2.
 */

public class LeftMenuFragment extends BaseFragment {
    private List<NewsControlBean.DataBean> datas;
    private ListView listView;
    private View preView;

    @Override
    public View initView() {
        listView = new ListView(context);
        listView.setPadding(0, DensityUtil.dip2px(context,40),0,0);
        listView.setDivider(new ColorDrawable(Color.RED));
        listView.setDividerHeight(DensityUtil.dip2px(context,2));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view != preView) {
                    view.setEnabled(true);
                    if(preView!= null) {
                        preView.setEnabled(false);
                    }
                    preView = view;
                }

                SwitchMenuDetailPager(position);
            }
        });
        return listView;
    }

    private void SwitchMenuDetailPager(int position) {
        MainActivity mainActivity = (MainActivity) context;
        ContentFragment contentFragment = mainActivity.getContentFragment();
        NewsPager newsPager = contentFragment.getNewsPager();
        newsPager.switchPager(position);

    }

    @Override
    public void initData() {
        super.initData();
    }

    public void setData(List<NewsControlBean.DataBean> datas) {
        this.datas = datas;
        Log.e("TAG","数据已传递到侧滑菜单");
        listView.setAdapter(new MyAdapter());
        SwitchMenuDetailPager(0);

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas == null? 0 : datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) View.inflate(context, R.layout.item_list_leftmenu,null);
            if(position == 0) {
                textView.setEnabled(true);
            }else {
                textView.setEnabled(false);
            }
            NewsControlBean.DataBean dataBean = datas.get(position);
            textView.setText(dataBean.getTitle());
            return textView;
        }
    }
}
