package com.example.lwxwl.coin.Model;

public class Trace {
    // 时间
    private String tvTime;
    // 总支出
    private String tvSummary;
    // 每项支出
    private String tvItem;

    public Trace() {
    }

    public Trace(String tvTime, String tvSummary, String tvItem) {
        this.tvTime = tvTime;
        this.tvSummary = tvSummary;
        this.tvItem = tvItem;
    }

    public String getTvTime() {
        return tvTime;
    }

    public void setTvTime(String tvTime) {
        this.tvTime = tvTime;
    }

    public String getTvSummary() {
        return tvSummary;
    }

    public void setTvSummary(String tvSummary) {
        this.tvSummary = tvSummary;
    }

    public String getTvItem() {
        return tvItem;
    }

    public void setTvItem(String tvItem) {
        this.tvItem = tvItem;
    }
}
