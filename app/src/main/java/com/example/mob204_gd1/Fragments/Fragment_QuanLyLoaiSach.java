package com.example.mob204_gd1.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mob204_gd1.Adapter.LoaiSachAdapter;
import com.example.mob204_gd1.Model.LoaiSach;
import com.example.mob204_gd1.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_QuanLyLoaiSach extends Fragment {
    ListView lv;
    LoaiSachAdapter adapter;
    List<LoaiSach> list;

    public Fragment_QuanLyLoaiSach() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__quan_ly_loai_sach, container, false);

        // anh xa listview
        lv = view.findViewById(R.id.lv_quanlyloaisach);

        // khoi tao list va do du lieu vao
        list = new ArrayList<>();
        list.add(new LoaiSach(1,"Tieu thuyet"));
        list.add(new LoaiSach(2,"Lap trinh"));
        list.add(new LoaiSach(3,"Kinh te"));
        list.add(new LoaiSach(4,"Khoa hoc"));

        // Khoi tao adapter
        adapter = new LoaiSachAdapter(getContext(),R.layout.item_loaisach,list);

        // set adapter cho listview
        lv.setAdapter(adapter);

        return view;
    }
}