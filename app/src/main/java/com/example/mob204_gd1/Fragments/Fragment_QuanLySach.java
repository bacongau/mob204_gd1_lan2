package com.example.mob204_gd1.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mob204_gd1.Adapter.LoaiSachAdapter;
import com.example.mob204_gd1.Adapter.SachAdapter;
import com.example.mob204_gd1.DAO.LoaiSachDAO;
import com.example.mob204_gd1.DAO.SachDAO;
import com.example.mob204_gd1.Model.LoaiSach;
import com.example.mob204_gd1.Model.Sach;
import com.example.mob204_gd1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class Fragment_QuanLySach extends Fragment {
    ListView lv;
    SachAdapter sachAdapter;
    List<Sach> sachList;
    FloatingActionButton fab;
    EditText edt_tenSach, edt_giaThue;
    Spinner spinner;
    int maLoaiSachDuocChon;
    List<LoaiSach> loaiSachList;
    LoaiSachDAO loaiSachDAO;
    SachDAO sachDAO;


    public Fragment_QuanLySach() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__quan_ly_sach, container, false);

        // anh xa floating action button
        fab = view.findViewById(R.id.fab_themSach);
        // anh xa listview
        lv = view.findViewById(R.id.lv_quanlysach);

        // khoi tao DAO
        loaiSachDAO = new LoaiSachDAO(getContext());
        sachDAO = new SachDAO(getContext());

        // khoi tao list va do du lieu vao
        sachList = new ArrayList<>();
        sachList = sachDAO.getAllData();
        loaiSachList = loaiSachDAO.getAllData();

        // Khoi tao adapter
        sachAdapter = new SachAdapter(getContext(), R.layout.item_sach, sachList);

        // set adapter cho listview
        lv.setAdapter(sachAdapter);

        // set click lv
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // khoi tao dialog
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_thongtin_sach);

                // anh xa view
                Spinner spinner_chonloaisach = dialog.findViewById(R.id.spinner_dialog_thongtin_chonloaisach);
                EditText edt_tensach = dialog.findViewById(R.id.edt_dialog_ThongTinSach_tenSach);
                EditText edt_giathue = dialog.findViewById(R.id.edt_dialog_ThongTinSach_giaThue);
                EditText edt_theloai = dialog.findViewById(R.id.edt_dialog_ThongTinSach_tenTheLoai);
                Button button_capNhat = dialog.findViewById(R.id.button_dialog_ThongTinSach_capnhat);
                Button button_huy = dialog.findViewById(R.id.button_dialog_ThongTinSach_huy);
                Button button_xoa = dialog.findViewById(R.id.button_dialog_ThongTinSach_xoa);

                // lay thong tin 1 item
                Sach sach = sachList.get(position);

                String maLoaiSachDangChon = String.valueOf(sach.getMaLoai());
                LoaiSach loaiSach = loaiSachDAO.getId(maLoaiSachDangChon);
                // set text cho edittext
                edt_theloai.setText(loaiSach.getTenLoai());
                edt_tensach.setText(sach.getTenSach());
                edt_giathue.setText(sach.getGiaThue() + "");

                // setup spinner
                // tao list va lay du lieu loai sach
                loaiSachList = new ArrayList<>();
                loaiSachList = loaiSachDAO.getAllData();

                // khoi tao adapter
                LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(getContext(), R.layout.item_loaisach, loaiSachList);
                // set Adapter
                spinner_chonloaisach.setAdapter(loaiSachAdapter);

                final int[] check = {1};
                // set click
                spinner_chonloaisach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                        maLoaiSachDuocChon = loaiSachList.get(position1).getId();
                        if (check[0] == 2) {
                            edt_theloai.setText(loaiSachList.get(position1).getTenLoai());
                        }
                        if (check[0] == 1) {
                            check[0]++;
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                button_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                button_capNhat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int t = 0;
                        // gan thong tin moi object
                        sach.setTenSach(edt_tensach.getText().toString());
                        try {
                            sach.setGiaThue(Integer.parseInt(edt_giathue.getText().toString()));
                        } catch (Exception e) {
                            t = 1;
                        }
                        sach.setMaLoai(maLoaiSachDuocChon);

                        // kiem tra va cap nhat
                        if (edt_tensach.getText().length() == 0 || edt_giathue.getText().length() == 0) {
                            Toast.makeText(getContext(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                        } else if (t == 1) {
                            Toast.makeText(getContext(), "Nhập sai định dạng", Toast.LENGTH_SHORT).show();
                        } else {
                            if (sachDAO.update(sach) > 0) {
                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        CapNhatListView();
                        dialog.dismiss();

                    }
                });

                button_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Xóa sách");
                        builder.setMessage("Bạn chắc chắn muốn xóa");
                        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog1, int which) {
                                if (sachDAO.delete(String.valueOf(sach.getId())) > 0) {
                                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    CapNhatListView();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        builder.setNegativeButton("Hủy", null);
                        AlertDialog alertDialog = builder.create();
                        builder.show();

                    }
                });

                dialog.show();
            }
        });

        // set click cho fab
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_them_sach);

                // anh xa
                edt_tenSach = dialog.findViewById(R.id.edt_dialogThemSach_tensach);
                edt_giaThue = dialog.findViewById(R.id.edt_dialogThemSach_giathue);
                spinner = dialog.findViewById(R.id.spinner_dialogThemSach_chonLoaiSach);

                // setup spinner
                // tao list va lay du lieu loai sach
                loaiSachList = new ArrayList<>();
                loaiSachList = loaiSachDAO.getAllData();
                // khoi tao adapter
                LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(getContext(), R.layout.item_loaisach, loaiSachList);
                // set Adapter
                spinner.setAdapter(loaiSachAdapter);

                // set click
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maLoaiSachDuocChon = loaiSachList.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                Button button_luu = dialog.findViewById(R.id.button_dialogThemSach_luu);
                button_luu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int t = 0;
                        Sach sach = new Sach();
                        sach.setTenSach(edt_tenSach.getText().toString());
                        try {
                            sach.setGiaThue(Integer.parseInt(edt_giaThue.getText().toString()));
                        } catch (Exception e) {
                            t = 1;
                        }
                        sach.setMaLoai(maLoaiSachDuocChon);
                        if (edt_tenSach.getText().length() == 0 || edt_giaThue.getText().length() == 0) {
                            Toast.makeText(getContext(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                        } else if (t == 1) {
                            Toast.makeText(getContext(), "Nhập sai định dạng", Toast.LENGTH_SHORT).show();
                        } else {
                            if (sachDAO.insert(sach) > 0) {
                                Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        CapNhatListView();
                        dialog.dismiss();

                    }
                });

                Button button_huy = dialog.findViewById(R.id.button_dialogThemSach_huy);
                button_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        return view;
    }

    public void CapNhatListView() {
        // lay du lieu + do vao list
        sachList = sachDAO.getAllData();

        // Khoi tao adapter
        sachAdapter = new SachAdapter(getContext(), R.layout.item_sach, sachList);

        // set adapter cho listview
        lv.setAdapter(sachAdapter);
    }

}