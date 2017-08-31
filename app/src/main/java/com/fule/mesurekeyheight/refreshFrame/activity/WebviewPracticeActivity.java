package com.fule.mesurekeyheight.refreshFrame.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fule.mesurekeyheight.R;
import com.fule.mesurekeyheight.util.StatusBarUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.Locale;

/**
 * Created by Administrator on 2017/8/18.
 */

public class WebviewPracticeActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_webview);

        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final WebView webView = (WebView) findViewById(R.id.webView);
        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                webView.loadUrl("http://mp.weixin.qq.com/s?__biz=MjM5OTA1MDUyMA==&mid=2655438930&idx=1&sn=98c2b7a27dc81690fa46acb157e4b0c3&chksm=bd7304258a048d339dbdf972aea51eb4b3f5dd004b8466d041a6467cc259323623de98539dc6&mpshare=1&scene=23&srcid=0818bSE9YWw24C8j8svJrEhP#rd");
            }
        });
        refreshLayout.autoRefresh();
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                refreshLayout.finishRefresh();
                //由于全屏的毛玻璃  所以需要设置 距离顶部的距离
                view.loadUrl(String.format(Locale.CHINA, "javascript:document.body.style.paddingTop='%fpx'; void 0", DensityUtil.px2dp(webView.getPaddingTop())));
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, webView);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        StatusBarUtil.setMargin(this, findViewById(R.id.header));
        StatusBarUtil.setPaddingSmart(this, findViewById(R.id.blurview));
    }
}
