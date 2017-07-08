/*package com.example.lwxwl.coin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lwxwl.coin.R;

public class ChooseAvatarActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_choose, btn_next;
    private ImageView imageView;
    private int clickStatus;
    private int imageIndex;
    private int imageIndexRest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        imageIndex = 1;
        clickStatus =0;
        initView();
    }
    private void initView(){
        btn_choose= (Button) findViewById(R.id.btn_choose);
        btn_next = (Button) findViewById(R.id.btn_next);
        imageView  = (ImageView) findViewById(R.id.ivChooseAvatar);
        btn_choose.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_choose:
                Intent intent = new Intent(ChooseAvatarActivity.this, MainActivity.class);
                intent.putExtra("id", 3);
                startActivity(intent);

                if (imageIndex <= 5) {
                    switch (imageIndex) {
                        case 2:
                            intent.putExtra("key", R.drawable.user_avatar);
                            break;
                        case 3:
                            intent.putExtra("key", R.drawable.user_avatar);
                            break;
                        case 4:
                            intent.putExtra("key", R.drawable.user_avatar);
                            break;
                        case 5:
                            intent.putExtra("key", R.drawable.user_avatar);
                            break;
                    }
                }
                if (imageIndex > 5 ) {
                    imageIndexRest = imageIndex % 5;
                    switch (imageIndexRest) {
                        case 1:
                            intent.putExtra("key", R.drawable.user_avatar);
                            break;
                        case 2:
                            intent.putExtra("key", R.drawable.user_avatar);
                            break;
                        case 3:
                            intent.putExtra("key", R.drawable.user_avatar);
                            break;
                        case 4:
                            intent.putExtra("key", R.drawable.user_avatar);
                            break;
                        case 0:
                            intent.putExtra("key", R.drawable.user_avatar);
                            break;
                    }
                }
                startActivity(intent);
                break;
            case R.id.btn_next:
                switch (clickStatus) {
                    case 0:
                        imageView.setImageResource(R.drawable.user_avatar);
                        clickStatus++;
                        imageIndex++;
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.user_avatar);
                        clickStatus++;
                        imageIndex++;
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.user_avatar);
                        clickStatus++;
                        imageIndex++;
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.user_avatar);
                        clickStatus++;
                        imageIndex++;
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.user_avatar);
                        clickStatus = 0;
                        imageIndex++;
                        break;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
*/