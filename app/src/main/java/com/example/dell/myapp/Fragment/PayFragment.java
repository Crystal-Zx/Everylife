package com.example.dell.myapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.dell.myapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayFragment extends Fragment {

    private ImageView imageView;
    private TextView imageViewName;
    private GridView gridViewPay;

    private int[] imageIdsPay = new int[] {
            R.drawable.catering_1,R.drawable.commodity_1,R.drawable.communication_1,
            R.drawable.decoration_1,R.drawable.dessert_1,R.drawable.drink_1,R.drawable.education_1,
            R.drawable.entertainment_1,R.drawable.fit_1,R.drawable.fruit_1, R.drawable.gasoline_1,
            R.drawable.home_1,R.drawable.kids_1,R.drawable.luxury_1,R.drawable.medical_1,
            R.drawable.net_1,R.drawable.shopping_1,R.drawable.traffic_1,R.drawable.travel_1,
            R.drawable.pet_1
    };
    private String[] imageNamesPay = new String[] {
           "正餐","日用品","通讯","维修","甜品","茶饮","教育","游戏","健身","买菜",
            "加油","家庭","孩子","奢侈品","医疗","网络","购物","交通","旅行","宠物"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pay, container, false);
        //创建List对象,List对象的元素是Map
        List<Map<String,Object>> listItemsPay = new ArrayList<Map<String, Object>>();
        for(int i = 0;i < imageIdsPay.length;i++) {
            Map<String,Object> listItemPay = new HashMap<String,Object>();
            listItemPay.put("image",imageIdsPay[i]);
            listItemPay.put("imageName",imageNamesPay[i]);
            listItemsPay.add(listItemPay);
        }
        //获取显示图片的ImageView和图片的对应的类别
        imageView = (ImageView) this.getActivity().findViewById(R.id.image_view);
        imageViewName = (TextView) this.getActivity().findViewById(R.id.image_view_name);
        //创建一个SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(),listItemsPay,
                R.layout.cell,new String[] {"image","imageName"},
                new int[] {R.id.image1,R.id.image1_name});
        gridViewPay = view.findViewById(R.id.list_pay);
        //为GridView设置Adapter
        gridViewPay.setAdapter(simpleAdapter);
        //添加列表项被选中的监听器
        gridViewPay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageView.setImageResource(imageIdsPay[position]);
                imageViewName.setText(imageNamesPay[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //添加列表项被单击的监听器
        gridViewPay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imageView.setImageResource(imageIdsPay[position]);
                imageViewName.setText(imageNamesPay[position]);
            }
        });
        return view;
    }

}
