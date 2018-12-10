package com.example.dell.myapp.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.myapp.R;

public class ResetPwActivity extends AppCompatActivity {

    private TextView tvPw;
    private TextView tvPwA;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pw);
        tvPw = findViewById(R.id.edit_pw);
        tvPwA = findViewById(R.id.edit_pwa);
        btnSave = findViewById(R.id.edit_save_pw);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvPw.getText().toString().equals(tvPw.getText().toString())) {
                    SharedPreferences.Editor editor = getSharedPreferences("userInformation",MODE_PRIVATE).edit();
                    editor.putString("userPsw",tvPw.getText().toString());
                    editor.apply();
                    Toast.makeText(ResetPwActivity.this,"保存成功！",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(ResetPwActivity.this,"密码输入不一致！",Toast.LENGTH_SHORT).show();
                    tvPw.setText("");
                    tvPwA.setText("");
                }
            }
        });
    }
}
