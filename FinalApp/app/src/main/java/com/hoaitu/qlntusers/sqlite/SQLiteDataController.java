package com.hoaitu.qlntusers.sqlite;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by CongHoang on 3/17/2018.
 */

public class SQLiteDataController extends SQLiteOpenHelper {
//C:\Users\Admin\AndroidStudioProjects\qlntUsers\app\src\main\assets
//    //data//data//com.example.test_search_2.sqlite//databases//
//    public String DB_PATH = "//data//data//com.thanhtai.qlntusers//databases//";
public String DB_PATH = "//data//data//com.hoaitu.qlntusers//databases//";
    // đường dẫn nơi chứa database
    private static String DB_NAME = "dataPhongTro.sqlite";
    public SQLiteDatabase database;
    private final Context mContext;
    private SQLiteDatabase myDatabase;

    public SQLiteDataController(Context con) {
        super(con, DB_NAME, null, 1);
        DB_PATH = String.format(DB_PATH, con.getPackageName());
        this.mContext = con;
        boolean dbexist = checkdatabase();
        if (dbexist) {
        } else {
            System.out.println("Database doesn't exist!");

            createDatabse();
        }

    }

    public void createDatabse() {

        this.getReadableDatabase();

        try {
            copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SQLiteDatabase getMyDatabase() {
        return myDatabase;
    }

    private void copyDatabase() throws IOException {

        AssetManager dirPath = mContext.getAssets();

        InputStream myinput = mContext.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = myinput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myinput.close();
    }

    public void open() {
        String myPath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        myDatabase.close();
        super.close();
    }

    private boolean checkdatabase() {

        boolean checkdb = false;

        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Databse doesn't exist!");
        }

        return checkdb;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
