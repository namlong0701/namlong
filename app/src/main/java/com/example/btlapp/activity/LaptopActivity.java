package com.example.btlapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.btlapp.R;
import com.example.btlapp.adapter.LaptopAdapter;
import com.example.btlapp.model.Sanpham;
import com.example.btlapp.ultil.checkconection;
import com.example.btlapp.ultil.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** @noinspection ALL*/
public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbarlaptop;
    ListView lvlaptop;
    LaptopAdapter laptopAdapter;
    ArrayList<Sanpham> manglaptop;
    int Idlaptop = 0;
    int page =1;
    View footerview;
    boolean isLoading=false;
    boolean limitData=false;
    LaptopActivity.mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        Anhxa();
        if(checkconection.haveNetworkConnection(getApplicationContext())){

            getIdLoaisp();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        }else{
            checkconection.ShowToast_Short(getApplicationContext(),"No Internet");
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

    @SuppressLint("InflateParams")
    private void Anhxa() {
        toolbarlaptop = (Toolbar) findViewById(R.id.toolbarlaptop);
        setSupportActionBar(toolbarlaptop); // toolbar là một đối tượng androidx.appcompat.widget.Toolbar
        lvlaptop = (ListView) findViewById(R.id.listviewlaptop);
        manglaptop = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(), manglaptop);
        lvlaptop.setAdapter(laptopAdapter);
        LayoutInflater inflater =(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview= inflater.inflate(R.layout.progressbar,null);
        mHandler= new LaptopActivity.mHandler();
    }
    private void getIdLoaisp() {
        Idlaptop = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("giatriloaisanpham", Idlaptop+"");
    }
    private void ActionToolbar() {
        setSupportActionBar(toolbarlaptop);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // tạo nút home để back về
        //trở lại trang trước
        toolbarlaptop.setNavigationOnClickListener(v -> finish());
    }
    private void GetData(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= server.Duongdandienthoai+ Page;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, duongdan, response -> {
            int id;
            String Tenlaptop;
            int Gialaptop;
            String Hinhanhlaptop;
            String Motalaptop;
            int Idsanphamlaptop;
            if(response!=null && response.length()!=2  ){
                lvlaptop.removeFooterView(footerview);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        id=jsonObject.getInt("id");
                        Tenlaptop=jsonObject.getString("tensanpham");
                        Gialaptop=jsonObject.getInt("giasanpham");
                        Hinhanhlaptop=jsonObject.getString("hinhanhsanpham");
                        Motalaptop=jsonObject.getString("motasanpham");
                        Idsanphamlaptop=jsonObject.getInt("idsanpham");
                        manglaptop.add(new Sanpham(id,Tenlaptop,Gialaptop,Hinhanhlaptop,Motalaptop,Idsanphamlaptop));
                        laptopAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
//                        throw new RuntimeException(e);
                    e.printStackTrace();
                }
            }else{
                limitData=true;
                lvlaptop.removeFooterView(footerview);
                checkconection.ShowToast_Short(getApplicationContext(),"Het Dữ liệu");
            }

        }, error -> {

        }){ //day dl len server duoi dang Hasmap
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> param= new HashMap<>();
                param.put("idsanpham",String.valueOf(Idlaptop));

                return param;
            }
        };
        requestQueue.add(stringRequest);

    }
    //phân bố công việc cho thread
    @SuppressLint("HandlerLeak")
    public  class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0://neu thread gưi len =0
                    lvlaptop.addFooterView((footerview));
                    break;
                case 1:
                    page++;
                    GetData(page);
                    isLoading=false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    //thực hiện trên nhiều luồng
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Message message=mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
    private void LoadMoreData() {
        lvlaptop.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent=new Intent(getApplicationContext(),ChiTietSanPham.class);
            intent.putExtra("thongtinsanpham", manglaptop.get(position));
            startActivity(intent);
        });
        lvlaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstItem, int visibleItem, int totalItem) {
                if(firstItem+visibleItem==totalItem && totalItem!=0&& !isLoading && !limitData){
                    isLoading=true;
                    LaptopActivity.ThreadData threadData=new LaptopActivity.ThreadData();
                    threadData.start();
                }
            }
        });
    }
}