package com.example.dell.myapp.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.myapp.Fragment.IncomeFragment;
import com.example.dell.myapp.Fragment.PayFragment;
import com.example.dell.myapp.R;
import com.example.dell.myapp.Adapter.RecordFragmentAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class CategorySelectActivity extends FragmentActivity{

    private SQLiteDatabase db;
    private ImageButton btnBack;
    private ImageButton btnSave;
    private ImageView imageView;
    private TextView imageViewName;
    private RadioGroup rg;
    private RadioButton btnIncome;
    private RadioButton btnPay;
    private EditText amountInput;
    ViewPager vp;
    private String imageName;
    private String dateStr;
    private float price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);
        initView();
        initEvent();
        btnBack = (ImageButton) this.findViewById(R.id.back);
        btnSave = (ImageButton) this.findViewById(R.id.save);
        imageView = (ImageView) findViewById(R.id.image_view);
        imageViewName = (TextView) findViewById(R.id.image_view_name);
        amountInput = (EditText) this.findViewById(R.id.amount_input);
        amountInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//        amountInput.setKeyListener(new DigitsKeyListener(false,true));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDealBookInfo();
                saveDealBookInfo(imageName, price, dateStr);
                Intent intent = new Intent(CategorySelectActivity.this, FinanceMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //判断EditText是否获得焦点，若无则显示默认值，若获得焦点则清空内容等待用户输入
        amountInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    amountInput.setText("");
                }
            }
        });
    }

    public void initView() {
        //构造适配器
        final List<Fragment> fragments=new ArrayList<Fragment>();
        fragments.add(new IncomeFragment());
        fragments.add(new PayFragment());
        RecordFragmentAdapter adapter = new RecordFragmentAdapter(getSupportFragmentManager(),
                fragments);
        //设定适配器
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(adapter);
    }

    public void initEvent() {
        btnIncome = findViewById(R.id.btn_income);
        btnPay = findViewById(R.id.btn_pay);
        //使RadioButton的值与ViewPage值相对应
        rg = findViewById(R.id.btn_rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_income:
                        vp.setCurrentItem(0);
                        imageView.setImageResource(R.drawable.salary_1);
                        imageViewName.setText("工资");
                        break;
                    case R.id.btn_pay:
                        vp.setCurrentItem(1);
                        imageView.setImageResource(R.drawable.catering_1);
                        imageViewName.setText("正餐");
                        break;
                }
            }
        });
        //使ViewPage变化时，RadioButton的显示相应变化
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position) {
                    case 0:
                        btnIncome.setChecked(true);
                        imageView.setImageResource(R.drawable.salary_1);
                        imageViewName.setText("工资");
                        break;
                    case 1:
                        btnPay.setChecked(true);
                        imageView.setImageResource(R.drawable.catering_1);
                        imageViewName.setText("正餐");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getDealBookInfo() {
        price = Float.parseFloat(amountInput.getText().toString());
        if(price > 0) {
            //记录当前选中的图片
//            imageView.setDrawingCacheEnabled(true);
////            imgBtm = imageView.getDrawingCache();
////            imageId = imgBtm.toString();
//            imageView.setDrawingCacheEnabled(false);
            //记录当前选中的图片的名字
            imageName = imageViewName.getText().toString();
            //获取当前系统时间，即记录此条信息时的时间
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
            Date curDate =  new Date(System.currentTimeMillis());
            dateStr = formatter.format(curDate);
            //判断当前是支出还是收入
            if(rg.getCheckedRadioButtonId() == R.id.btn_pay ||
                    vp.getCurrentItem() == 1) {
                price = - price;
            }
        }
        else {
            price = 0;
            Toast.makeText(CategorySelectActivity.this,
                    "请输入正确的金额！",Toast.LENGTH_SHORT).show();
        }

    }

    private void saveDealBookInfo(String imageName,float price,
                                String dateStr) {
        //打开指定数据库
        String path = "/data/data/com.example.dell.myapp/databases/myDatabase.db";
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.beginTransaction();
//        try {
            ContentValues values = new ContentValues();
            //组装当前数据记录
//            values.put("imageId",imageId);//imageView.getId()
            values.put("imageName",imageName);//imageViewName.getText().toString()
            values.put("price",price);//price
            values.put("dateStr",dateStr);//dateStr
            db.insert("Deal",null,values);
            values.clear();
            db.close();
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            e.printStackTrace();
//            // TODO:handle exception
//        }finally{
//            db.endTransaction();
//            db.close();
        //      将当前image_view（id）、image_view_name和amount_input内容保存入数据库
        Toast.makeText(CategorySelectActivity.this,
                "记录成功！",Toast.LENGTH_SHORT).show();
        }





}
