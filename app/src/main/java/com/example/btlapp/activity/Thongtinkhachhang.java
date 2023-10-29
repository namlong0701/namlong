package com.example.btlapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.btlapp.R;
import com.example.btlapp.ultil.checkconection;
import com.example.btlapp.ultil.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//insert dl tu app lên server
public class Thongtinkhachhang extends AppCompatActivity {
    EditText edttenkhachhang,edtemail,edtsdt;
    Button btnxacnhan,btntrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        Anhxa();
        //sk kick vao button tro ve
        btntrove.setOnClickListener(v -> finish());
        //kiem tra ket noi
        if(checkconection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }else{
            checkconection.ShowToast_Short(getApplicationContext(),"No Internet");
        }
    }
    //sk click vao nut xac nhan mua hang
    private void EventButton() {
        btnxacnhan.setOnClickListener(v -> {
            //lay gt  tu Editext
            //tao bien hung DL
            final String ten=edttenkhachhang.getText().toString().trim();
            final String sdt=edtsdt.getText().toString().trim();
            final String email=edtemail.getText().toString().trim();
            if(ten.length()>0 && sdt.length()>0 && email.length()>0){
                //Volley : gui len du lieu cho server
                RequestQueue  requestQueue= Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest=new StringRequest(Request.Method.POST, server.Duongdandonhang, madonhang -> {
                    Log.d("madonhang",madonhang);//kiem tra
                    //kiem tra xem có dũ liệu chưa
                    if(Integer.parseInt(madonhang)>0){
                        RequestQueue requestQueue1=Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest1=new StringRequest(Request.Method.POST, server.Duongdanchitietdonhang, response -> {
                            if(response.equals("1")){
                                MainActivity.manggiohang.clear();//xoa dl gio hang
                                checkconection.ShowToast_Short(getApplicationContext(),"You have successfully added cart data");
                                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                checkconection.ShowToast_Short(getApplicationContext(),"Please continue to buy");
                            }else{
                                checkconection.ShowToast_Short(getApplicationContext(),"The shopping cart data is error");
                            }

                        }, error -> {

                        }){
                            @NonNull
                            @Override
                            protected Map<String, String> getParams() {
                                JSONArray jsonArray = new JSONArray();
                                for (int i=0;i<MainActivity.manggiohang.size();i++){
                                    JSONObject jsonObject=new JSONObject();
                                    try {
                                        jsonObject.put("madonhang",madonhang);
                                        jsonObject.put("masanpham",MainActivity.manggiohang.get(i).getIdsp());
                                        jsonObject.put("tensanpham",MainActivity.manggiohang.get(i).getTensp());
                                        jsonObject.put("giasanpham",MainActivity.manggiohang.get(i).getGiasp());
                                        jsonObject.put("soluongsanpham",MainActivity.manggiohang.get(i).getSoluongsp());
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                    jsonArray.put(jsonObject);
                                }
                                HashMap<String,String> hashMap= new HashMap<>();
                                hashMap.put("json",jsonArray.toString());
                                return hashMap;
                            }
                        };
                        requestQueue1.add(stringRequest1);
                    }
                }, error -> {

                }){
                    @NonNull
                    @Override
                    //day du lieu len duoi dang Hasmap
                    protected Map<String, String> getParams() {
                        HashMap<String,String> hashMap= new HashMap<>();
                        hashMap.put("tenkhachhang",ten);//chuyen key theo post bên file api
                        hashMap.put("sodienthoai",sdt);
                        hashMap.put("email",email);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }else{
                checkconection.ShowToast_Short(getApplicationContext(),"Please check the data again");
            }
        });
    }

    private void Anhxa() {
        edttenkhachhang = findViewById(R.id.edittexttenkhachhang);
        edtemail=findViewById(R.id.edittextemailkhachhang);
        edtsdt=findViewById(R.id.edittextsdtkhachhang);
        btnxacnhan=findViewById(R.id.buttonxacnhan);
        btntrove=findViewById(R.id.buttontrove);
    }
}