package com.example.ybssqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et_Ad = (EditText) findViewById(R.id.et_Ad);
        final EditText et_Soyad = (EditText) findViewById(R.id.et_Soyad);
        final EditText et_Telefon = (EditText) findViewById(R.id.et_Telefon);



        Button btn_ekle = (Button) findViewById(R.id.btn_Ekle);
        Button btn_sil = (Button) findViewById(R.id.btn_sil);
        Button btn_guncelle = (Button) findViewById(R.id.btn_Guncelle);
        Button btn_listele = (Button) findViewById(R.id.btn_Listele);

        final ListView liste = (ListView) findViewById(R.id.list_liste);
        final ArrayList<String> kayitlar = new ArrayList<>();
        final ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kayitlar);

        final Veritabani db = new Veritabani(MainActivity.this);



        btn_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Ad = et_Ad.getText().toString();
                String Soyad = et_Soyad.getText().toString();
                String Telefon = et_Telefon.getText().toString();


              boolean sonuc=  db.Ekle(Ad, Soyad, Telefon);

                et_Ad.setText("");
                et_Soyad.setText("");
                et_Telefon.setText("");

              if(sonuc==true )  Toast.makeText(getApplicationContext(), "Ekleme başarılı", Toast.LENGTH_LONG).show();

            }
        });

        btn_sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Veritabani vt= new Veritabani(MainActivity.this);

                vt.VeriSil(id);
            }
        });

        btn_guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gelenad = et_Ad.getText().toString();
                String gelensoyad = et_Soyad.getText().toString();
                String gelentelefon = et_Telefon.getText().toString();
                Veritabani vt= new Veritabani(MainActivity.this);
                vt.VeriGuncelle(id,gelenad,gelensoyad,gelentelefon);

            }
        });


            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String alinanveri = liste.getItemAtPosition(i).toString();


                    String[] veridizi=alinanveri.split("--");
                    id=Integer.valueOf(veridizi[0]);
                    et_Ad.setText(veridizi[1].toString());
                    et_Soyad.setText(veridizi[2].toString());
                    et_Telefon.setText(veridizi[3].toString());

                }
            });




        btn_listele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Cursor data = db.Listele();

                if (data.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "Veri yok!", Toast.LENGTH_SHORT).show();

                } else {
                    ((ArrayAdapter) listAdapter).clear();
                    while (data.moveToNext()) {
                        kayitlar.add(data.getString(0) +"--" +data.getString(1) + "--" + data.getString(2) + "--" + data.getString(3));

                    }
                    liste.setAdapter(listAdapter);
                }
            }
        });

    }

}

