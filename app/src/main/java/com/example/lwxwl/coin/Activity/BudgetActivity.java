package com.example.lwxwl.coin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.example.lwxwl.coin.Adapter.InterfaceAdapter;
import com.example.lwxwl.coin.Model.AddBudget;
import com.example.lwxwl.coin.Model.AddBudgetUser;
import com.example.lwxwl.coin.Model.Application;
import com.example.lwxwl.coin.R;
import com.example.lwxwl.coin.Utils.ToastUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BudgetActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton btn_clear;
    private ImageButton btn_done;
    private MaterialEditText edit_budget;

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    Retrofit retrofit;
    InterfaceAdapter interfaceAdapter;

    Calendar c = Calendar.getInstance();
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        btn_clear = (ImageButton) findViewById(R.id.btn_clear);
        btn_done = (ImageButton) findViewById(R.id.btn_done);
        edit_budget = (MaterialEditText) findViewById(R.id.edit_budget);
        toolbar = (Toolbar) findViewById(R.id.toolbar_budget);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://120.77.246.73:5488/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        interfaceAdapter = retrofit.create(InterfaceAdapter.class);

        edit_budget.setText(Application.storedUserAddBudget);

        // 不保存数据
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BudgetActivity.this, CoinMainActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
            }
        });

        // 保存数据
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int budget = Integer.parseInt(edit_budget.getText().toString());
                Application.storedUserAddBudget = budget;
                AddBudgetUser user = new AddBudgetUser(budget, month);
                Call<AddBudget> call = interfaceAdapter.getAddBudget(user);
                call.enqueue(new Callback<AddBudget>() {
                    @Override
                    public void onResponse(Call<AddBudget> call, Response<AddBudget> response) {
                        AddBudget addBudget = response.body();
                        if (response.code() == 200) {
                            Application.storedUserAddBudget = addBudget.getBudget();
                            ToastUtils.showShort(BudgetActivity.this, R.string.save_successfully_local);
                            Intent intent = new Intent(BudgetActivity.this, CoinMainActivity.class);
                            startActivity(intent);
                        }
                        if(response.code() == 502) {
                            ToastUtils.showShort(BudgetActivity.this, R.string.save_failed_local);
                        }
                    }

                    @Override
                    public void onFailure(Call<AddBudget> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                Intent intent = new Intent(BudgetActivity.this, CoinMainActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
            }
        });

    }

}

