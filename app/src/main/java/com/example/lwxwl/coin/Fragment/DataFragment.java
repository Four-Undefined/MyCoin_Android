package com.example.lwxwl.coin.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.lwxwl.coin.Model.Application;
import com.example.lwxwl.coin.Model.Budget;
import com.example.lwxwl.coin.Model.BudgetUser;
import com.example.lwxwl.coin.Model.Cost;
import com.example.lwxwl.coin.Model.CostUser;
import com.example.lwxwl.coin.R;

import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DataFragment extends Fragment {

    private TextView tvBudget, tvBudget2;
    private TextView tvCost, tvCost2;
    private ImageButton ibReport;
    private Budget budget;
    private Cost cost;

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

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://120.77.246.73:5488/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        interfaceAdapter = retrofit.create(InterfaceAdapter.class);

        tvBudget = (TextView) view.findViewById(R.id.tvBudget);
        tvBudget2 = (TextView) view.findViewById(R.id.tvBudget2);
        tvCost = (TextView) view.findViewById(R.id.tvCost);
        tvCost2 = (TextView) view.findViewById(R.id.tvCost2);
        ibReport = (ImageButton) view.findViewById(R.id.ibReport);

        //getUserBudget();
        //getUserCost();
        BudgetUser user1 = new BudgetUser(month);
        Call<Budget> call1 = interfaceAdapter.getUserBudget(user1, Application.storedUserToken);
        call1.enqueue(new Callback<Budget>() {
            @Override
            public void onResponse(Call<Budget> call, Response<Budget> response) {
                budget = response.body();
                if (response.code() == 200) {
                    Application.storedUserBudget = budget.getBudget();
                    Log.d("TTTT", budget.getBudget()+"");
                    tvBudget2.setText(String.valueOf(budget.getBudget()));
                }
                if(response.code() == 502) {
                    tvBudget2.setText(String.valueOf(2000));
                }
            }

            @Override
            public void onFailure(Call<Budget> call, Throwable t) {
                t.printStackTrace();
            }
        });

        CostUser user2 = new CostUser(month);
        Call<Cost> call2 = interfaceAdapter.getCost(month, Application.storedUserToken);
        call2.enqueue(new Callback<Cost>() {
            @Override
            public void onResponse(Call<Cost> call, Response<Cost> response) {
                cost = response.body();
                tvCost2.setText(String.valueOf(cost.getSum()));
                //try {
                //    tvCost2.setText(String.valueOf(cost.getSum()));
                //} catch (Exception e) {
                //    tvCost2.setText(0);
                //}
            }

            @Override
            public void onFailure(Call<Cost> call, Throwable t) {
                t.printStackTrace();
            }
        });

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
/*
    private void getUserBudget() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BudgetUser user = new BudgetUser(month);
                Call<Budget> call = interfaceAdapter.getUserBudget(user, Application.storedUserToken);
                call.enqueue(new Callback<Budget>() {
                    @Override
                    public void onResponse(Call<Budget> call, Response<Budget> response) {
                        budget = response.body();
                        if (response.code() == 200) {
                            Application.storedUserBudget = budget.getBudget();
                            Log.d("TTTT", budget.getBudget()+"");
                            tvBudget2.setText(String.valueOf(budget.getBudget()));
                        }
                        if(response.code() == 502) {
                           tvBudget2.setText(String.valueOf(2000));
                        }
                    }

                    @Override
                    public void onFailure(Call<Budget> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }).start();
    }
    */
/*
    private void getUserCost() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CostUser user2 = new CostUser(month);
                Call<Cost> call2 = interfaceAdapter.getCost(month, Application.storedUserToken);
                call2.enqueue(new Callback<Cost>() {
                    @Override
                    public void onResponse(Call<Cost> call, Response<Cost> response) {
                        cost = response.body();
                        tvCost2.setText(String.valueOf(cost.getSum()));
                        //try {
                        //    tvCost2.setText(String.valueOf(cost.getSum()));
                        //} catch (Exception e) {
                        //    tvCost2.setText(0);
                        //}
                    }

                    @Override
                    public void onFailure(Call<Cost> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }).start();
    }

    */

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
