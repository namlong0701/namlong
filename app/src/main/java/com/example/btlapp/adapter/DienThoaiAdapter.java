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

public class DienThoaiAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Sanpham> arraydienthoai;
    private final LayoutInflater inflater;

    public DienThoaiAdapter(Context context, ArrayList<Sanpham> arraydienthoai) {
        this.context = context;
        this.arraydienthoai = arraydienthoai;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arraydienthoai.size();
    }

    @Override
    public Object getItem(int position) {
        return arraydienthoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        public TextView txttendienthoai, txtgiadienthoai, txtmotadienthoai;
        public ImageView imgdienthoai;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.dong_dienthoai, null);
            viewHolder.txttendienthoai = convertView.findViewById(R.id.textviewtendienthoai);
            viewHolder.txtgiadienthoai = convertView.findViewById(R.id.textviewgiadienthoai);
            viewHolder.txtmotadienthoai = convertView.findViewById(R.id.textviewmotadienthoai);
            viewHolder.imgdienthoai = convertView.findViewById(R.id.imageviewdienthoai);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txttendienthoai.setText(sanpham.getTensanpham());

        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        String format = decimalFormat.format(sanpham.getGiasanpham());
        viewHolder.txtgiadienthoai.setText("Price: " + format + " Đ");

        viewHolder.txtmotadienthoai.setMaxLines(2);
        viewHolder.txtmotadienthoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotadienthoai.setText(sanpham.getMotasanpham());

        Picasso.with(context)
                .load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgdienthoai);

        return convertView;
    }
}

