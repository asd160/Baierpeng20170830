package com.bwie.riko8_30;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class WebViewActivity extends AppCompatActivity {

    @ViewInject(R.id.wv)
    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        x.view().inject(this);

        Intent in=getIntent();
        wv.loadUrl(in.getStringExtra("url"));
    }
}
