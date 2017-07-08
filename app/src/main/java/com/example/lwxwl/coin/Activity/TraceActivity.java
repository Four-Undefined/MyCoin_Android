package com.example.lwxwl.coin.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lwxwl.coin.Adapter.TraceListAdapter;
import com.example.lwxwl.coin.R;
import com.example.lwxwl.coin.Model.Trace;

import java.util.ArrayList;
import java.util.List;

public class TraceActivity extends AppCompatActivity {

    private RecyclerView rvTrace;
    private List<Trace> traceList = new ArrayList<>(10);
    private TraceListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace);
        findView();
        initData();
    }

    private void findView() {
        rvTrace = (RecyclerView) findViewById(R.id.rvTrace);
    }

    private void initData() {
        // 模拟一些假的数据
        traceList.add(new Trace("今天", "总支出：40.00", "饮食：30.00"));
        traceList.add(new Trace("昨天", "总支出：40.00", "饮食：30.00"));
        traceList.add(new Trace("06.16", "总支出：40.00", "饮食：30.00"));
        adapter = new TraceListAdapter(this, traceList);
        rvTrace.setLayoutManager(new LinearLayoutManager(this));
        rvTrace.setAdapter(adapter);
    }
}
