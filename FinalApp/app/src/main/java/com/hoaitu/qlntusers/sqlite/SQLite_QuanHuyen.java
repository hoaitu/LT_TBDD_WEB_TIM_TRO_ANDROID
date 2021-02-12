package com.hoaitu.qlntusers.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.hoaitu.qlntusers.model.QuanHuyen;

import java.util.ArrayList;

public class SQLite_QuanHuyen extends SQLiteDataController {
    public SQLite_QuanHuyen(Context con){
        super(con);
    }

    public ArrayList<QuanHuyen> getDSQH(int id) {
        ArrayList<QuanHuyen> lstQH = new ArrayList<>();
        // mo ket noi
        try {
            open();
            Cursor cs = getMyDatabase().rawQuery("select * from QuanHuyen where idtp =" + id, null);
            QuanHuyen qh;
            while (cs.moveToNext()) {
                qh = new QuanHuyen(cs.getInt(0), cs.getInt(2),cs.getString(1));
                lstQH.add(qh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return lstQH;
    }


}