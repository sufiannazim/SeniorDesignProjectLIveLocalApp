package com.example.sufian.livelocal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ValentinesGuideFragment extends Fragment {

    private View rootView;

    public ValentinesGuideFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_turkeyguide, container, false);

        WebView htmlWebView = (WebView)rootView.findViewById(R.id.webView);
        htmlWebView.setWebViewClient(new CustomWebViewClient());
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);
        htmlWebView.loadUrl("http://www.buyctgrown.com/wine-dine-and-cuddle-buyctgrowns-valentines-day-guide");

        return rootView;
    }

    private class CustomWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            rootView.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            rootView.findViewById(R.id.progressBar).setVisibility(View.GONE);
            rootView.findViewById(R.id.webView).setVisibility(View.VISIBLE);
        }
    }
}


