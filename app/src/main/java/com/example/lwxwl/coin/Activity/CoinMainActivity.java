package com.example.lwxwl.coin.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.lwxwl.coin.Adapter.ViewPagerAdapter;
import com.example.lwxwl.coin.Fragment.DataFragment;
import com.example.lwxwl.coin.Fragment.ProfileFragment;
import com.example.lwxwl.coin.Fragment.WriteFragment;
import com.example.lwxwl.coin.R;


public class CoinMainActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;

    private ViewPager viewPager;

    DataFragment dataFragment;
    WriteFragment writeFragment;
    ProfileFragment profileFragment;
    MenuItem menuItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_data:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.navigation_write:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.navigation_profile:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                menuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
        initView();

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        dataFragment = new DataFragment();
        writeFragment = new WriteFragment();
        profileFragment = new ProfileFragment();
        adapter.addFragment(dataFragment);
        adapter.addFragment(writeFragment);
        adapter.addFragment(profileFragment);
        viewPager.setAdapter(adapter);
    }

    private void initView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        dataFragment = new DataFragment();
        writeFragment = new WriteFragment();
        profileFragment = new ProfileFragment();
        ft.commit();
    }

    @Override
    protected void onResume() {
        int id = getIntent().getIntExtra("id", 0);
        switch (id) {
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.data_fragment, new DataFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.write_fragment, new WriteFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case 3:
                getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.llProfile, new ProfileFragment())
                    .addToBackStack(null)
                    .commit();
                break;
            default:
                break;
        }
        super.onResume();
    }
}
