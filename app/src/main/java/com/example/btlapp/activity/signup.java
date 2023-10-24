package com.example.btlapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.btlapp.R;
import com.example.btlapp.ultil.checkconection;
import com.example.btlapp.ultil.server;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    EditText edtfirstName,edtlastName,edtphone,edtaddress,edtusername,edtpassword;
    Button btnsignup,btnsignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Anhxa();
        EventButton();
    }

//    private void EventQuayLaiSaukhidangki() {
//        btnsignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(signup.this,login.class);
//                startActivity(intent);
////                finish();
//            }
//        });
//
//    }

    //sk click vao nut xac nhan mua hang
    private void EventButton() {
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(signup.this, login.class);
                startActivity(intent);
                finish();
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lay gt  tu Editext
                //tao bien hung DL
                final String tendau=edtfirstName.getText().toString().trim();
                final String tencuoi=edtlastName.getText().toString().trim();
                final String sdtkhach=edtphone.getText().toString().trim();
                final String address=edtaddress.getText().toString().trim();
                final String username=edtusername.getText().toString().trim();
                final String password=edtpassword.getText().toString().trim();
                if(tendau.length()>0 && sdtkhach.length()>0 && tencuoi.length()>0 && username.length()>0 && password.length()>0 && address.length()>0 ){
                    //Volley : gui len du lieu cho server
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, server.Duongdanaccount, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            Log.d("madonhang",response);//kiem tra
                                Intent intent = new Intent(signup.this, login.class);
                                startActivity(intent);
                                finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Nullable
                        @Override
                        //day du lieu len duoi dang Hasmap
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap=new HashMap<String,String>();
                            hashMap.put("firstname",tendau);//chuyen key theo post bên file api
                            hashMap.put("lastname",tencuoi);
                            hashMap.put("phonenumber",sdtkhach);
                            hashMap.put("address",address);
                            hashMap.put("username",username);
                            hashMap.put("userpassword",password);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                    checkconection.ShowToast_Short(getApplicationContext(),"Successfully registered account");
                }else{
                    checkconection.ShowToast_Short(getApplicationContext(),"Please check the data again");
                }
            }
        });
    }

    private void Anhxa() {
       edtfirstName=(EditText) findViewById(R.id.FirstName);
       edtlastName=(EditText) findViewById(R.id.LastName);
       edtphone=(EditText) findViewById(R.id.Phone);
       edtaddress=(EditText) findViewById(R.id.EmailSign);
       edtpassword= (EditText) findViewById(R.id.PassSign);
       edtusername=(EditText) findViewById(R.id.User);
       btnsignup= (Button) findViewById(R.id.btnRegis);
       btnsignin=(Button) findViewById(R.id.btnDacotaikhoan);
    }
}