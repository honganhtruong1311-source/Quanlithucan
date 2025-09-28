package com.example.baitap;

    import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

    public class activity_thucan extends AppCompatActivity {
            RecyclerView rcvThucAn;

            Button btnThemThucAn;
            ArrayList<thucanngay> dsNgay;
            thucan_adapter adapter;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_thucan);

                rcvThucAn = findViewById(R.id.rcvThucAn);
                btnThemThucAn = findViewById(R.id.btnThemThucAn);
                // 🔹 Load danh sách đã lưu
                dsNgay = save_thucan.loadList(this);

                adapter = new thucan_adapter(this, dsNgay);
                rcvThucAn.setLayoutManager(new LinearLayoutManager(this));
                rcvThucAn.setAdapter(adapter);


                btnThemThucAn.setOnClickListener(v -> {
                    Intent intent = new Intent(activity_thucan.this, thucan_them.class);
                    startActivityForResult(intent, 100);
                });
            }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
                    thucanngay thucAnNgay = (thucanngay) data.getSerializableExtra("thucAn");
                    dsNgay.add(thucAnNgay);
                    adapter.notifyItemInserted(dsNgay.size() - 1);

                    // 🔹 Lưu lại sau khi thêm
                    save_thucan.saveList(this, dsNgay);
                }
            }

            @Override
            protected void onPause() {
                super.onPause();
                // 🔹 Tự động lưu khi thoát Activity
                save_thucan.saveList(this, dsNgay);
            }
        }




