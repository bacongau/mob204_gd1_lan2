package com.example.mob204_gd1;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mob204_gd1.DAO.ThuThuDAO;
import com.example.mob204_gd1.Fragments.Fragment_DoanhThu;
import com.example.mob204_gd1.Fragments.Fragment_DoiMatKhau;
import com.example.mob204_gd1.Fragments.Fragment_QuanLyLoaiSach;
import com.example.mob204_gd1.Fragments.Fragment_QuanLyPhieuMuon;
import com.example.mob204_gd1.Fragments.Fragment_QuanLySach;
import com.example.mob204_gd1.Fragments.Fragment_QuanLyThanhVien;
import com.example.mob204_gd1.Fragments.Fragment_ThemNguoiDung;
import com.example.mob204_gd1.Fragments.Fragment_top10;
import com.example.mob204_gd1.Model.ThuThu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    TextView textView_tenThuThu;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    View headerView,menuView;
    String tenDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // display <- icon on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // find drawerlayout
        drawer = findViewById(R.id.drawer);
        actionBarDrawerToggle = setupDrawerToggle();

        navigationView = findViewById(R.id.nav_view);
        //setup drawer view
        setupDrawerContent(navigationView);

        // Setup toggle to display hamburger icon with nice animation
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        actionBarDrawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        drawer.addDrawerListener(actionBarDrawerToggle);

        // set quanly phieu muon as default fragment
        Fragment fragment_qlPhieuMuon = new Fragment_QuanLyPhieuMuon();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment_qlPhieuMuon).commit();


        // nhan user dang su dung app
        Intent intent = getIntent();
        tenDangNhap = intent.getStringExtra("tenTK");
        // gui thong tin usesr dang su dung app sang fragment quan ly phieu muon
        Bundle bundle = new Bundle();
        bundle.putString("user_key",tenDangNhap);
        Fragment_QuanLyPhieuMuon fragment_quanLyPhieuMuon = new Fragment_QuanLyPhieuMuon();
        fragment_qlPhieuMuon.setArguments(bundle);
        Fragment_DoiMatKhau fragment_doiMatKhau = new Fragment_DoiMatKhau();
        fragment_doiMatKhau.setArguments(bundle);
        // set text welcome user
        if (tenDangNhap.equalsIgnoreCase("admin")) {
            headerView = navigationView.getHeaderView(0);
            textView_tenThuThu = headerView.findViewById(R.id.textView_librarianName);
            textView_tenThuThu.setText("Xin chào ADMIN");
        } else {
            headerView = navigationView.getHeaderView(0);
            textView_tenThuThu = headerView.findViewById(R.id.textView_librarianName);
            ThuThuDAO thuThuDAO = new ThuThuDAO(this);
            ThuThu thuThu = thuThuDAO.getId(tenDangNhap);
            String a = thuThu.getHoTen();
            textView_tenThuThu.setText("Xin chào " + a);
            navigationView.getMenu().findItem(R.id.nav_themnguoidung).setVisible(false);
        }

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                });
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass;
        switch (item.getItemId()) {
            case R.id.nav_quanlyphieumuon:
                fragmentClass = Fragment_QuanLyPhieuMuon.class;
                break;

            case R.id.nav_quanlyloaisach:
                fragmentClass = Fragment_QuanLyLoaiSach.class;
                break;

            case R.id.nav_quanlysach:
                fragmentClass = Fragment_QuanLySach.class;
                break;
            case R.id.nav_quanlythanhvien:
                fragmentClass = Fragment_QuanLyThanhVien.class;
                break;
            case R.id.nav_top10sach:
                fragmentClass = Fragment_top10.class;
                break;
            case R.id.nav_doanhthu:
                fragmentClass = Fragment_DoanhThu.class;
                break;
            case R.id.nav_themnguoidung:
                fragmentClass = Fragment_ThemNguoiDung.class;
                break;
            case R.id.nav_doimatkhau:
                fragmentClass = Fragment_DoiMatKhau.class;
                break;
            case R.id.nav_dangxuat:
                Intent intent = new Intent(this, LoginScreen.class);
                startActivity(intent);
                finish();

            default:
                fragmentClass = Fragment_QuanLyPhieuMuon.class;
        }


        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);


        // Set action bar title
        setTitle(item.getTitle());

        // Close the navigation drawer
        drawer.closeDrawers();

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}