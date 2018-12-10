package com.example.dell.myapp.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageButton;

import com.example.dell.myapp.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class GraphicalActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private PieChart incomePieChart;
    private PieChart outcomePieChart;
    private SQLiteDatabase db;

//    float salary = 0,invest = 0,partTimeJob = 0,redpacket = 0,bonus = 0;
//    float catering = 0,commodity = 0,communication = 0, decoration = 0,dessert = 0,drink = 0;
//    float education = 0,entertainment = 0,fit = 0;
//    float fruit = 0,gasoline = 0,home = 0,kids = 0,luxury = 0,medical = 0,net = 0,shopping = 0;
//    float traffic = 0,travel = 0,pet = 0;
    private float[] incomeAmounts = {0,0,0,0,0};
//            {salary,invest,partTimeJob,redpacket,bonus};
    private float[] outcomeAmounts = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
//        {catering,commodity,communication, decoration,dessert,drink,
//            education,entertainment,fit,fruit,gasoline,home,kids,luxury,medical,net,shopping,
//            traffic,travel,pet};

    private String incomeAmountComments[] = {"工资","投资","兼职","礼金","奖金"};
    private String outcomeAmountComments[] = { "正餐","日用品","通讯","维修","甜品","茶饮","教育","游戏","健身","买菜",
            "加油","家庭","孩子","奢侈品","医疗","网络","购物","交通","旅行","宠物"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphical);
        btnBack = findViewById(R.id.edit_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        incomePieChart = (PieChart) findViewById(R.id.income_pieChart);
        outcomePieChart = (PieChart) findViewById(R.id.outcome_pieChart);
        getData();
        createPieChart(incomePieChart,0);
        createPieChart(outcomePieChart,1);
    }

    private void getData() {
        //打开数据库
        String path = "/data/data/com.example.dell.myapp/databases/myDatabase.db";
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        //得到Deal表中的数据源
        Cursor cursor = db.query("Deal",new String[]{"price","imageName"}, null, null, null, null, null);
        if(cursor != null){
            while(cursor.moveToNext()) {
                String imageName = cursor.getString(cursor.getColumnIndex("imageName"));
                float price = cursor.getFloat(cursor.getColumnIndex("price"));
                switch (imageName) {
                    case "工资":
                         incomeAmounts[0] += price; break;
                        //incomeAmounts[0]
                    case "投资":
                        //invest += price; break;
                        incomeAmounts[1] += price; break;

                    case "兼职":
                        //partTimeJob += price; break;
                        incomeAmounts[2] += price; break;
                    case "礼金":
                        //redpacket += price; break;
                        incomeAmounts[3] += price; break;
                    case "奖金":
                        //bonus += price; break;
                        incomeAmounts[4] += price; break;
                    case "正餐":
                        //catering -= price;break;
                        outcomeAmounts[0] -= price;break;
                    case "日用品":
                        //commodity -= price;break;
                        outcomeAmounts[1] -= price;break;
                    case "通讯":
                        //communication -= price;break;
                        outcomeAmounts[2] -= price;break;
                    case "维修":
                        //decoration -= price;break;
                        outcomeAmounts[3] -= price;break;
                    case "甜品":
                        //dessert -= price;break;
                        outcomeAmounts[4] -= price;break;
                    case "茶饮":
                        //drink -= price;break;
                        outcomeAmounts[5] -= price;break;
                    case "教育":
                        //education -= price;break;
                        outcomeAmounts[6] += price;break;
                    case "游戏":
                        //entertainment -= price;break;
                        outcomeAmounts[7] -= price;break;
                    case "健身":
                        //fit -= price;break;
                        outcomeAmounts[8] -= price;break;
                    case "买菜":
//                        fruit -= price;break;
                        outcomeAmounts[9] -= price;break;
                    case "加油":
//                        gasoline -= price;break;
                        outcomeAmounts[10] -= price;break;
                    case "家庭":
//                        home -= price;break;
                        outcomeAmounts[11] -= price;break;
                    case "孩子":
//                        kids -= price;break;
                        outcomeAmounts[12] -= price;break;
                    case "奢侈品":
//                        luxury -= price;break;
                        outcomeAmounts[13] -= price;break;
                    case "医疗":
//                        medical -= price;break;
                        outcomeAmounts[14] -= price;break;
                    case "网络":
//                        net -= price;break;
                        outcomeAmounts[15] -= price;break;
                    case "购物":
//                        shopping -= price;break;
                        outcomeAmounts[16] -= price;break;
                    case "交通":
//                        traffic -= price;break;
                        outcomeAmounts[17] -= price;break;
                    case "旅行":
//                        travel -= price;break;
                        outcomeAmounts[18] -= price;break;
                    case "宠物":
//                        pet -= price;break;
                        outcomeAmounts[19] -= price;break;
                    default:
                }
            }
        }
        cursor.close();
    }

    private void createPieChart(PieChart mPieChart,int index) {
        //饼状图
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
        mPieChart.setCenterText(generateCenterSpannableText(index));

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        //变化监听
        mPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //传入数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        if(index == 0) {
            for(int i = 0;i < incomeAmounts.length;i++) {
                if(incomeAmounts[i] != 0)
                    entries.add(new PieEntry(incomeAmounts[i],incomeAmountComments[i]));
                else
                    continue;
            }
            //设置数据
            setData(mPieChart,entries,0);
        } else if(index == 1) {
            for(int i = 0;i < outcomeAmounts.length;i++) {
                if(outcomeAmounts[i] != 0)
                    entries.add(new PieEntry(outcomeAmounts[i],outcomeAmountComments[i]));
                else
                    continue;
            }
            //设置数据
            setData(mPieChart,entries,1);
        }

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(12f);
    }

    //设置中间文字
    private SpannableString generateCenterSpannableText(int index) {
        String str = "";
        if(index == 0) {
            str = "收入";
        } else if(index == 1) {
            str = "支出";
        }
        SpannableString s = new SpannableString(str);
        return s;
    }

    //设置数据
    private void setData(PieChart mPieChart,ArrayList<PieEntry> entries,int index) {
        String label = "";
        if(index == 0) {
            label = "收入";
//            dataSet = new PieDataSet(entries, "收入");
        } else if(index == 1) {
            label = "支出";
//            dataSet = new PieDataSet(entries, "支出");
        }
        PieDataSet dataSet = new PieDataSet(entries,label);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }
}