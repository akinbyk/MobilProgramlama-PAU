package com.example.firebasedeneme;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ArrayList gelenveri= new ArrayList();
        final ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,gelenveri);
        final EditText et_Ad= (EditText) findViewById(R.id.et_Ad);
        final EditText et_Soyad = (EditText) findViewById(R.id.et_Soyad);
        final EditText et_Telefon = (EditText) findViewById(R.id.et_Telefon);
        final TextView tv_key=(TextView) findViewById(R.id.tv_key);

        Button btn_Kaydet = (Button) findViewById(R.id.btn_Kaydet);
        Button btn_sil = (Button) findViewById(R.id.btn_sil);
        Button btn_guncelle=(Button) findViewById(R.id.btn_guncelle);


        final ListView lst_liste = (ListView) findViewById(R.id.lst_liste);

        final DatabaseReference dbreference = FirebaseDatabase.getInstance().getReference("Urunler");
        dbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                gelenveri.clear();

                for(DataSnapshot verigetir : dataSnapshot.getChildren())
                {
                    Musteriler gelenmusteri=new Musteriler(
                            verigetir.child("key").getValue().toString(),
                            verigetir.child("ad").getValue().toString(),
                            verigetir.child("soyad").getValue().toString(),
                            verigetir.child("telefon").getValue().toString()

                    );

                   gelenveri.add(gelenmusteri.getAd() +"--"
                           +gelenmusteri.getSoyad()+"--"+
                           gelenmusteri.getTelefon()+"--"+gelenmusteri.getKey());

                }
               lst_liste.setAdapter(listAdapter);



            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Silinecek=tv_key.getText().toString();
                dbreference.child(Silinecek).removeValue();
                et_Ad.setText("");
                et_Telefon.setText("");
                et_Soyad.setText("");
                tv_key.setText("");
            }
        });

        lst_liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String alinanveri=lst_liste.getItemAtPosition(i).toString();

                String[] veridizi=alinanveri.split("--");

                et_Ad.setText(veridizi[0].toString());
                et_Soyad.setText(veridizi[1].toString());
                et_Telefon.setText(veridizi[2].toString());
                tv_key.setText(veridizi[3].toString());
            }
        });

        btn_Kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = dbreference.push().getKey();
                Musteriler yenimusteri= new Musteriler(id,et_Ad.getText().toString(),et_Soyad.getText().toString(),et_Telefon.getText().toString());
                dbreference.child(id).setValue(yenimusteri);
                Toast.makeText(getApplicationContext(),"Ekleme Tamamlandı.",Toast.LENGTH_LONG);

                et_Ad.setText("");
                et_Soyad.setText("");
                et_Telefon.setText("");

            }
        });


 /*
                dbreference.child(id).child("ad").setValue(et_Ad.getText().toString());
                dbreference.child(id).child("soyad").setValue(et_Soyad.getText().toString());
                dbreference.child(id).child("telefon").setValue(et_Telefon.getText().toString());*/

    }
}
