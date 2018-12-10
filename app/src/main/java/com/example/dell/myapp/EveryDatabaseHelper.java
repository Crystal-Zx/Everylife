package com.example.dell.myapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Dell on 2018/3/13.
 */

public class EveryDatabaseHelper extends SQLiteOpenHelper {

//    public static final String TABLE_NAME = "DealBook";
    public static final String CREATE_DEAL = "create table Deal ("
            + "id integer primary key autoincrement, "
            + "imageName text, "
            + "price real, "
            + "dateStr text)";
    public static final String CREATE_FIT = "create table Fit ("
            + "id integer primary key autoincrement, "
            + "date text, "
            + "stepCount real, "
            + "energy real, "
            + "nutrition real)";

    private Context mContext;

    public EveryDatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,
                               int version) {
        super(context,name,null,version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DEAL);
        db.execSQL(CREATE_FIT);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Deal");
        db.execSQL("drop table if exists Fit");
        onCreate(db);
    }
}
