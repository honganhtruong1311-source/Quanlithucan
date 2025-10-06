package com.example.baitap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class activity_thucan extends AppCompatActivity {
    RecyclerView rcvThucAn;
    Button btnThem;
    TextView txtNgay;
    ArrayList<thucanngay> list;
    thucan_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thucan);

        rcvThucAn = findViewById(R.id.rcvThucAn);
        btnThem = findViewById(R.id.btnThemThucAn);
        txtNgay = findViewById(R.id.txtNgayHienTai);

        // Hiển thị ngày hiện tại
        String today = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        txtNgay.setText("Ngày: " + today);

        // Load danh sách
        list = save_thucan.loadList(this);
        adapter = new thucan_adapter(this, list);
        rcvThucAn.setLayoutManager(new LinearLayoutManager(this));
        rcvThucAn.setAdapter(adapter);

        btnThem.setOnClickListener(v -> {
            Intent intent = new Intent(activity_thucan.this, thucan_them.class);
            startActivityForResult(intent, 100);
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, @Nullable Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        if (reqCode == 100 && resCode == RESULT_OK && data != null) {
            thucanngay item = (thucanngay) data.getSerializableExtra("thucAn");
            list.add(item);
            adapter.notifyItemInserted(list.size() - 1);
            save_thucan.saveList(this, list);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        save_thucan.saveList(this, list);
    }
}
