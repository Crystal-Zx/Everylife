package com.example.dell.myapp.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager; //support.v4.
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.dell.myapp.Fragment.AccountBookFragment;
import com.example.dell.myapp.FinanceInfoFragment;
import com.example.dell.myapp.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FinanceMainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton toRecord;
    private ImageView toGraphical;
    private RadioButton financeInfo;
    private RadioButton accountBook;
    private TextView currentDate;
    private TextView mIncome;
    private TextView mOutcome;
    private TextView mBudget;
    private float mincome = 0;
    private float moutcome = 0;
    private List<Map<String, Object>> list;
    private SQLiteDatabase db ;
    private FinanceInfoFragment financeInfoFragment;
    private AccountBookFragment accountBookFragment;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_main);
        initView();
        setAccountInfo();
        toRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinanceMainActivity.this,
                        CategorySelectActivity.class);
                startActivity(intent);
            }
        });
        toGraphical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinanceMainActivity.this,GraphicalActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initView() {
        toRecord = findViewById(R.id.to_record);
        toGraphical = findViewById(R.id.to_graphical);
        mIncome = findViewById(R.id.m_income);
        mOutcome = findViewById(R.id.m_outcome);
        mBudget = findViewById(R.id.m_budget);
        financeInfo = (RadioButton) findViewById(R.id.finance_info);
        accountBook = (RadioButton) findViewById(R.id.account_book);
        currentDate = (TextView) findViewById(R.id.current_date);
        financeInfo.setOnClickListener(this);
        accountBook.setOnClickListener(this);
        accountBook.setChecked(true);
        //设置默认fragment
        setDefaultFragment();
        //获取当前系统时间，并显示在TextView中
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date curDate =  new Date(System.currentTimeMillis());
        String dateStr = formatter.format(curDate);
        currentDate.setText(dateStr);
    }

    private void setAccountInfo() {
        String path = "/data/data/com.example.dell.myapp/databases/myDatabase.db";
//        String dirPath="/data/data/"+FinanceMainActivity.this.getPackageName()+"/databases/";
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);//, SQLiteDatabase.OPEN_READWRITE
        //得到数据库中的数据源
        Cursor cursor = db.query("Deal",new String[]{"price"}, null, null, null, null, null);
        if(cursor.moveToLast()){
            do {
                float price = cursor.getFloat(cursor.getColumnIndex("price"));
                if(price > 0) {
                    mincome += price;
                } else {
                    moutcome += price;
                }
            }while(cursor.moveToPrevious());
        }
        mIncome.setText(mincome + "");
        mOutcome.setText(moutcome + "");
    }

    private void setDefaultFragment() {
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        accountBookFragment = new AccountBookFragment();
        ft.replace(R.id.content_layout,accountBookFragment);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finance_info:
                setTabSelection(0);
                break;
            case R.id.account_book:
                setTabSelection(1);
                break;
        }
    }

    private void setTabSelection(int index) {
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        这种做法是错误的，会引起程序崩溃
//        因为首次运行程序时，accountFragment和accountBookFragment是不存在的，为null
//        ft.hide(accountFragment);
//        ft.hide(accountBookFragment);
        hideFragment(ft);
        switch (index) {
            case 0:
                if(financeInfoFragment == null) {
                    financeInfoFragment = new FinanceInfoFragment();
                    ft.add(R.id.content_layout, financeInfoFragment);
                } else {
                    ft.show(financeInfoFragment);
                }
            case 1:
                if(accountBookFragment == null) {
                    accountBookFragment = new AccountBookFragment();
                    ft.add(R.id.content_layout,accountBookFragment);
                } else {
                    ft.show(accountBookFragment);
                }
        }
        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft) {
        if(financeInfoFragment != null) {
            ft.hide(financeInfoFragment);
        } else if(accountBookFragment != null) {
            ft.hide(accountBookFragment);
        }
    }
}
