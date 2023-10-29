package com.example.btlapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.btlapp.R;
import com.example.btlapp.adapter.GiohangAdapter;
import com.example.btlapp.ultil.checkconection;

import java.text.DecimalFormat;
import java.util.Objects;

public class Giohang extends AppCompatActivity {


ListView lvgiohang;
    TextView txtthongbao;
    @SuppressLint("StaticFieldLeak")
    static TextView txttongtien;
    Button btnthanhtoan,btntieptucmua;
    Toolbar toolbargiohang;
    GiohangAdapter giohangAdapter;// gọi lại bản vẽ adapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        ActionToolbar();
        CheckData();
        EventUltil();
        CatchOnItemListView();
        EventButton();
    }
//     bắt sk button
    private void EventButton() {
        // nếu ấn vào nút tiếp tục mua
        btntieptucmua.setOnClickListener(v -> {// chuyển sang màn hình chính
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        });
        // nếu ấn vào nút thanh toán
        btnthanhtoan.setOnClickListener(v -> {
            if(MainActivity.manggiohang.size()>0){// nếu có sp
                Intent intent = new Intent(getApplicationContext(), Thongtinkhachhang.class);// đền màn hình thông tin khách hàng
                startActivity(intent);
            }else {//nếu ngước lại thì thông báo k có sp
                checkconection.ShowToast_Short(getApplicationContext(),"\n" + "Your shopping cart does not have products to checkout");//checkout thanh toán
            }
        });
    }
    //xóa sp trong giỏ hàng
    private void CatchOnItemListView() {
        //giữ lâu sp
        lvgiohang.setOnItemLongClickListener((adapterView, view, position, l) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Giohang.this);//khởi tạo hàm tạo ra hộp thông báo
            builder.setTitle("\n" + "Confirm product deletion");
            builder.setMessage("\n" + "Are you sure you want to delete the product?");
            // nếu ấn có
            builder.setPositiveButton("Yes", (dialog, which) -> {
                if(MainActivity.manggiohang.size() == 0){//nếu mảng < 0
                    txtthongbao.setVisibility(View.VISIBLE);// tb hiện lên
                }else{
                    MainActivity.manggiohang.remove(position);// rời vị trí
                    giohangAdapter.notifyDataSetChanged();// cập nhật Adapter
                    EventUltil();// cập nhật tổng tiền
                    if(MainActivity.manggiohang.size() == 0){
                        txtthongbao.setVisibility(View.VISIBLE);//hien thong bao rong
                    }else{
                        txtthongbao.setVisibility(View.INVISIBLE);//khong hien
                        giohangAdapter.notifyDataSetChanged();//cập nhật adapter
                        EventUltil();
                    }
                }
            });
            // nếu ấn không
            builder.setNegativeButton("No", (dialog, which) -> {
                giohangAdapter.notifyDataSetChanged();// cập nhật adapter và giá
                EventUltil();
            });
            builder.show();// hiển thị ra
            return true;
        });
    }
    // đổ dữ liệu lên lv
    @SuppressLint("SetTextI18n")
    public static void EventUltil() {
        long tongtien=0;// lấy giá trị trong mảng ra
        for(int i = 0;i<MainActivity.manggiohang.size();i++){// đi vào trong mảng và gán tất cả dl
            tongtien+= MainActivity.manggiohang.get(i).getGiasp();
        }
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String format = decimalFormat.format(tongtien);
        txttongtien.setText(format + " Đ");
    }
    // kiểm tra dl để hiển thị
    private void CheckData() {
        if(MainActivity.manggiohang.size() == 0){// nếu mảng ko có dl
            giohangAdapter.notifyDataSetChanged();// cập nhật Adapter
            txtthongbao.setVisibility(View.VISIBLE);// cho thông báo hiện ra
            lvgiohang.setVisibility(View.INVISIBLE);//ẩn lv
        }else{// nếu có dl
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);// ẩn tb
            lvgiohang.setVisibility(View.VISIBLE);// hiện lv giỏ hàng
        }
    }
    // bắt sự kiện cho nút quay về
    private void ActionToolbar() {
        setSupportActionBar(toolbargiohang);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(v -> finish());
    }
    //khởi tạo và gán các giá trị id vào thuộc tính
    private void Anhxa() {
        lvgiohang = findViewById(R.id.listviewgiohang);
        txtthongbao = findViewById(R.id.textviewthongbao);
        txttongtien = findViewById(R.id.textviewtongtien);
        btnthanhtoan = findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmua = findViewById(R.id.buttontieptucmuahang);
        toolbargiohang = findViewById(R.id.toolbargiohang);
        giohangAdapter = new GiohangAdapter(Giohang.this,MainActivity.manggiohang);
        lvgiohang.setAdapter(giohangAdapter);
    }
}


