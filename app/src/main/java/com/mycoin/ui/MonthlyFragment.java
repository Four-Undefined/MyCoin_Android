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

        mWvMonthly = (WebView) view.findViewById(R.id.wv_monthly);
        initWebView();

        return view;
    }

    private void initWebView() {
        mWvMonthly.getSettings().setJavaScriptEnabled(true);
        mWvMonthly.setWebViewClient(new WebViewClient());
        // http://120.77.246.73:5589/<token>/month
        mWvMonthly.loadUrl("http://120.77.246.73:4600/" + Application.storedUserToken + "/month");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

