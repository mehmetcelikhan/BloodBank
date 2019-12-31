package com.example.mehmet.bloodbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;

public class VeriTabani  extends SQLiteOpenHelper {
    List<String> veriler;
    private static final String DATABASE_NAME = "musteriler";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLO_KISILER = "kisiler";
    private static final String ROW_ID = "id";
    private static final String ROW_AD = "ad";
    private static final String ROW_SOYAD = "soyad";
    private static final String ROW_TEL = "tel";
    private static final String ROW_EMAIL = "email";
    private static final String ROW_PASS = "pass";
    private static final String ROW_BGROUP = "bgroup";


    public VeriTabani(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SqlCreateScript = "";
        SqlCreateScript = "CREATE TABLE " + TABLO_KISILER + "("
                + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + ROW_AD + " TEXT NOT NULL , "
                + ROW_SOYAD + " TEXT NOT NULL , "
                + ROW_TEL + " TEXT NOT NULL , "
                + ROW_EMAIL + " TEXT NOT NULL , "
                + ROW_PASS + " TEXT NOT NULL , "
                + ROW_BGROUP + " TEXT NOT NULL  )";

        sqLiteDatabase.execSQL(SqlCreateScript);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLO_KISILER);
        onCreate(sqLiteDatabase);
    }

    public void VeriEkle(String Ad, String SoyAd, String TelefonNo, String Email, String Pass, String Bgroup) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ROW_AD, Ad);
        cv.put(ROW_SOYAD, SoyAd);
        cv.put(ROW_TEL, TelefonNo);
        cv.put(ROW_EMAIL, Email);
        cv.put(ROW_PASS, Pass);
        cv.put(ROW_BGROUP, Bgroup);

        db.insert(TABLO_KISILER, null, cv);
        db.close();
    }

    public void VeriSil(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClouse = ROW_ID + " = " + String.valueOf(Id);
        db.delete(TABLO_KISILER, whereClouse, null);

        db.close();
    }


    public void VeriDuzenle(int Id, String Ad, String SoyAd, String TelefonNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ROW_AD, Ad);
        cv.put(ROW_SOYAD, SoyAd);
        cv.put(ROW_TEL, TelefonNo);

        String whereClouse = ROW_ID + " = '" + String.valueOf(Id) + "'";

        db.update(TABLO_KISILER, cv, whereClouse, null);
        db.close();
    }


    public List<String> VerileriListele() {
        veriler = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();
        String SelectStatement = "Select * from " + TABLO_KISILER;
        Cursor cursor = db.rawQuery(SelectStatement, null);

        while (cursor.moveToNext()) {
            veriler.add(cursor.getInt(0)
                    + " - "
                    + cursor.getString(1)
                    + " - "
                    + cursor.getString(2)
                    + " - "
                    + cursor.getString(3)
                    + " - "
                    + cursor.getString(4)
                    + " - "
                    + cursor.getString(5)
            );
        }
        db.close();
        return veriler;
    }

    public boolean HasEmailAlreadyTaken(String email) {
        int i = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String SelectStatement = "Select * from " + TABLO_KISILER;
        Cursor cursor = db.rawQuery(SelectStatement, null);
        while (cursor.moveToNext()) {
            String mail = cursor.getString(4);
            if (email.equals(mail)) {

                i++;
            } else {

            }
        }
        if (i == 0)
            return true;
        else
            return false;
    }

    public boolean checkUser(String email,String password) {
        int i = 0;
       // loginActivity newPerson=loginActivity.GetInstance();
        // array of columns to fetch
        SQLiteDatabase db = this.getReadableDatabase();
        String SelectStatement = "Select * from " + TABLO_KISILER;
        Cursor cursor = db.rawQuery(SelectStatement, null);
        while (cursor.moveToNext()) {
            String mail = cursor.getString(4);
            String pass = cursor.getString(5);
            if (email.equals(mail) && password.equals(pass)) {
                i++;
            }
            else
                {
            }
        }
        if (i != 0)
            return true;
        else
            return false;
    }
}