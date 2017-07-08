package com.example.lwxwl.coin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lwxwl.coin.Adapter.InterfaceAdapter;
import com.example.lwxwl.coin.Model.Application;
import com.example.lwxwl.coin.Model.Login;
import com.example.lwxwl.coin.Model.LoginUser;
import com.example.lwxwl.coin.R;
import com.example.lwxwl.coin.Utils.ConnectionUtils;
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

public class MainActivity extends AppCompatActivity {

    private LinearLayout llLogin;
    private MaterialEditText user_name;
    private MaterialEditText user_password;
    private TextView go_to_register;
    private Button btn_login;

    private String userName;
    private String userPassword;

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    Retrofit retrofit;
    InterfaceAdapter interfaceAdapter;

    Calendar c = Calendar.getInstance();
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://120.77.246.73:5488/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        interfaceAdapter = retrofit.create(InterfaceAdapter.class);
        initView();
    }

    private void initView(){
        llLogin = (LinearLayout) findViewById(R.id.llLogin);
        user_name = (MaterialEditText) findViewById(R.id.user_name);
        user_password = (MaterialEditText) findViewById(R.id.user_password);
        go_to_register = (TextView) findViewById(R.id.go_to_register);
        user_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        go_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = user_name.getText().toString();
                userPassword = user_password.getText().toString();
                ConnectionUtils.makeSnackBar(llLogin, getApplicationContext());
                if ((userName.length() >= 4 && userName.length() <= 20)){

                    if ((userPassword.length() >= 6 && userPassword.length() <= 32)) {

                        Application.storedUsername = user_name.getText().toString();
                        LoginUser user = new LoginUser(userName, userPassword, month, day);
                        Call<Login> call = interfaceAdapter.getUserToken(user);
                        call.enqueue(new Callback<Login>() {
                            @Override
                            public void onResponse(Call<Login> call, Response<Login> response) {
                                Login bean = response.body();
                                if (response.code() == 200) {
                                    Application.storedUserToken = bean.getToken();
                                    ToastUtils.showShort(MainActivity.this, R.string.login_successfully);
                                    Intent intent = new Intent(MainActivity.this, CoinMainActivity.class);
                                    startActivity(intent);
                                }
                                if(response.code() == 502) {
                                    Snackbar.make(llLogin, R.string.login_error,Snackbar.LENGTH_INDEFINITE)
                                            .setAction(R.string.input_again, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    user_password.setText("");
                                                }
                                            }).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Login> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });

                    }else{
                        Snackbar.make(llLogin, R.string.password_length_error, Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.input_again, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        user_password.setText("");
                                    }
                                }).show();
                    }
                }else{
                    Snackbar.make(llLogin, R.string.name_length_error, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.input_again, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    user_name.setText("");
                                }
                            }).show();
                }

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private Application getApp(){
        return ((Application) getApplicationContext());
    }


}
