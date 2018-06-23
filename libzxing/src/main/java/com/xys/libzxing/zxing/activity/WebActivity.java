package com.xys.libzxing.zxing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xys.libzxing.R;

public class WebActivity extends Activity{

    private ImageButton ib_close,ib_flush;
    private TextView tv_title,tv_result;
    private WebView wv;

    private Intent intent;

    private String result;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.web_activity);

        initView();

        intent = getIntent();
        result = intent.getStringExtra("result");
        if(result.contains("http://") || result.contains("https://")){
            wv.loadUrl(result);
        }else{
            wv.setVisibility(View.GONE);
            tv_result.setText(result);
        }
        tv_title.setText(result);
    }

    public void initView(){
        ib_close = (ImageButton)findViewById(R.id.ib_close);
        ib_flush = (ImageButton)findViewById(R.id.ib_flush);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_result = (TextView)findViewById(R.id.tv_result);

        wv = (WebView)findViewById(R.id.wv);

        ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ib_flush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv.reload();
            }
        });

        wv.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wv.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

}
