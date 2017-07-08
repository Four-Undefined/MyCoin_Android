package com.example.lwxwl.coin.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.lwxwl.coin.Fragment.LatelyFragment;
import com.example.lwxwl.coin.Fragment.MonthlyFragment;
import com.example.lwxwl.coin.R;


public class ReportActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private WebView webView;
    private Toolbar toolbar2;
    private ImageButton btn_back;
    private Button btn_lately;
    private Button btn_monthly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        //初始化ViewPager
        InitViewPager();
        //初始化布局
        InitView();


        webView = new WebView(this);
        webView.getSettings().getJavaScriptEnabled();

        btn_lately.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //设置打开的页面地址
                    webView.loadUrl("http://www.baidu.com");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setContentView(webView);
            }
        });

        btn_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    webView.loadUrl("http://www.baidu.com");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setContentView(webView);
            }
        });

    }

    private void InitViewPager() {
        //获取ViewPager
        //创建一个FragmentPagerAdapter对象，该对象负责为ViewPager提供多个Fragment
        viewPager = (ViewPager) findViewById(R.id.vpChoose);
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(
                getSupportFragmentManager()) {

            //获取第position位置的Fragment
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new LatelyFragment();
                        break;
                    case 1:
                        fragment = new MonthlyFragment();
                        break;
                }
                return fragment;
            }

            //该方法的返回值i表明该Adapter总共包括多少个Fragment
            @Override
            public int getCount() {
                return 2;
            }

        };
        //为ViewPager组件设置FragmentPagerAdapter
        viewPager.setAdapter(pagerAdapter);

        //为viewpager组件绑定时间监听器
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            //当ViewPager显示的Fragment发生改变时激发该方法
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    //如果是点击的第一个button，那么就让第一个button的字体变为蓝色
                    //其他的button的字体的颜色变为黑色
                    case 0:
                        btn_lately.setTextColor(getResources().getColor(R.color.purple));
                        btn_monthly.setTextColor(Color.BLACK);
                        break;
                    case 1:
                        btn_lately.setTextColor(Color.BLACK);
                        btn_monthly.setTextColor(getResources().getColor(R.color.purple));
                        break;
                }
                super.onPageSelected(position);
            }
        });
    }

    private void InitView() {
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_lately = (Button) findViewById(R.id.btn_lately);
        btn_monthly = (Button) findViewById(R.id.btn_monthly);
        toolbar2 = (Toolbar) findViewById(R.id.toolbar_report);

        //设置点击监听
        btn_lately.setOnClickListener(this);
        btn_monthly.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        //将button中字体的颜色先按照点击第一个button的效果初始化
        btn_lately.setTextColor(getResources().getColor(R.color.purple));
        btn_monthly.setTextColor(Color.BLACK);

        //setSupportActionBar(toolbar2);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    //点击主界面上面的button后，将viewpager中的fragment跳转到对应的item
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lately:
                viewPager.setCurrentItem(0);
                break;
            case R.id.btn_monthly:
                viewPager.setCurrentItem(1);
                break;
            case R.id.btn_back:
                Intent intent = new Intent(ReportActivity.this, CoinMainActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
                break;
        }
    }

}

