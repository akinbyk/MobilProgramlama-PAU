package com.example.firebasedeneme;

public class Musteriler {

    private String ad;
    private  String soyad;
    private String telefon;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }



    public Musteriler(String key,String ad,String soyad, String telefon)
    {
        this.key=key;
        this.ad=ad;
        this.soyad=soyad;
        this.telefon=telefon;

    }


}
