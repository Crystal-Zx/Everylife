package com.example.dell.myapp.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.myapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FitMainActivity extends AppCompatActivity{// AppCompat implements View.OnClickListener
//    private SQLiteDatabase db;

    private ImageButton toEnergy;
    private ImageButton toNutrition;
    private ImageButton toRecord;
    private ImageButton toSave;
    private TextView tvEnergy;
    private TextView tvNutrition;
    private SensorManager sManager;
    private TextView tvStepCount;
    private String dateStr;

    private Sensor stepCounter;//步伐总数传感器
    private SensorEventListener stepCounterListener;//步伐总数传感器事件监听器
    private SimpleDateFormat simpleDateFormat;//时间格式化

    private SQLiteDatabase db;
    private boolean isExist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit);
        sManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        bindView();
        initData();
        initListener();
        toEnergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FitMainActivity.this,EnergyActivity.class);
                startActivityForResult(intent,1);
            }
        });
        toNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FitMainActivity.this,NutrtionActivity.class);
                startActivityForResult(intent,2);
            }
        });
        toRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FitMainActivity.this,FitRecordActivity.class);
                startActivity(intent);
            }
        });
        toSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFitInfo();
            }
        });
    }

    private void bindView() {
        tvStepCount = findViewById(R.id.step_count);
        toEnergy = findViewById(R.id.to_energy_rc);
        toNutrition = findViewById(R.id.to_nutrition_rc);
        toRecord = findViewById(R.id.fit_record);
        toSave = findViewById(R.id.fit_save);
        tvEnergy = findViewById(R.id.energy_rc);
        tvNutrition = findViewById(R.id.nutrition_rc);
    }

    private void saveFitInfo() {
        float stepCount = Float.parseFloat(tvStepCount.getText().toString());
        float energyRc = Float.parseFloat(tvEnergy.getText().toString());
        float nutritionRc = Float.parseFloat(tvNutrition.getText().toString());
        //获取当前系统时间，即记录此条信息时的时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate =  new Date(System.currentTimeMillis());
        dateStr = formatter.format(curDate);
        ContentValues values = new ContentValues();
        //组装当前数据记录
        values.put("date",dateStr);
        values.put("stepCount",stepCount);
        values.put("energy",energyRc);
        values.put("nutrition",nutritionRc);
        //打开指定数据库
        String path = "/data/data/com.example.dell.myapp/databases/myDatabase.db";
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor = db.query("Fit",new String[]{"date"},null,null,null,null,null);
        if(cursor.moveToFirst()) {
            do {
                String dateRc = cursor.getString(cursor.getColumnIndex("date"));
                if(dateRc.equals(dateStr)) {
                    isExist = true;
                    db.update("Fit",values,"date = ?",new String[]{dateStr});
                    Toast.makeText(FitMainActivity.this,"更新成功！",Toast.LENGTH_SHORT).show();
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        if(!isExist) {
            db.insert("Fit",null,values);
        }
        values.clear();
        db.close();
        Toast.makeText(FitMainActivity.this,"记录成功！",Toast.LENGTH_SHORT).show();
    }

    protected void initData() {
        sManager= (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器系统服务
        stepCounter=sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);//获取计步总数传感器
        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    }

    protected void initListener() {
        stepCounterListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Log.e("Counter-SensorChanged",event.values[0]+"---"+event.accuracy+"---"+event.timestamp);
                tvStepCount.setText("" + event.values[0]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.e("Counter-Accuracy",sensor.getName()+"---"+accuracy);

            }
        };
    }
    private void registerSensor(){
        //注册传感器事件监听器
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)){
//            sManager.registerListener(stepDetectorListener,stepDetector,SensorManager.SENSOR_DELAY_FASTEST);
            sManager.registerListener(stepCounterListener,stepCounter,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
//        unregisterSensor();
    }

    @Override
    public void onResume(){
        super.onResume();
        registerSensor();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if(resultCode == RESULT_OK) {
                    float sumE = data.getFloatExtra("energy",0);
                    tvEnergy.setText(Float.toString(sumE));
                }
                break;
            case 2:
                if(resultCode == RESULT_OK) {
                    float sumN = data.getFloatExtra("nutrition",0);
                    tvNutrition.setText(Float.toString(sumN));
                }
        }
    }

}
