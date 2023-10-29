package com.example.btlapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.btlapp.R;
import com.example.btlapp.adapter.LoaispAdapter;
import com.example.btlapp.adapter.SanphamAdapter;
import com.example.btlapp.model.Giohang;
import com.example.btlapp.model.ItemDecoration;
import com.example.btlapp.model.Loaisp;
import com.example.btlapp.model.Sanpham;
import com.example.btlapp.ultil.checkconection;
import com.example.btlapp.ultil.server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/** @noinspection ALL*/
public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    int id=0;
    String tenloaisp="";
    String hinhanhloaisp="";
    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;
    public static ArrayList<Giohang> manggiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if(checkconection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFliper();
            GetDuLieuloaisp();
            GetDuLieuSPMoiNhat();
            CatchOnItemListView();
        }else {
            checkconection.ShowToast_Short(getApplicationContext(), "No Internet");
            finish();
        }
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

    private void CatchOnItemListView() {
        listViewmanhinhchinh.setOnItemClickListener((parent, view, position, id) -> {

            switch (position){
                case 0:
                    if(checkconection.haveNetworkConnection(getApplicationContext())){
                        Intent intent =new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        checkconection.ShowToast_Short(getApplicationContext(),"No Internet");
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case 1:
                    if(checkconection.haveNetworkConnection(getApplicationContext())){
                        Intent intent =new Intent(MainActivity.this,DienThoaiActivity.class);
                        intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                        startActivity(intent);
                    }else{
                        checkconection.ShowToast_Short(getApplicationContext(),"No Internet");
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case 2:
                    if(checkconection.haveNetworkConnection(getApplicationContext())){
                        Intent intent =new Intent(MainActivity.this,LaptopActivity.class);
                        intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                        startActivity(intent);
                    }else{
                        checkconection.ShowToast_Short(getApplicationContext(),"No Internet");
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case 3:
                    if(checkconection.haveNetworkConnection(getApplicationContext())){
                        Intent intent =new Intent(MainActivity.this,LienHeActivity.class);
                        startActivity(intent);
                    }else{
                        checkconection.ShowToast_Short(getApplicationContext(),"No Internet");
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
//                    case 4:
//                        if(checkconection.haveNetworkConnection(getApplicationContext())){
//                            Intent intent =new Intent(MainActivity.this,ThongTinActivity.class);
//                            startActivity(intent);
//                        }else{
//                            checkconection.ShowToast_Short(getApplicationContext(),"No Internet");
//                        }
//                        drawerLayout.closeDrawer(GravityCompat.START);
//                        break;
            }
        });
    }


    private void GetDuLieuSPMoiNhat() {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        @SuppressLint("NotifyDataSetChanged") JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(server.Duongdansanphammonhat, response -> {
            if(response!=null){
                int ID;
                String Tensanpham;
                int Giasanpham;
                String Hinhanhsanpham;
                String Motasanpham;
                int IDsanpham;
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        ID=jsonObject.getInt("id");
                        Tensanpham=jsonObject.getString("tensanpham");
                        Giasanpham=jsonObject.getInt("giasanpham");
                        Hinhanhsanpham=jsonObject.getString("hinhanhsanpham");
                        Motasanpham=jsonObject.getString("motasanpham");
                        IDsanpham=jsonObject.getInt("idsanpham");
                        mangsanpham.add(new Sanpham(ID,Tensanpham,Giasanpham,Hinhanhsanpham,Motasanpham,IDsanpham));
                        sanphamAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }, error -> {

        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFliper() {
        ArrayList<String> mangquangcao=new ArrayList<>();
        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTkFXlcfaVS7WkqRwDxVJYBkXLZK4glKM9now&usqp=CAU");
        mangquangcao.add("https://vietads.net.vn/image/news/7-8-2018/son-tung-oppo-15.jpg");
        mangquangcao.add("https://cdn.tgdd.vn/Files/2020/04/28/1252401/maxresdefault_800x450.jpg");
        mangquangcao.add("https://cdn.sforum.vn/sforum/wp-content/uploads/2019/05/60512122_646136495832169_9105306568458502144_n.png");
        for(int i=0;i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
//        Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
//        Animation animation_slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
//        viewFlipper.setInAnimation(animation_slide_in);
//        viewFlipper.setOutAnimation((animation_slide_out));

    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }

    private void Anhxa(){
        toolbar=(Toolbar) findViewById(R.id.toolbarmanhinhchinh);
//        setSupportActionBar(toolbar);
        viewFlipper=(ViewFlipper) findViewById(R.id.viewlipper);
        recyclerViewmanhinhchinh=(RecyclerView) findViewById(R.id.recyclerview);
        navigationView=(NavigationView) findViewById(R.id.navigationview);
        listViewmanhinhchinh= (ListView) findViewById(R.id.listviewmanhinhchinh);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
        mangloaisp=new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Home","https://cdn.pixabay.com/photo/2015/12/28/02/58/home-1110868_1280.png"));
        loaispAdapter=new LoaispAdapter(mangloaisp,getApplicationContext());
        listViewmanhinhchinh.setAdapter((loaispAdapter));
        mangsanpham = new ArrayList<>();
        sanphamAdapter=new SanphamAdapter(getApplicationContext(),mangsanpham);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewmanhinhchinh.setAdapter(sanphamAdapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing); // R.dimen.spacing là resource dimension cho khoảng cách bạn muốn
        recyclerViewmanhinhchinh.addItemDecoration(new ItemDecoration(spacingInPixels));
        if(manggiohang!=null){

        }else{
            manggiohang=new ArrayList<>();
        }

    }


    private void GetDuLieuloaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.Duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            tenloaisp=jsonObject.getString("tenloaisanpham");
                            hinhanhloaisp=jsonObject.getString("hinhanhloaisanpham");
                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
//                                throw new RuntimeException(e);
                        }
                    }
                    mangloaisp.add(3,new Loaisp(0,"Contact","https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355897_960_720.png"));
                    mangloaisp.add(4,new Loaisp(0,"Information","https://cdn.pixabay.com/photo/2015/06/09/16/12/info-803717_960_720.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkconection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}