/*package com.example.lwxwl.coin.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import com.example.lwxwl.coin.R;

public class SplashActivity extends Activity {

    private LinearLayout llSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       //
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,
                        MainActivity.class); // 从启动动画ui跳转到主ui
                startActivity(intent);
                //overridePendingTransition(R.in_from_right,
                //      R.anim.out_to_left);
                SplashActivity.this.finish(); // 结束启动动画界面

            }
        }, 4000); // 启动动画持续3秒钟
        //

        llSplash = (LinearLayout) findViewById(R.id.llSplash);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(2000);
        llSplash.startAnimation(alphaAnimation);
    }

}
*/
