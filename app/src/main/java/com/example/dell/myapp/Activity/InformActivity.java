package com.example.dell.myapp.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dell.myapp.R;

public class InformActivity extends AppCompatActivity {

    private TextView userIdTv;
    private TextView userPhoneTv;
    private TextView userEmailTv;

    String userId;
    String userPhone;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        SharedPreferences pref = getSharedPreferences("userInformation",MODE_PRIVATE);
        userId = pref.getString("userId","");
        userPhone = pref.getString("userPhone","");
        userEmail = pref.getString("userEmail","");
        bindView();
        initView();
    }

    private void bindView() {
        userIdTv = findViewById(R.id.user_id);
        userPhoneTv = findViewById(R.id.user_phone);
        userEmailTv = findViewById(R.id.user_email);
    }

    private void initView() {
        userIdTv.setText(userId);
        userPhoneTv.setText(userPhone);
        userEmailTv.setText(userEmail);
    }
}
