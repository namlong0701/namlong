package com.example.btlapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlapp.R;
import com.example.btlapp.model.Api_User;
import com.example.btlapp.model.modelUser;
import com.example.btlapp.ultil.checkconection;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {
        Button btnRegis;
        EditText edtEmail, edtPass;
        Button btnLogin;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_login);
                Anhxa();
                btnRegis.setOnClickListener(view -> {
                        Intent intent = new Intent(login.this, signup.class);
                        startActivity(intent);
                });

                btnLogin.setOnClickListener(view -> {
                        // Sử dụng Retrofit để gọi API User từ tệp Api_User
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://namlong71.000webhostapp.com/namlong/") // Thay URL bằng URL cụ thể của API User
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        Api_User apiUser = retrofit.create(Api_User.class);

                        // Gọi API để lấy danh sách người dùng
                        Call<List<modelUser>> call = apiUser.getUser();
                        call.enqueue(new Callback<List<modelUser>>() {
                                @Override
                                public void onResponse(@NonNull Call<List<modelUser>> call, @NonNull Response<List<modelUser>> response) {
                                        if (response.isSuccessful()) {
                                                List<modelUser> userList = response.body();
                                                // Kiểm tra thông tin đăng nhập
                                                String email = edtEmail.getText().toString();
                                                String password = edtPass.getText().toString();

                                                assert userList != null;
                                                for (modelUser user : userList) {
                                                        if (email.equals(user.getUserName()) && password.equals(user.getUserPassword())) {
                                                                // Đăng nhập thành công, chuyển đến màn hình homeDisplay
                                                                Intent intent = new Intent(login.this, MainActivity.class);
                                                                startActivity(intent);
                                                                return; // Thoát vòng lặp sau khi tìm thấy người dùng hợp lệ
                                                        }
                                                }

                                                // Nếu vòng lặp không tìm thấy người dùng hợp lệ
//                                                        Toast.makeText(login.this, "Login unsuccessful. Check the information again", Toast.LENGTH_SHORT).show();
                                                checkconection.ShowToast_Short(getApplicationContext(),"\n" + "Login unsuccessful. Check the information again");
                                        } else {
                                                // Xử lý lỗi kết nối hoặc lỗi từ API
//                                                        Toast.makeText(login.this, "\n" + "An error occurred while calling the API", Toast.LENGTH_SHORT).show();
                                                checkconection.ShowToast_Short(getApplicationContext(),"\n" + "An error occurred while calling the API");
                                        }
                                }

                                @Override
                                public void onFailure(@NonNull Call<List<modelUser>> call, @NonNull Throwable t) {
                                        // Xử lý lỗi kết nối
//                                                Toast.makeText(login.this, "\n" + "Connection errors.", Toast.LENGTH_SHORT).show();
                                        checkconection.ShowToast_Short(getApplicationContext(),"\n" + "Connection errors");
                                }
                        });
                });
        }

        private void Anhxa() {
                btnRegis = findViewById(R.id.btnRegis);
                edtEmail = findViewById(R.id.edtEmail);
                edtPass = findViewById(R.id.edtPass);
                btnLogin = findViewById(R.id.btnLogin);
        }
}