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
import com.example.lwxwl.coin.R;
import com.example.lwxwl.coin.Utils.ConnectionUtils;
import com.example.lwxwl.coin.Utils.ToastUtils;
import com.example.lwxwl.coin.Model.Application;
import com.example.lwxwl.coin.Model.Register;
import com.example.lwxwl.coin.Model.RegisterUser;
import com.rengwuxian.materialedittext.MaterialEditText;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener {

    private MaterialEditText edit_name;
    private MaterialEditText edit_password;
    private TextView user_existed;
    private Button btn_register;
    private LinearLayout llRegister;
    private String userName;
    private String userPassword;

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    Retrofit retrofit;
    InterfaceAdapter interfaceAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://120.77.246.73:5488/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        interfaceAdapter = retrofit.create(InterfaceAdapter.class);

        edit_name = (MaterialEditText) findViewById(R.id.edit_name);
        edit_password = (MaterialEditText) findViewById(R.id.edit_password);
        user_existed = (TextView) findViewById(R.id.user_existed);
        user_existed.setOnClickListener(this);
        llRegister = (LinearLayout) findViewById(R.id.llRegister);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                userName = edit_name.getText().toString();
                userPassword = edit_password.getText().toString();
                ConnectionUtils.makeSnackBar(llRegister,getApplicationContext());
                if (userName.length() >= 4 && userName.length() < 20) {
                    if ((userPassword.length() >= 6 && userPassword.length() < 32)) {
                        RegisterUser user = new RegisterUser(edit_name.getText().toString(), edit_password.getText().toString());
                        Call<Register> call = interfaceAdapter.getRegisterInfo(user);
                        call.enqueue(new Callback<Register>() {
                                @Override
                                public void onResponse(Call<Register> call, Response<Register> response) {
                                    Register bean = response.body();
                                    Application.storedUsername = userName;
                                    Intent intent = new Intent(RegisterActivity.this, CoinMainActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<Register> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        ToastUtils.showShort(this, R.string.register_successfully);
                    }else {
                        Snackbar.make(llRegister, R.string.tip_please_input_6_32_password, Snackbar.LENGTH_INDEFINITE).
                                setAction(R.string.input_again, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        edit_password.setText("");
                                    }
                                }).show();
                    }
                }else{
                    Snackbar.make(llRegister, R.string.tip_please_input_4_20_name, Snackbar.LENGTH_INDEFINITE).
                            setAction(R.string.input_again, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    edit_name.setText("");
                                }
                            }).show();
                }
                break;
            case R.id.user_existed:
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

}