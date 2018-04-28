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

public class LatelyFragment extends Fragment {

    private WebView mWvLately;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lately, container, false);

        mWvLately = (WebView) view.findViewById(R.id.wv_lately);
        initWebView();

        return view;
    }

    private void initWebView() {
        mWvLately.getSettings().setJavaScriptEnabled(true);
        mWvLately.setWebViewClient(new WebViewClient());
        // http://120.77.246.73:5589/<token>/week
        mWvLately.loadUrl("http://120.77.246.73:4600/" + Application.storedUserToken + "/week");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

