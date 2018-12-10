package com.example.dell.myapp.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.myapp.R;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    private ImageButton editback;
    private ImageButton editsave;
    private EditText editPrice;
    private TextView oldCategory;
    private TextView oldDateStr;
    private Button btnDateStr;
    private Button btnDelete;

    private String dateStr;
    private String imageName;
    private String returnDateStr;
    private String returnImageName;
    private float price;
    private float returnPrice;
//    int clickPosition;
    private int mYear, mMonth, mDay;
    private final int DATE_DIALOG = 1;

//    TextView dateDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        bindView();
//        initEvent();
        getData();
        setSpinner();
        setDateStr();
        editback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(1,intent);
                finish();
            }
        });
        editsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                returnPrice = Float.parseFloat(editPrice.getText().toString());
//                intent.putExtra("clickPosition",clickPosition);
                intent.putExtra("returnDateStr",returnDateStr);
                intent.putExtra("returnImageName",returnImageName);
                intent.putExtra("returnPrice",returnPrice);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(0,intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(1,intent);
        finish();
    }

    private void bindView() {
        editback = findViewById(R.id.edit_back);
        editsave = findViewById(R.id.edit_save);
        editPrice = findViewById(R.id.edit_price);
        oldCategory = findViewById(R.id.old_category);
        oldDateStr = findViewById(R.id.old_dateStr);
        btnDateStr = (Button) findViewById(R.id.new_dateStr);
        btnDelete = findViewById(R.id.btn_delete);
    }

//    private void initEvent() {
//        editback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        editsave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                returnPrice = Float.parseFloat(editPrice.getText().toString());
//                intent.putExtra("clickPosition",clickPosition);
//                intent.putExtra("returndateStr",returnDateStr);
//                intent.putExtra("returnImageName",returnImageName);
//                intent.putExtra("returnPrice",returnPrice);
//                setResult(RESULT_OK,intent);
//                finish();
//            }
//        });
//    }

    private void getData() {
        Intent intent = getIntent();
        dateStr = intent.getStringExtra("dateStr");
        imageName = intent.getStringExtra("imageName");
        price = intent.getFloatExtra("price",0);
//        clickPosition = intent.getIntExtra("clickPosition",clickPosition);
        editPrice.setText(Float.toString(price));
        oldCategory.setText(imageName);
        oldDateStr.setText(dateStr);
    }

    private void setSpinner() {
        // 初始化控件
        Spinner spinner = (Spinner) findViewById(R.id.edit_category_spinner);
        // 建立数据源
        String[] mItems = getResources().getStringArray(R.array.category);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                TextView tv = (TextView) view;
                String[] categories = getResources().getStringArray(R.array.category);
                returnImageName = categories[pos];
                tv.setText(categories[pos]);
                tv.setTextColor(Color.BLACK);
                Toast.makeText(EditActivity.this, "您将修改此条账目分类为:"
                        + categories[pos], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void setDateStr() {
        btnDateStr.setOnClickListener(new View.OnClickListener() {
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

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        if(mDay / 10 < 1)
            btnDateStr.setText(new StringBuffer().append(mYear).append("年").append("0" + (mMonth + 1)).append("月").append("0" + mDay).append("日"));
        else
            btnDateStr.setText(new StringBuffer().append(mYear).append("年").append("0" + (mMonth + 1)).append("月").append(mDay).append("日"));
        returnDateStr = btnDateStr.getText().toString();
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };

}
