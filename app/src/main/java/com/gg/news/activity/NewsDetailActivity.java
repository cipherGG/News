package com.gg.news.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gg.news.R;
import com.gg.news.utils.DialogUtils;
import com.gg.news.utils.PrefUtils;
import com.gg.news.utils.ShareUtils;

public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {

    private WebView webView;
    private ImageButton back;
    private ImageButton share;
    private ImageButton size;
    private ProgressBar loadingBar;
    private TextView tvTitle;
    private WebSettings webSettings;
    private String url;

    @Override
    public void initView() {
        setContentView(R.layout.activity_news_detail);

        webView = (WebView) findViewById(R.id.wv_activity_news_detail);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持JavaScript
        webSettings.setBuiltInZoomControls(true);//支持缩放,为什么我的不行？
        webSettings.setUseWideViewPort(true);//支持双击缩放

        back = (ImageButton) findViewById(R.id.ib_activity_news_detail_back);
        share = (ImageButton) findViewById(R.id.ib_activity_news_detail_share);
        size = (ImageButton) findViewById(R.id.ib_activity_news_detail_size);
        tvTitle = (TextView) findViewById(R.id.tv_activity_news_detail_title);
        loadingBar = (ProgressBar) findViewById(R.id.pb_activity_news_detail);
    }

    @Override
    public void initData() {
        url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
    }

    @Override
    public void initListener() {
        //更多方法请点击进去看源码
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                loadingBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);//不在浏览器上加载点击的url
                loadingBar.setVisibility(View.VISIBLE);
                return true;
                //return super.shouldOverrideUrlLoading(view, url);
            }
        });

        //更多方法请点击进去看源码
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //加载网页进度变化回调方法
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                tvTitle.setText(title);
            }
        });

        back.setOnClickListener(this);
        size.setOnClickListener(this);
        share.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_activity_news_detail_back:
                finish();

                break;
            case R.id.ib_activity_news_detail_size:
                setTextSize();

                break;
            case R.id.ib_activity_news_detail_share:
                String title = tvTitle.getText().toString();
                ShareUtils.showShare(this, title, url);
                break;
        }
    }

    private void setTextSize() {
        String[] items = new String[]{"超大字体", "大号字体", "正常字体", "小号字体", "超小号字体"};
        int textSize = PrefUtils.getInt(this, "textSize", 2);

        DialogUtils.showSingleDialog(this, "选择字体大小", items, textSize, new DialogUtils.SingleCallBack() {
            @Override
            public void single(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        webSettings.setTextZoom(200);
                        break;
                    case 1:
                        webSettings.setTextZoom(150);
                        break;
                    case 2:
                        webSettings.setTextZoom(100);
                        break;
                    case 3:
                        webSettings.setTextZoom(75);
                        break;
                    case 4:
                        webSettings.setTextZoom(50);
                        break;
                }
                PrefUtils.putInt(NewsDetailActivity.this, "textSize", which);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
