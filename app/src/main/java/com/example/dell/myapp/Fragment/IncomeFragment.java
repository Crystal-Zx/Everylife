package com.example.dell.myapp.Fragment;

import android.os.Bundle;
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

public class IncomeFragment extends Fragment {

    private ImageView imageView;
    private TextView imageViewName;
    private GridView gridViewIncome;
//    public int imgp;

    int[] imageIdsIncome = new int[] {
            R.drawable.salary_1,R.drawable.invest_1,R.drawable.parttimejob_1,
            R.drawable.redpacket_1,R.drawable.bonus_1
    };

    String[] imageNamesIncome = new String[] {
            "工资","投资","兼职","礼金","奖金"
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.income, container, false);
        //创建List对象,List对象的元素是Map
        List<Map<String,Object>> listItemsIncome = new ArrayList<Map<String, Object>>();
        for(int i = 0;i < imageIdsIncome.length;i++) {
            Map<String,Object> listItemIncome = new HashMap<String,Object>();
            listItemIncome.put("image",imageIdsIncome[i]);
            listItemIncome.put("imageName",imageNamesIncome[i]);
            listItemsIncome.add(listItemIncome);
        }
        //获取显示图片的ImageView和图片的对应的类别
        imageView = (ImageView) getActivity().findViewById(R.id.image_view);
        imageViewName = (TextView) getActivity().findViewById(R.id.image_view_name);
        //创建一个SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(),listItemsIncome,
                R.layout.cell,new String[] {"image","imageName"},
                new int[] {R.id.image1,R.id.image1_name});
        gridViewIncome = view.findViewById(R.id.list_income);
        //为GridView设置Adapter
        gridViewIncome.setAdapter(simpleAdapter);
        //添加列表项被选中的监听器
        gridViewIncome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageView.setImageResource(imageIdsIncome[position]);
                imageViewName.setText(imageNamesIncome[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //添加列表项被单击的监听器
        gridViewIncome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imageView.setImageResource(imageIdsIncome[position]);
                imageViewName.setText(imageNamesIncome[position]);
            }
        });
        return view;
    }
}
