package com.example.lwxwl.coin.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.lwxwl.coin.R;


public class LatelyFragment extends Fragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lately, container, false);//关联布局文件
        WebView wvLately = (WebView) view.findViewById(R.id.wv_lately);
        wvLately.getSettings().setJavaScriptEnabled(true);
        wvLately.setWebViewClient(new WebViewClient());
        wvLately.loadUrl("http://www.baidu.com");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
