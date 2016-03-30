package com.example.sufian.livelocal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebLinksView extends AppCompatActivity {

    private TextView removeView;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_links_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (TextView) findViewById(R.id.WebViewTitle);
        title.setText(DiscoverFragment.getViewTitle());

        WebView htmlWebView = (WebView) findViewById(R.id.webViewLinks);
        htmlWebView.setWebViewClient(new CustomWebViewClient());
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setUseWideViewPort(true);
        htmlWebView.loadUrl(DiscoverFragment.getWebLink());

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        removeView = (TextView) findViewById(R.id.dismissFishingView);
        removeView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            findViewById(R.id.webViewLinks).setVisibility(View.VISIBLE);
        }
    }

}
