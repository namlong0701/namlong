package com.example.btlapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btlapp.R;
import com.example.btlapp.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter  extends BaseAdapter {
    private final Context context;
    private final ArrayList<Sanpham> arraylaptop;
    private final LayoutInflater inflater;

    public LaptopAdapter(Context context, ArrayList<Sanpham> arraylaptop) {
        this.context = context;
        this.arraylaptop = arraylaptop;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arraylaptop.size();
    } // trả về số lượng bản vẽ

    @Override
    public Object getItem(int position) {
        return arraylaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        public TextView txttenlaptop, txtgialaptop, txtmotalaptop;
        public ImageView imglaptop;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LaptopAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new LaptopAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.dong_laptop, null);
            viewHolder.txttenlaptop = (TextView) convertView.findViewById(R.id.textviewtenlaptop);
            viewHolder.txtgialaptop = (TextView)convertView.findViewById(R.id.textviewgialaptop);
            viewHolder.txtmotalaptop =(TextView) convertView.findViewById(R.id.textviewmotalaptop);
            viewHolder.imglaptop = convertView.findViewById(R.id.imageviewlaptop);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LaptopAdapter.ViewHolder) convertView.getTag();
        }

        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txttenlaptop.setText(sanpham.getTensanpham());

        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");//dinh dang lại chuỗi
        String format = decimalFormat.format(sanpham.getGiasanpham());
        viewHolder.txtgialaptop.setText("Price: " + format + " Đ");

        viewHolder.txtmotalaptop.setMaxLines(2);//mo ta chi de 2 dong
        viewHolder.txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotalaptop.setText(sanpham.getMotasanpham());

        Picasso.with(context)
                .load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imglaptop);

        return convertView;
    }
}
