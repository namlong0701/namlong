package com.example.btlapp.model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    public int Id;
    public String Tensanpham;
    public Integer Giasanpham;
    public String Hinhanhsanpham;
    public String Motasanpham;
    public int IDSanpham;

    public Sanpham(int id, String tensanpham, Integer giasanpham, String hinhanhsanpham, String motasanpham, int IDSanpham) {
        Tensanpham = tensanpham;
        this.Id = id;
        Giasanpham = giasanpham;
        Hinhanhsanpham = hinhanhsanpham;
        Motasanpham = motasanpham;
        this.IDSanpham = IDSanpham;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTensanpham() {
        return Tensanpham;
    }

    public Integer getGiasanpham() {
        return Giasanpham;
    }

    public String getHinhanhsanpham() {
        return Hinhanhsanpham;
    }

    public String getMotasanpham() {
        return Motasanpham;
    }

    public int getIDSanpham() {
        return IDSanpham;
    }

}
