package com.example.btlapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.btlapp.R;
import com.example.btlapp.model.Giohang;
import com.example.btlapp.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Objects;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txttenspchitiet,txtgiaspchitiet,txtmotaspchitiet;
    Spinner spinner;
    Button btndatmua;
    //tao bien hung cac gia tri trong getinformation
    int id=0;
    int idsanpham=0;
    String  Tenchitiet="";
    String Hinhanhchitiet="";
    String Motachitiet="";
    int Giachitiet=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActionToolBar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.mngiohang){
            Intent intent = new Intent(getApplicationContext(), com.example.btlapp.activity.Giohang.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btndatmua.setOnClickListener(
                v -> {
            if (MainActivity.manggiohang.size()>0){
                int sl=Integer.parseInt((spinner.getSelectedItem().toString()));
                boolean exists=false;
                for(int i = 0; i< MainActivity.manggiohang.size(); i++){
                    if(MainActivity.manggiohang.get(i).getIdsp()==id){ // Kiểm tra id để update số lượng sản phẩm
                        MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp()+sl);
                        if(MainActivity.manggiohang.get(i).getSoluongsp()>=10){//set lại số lương sp nếu thêm sl quá 10
                            MainActivity.manggiohang.get(i).setSoluongsp(10); // set lại số luong =10
                        }
                        MainActivity.manggiohang.get(i).setGiasp((long) Giachitiet * MainActivity.manggiohang.get(i).getSoluongsp());
                        exists=true;
                    }
                }
                if (!exists){
                    //tính tổng tiền thanh toán
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi= (long) soluong *Giachitiet;
                    MainActivity.manggiohang.add(new Giohang(id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
                }

            }else{
                int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                long Giamoi= (long) soluong *Giachitiet;
                MainActivity.manggiohang.add(new Giohang(id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
            }
            Intent intent=new Intent(getApplicationContext(), com.example.btlapp.activity.Giohang.class);
            startActivity(intent);
        });
    }


    //gioi han so luong san pham qua  spinner
    private void CatchEventSpinner() {
        Integer[] soluong= new Integer[]{1,2,3,4,5,6,7,8,9,10};
        //gọi lại bản vẽ
        ArrayAdapter<Integer> arrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter); //truyen ve ban ve
    }
    //lay du lieu man hinh khac truyen cho man hinh nay
    @SuppressLint("SetTextI18n")
    private void GetInformation() {
        Sanpham sanpham= (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        assert sanpham != null;
        id=sanpham.getId();//gán dữ liệu cho các biến ở phía trên khai báo
        Tenchitiet=sanpham.getTensanpham();
        Giachitiet=sanpham.getGiasanpham();
        Hinhanhchitiet=sanpham.getHinhanhsanpham();
        Motachitiet=sanpham.getMotasanpham();
        idsanpham=sanpham.getIDSanpham();
        //gán dữ liệu trên cho các layout
        txttenspchitiet.setText(Tenchitiet);
        DecimalFormat decimalFormat= new DecimalFormat("###,###.###");
        txtgiaspchitiet.setText("Price: "+decimalFormat.format(Giachitiet)+"Đ");
        txtmotaspchitiet.setText(Motachitiet);
        Picasso.with(getApplicationContext()).load(Hinhanhchitiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgChitiet);
    }


    private void ActionToolBar() {
        setSupportActionBar(toolbarChitiet);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(v -> finish());
    }
    //khởi tạo và gán các giá trị id vào thuộc tính
    private void Anhxa() {
        toolbarChitiet= findViewById(R.id.toolbarchitietsanpham);
        imgChitiet= findViewById(R.id.imageviewchitietsanpham);
        txttenspchitiet= findViewById(R.id.textviewtenchitietsanpham);
        txtgiaspchitiet = findViewById(R.id.textviewgiachitietsanpham);
        txtmotaspchitiet= findViewById(R.id.textviewmotachitietsanpham);
        spinner= findViewById(R.id.spinner);
        btndatmua= findViewById(R.id.buttondatmua);


    }
}