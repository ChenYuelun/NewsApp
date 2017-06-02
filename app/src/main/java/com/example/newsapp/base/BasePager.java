package com.example.newsapp.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.newsapp.R;

import static android.view.View.inflate;
import static com.example.newsapp.R.id.fl_content;

/**
 * Created by chenyuelun on 2017/6/2.
 */

public class BasePager {


    public View rootView;
    public Context context;

    public TextView tv_title;
    public ImageButton ib_menu;

    public FrameLayout fl_content;

    public BasePager(Context context){
        this.context =context;

        rootView = View.inflate(context, R.layout.pager_base,null);

        tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        ib_menu = (ImageButton) rootView.findViewById(R.id.ib_menu);
        fl_content = (FrameLayout) rootView.findViewById(R.id.fl_content);
    }

    public void initData(){

    }
}
