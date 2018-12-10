package com.example.dell.myapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dell.myapp.R;

public class EnergyActivity extends AppCompatActivity {

    private EditText runRc;
    private EditText bicycleRc;
    private EditText swimRc;
    private EditText skiRc;
    private EditText ballRc;
    private EditText boatRc;
    private ImageButton toSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy);
        bindView();
        toSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float run,bicycle,swim,ski,ball,boat,sumE = 0;
                if(runRc.getText().toString().equals("") || bicycleRc.getText().toString().equals("")
                        || swimRc.getText().toString().equals("") || skiRc.getText().toString().equals("")
                        || ballRc.getText().toString().equals("") || boatRc.getText().toString().equals("")) {
                    Toast.makeText(EnergyActivity.this,"运动时间不能为空！",Toast.LENGTH_LONG).show();
                }else {
                    run = Float.parseFloat(runRc.getText().toString());
                    bicycle = Float.parseFloat(bicycleRc.getText().toString());
                    swim = Float.parseFloat(swimRc.getText().toString());
                    ski = Float.parseFloat(skiRc.getText().toString());
                    ball = Float.parseFloat(ballRc.getText().toString());
                    boat = Float.parseFloat(boatRc.getText().toString());
                    sumE = run * 13 + bicycle *8 + swim * 11 + ski * 12 + ball *12 + boat * 5;
                    sumE = (float)(Math.round(sumE*10))/10;
                    Intent intent = new Intent();
                    intent.putExtra("energy",sumE);
                    setResult(RESULT_OK,intent);
                    Toast.makeText(EnergyActivity.this,"记录成功！",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    private void bindView() {
        runRc = findViewById(R.id.run_rc);
        bicycleRc = findViewById(R.id.bicycle_rc);
        swimRc = findViewById(R.id.swim_rc);
        skiRc = findViewById(R.id.ski_rc);
        ballRc = findViewById(R.id.ball_rc);
        boatRc = findViewById(R.id.boat_rc);
        toSave = findViewById(R.id.energy_save);
    }

}
