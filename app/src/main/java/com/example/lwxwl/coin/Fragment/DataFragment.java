package com.example.lwxwl.coin.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lwxwl.coin.Activity.BudgetActivity;
import com.example.lwxwl.coin.Activity.ReportActivity;
import com.example.lwxwl.coin.R;


public class DataFragment extends Fragment {

    private TextView tvBudget;
    private TextView tvCost;
    private ImageButton ibRport;

    /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    */

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        tvBudget = (TextView) view.findViewById(R.id.tvBudget);
        tvCost = (TextView) view.findViewById(R.id.tvCost);
        ibRport = (ImageButton) view.findViewById(R.id.ibReport);
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
        ibRport.setOnClickListener(new View.OnClickListener() {
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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    */

}
