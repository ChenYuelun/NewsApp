package com.example.newsapp.acitivyty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TabNewsDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.ib_textsize)
    ImageButton ibTextsize;
    @BindView(R.id.ib_share)
    ImageButton ibShare;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private WebSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_news_detail);
        ButterKnife.bind(this);
        setView();
        String url = getIntent().getStringExtra("url");
        webview.loadUrl(url);

        settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
    }


    private void setView() {
        tvTitle.setVisibility(View.GONE);
        ibBack.setVisibility(View.VISIBLE);
        ibTextsize.setVisibility(View.VISIBLE);
        ibShare.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.ib_back, R.id.ib_textsize, R.id.ib_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_textsize:
                showTextSizeChangeDialog();
                break;
            case R.id.ib_share:
                Toast.makeText(TabNewsDetailActivity.this, "分享", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private int realSize = 2;
    private int tempSize = realSize;
    private void showTextSizeChangeDialog() {
        String[] items = {"超大","大","标准","小","超小"};
        new AlertDialog.Builder(this)
                    .setTitle("请选择字体大小")
                    .setSingleChoiceItems(items, realSize, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tempSize = which;
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            realSize = tempSize;
                            changeSize(realSize);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
    }

    private void changeSize(int realSize) {
        switch (realSize) {
            case 0 :
                settings.setTextZoom(200);
                break;
            case 1 :
                settings.setTextZoom(150);
                break;
            case 2 :
                settings.setTextZoom(100);
                break;
            case 4 :
                settings.setTextZoom(75);
                break;
            case 5 :
                settings.setTextZoom(50);
                break;
        }
    }
}
