package com.josevahandika.akb_mobile.entity;

import java.io.Serializable;

public class Menu implements Serializable {
    private String nama_menu,kategori, unit, deskripsi,takaran_saji,id_bahan,harga;
    private int id, tempJumlah;

    public Menu(int id, String nama_menu, String takaran_saji, String harga, String kategori, String unit, String deskripsi, String id_bahan, int tempJumlah)
    {
        this.id = id;
        this.nama_menu = nama_menu;
        this.takaran_saji = takaran_saji;
        this.harga = harga;
        this.kategori = kategori;
        this.unit = unit;
        this.deskripsi= deskripsi;
        this.id_bahan = id_bahan;
        this.tempJumlah = tempJumlah;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public int getTempJumlah() {
        return tempJumlah;
    }

    public void setTempJumlah(int tempJumlah) {
        this.tempJumlah = tempJumlah;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTakaran_saji() {
        return takaran_saji;
    }

    public void setTakaran_saji(String takaran_saji) {
        this.takaran_saji = takaran_saji;
    }

    public String getId_bahan() {
        return id_bahan;
    }

    public void setId_bahan(String id_bahan) {
        this.id_bahan = id_bahan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
