package com.example.lehuyhoang_ktra2_bai2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQliteHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "VeMayBay.db";
    public static final int DB_VER = 1;

    public SQliteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE datve (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "start_location TEXT," +
                "start_date TEXT," +
                "pack INTEGER" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Order> GetAllOrder() {
        List<Order> data = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("datve", null, null, null, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String start_location = rs.getString(2);
            String start_date = rs.getString(3);
            int pack = rs.getInt(4);
            data.add(new Order(id, pack, name, start_location, start_date));
        }
        return data;
    }

    public void AddOrder(Order f) {
        String sql = "INSERT INTO datve (name, start_location, start_date, pack) VALUES (?,?,?,?)";
        String[] agrs = {f.getName(), f.getStart_location(), f.getStart_date(), String.valueOf(f.getPack())};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql, agrs);
    }

    public int EditData(Order x) {
        ContentValues newvalues = new ContentValues();
        newvalues.put("name", x.getName());
        newvalues.put("pack", x.getPack());
        newvalues.put("start_location", x.getStart_location());
        newvalues.put("start_date", x.getStart_date());
        String whereClause = "id=?";
        String[] args = {Integer.toString(x.getId())};
        SQLiteDatabase st = getWritableDatabase();
        return st.update("datve", newvalues, whereClause, args);
    }

    public int DeleteData(int id) {
        String whereCaluse = "id=?";
        String[] args = {Integer.toString(id)};
        SQLiteDatabase st = getWritableDatabase();
        return st.delete("datve", whereCaluse, args);
    }

    public List<Order> SearchData(String keyword) {
        List<Order> data = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String whereClause = "name LIKE '%"+keyword+"%'";
        Cursor rs = st.query("datve", null, whereClause, null, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String start_location = rs.getString(2);
            String start_date = rs.getString(3);
            int pack = rs.getInt(4);
            data.add(new Order(id, pack, name, start_location, start_date));
        }
        return data;
    }
}
