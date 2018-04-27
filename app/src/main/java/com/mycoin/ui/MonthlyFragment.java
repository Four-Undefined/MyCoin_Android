package com.mycoin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mycoin.Application;
import com.mycoin.R;

public class MonthlyFragment extends Fragment {

    // private BridgeWebView mBwvMonthly;
    private WebView mWvMonthly;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monthly, container, false);

        // mBwvMonthly = (BridgeWebView) view.findViewById(R.id.wv_monthly);
        mWvMonthly = (WebView) view.findViewById(R.id.wv_monthly);
        initWebView();

        return view;
    }

    private void initWebView() {
        // WebSettings settings = mBwvMonthly.getSettings();
        // settings.setJavaScriptEnabled(true);
        // settings.setAppCacheEnabled(true);
        // mBwvMonthly.setInitData(Application.storedUserToken);
        // mBwvMonthly.loadUrl("http://120.77.246.73:5489/month");
        mWvMonthly.getSettings().setJavaScriptEnabled(true);
        mWvMonthly.setWebViewClient(new WebViewClient());
        mWvMonthly.loadUrl("http://120.77.246.73:5489/month/" + Application.storedUserToken);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

