package com.example.baitap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class thucan_them extends Activity {
    EditText edtSangMon, edtSangCalo, edtTruaMon, edtTruaCalo, edtToiMon, edtToiCalo;
    Button btnLuu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thucan_them);

        edtSangMon = findViewById(R.id.edtSang);
        edtSangCalo = findViewById(R.id.edtCaloSang);
        edtTruaMon = findViewById(R.id.edtTrua);
        edtTruaCalo = findViewById(R.id.edtCaloTrua);
        edtToiMon = findViewById(R.id.edtToi);
        edtToiCalo = findViewById(R.id.edtCaloToi);
        btnLuu = findViewById(R.id.btnLuuThucAn);

        btnLuu.setOnClickListener(v -> {
            String ngay = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

            String sang = edtSangMon.getText() + " (" + edtSangCalo.getText() + " kcal)";
            String trua = edtTruaMon.getText() + " (" + edtTruaCalo.getText() + " kcal)";
            String toi = edtToiMon.getText() + " (" + edtToiCalo.getText() + " kcal)";

            int tong = 0;
            try {
                tong = Integer.parseInt(edtSangCalo.getText().toString())
                        + Integer.parseInt(edtTruaCalo.getText().toString())
                        + Integer.parseInt(edtToiCalo.getText().toString());
            } catch (Exception e) {}

            thucanngay item = new thucanngay(ngay, sang, trua, toi, tong);

            Intent intent = new Intent();
            intent.putExtra("thucAn", item);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
