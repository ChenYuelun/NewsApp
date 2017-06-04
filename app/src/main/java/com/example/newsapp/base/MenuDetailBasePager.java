package com.example.newsapp.base;

import android.content.Context;
import android.view.View;

/**
 * Created by chenyuelun on 2017/6/4.
 */

public abstract class MenuDetailBasePager {

    public Context context;
    public View rootView;

    public MenuDetailBasePager(Context context){
        this.context =context;
        rootView = initView();

    }

    public abstract View initView();

    public void initData(){}
}
