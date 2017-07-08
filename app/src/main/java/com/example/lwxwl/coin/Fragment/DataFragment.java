package com.example.lwxwl.coin.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lwxwl.coin.Activity.BudgetActivity;
import com.example.lwxwl.coin.Activity.ReportActivity;
import com.example.lwxwl.coin.Adapter.InterfaceAdapter;
import com.example.lwxwl.coin.R;

import java.util.Calendar;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;


public class DataFragment extends Fragment {

    private TextView tvBudget, tvBudget2;
    private TextView tvCost, tvCost2;
    private ImageButton ibReport;

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    Retrofit retrofit;
    InterfaceAdapter interfaceAdapter;


    Calendar c = Calendar.getInstance();
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        WebView wvTime = (WebView) view.findViewById(R.id.wv_time);
        wvTime.getSettings().setJavaScriptEnabled(true);
        wvTime.setWebViewClient(new WebViewClient());
        wvTime.loadUrl("http://www.baidu.com");

        tvBudget = (TextView) view.findViewById(R.id.tvBudget);
        tvBudget2 = (TextView) view.findViewById(R.id.tvBudget2);
        tvCost = (TextView) view.findViewById(R.id.tvCost);
        tvCost2 = (TextView) view.findViewById(R.id.tvCost2);
        ibReport = (ImageButton) view.findViewById(R.id.ibReport);

        tvBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), BudgetActivity.class);
                getActivity().startActivity(intent);
            }
        });

        tvCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), getActivity().getClass());
            }
        });

        ibReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReportActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
