package com.example.lwxwl.coin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.lwxwl.coin.R;
import com.example.lwxwl.coin.Model.Trace;

import java.util.ArrayList;
import java.util.List;

public class TraceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private List<Trace> traceList = new ArrayList<>(1);
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL= 0x0001;

    public TraceListAdapter(Context context, List<Trace> traceList) {
        inflater = LayoutInflater.from(context);
        this.traceList = traceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_trace, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemHolder = (ViewHolder) holder;
        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            itemHolder.tvTopLine.setVisibility(View.INVISIBLE);
            // 字体颜色加深
            itemHolder.tvTime.setTextColor(0xff555555);
            itemHolder.tvSummary.setTextColor(0xff555555);
            itemHolder.tvItem.setTextColor(0xff555555);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timelline_dot_first);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            itemHolder.tvTopLine.setVisibility(View.VISIBLE);
            itemHolder.tvTime.setTextColor(0xff999999);
            itemHolder.tvSummary.setTextColor(0xff999999);
            itemHolder.tvItem.setTextColor(0xff999999);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timelline_dot_normal);
        }

        itemHolder.bindHolder(traceList.get(position));
    }

    @Override
    public int getItemCount() {
        return traceList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime, tvSummary, tvItem;
        private TextView tvTopLine, tvDot;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvSummary = (TextView) itemView.findViewById(R.id.tvSummary);
            tvItem = (TextView) itemView.findViewById(R.id.tvItem);
            tvTopLine = (TextView) itemView.findViewById(R.id.tvTopLine);
            tvDot = (TextView) itemView.findViewById(R.id.tvDot);
        }

        public void bindHolder(Trace trace) {
            tvTime.setText(trace.getTvTime());
            tvSummary.setText(trace.getTvSummary());
            tvItem.setText(trace.getTvItem());
        }
    }
}
