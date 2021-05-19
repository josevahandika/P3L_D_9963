package com.josevahandika.akb_mobile.entity;

public class Cart {
    private String nama_menu,harga, tempJumlah;

    public Cart(String nama_menu, String harga, String tempJumlah)
    {
        this.nama_menu = nama_menu;
        this.harga = harga;
        this.tempJumlah = tempJumlah;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getTempJumlah() {
        return tempJumlah;
    }

    public void setTempJumlah(String tempJumlah) {
        this.tempJumlah = tempJumlah;
    }
}
