/*package com.example.lwxwl.coin.Utils;


import android.graphics.Color;

import com.example.lwxwl.coin.Model.CoinApplication;
import com.github.johnpersano.supertoasts.library.SuperToast;

public class CoinToast {
    private static CoinToast ourInstance = new CoinToast();

    public static CoinToast getInstance() {
        return ourInstance;
    }

    private CoinToast() {
    }

    public void showToast(int text, int color) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(CoinApplication.getAppContext());
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setText(CoinApplication.getAppContext().getResources().getString(text));
        superToast.show();
    }

    public void showToast(String text, int color) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(CoinApplication.getAppContext());
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setText(text);
        superToast.show();
    }
}
*/