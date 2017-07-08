
/*package com.example.lwxwl.coin.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lwxwl.coin.Fragment.EditMoneyFragment;
import com.example.lwxwl.coin.Fragment.EditRemarkFragment;

public class EditMoneyRemarkFragmentAdapter extends FragmentPagerAdapter {

    private int type;

    public EditMoneyRemarkFragmentAdapter(FragmentManager fm, int type) {
        super(fm);
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) return EditMoneyFragment.newInstance(0, type);
        else return EditRemarkFragment.newInstance(1, type);
    }

    @Override
    public int getCount() {
        return 2;
    }
}*/