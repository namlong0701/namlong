package com.example.btlapp.model;

public class Loaisp {
    public int Id;
    public String Tenloaisp;
    public String Hinhanhloaisp;
    public Loaisp(int id,String tenloaisp,String hinhanhloaisp){
        Id=id;
        Tenloaisp=tenloaisp;
        Hinhanhloaisp=hinhanhloaisp;
    }
    public int getId(){
        return Id;
    }
    public void  setId(int id){
        Id=id;
    }
    public String getTenloaisp(){
        return Tenloaisp;
    }

    public String getHinhanhloaisp(){
        return  Hinhanhloaisp;
    }
}
