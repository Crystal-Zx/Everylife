package com.example.dell.myapp.Fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.dell.myapp.Activity.EditActivity;
import com.example.dell.myapp.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class AccountBookFragment extends Fragment {

    private ListView lv;
    private List<Map<String, Object>> list;
    private SQLiteDatabase db;
    private SimpleAdapter adapter;

    private String returnDateStr;
    private String returnImageName;
    private float returnPrice;
//    private int listCount;
    private int clickPosition;
    private int lineIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_book_fragment,container,false);
        list = new ArrayList<Map<String,Object>>();
        lv = (ListView) view.findViewById(R.id.account_book_lv);
        lv.setClickable(true);
        String path = "/data/data/com.example.dell.myapp/databases/myDatabase.db";
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
//        String dirPath="/data/data/"+getContext().getPackageName()+"/databases/";
//        db = SQLiteDatabase.openOrCreateDatabase(dirPath + "myDatabase.db", null);//, SQLiteDatabase.OPEN_READWRITE

        //得到数据库中的数据源
        list = getToDbData();
        adapter = new SimpleAdapter(getContext(), list, R.layout.account_lv_layout,
                new String[]{"imageName","price","dateStr"},
                new int[]{R.id.image_name,R.id.price,R.id.date_str});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast.makeText(getContext(),"you clicked " + position,Toast.LENGTH_SHORT).show();
//                list.remove(position);
                String dateStr = (String) list.get(position).get("dateStr");
                String imageName = (String) list.get(position).get("imageName");
                float price = (float) list.get(position).get("price");
                lineIndex = (int) list.get(position).get("id");
                clickPosition = position;
                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra("dateStr",dateStr);
                intent.putExtra("imageName",imageName);
                intent.putExtra("price",price);
//                intent.putExtra("clickPosition",clickPosition);
//                startActivity(intent);
                startActivityForResult(intent,1);
//                lv.setAdapter(adapter);
            }
        });
        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"you clicked " + position,Toast.LENGTH_SHORT).show();
//                list.remove(position);
//                lv.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
//                int databasePosition = listCount - clickPosition;
                if(resultCode == RESULT_OK) {
                    returnDateStr = data.getStringExtra("returnDateStr");
                    returnImageName = data.getStringExtra("returnImageName");
                    returnPrice = data.getFloatExtra("returnPrice",0);
                    ContentValues returnValues = new ContentValues();
                    returnValues.put("dateStr",returnDateStr);
                    returnValues.put("imageName",returnImageName);
                    returnValues.put("price",returnPrice);
                    db.update("Deal",returnValues,"id = ?",
                            new String[]{String.valueOf(lineIndex)});
                    db.close();
                    //更新列表信息
                    getActivity().recreate();
                    Toast.makeText(getContext(),"修改成功！",Toast.LENGTH_SHORT).show();
                } else if(resultCode == 0) {
//                    Cursor cursor = db.query("Deal", null, null, null, null, null, null);
//                    cursor.moveToPosition(databasePosition);
                    list.remove(clickPosition);
                    lv.setAdapter(adapter);
                    db.delete("Deal","id = ?",
                            new String[]{String.valueOf(lineIndex)});
                    db.close();
                    getActivity().recreate();
                    Toast.makeText(getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                } else if(resultCode == 1) {

                }
                break;
                default:
        }
    }

    private List<Map<String, Object>> getToDbData(){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Cursor cursor = db.query("Deal", null, null, null, null, null, null);
//        listCount = cursor.getCount();
        if(cursor.moveToLast()){
            do {
                Map<String, Object> map = new HashMap<String, Object>();
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String dateStr = cursor.getString(cursor.getColumnIndex("dateStr"));
                String imageName = cursor.getString(cursor.getColumnIndex("imageName"));
                float price = cursor.getFloat(cursor.getColumnIndex("price"));
                map.put("id",id);
                map.put("dateStr",dateStr);
                map.put("imageName", imageName);
                map.put("price",price);
                list.add(map);
            }while(cursor.moveToPrevious());
        }
        return list;
    }
}
