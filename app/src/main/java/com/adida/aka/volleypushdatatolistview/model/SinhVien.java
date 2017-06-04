package com.adida.aka.volleypushdatatolistview.model;

import java.io.Serializable;

/**
 * Created by Aka on 6/2/2017.
 */

public class SinhVien implements Serializable {
    String id;
    String ten;
    String namSinh;
    String diaChi;

    public SinhVien(){
    }

    public SinhVien(String id, String ten, String namSinh, String diaChi) {
        this.id = id;
        this.ten = ten;
        this.namSinh = namSinh;
        this.diaChi = diaChi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
