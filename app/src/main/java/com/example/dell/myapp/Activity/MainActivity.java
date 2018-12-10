package com.example.dell.myapp.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.dell.myapp.EveryDatabaseHelper;
import com.example.dell.myapp.R;

public class MainActivity extends AppCompatActivity {

    public EveryDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private RadioButton toFinance;
    private RadioButton toInform;
    private RadioButton toFit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new EveryDatabaseHelper(MainActivity.this,"myDatabase.db",
                null,4);
        db = dbHelper.getWritableDatabase();
        bindView();
        toFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FinanceMainActivity.class);
                startActivity(intent);
            }
        });
        toInform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InformActivity.class);
                startActivity(intent);
            }
        });
        toFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FitMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void bindView() {
        toFinance = (RadioButton) findViewById(R.id.to_finance);
        toInform = findViewById(R.id.to_inform);
        toFit = findViewById(R.id.to_fit);
    }
}
