package com.example.dell.myapp.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.myapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FitRecordActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnCurDate;
    private TextView tvStepCount;
    private TextView tvEnergy;
    private TextView tvNutrition;
    private SQLiteDatabase db;

    private int mYear, mMonth, mDay;
    private boolean isFound = false;
//    private int isChanged = 0;
    private final int DATE_DIALOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_record);
        bindView();
        //获取当前系统时间，并显示在Btn中
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
//        Date curDate =  new Date(System.currentTimeMillis());
//        String dateStr = formatter.format(curDate);
        btnCurDate.setText("请选择查询时间：");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setDateStr();
//        setView();
    }

    private void bindView() {
        btnBack = findViewById(R.id.fit_record_back);
        btnCurDate = findViewById(R.id.fit_record_date);
        tvStepCount = findViewById(R.id.fit_record_step);
        tvEnergy = findViewById(R.id.fit_record_energy);
        tvNutrition = findViewById(R.id.fit_record_nutrition);
    }

    private void setDateStr() {
        btnCurDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    public void display() {
        if(mDay / 10 < 1)
//            return (new StringBuffer().append(mYear).append("年").append("0" + (mMonth + 1)).append("月").append("0" + mDay).append("日")).toString();
            btnCurDate.setText(new StringBuffer().append(mYear).append("年").append("0" + (mMonth + 1)).append("月").append("0" + mDay).append("日"));
        else
            btnCurDate.setText(new StringBuffer().append(mYear).append("年").append("0" + (mMonth + 1)).append("月").append(mDay).append("日"));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
            setView();
        }
    };

    private void setView() {
        String selectDate = btnCurDate.getText().toString();
        String path = "/data/data/com.example.dell.myapp/databases/myDatabase.db";
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);//, SQLiteDatabase.OPEN_READWRITE
        //得到数据库中的数据源
        Cursor cursor = db.query("Fit", new String[]{"date", "stepCount", "energy", "nutrition"}, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String queryDate = cursor.getString(cursor.getColumnIndex("date"));
                if (!queryDate.equals(selectDate))
                    continue;
                else {
                    isFound = true;
                    float stepCount = cursor.getFloat(cursor.getColumnIndex("stepCount"));
                    float energy = cursor.getFloat(cursor.getColumnIndex("energy"));
                    float nutrition = cursor.getFloat(cursor.getColumnIndex("nutrition"));
                    tvStepCount.setText(Float.toString(stepCount));
                    tvEnergy.setText(Float.toString(energy));
                    tvNutrition.setText(Float.toString(nutrition));
                }
                break;
            }
        }
        cursor.close();
        if(!isFound) {
            tvStepCount.setText("0.0");
            tvEnergy.setText("0.0");
            tvNutrition.setText("0.0");
            Toast.makeText(FitRecordActivity.this,"该日期无记录！",Toast.LENGTH_SHORT).show();
        }
        isFound = false;
    }
}
