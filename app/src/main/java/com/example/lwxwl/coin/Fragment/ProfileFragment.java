package com.example.lwxwl.coin.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lwxwl.coin.Activity.MainActivity;
import com.example.lwxwl.coin.Adapter.InterfaceAdapter;
import com.example.lwxwl.coin.Model.Application;
import com.example.lwxwl.coin.Model.Profile;
import com.example.lwxwl.coin.R;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    private CircleImageView profile_user_avatar;
    private TextView profile_user_name;
    private Button btn_exit;
    private Profile profile;

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    Retrofit retrofit;
    InterfaceAdapter interfaceAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://120.77.246.73:5488/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        interfaceAdapter = retrofit.create(InterfaceAdapter.class);
        getUserProfile();

        profile_user_avatar = (CircleImageView) view.findViewById(R.id.profile_user_avatar);
        profile_user_name = (TextView) view.findViewById(R.id.profile_user_name);
        //profile_user_name.setText(Application.storedUsername);
        btn_exit = (Button) view.findViewById(R.id.btn_exit);
        /*profile_user_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAvatar();
            }
        });
        */
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    /*private void changeAvatar() {
        Intent intent = new Intent();
        int bitmapSrc = intent.getIntExtra("key", R.drawable.user_avatar);
        profile_user_avatar.setImageResource(bitmapSrc);
    }
    */

    private void getUserProfile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Profile> call = interfaceAdapter.getProfile(Application.storedUserToken);
                call.enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        profile = response.body();
                        profile_user_name.setText(profile.getUsername());
                        //Message msg = new Message();
                        //msg.obj = profile;
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }).start();

    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}

