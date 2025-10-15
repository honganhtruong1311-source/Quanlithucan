
    package com.example.baitap;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

    public class ThucAn_them extends AppCompatActivity {

        private EditText edtTen, edtCalo;
        private Spinner spBuaAn;
        private Button btnLuu, btnHuy;
        private database dbHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.thucan_them);

            edtTen = findViewById(R.id.edtTenThucAn);
            edtCalo = findViewById(R.id.edtCalo);
            spBuaAn = findViewById(R.id.spBuaAn);
            btnLuu = findViewById(R.id.btnLuu);
            btnHuy = findViewById(R.id.btnHuy);

            dbHelper = new database(this);

            btnLuu.setOnClickListener(v -> {
                String ten = edtTen.getText().toString().trim();
                String buaan = spBuaAn.getSelectedItem().toString();
                String caloStr = edtCalo.getText().toString().trim();

                if (ten.isEmpty() || caloStr.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                int calo = Integer.parseInt(caloStr);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("tenthucan", ten);
                values.put("buaan", buaan);
                values.put("calohapthu", calo);
                values.put("ngay", today);
                db.insert("thucan", null, values);

                Toast.makeText(this, "Đã thêm món ăn", Toast.LENGTH_SHORT).show();
                finish();
            });

            btnHuy.setOnClickListener(v -> finish());
        }
    }


