package com.example.baitap;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThucAn_activity extends AppCompatActivity {

    private ListView listThucAn;
    private TextView tvTongCalo, tvNgay;
    private Button btnThem;
    private database dbHelper;
    private List<ThucAn> list;
    private ThucAn_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thucan);

        // Ánh xạ view
        listThucAn = findViewById(R.id.listThucAn);
        tvTongCalo = findViewById(R.id.tvTongCalo);
        tvNgay = findViewById(R.id.tvNgay);
        btnThem = findViewById(R.id.btnThemThucAn);

        dbHelper = new database(this);

        // Hiển thị ngày hôm nay
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        tvNgay.setText("Ngày hôm nay: " + today);

        // Tải dữ liệu khi khởi động
        loadData();

        // Sự kiện thêm món
        btnThem.setOnClickListener(v -> {
            startActivity(new Intent(ThucAn_activity.this, ThucAn_them.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // Tải lại dữ liệu khi quay lại màn hình
    }

    private void loadData() {
        list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Cursor cursor = db.rawQuery("SELECT * FROM thucan WHERE ngay = ?", new String[]{today});

        int tongCalo = 0;

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                String buaan = cursor.getString(2);
                int calo = cursor.getInt(3);
                String ngay = cursor.getString(4);

                list.add(new ThucAn(id, ten, buaan, calo, ngay));
                tongCalo += calo;
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Gán adapter cho ListView
        adapter = new ThucAn_adapter(this, list);
        listThucAn.setAdapter(adapter);

        // Cập nhật tổng calo hiển thị
        tvTongCalo.setText("Tổng calo hấp thu: " + tongCalo + " kcal");
    }

    // HÀM CẬP NHẬT LẠI TỔNG CALO (có thể gọi từ nơi khác nếu cần)
    public void updateTongCalo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Cursor cursor = db.rawQuery("SELECT SUM(calohapthu) FROM thucan WHERE ngay = ?", new String[]{today});

        if (cursor.moveToFirst()) {
            int tongCalo = cursor.getInt(0);
            tvTongCalo.setText("Tổng calo hấp thu: " + tongCalo + " kcal");
        } else {
            tvTongCalo.setText("Tổng calo hấp thu: 0 kcal");
        }

        cursor.close();
    }
}
