package com.example.dell.myapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.myapp.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText userId;
    private EditText userPsw;
    private EditText userPswAgain;
    private EditText userPhone;
    private EditText userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userPsw = (EditText) findViewById(R.id.user_psw);
        userPswAgain = (EditText) findViewById(R.id.user_psw_again);
        userId = (EditText) findViewById(R.id.user_id);
        userPhone = (EditText) findViewById(R.id.user_phone);
        userEmail = (EditText) findViewById(R.id.user_email);
        Button btnReg = (Button) findViewById(R.id.btn_reg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = userId.getText().toString();
                String psw = userPsw.getText().toString();
                String pswA = userPswAgain.getText().toString();
                String phone = userPhone.getText().toString();
                String email = userEmail.getText().toString();
                while(true) {
                    if(!psw.equals(pswA)) {
                        Toast.makeText(RegisterActivity.this,"密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
                        userPsw.setText("");
                        userPswAgain.setText("");
                    }
                    else if(id == "" || psw == "" || pswA == "") {
                        Toast.makeText(RegisterActivity.this,"用户名和密码不能为空，请重新输入",Toast.LENGTH_SHORT).show();
                        userId.setText("");
                        userPsw.setText("");
                        userPswAgain.setText("");
                    }
                    else if(phone == "" || email == "") {
                        Toast.makeText(RegisterActivity.this,"请输入手机号或邮箱，便于密码找回",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SharedPreferences.Editor editor = getSharedPreferences("userInformation",MODE_PRIVATE).edit();
                        editor.putString("userId",userId.getText().toString());
                        editor.putString("userPsw",userPsw.getText().toString());
                        editor.putString("userPhone",userPhone.getText().toString());
                        editor.putString("userEmail",userEmail.getText().toString());
                        editor.apply();
                        break;
                    }
                }
                Toast.makeText(RegisterActivity.this,"注册成功！",10000 * Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
