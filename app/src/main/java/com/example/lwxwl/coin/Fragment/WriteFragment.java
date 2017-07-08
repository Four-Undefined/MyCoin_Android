package com.example.lwxwl.coin.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lwxwl.coin.Adapter.InterfaceAdapter;
import com.example.lwxwl.coin.Model.Accounting;
import com.example.lwxwl.coin.Model.AccountingUser;
import com.example.lwxwl.coin.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WriteFragment extends Fragment {

    private Toolbar toolbar_cost;
    private ImageButton btn_clear_cost;
    private ImageView ivTag;
    private TextView tvTag;
    private MaterialEditText edit_money;
    private ImageButton btn_eat, btn_hung, btn_cloth, btn_play, btn_education, btn_general;
    private int money;

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    Retrofit retrofit;
    InterfaceAdapter interfaceAdapter;

    /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write, container, false);

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://120.77.246.73:5488/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        interfaceAdapter = retrofit.create(InterfaceAdapter.class);

        toolbar_cost = (Toolbar) view.findViewById(R.id.toolbar_cost);
        btn_clear_cost = (ImageButton) view.findViewById(R.id.btn_clear_cost);
        ivTag = (ImageView) view.findViewById(R.id.ivTag);
        tvTag = (TextView) view.findViewById(R.id.tvTag);
        edit_money = (MaterialEditText) view.findViewById(R.id.edit_money);
        btn_eat = (ImageButton) view.findViewById(R.id.btn_eat);
        btn_hung = (ImageButton) view.findViewById(R.id.btn_hung);
        btn_cloth = (ImageButton) view.findViewById(R.id.btn_cloth);
        btn_play = (ImageButton) view.findViewById(R.id.btn_play);
        btn_education = (ImageButton) view.findViewById(R.id.btn_education);
        btn_general = (ImageButton) view.findViewById(R.id.btn_general);

        btn_clear_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.data_fragment, new DataFragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        btn_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = Integer.parseInt(edit_money.getText().toString());
                ivTag.setImageResource(R.drawable.ic_eat);
                tvTag.setText(R.string.tag_eat);
                tvTag.setTextColor(getResources().getColor(R.color.ic_eat));
                AccountingUser user = new AccountingUser(money, 0, 0, 0, 0, 0);
                Call<Accounting> call = interfaceAdapter.getAccounting(user);
                call.enqueue(new Callback<Accounting>() {
                    @Override
                    public void onResponse(Call<Accounting> call, Response<Accounting> response) {
                        Accounting accounting = response.body();
                    }

                    @Override
                    public void onFailure(Call<Accounting> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
        btn_hung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivTag.setImageResource(R.drawable.ic_hung);
                tvTag.setText(R.string.tag_hung);
                tvTag.setTextColor(getResources().getColor(R.color.ic_hung));
            }
        });
        btn_cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivTag.setImageResource(R.drawable.ic_cloth);
                tvTag.setText(R.string.tag_cloth);
                tvTag.setTextColor(getResources().getColor(R.color.ic_cloth));
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivTag.setImageResource(R.drawable.ic_play);
                tvTag.setText(R.string.tag_play);
                tvTag.setTextColor(getResources().getColor(R.color.ic_play));
            }
        });
        btn_general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivTag.setImageResource(R.drawable.ic_general);
                tvTag.setText(R.string.tag_general);
                tvTag.setTextColor(getResources().getColor(R.color.ic_general));
            }
        });
        return view;
    }

    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    */

}

    /*
    public TagChooseGridViewAdapter getTagAdapter() {
        return tagAdapter;
    }

    public void setTagAdapter(TagChooseGridViewAdapter tagAdapter) {
        this.tagAdapter = tagAdapter;
    }

    private TagChooseGridViewAdapter tagAdapter;
    private int fragmentPosition;
    public MyGridView myGridView;

    Activity activity;

    static public WriteFragment newInstance(int position) {
        WriteFragment fragment = new WriteFragment();

        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            activity = (Activity)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write, container, false);
        myGridView = (MyGridView)view.findViewById(R.id.gridView);

        tagAdapter = new TagChooseGridViewAdapter(getActivity(), fragmentPosition);

        myGridView.setAdapter(tagAdapter);

        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    ((OnTagItemSelectedListener)activity).onTagItemPicked(position);
                    ((OnTagItemSelectedListener)activity).onAnimationStart(RecordManager.TAGS.get(fragmentPosition * 6 + position + 2).getId());
                } catch (ClassCastException cce){
                    cce.printStackTrace();
                }
            }
        });
        return view;
    }

    public interface OnTagItemSelectedListener {
        void onTagItemPicked(int position);
        void onAnimationStart(int id);
    }

    public void updateTags() {
        ((BaseAdapter)myGridView.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter)myGridView.getAdapter()).notifyDataSetInvalidated();
        myGridView.invalidateViews();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}




*/