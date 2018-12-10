package com.example.dell.myapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.myapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    private Button btnResetPw;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPsw;
    int isFirstIn = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isFirstIn++;
        pref = getSharedPreferences("userInformation",MODE_PRIVATE);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btn_login);
        rememberPsw = (CheckBox) findViewById(R.id.remember_psw);
        if(isFirstIn == 0) {
            editor = getSharedPreferences("userInformation",0).edit();
            editor.putBoolean("remember_psw",false);
        }
        boolean isRemember = pref.getBoolean("remember_psw",false);
        if(isRemember) {
            String userId = pref.getString("userId","");
            String userPsw = pref.getString("userPsw","");
            accountEdit.setText(userId);
            passwordEdit.setText(userPsw);
            rememberPsw.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String userId = pref.getString("userId","");
                String userPsw = pref.getString("userPsw","");
                if(account.equals(userId) && password.equals(userPsw) && userId != "" && userPsw != "") {
                    Log.d("LoginActivity.this", "rememberPsw isChecked =  " + rememberPsw.isChecked());
                    if(rememberPsw.isChecked()) {     //“记住密码”复选框被选中
                        editor.putBoolean("remember_psw", true);
                        editor.apply();
                    }
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this,"账号或密码错误！",Toast.LENGTH_SHORT).show();
                    accountEdit.setText("");
                    passwordEdit.setText("");
                }
            }
        });
        Button toReg = (Button) findViewById(R.id.btn_toReg);
        toReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnResetPw = findViewById(R.id.btnForgetPw);
        btnResetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ResetPwActivity.class);
                startActivity(intent);
            }
        });
    }
}
