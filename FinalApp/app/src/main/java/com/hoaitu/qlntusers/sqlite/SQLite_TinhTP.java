package com.hoaitu.qlntusers.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.hoaitu.qlntusers.model.TinhTP;

import java.util.ArrayList;

public class SQLite_TinhTP extends SQLiteDataController {
    public SQLite_TinhTP(Context con){
        super(con);
    }

    public ArrayList<TinhTP> getDSTP() {
        ArrayList<TinhTP> lstTP = new ArrayList<>();
        ArrayList<TinhTP> lstTenTP = new ArrayList<>();
        // mo ket noi
        try {
            open();
            Cursor cs = getMyDatabase().rawQuery("select * from TinhThanh", null);
            TinhTP tp;
            while (cs.moveToNext()) {
                tp = new TinhTP(cs.getInt(0),cs.getString(1));
                lstTP.add(tp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return lstTP;
    }
    public ArrayList<String> getTenDSTP() {
        ArrayList<String> lstTenTP = new ArrayList<>();
        // mo ket noi
        try {
            open();
            Cursor cs = getMyDatabase().rawQuery("select * from TinhThanh", null);
            TinhTP tp;
            while (cs.moveToNext()) {
                tp = new TinhTP(cs.getInt(0),cs.getString(1));

                String a = tp.getTen();
                lstTenTP.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return lstTenTP;
    }


}
