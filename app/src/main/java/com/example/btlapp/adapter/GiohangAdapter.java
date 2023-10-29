package com.example.btlapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btlapp.R;
import com.example.btlapp.activity.MainActivity;
import com.example.btlapp.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arraygiohang;

    public GiohangAdapter(Context context, ArrayList<Giohang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public static class ViewHolder{
        public TextView txttengiohang,txtgiagiohang;
        public ImageView imggiohang;
        public Button btntru,btnvalues,btncong;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){//nếu view null
            viewHolder = new ViewHolder();//khởi tạo 1 class viewholder
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_giohang,null);//gán dữ liệu dòng giỏ hàng cho vỉew
            //lấy những dl trong class viewholder
            viewHolder.txttengiohang=(TextView) convertView .findViewById(R.id.textviewtengiohang);
            viewHolder.txtgiagiohang=(TextView) convertView .findViewById(R.id.textviewgiagiohang);
            viewHolder.imggiohang=(ImageView) convertView .findViewById(R.id.imageviewgiohang);
            viewHolder.btntru=(Button) convertView .findViewById(R.id.buttontru);
            viewHolder.btnvalues=(Button) convertView .findViewById(R.id.buttonvalues);
            viewHolder.btncong=(Button) convertView.findViewById(R.id.buttoncong);
            convertView.setTag(viewHolder);
        }
        else{
            //gán dữ liệu nếu có view
            viewHolder=(ViewHolder) convertView.getTag();
        }
        Giohang giohang=(Giohang) getItem(position);//khởi tạo class của giỏ hàng
        //gán dữ liệu cho từng thuộc tính
        viewHolder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat(("###,###.###"));
        viewHolder.txtgiagiohang.setText("Price: "+decimalFormat.format(giohang.getGiasp())+"Đ");
        Picasso.with(context).load(giohang.getHinhsp())//gán hình ảnh qua thư viện picasso
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imggiohang);
        viewHolder.btnvalues.setText(giohang.getSoluongsp()+"");//chuyển về dạng chuỗi
        int sl = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if(sl>=10){//nếu sl >=10
            viewHolder.btncong.setVisibility(View.INVISIBLE);//+mờ
            viewHolder.btntru.setVisibility(View.VISIBLE);
        }else if(sl<=1){
            viewHolder.btntru.setVisibility(View.INVISIBLE);
        }else
        {
            viewHolder.btntru.setVisibility(View.VISIBLE);
            viewHolder.btncong.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btncong.setOnClickListener(v -> {//bắt sự kiện cho nút cộng
            int slmoinhat=Integer.parseInt(finalViewHolder.btnvalues.getText().toString())+1;
            int slhientai= MainActivity.manggiohang.get(position).getSoluongsp();
            finalViewHolder.btnvalues.setText(slmoinhat+"");
            //lấy gt hiện tại
            long giaht= MainActivity.manggiohang.get(position).getGiasp();
            MainActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
            long giamoinhat=(giaht*slmoinhat)/slhientai;
            //gán các gt mới cho mảng
            MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
            DecimalFormat decimalFormat1 =new DecimalFormat(("###,###.###"));
            finalViewHolder.txtgiagiohang.setText("Price: "+ decimalFormat1.format(giohang.getGiasp())+"Đ");
            com.example.btlapp.activity.Giohang.EventUltil();
            if(slmoinhat>9){
                finalViewHolder.btncong.setVisibility(View.INVISIBLE);//set làm mờ dấu +
            }
            else{
                finalViewHolder.btncong.setVisibility(View.VISIBLE);//hiển thị bthg
            }
            finalViewHolder.btntru.setVisibility(View.VISIBLE);
            finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
        });
        viewHolder.btntru.setOnClickListener(v -> {//bắt sự kiện cho nút trừ
            //lấy sl mới nhất
            int slmoinhat = Integer.parseInt(finalViewHolder.btnvalues.getText().toString())-1;
            int slhientai = MainActivity.manggiohang.get(position).getSoluongsp();
            finalViewHolder.btnvalues.setText(slmoinhat +"");
            long giaht = MainActivity.manggiohang.get(position).getGiasp();
            MainActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
            // tính giá mới nhất
            long giamoinhat = (giaht*slmoinhat)/slhientai;
            //gán gtri cho mảng
            MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
            String pattern = "###,###.###";
            DecimalFormat decimalFormat12 = new DecimalFormat(pattern);
            String format = decimalFormat12.format(giamoinhat);
            finalViewHolder.txtgiagiohang.setText("Price: " + format + " Đ");
            com.example.btlapp.activity.Giohang.EventUltil();
            finalViewHolder.btncong.setVisibility(View.VISIBLE);
            if(slmoinhat<2){
                finalViewHolder.btntru.setVisibility(View.INVISIBLE);//ẩn nút trừ
            }else{
                finalViewHolder.btntru.setVisibility(View.VISIBLE);
            }
            finalViewHolder.btnvalues.setText((String.valueOf(slmoinhat)));
        });
        return convertView;
    }
}

