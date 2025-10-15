
    package com.example.baitap;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

    public class ThucAn_sua extends AppCompatActivity {

        private EditText edtTen, edtCalo;
        private Spinner spBuaAn;
        private Button btnCapNhat, btnHuy;
        private database dbHelper;
        private int id;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.sua_bua);

            edtTen = findViewById(R.id.edtTenThucAnSua);
            edtCalo = findViewById(R.id.edtCaloSua);
            spBuaAn = findViewById(R.id.spBuaAnSua);
            btnCapNhat = findViewById(R.id.btnCapNhat);
            btnHuy = findViewById(R.id.btnHuySua);

            dbHelper = new database(this);

            // Nhận dữ liệu từ intent
            id = getIntent().getIntExtra("id", -1);
            String ten = getIntent().getStringExtra("ten");
            String buaan = getIntent().getStringExtra("buaan");
            int calo = getIntent().getIntExtra("calo", 0);

            edtTen.setText(ten);
            edtCalo.setText(String.valueOf(calo));

            // chọn đúng spinner
            String[] buaArray = getResources().getStringArray(R.array.buaan_array);
            for (int i = 0; i < buaArray.length; i++) {
                if (buaArray[i].equals(buaan)) {
                    spBuaAn.setSelection(i);
                    break;
                }
            }

            btnCapNhat.setOnClickListener(v -> {
                String tenMoi = edtTen.getText().toString().trim();
                String buaanMoi = spBuaAn.getSelectedItem().toString();
                String caloStr = edtCalo.getText().toString().trim();

                if (tenMoi.isEmpty() || caloStr.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                int caloMoi = Integer.parseInt(caloStr);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("tenthucan", tenMoi);
                values.put("buaan", buaanMoi);
                values.put("calohapthu", caloMoi);
                db.update("thucan", values, "id=?", new String[]{String.valueOf(id)});

                Toast.makeText(this, "Đã cập nhật món ăn", Toast.LENGTH_SHORT).show();
                finish();
            });

            btnHuy.setOnClickListener(v -> finish());
        }
    }


