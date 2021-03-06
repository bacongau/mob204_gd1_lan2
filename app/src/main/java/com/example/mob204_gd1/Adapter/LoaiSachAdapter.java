package com.example.mob204_gd1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mob204_gd1.Model.LoaiSach;
import com.example.mob204_gd1.R;

import java.util.List;

public class LoaiSachAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<LoaiSach> list;

    public LoaiSachAdapter(Context context, int layout, List<LoaiSach> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView tv_maLoai,tv_tenLoai;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            holder.tv_maLoai = convertView.findViewById(R.id.textView_idLoaiSach);
            holder.tv_tenLoai = convertView.findViewById(R.id.textView_tenLoaiSach);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_maLoai.setText("" + list.get(position).getId());
        holder.tv_tenLoai.setText("" + list.get(position).getTenLoai());

        return convertView;
    }
}
