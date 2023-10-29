package com.example.btlapp.model;

public class Giohang {
    //khai bao cac thuoc tinh co trong gio hang
    public int idsp;
    public String tensp;
    public long giasp;
    public String hinhsp;
    public int soluongsp;
    //cac phuong thuc get , set
    public int getIdsp() {
        return idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public long getGiasp() {
        return giasp;
    }

    public void setGiasp(long giasp) {
        this.giasp = giasp;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public int getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }

    public Giohang(int idsp, String tensp, long giasp, String hinhsp, int soluongsp) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhsp = hinhsp;
        this.soluongsp = soluongsp;
    }
}
