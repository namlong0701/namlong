package com.example.btlapp.model;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api_User {
    @GET("user.php")
    Call<List<modelUser>> getUser();
}
