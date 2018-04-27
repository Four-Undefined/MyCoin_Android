package com.mycoin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mycoin.Application;
import com.mycoin.R;
import com.mycoin.data.InterfaceAdapter;
import com.mycoin.data.Register;
import com.mycoin.data.RegisterUser;
import com.mycoin.util.ConnectionUtils;
import com.mycoin.util.ToastUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener {

    private LinearLayout mLlRegister;
    private MaterialEditText mEdtRegisterName;
    private MaterialEditText mEdtRegisterPassword;
    private TextView mTxvGoToLogin;
    private Button mBtnRegister;

    private String mUserName;
    private String mUserPassword;

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    Retrofit retrofit;
    InterfaceAdapter interfaceAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initNet();
    }

    private void initView() {
        mLlRegister = (LinearLayout) findViewById(R.id.ll_register);
        mEdtRegisterName = (MaterialEditText) findViewById(R.id.edt_register_name);
        mEdtRegisterPassword = (MaterialEditText) findViewById(R.id.edt_register_password);
        mTxvGoToLogin = (TextView) findViewById(R.id.txv_go_to_login);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mTxvGoToLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);

        mEdtRegisterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    private void initNet() {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://120.77.246.73:4588/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        interfaceAdapter = retrofit.create(InterfaceAdapter.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txv_go_to_login:
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register:
                mUserName = mEdtRegisterName.getText().toString();
                mUserPassword = mEdtRegisterPassword.getText().toString();
                ConnectionUtils.makeSnackBar(mLlRegister, getApplicationContext());
                if (mUserName.length() >= 4 && mUserName.length() < 20) {
                    if ((mUserPassword.length() >= 6 && mUserPassword.length() < 32)) {
                        register();
                    }else {
                        Snackbar.make(mLlRegister, R.string.tip_please_input_6_32_password, Snackbar.LENGTH_INDEFINITE).
                                setAction(R.string.input_again, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mEdtRegisterPassword.setText("");
                                    }
                                }).show();
                    }
                }else{
                    Snackbar.make(mLlRegister, R.string.tip_please_input_4_20_name, Snackbar.LENGTH_INDEFINITE).
                            setAction(R.string.input_again, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mEdtRegisterName.setText("");
                                }
                            }).show();
                }
                break;
        }
    }

    private void register() {
        RegisterUser registerUser = new RegisterUser(mUserName, mUserPassword);
        Call<Register> call = interfaceAdapter.getRegisterInfo(registerUser);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                Register bean = response.body();
                if (response.code() == 200) {
                    Application.storedUsername = mUserName;
                    ToastUtils.showShort(RegisterActivity.this, R.string.register_successfully);
                    Intent intent = new Intent(RegisterActivity.this, CoinMainActivity.class);
                    startActivity(intent);
                }else if(response.code() == 401) {
                    ToastUtils.showShort(RegisterActivity.this, R.string.user_existed);
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}