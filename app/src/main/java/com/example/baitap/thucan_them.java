package com.example.baitap;

    import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

    public class thucan_them extends AppCompatActivity {
        EditText edtNgay, edtSang, edtCaloSang, edtTrua, edtCaloTrua, edtToi, edtCaloToi;
        Button btnLuu;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.thucan_them);

            edtNgay = findViewById(R.id.edtNgay);
            edtSang = findViewById(R.id.edtSang);
            edtCaloSang = findViewById(R.id.edtCaloSang);
            edtTrua = findViewById(R.id.edtTrua);
            edtCaloTrua = findViewById(R.id.edtCaloTrua);
            edtToi = findViewById(R.id.edtToi);
            edtCaloToi = findViewById(R.id.edtCaloToi);
            btnLuu = findViewById(R.id.btnLuuThucAn);

            btnLuu.setOnClickListener(v -> {
                String ngay = edtNgay.getText().toString();
                String sang = edtSang.getText().toString();
                int caloSang = parseIntSafe(edtCaloSang.getText().toString());
                String trua = edtTrua.getText().toString();
                int caloTrua = parseIntSafe(edtCaloTrua.getText().toString());
                String toi = edtToi.getText().toString();
                int caloToi = parseIntSafe(edtCaloToi.getText().toString());

                int tongCalo = caloSang + caloTrua + caloToi;

                thucanngay thucAnNgay = new thucanngay(
                        ngay,
                        sang + " (" + caloSang + " kcal)",
                        trua + " (" + caloTrua + " kcal)",
                        toi + " (" + caloToi + " kcal)",
                        tongCalo
                );

                Intent resultIntent = new Intent();
                resultIntent.putExtra("thucAn", thucAnNgay);
                setResult(RESULT_OK, resultIntent);
                finish();
            });
        }

        private int parseIntSafe(String text) {
            if (text == null || text.isEmpty()) return 0;
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

    }


