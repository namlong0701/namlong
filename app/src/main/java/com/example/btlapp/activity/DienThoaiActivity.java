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
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.btlapp.R;
import com.example.btlapp.adapter.DienThoaiAdapter;
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
public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbardt;
    ListView lvdt;
    DienThoaiAdapter dienThoaiAdapter;
    ArrayList<Sanpham> mangdt;
    int Iddt = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitData = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        Anhxa();
        if (checkconection.haveNetworkConnection(getApplicationContext())) {
            getIdLoaisp();
            ActionToolbar();
            GetData(page);
            LoadMoreData();

        } else {
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

    private void LoadMoreData() {
        lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham", mangdt.get(position));
                startActivity(intent);
            }
        });
        lvdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem != 0 && isLoading == false && limitData == false) {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = server.Duongdandienthoai + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tendt = "";
                int Giadt = 0;
                String Hinhanhdt = "";
                String Motadt = "";
                int Idsanphamdt = 0;
                if (response != null && response.length() != 2) {
                    lvdt.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendt = jsonObject.getString("tensanpham");
                            Giadt = jsonObject.getInt("giasanpham");
                            Hinhanhdt = jsonObject.getString("hinhanhsanpham");
                            Motadt = jsonObject.getString("motasanpham");
                            Idsanphamdt = jsonObject.getInt("idsanpham");
                            mangdt.add(new Sanpham(id, Tendt, Giadt, Hinhanhdt, Motadt, Idsanphamdt));
                            dienThoaiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
//                        throw new RuntimeException(e);
                        e.printStackTrace();
                    }
                } else {
                    limitData = true;
                    lvdt.removeFooterView(footerview);
                    checkconection.ShowToast_Short(getApplicationContext(), "Het Dữ liệu");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) { //day dl len server duoi dang Hasmap
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> param = new HashMap<>();
                param.put("idsanpham", String.valueOf(Iddt));

                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    //bắt sự kiện quay về toolbar
    private void ActionToolbar() {
        setSupportActionBar(toolbardt);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(v -> finish());
    }

    //lay id loai san pham
    private void getIdLoaisp() {
        Iddt = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("giatriloaisanpham", Iddt + "");
    }

    //khoi tao va gan id vao cac thuoc tinh
    @SuppressLint("InflateParams")
    private void Anhxa() {
        toolbardt = findViewById(R.id.toolbardienthoai);
        setSupportActionBar(toolbardt); // toolbar là một đối tượng androidx.appcompat.widget.Toolbar
        lvdt = findViewById(R.id.listviewdienthoai);
        mangdt = new ArrayList<>();
        dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(), mangdt);
        lvdt.setAdapter(dienThoaiAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();
    }

    @SuppressLint("HandlerLeak")
    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0://neu thread gưi len =0
                    lvdt.addFooterView((footerview));
                    break;
                case 1:
                    page++;
                    GetData(page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

}