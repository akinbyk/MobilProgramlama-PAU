package com.example.ybssqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Veritabani extends SQLiteOpenHelper {

    private static final String Veritabani_Adi ="YBS_Rehber";
    private static final int versiyon=1;

    private static final String tablo_adi ="Tablo1";
    private static final String ID_alani ="ID";
    private static final String Ad_alani ="isim";
    private static final String Soyad_alani ="soyisim";
    private static final String Telefon_alani = "numara";

    public Veritabani(@Nullable Context context) {
        super(context, Veritabani_Adi,null, versiyon);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tablo_olustur="Create table "+tablo_adi+"(ID INTEGER PRIMARY KEY AUTOINCREMENT , isim TEXT, soyisim TEXT, numara TEXT)";
        db.execSQL(tablo_olustur);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tablo_adi);
        onCreate(sqLiteDatabase);
    }

    public  void VeriGuncelle(int id, String ad, String soyad, String telefon)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Ad_alani,ad);
        contentValues.put(Soyad_alani,soyad);
        contentValues.put(Telefon_alani,telefon);
        String sorgu=ID_alani +" = " + id;

        db.update(tablo_adi,contentValues,sorgu,null);
        db.close();

    }


    public void VeriSil(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String sorgu=ID_alani +" = " + id;
        db.delete(tablo_adi,sorgu,null);
        db.close();




    }


    public Cursor Listele() {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor veri=db.rawQuery("SELECT * FROM "+tablo_adi,null);
        return veri;
    }




    public boolean Ekle(String Ad, String Soyad, String Telefon)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Ad_alani,Ad);
        contentValues.put(Soyad_alani,Soyad);
        contentValues.put(Telefon_alani,Telefon);

        long result=db.insert(tablo_adi,null,contentValues);

        if(result==-1){

            return false;
        }
        return true;


    }
}
