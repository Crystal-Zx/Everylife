package com.example.dell.myapp.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dell.myapp.R;

public class LoadActivity extends AppCompatActivity {

    //图片延迟时间
    private final static  int LOAD_DISPLAY_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadActivity.this, LoginActivity.class);
                startActivity(intent);
                //加finish()避免返回键跳回加载页面！
                finish();
            }
        }, LOAD_DISPLAY_TIME);
    }
}
