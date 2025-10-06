package com.example.baitap;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class thucan_adapter extends RecyclerView.Adapter<thucan_adapter.ViewHolder> {
    private Context context;
    private ArrayList<thucanngay> list;

    public thucan_adapter(Context context, ArrayList<thucanngay> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_thucan, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        thucanngay item = list.get(position);

        holder.txtNgay.setText("Ngày: " + item.getNgay());
        holder.txtSang.setText(item.getSang());
        holder.txtTrua.setText(item.getTrua());
        holder.txtToi.setText(item.getToi());
        holder.txtTong.setText("Tổng: " + item.getTongCalo() + " kcal");

        // Nhấn giữ để xoá toàn bộ ngày
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa ngày " + item.getNgay())
                    .setMessage("Bạn có chắc chắn muốn xóa toàn bộ ngày này không?")
                    .setPositiveButton("Có", (d, w) -> {
                        list.remove(position);
                        notifyItemRemoved(position);
                        save_thucan.saveList(context, list);
                    })
                    .setNegativeButton("Không", null)
                    .show();
            return true;
        });

        // --- Nút sửa & xóa từng bữa ---
        holder.btnSuaSang.setOnClickListener(v -> suaBua(item, "Sáng", position));
        holder.btnSuaTrua.setOnClickListener(v -> suaBua(item, "Trưa", position));
        holder.btnSuaToi.setOnClickListener(v -> suaBua(item, "Tối", position));

        holder.btnXoaSang.setOnClickListener(v -> xoaBua(item, "Sáng", position));
        holder.btnXoaTrua.setOnClickListener(v -> xoaBua(item, "Trưa", position));
        holder.btnXoaToi.setOnClickListener(v -> xoaBua(item, "Tối", position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // ----- HÀM XỬ LÝ -----
    private void suaBua(thucanngay item, String bua, int pos) {
        View view = LayoutInflater.from(context).inflate(R.layout.sua_bua, null);
        EditText edtTen = view.findViewById(R.id.edtTenMonSua);
        EditText edtCalo = view.findViewById(R.id.edtCaloSua);

        new AlertDialog.Builder(context)
                .setView(view)
                .setTitle("Sửa bữa " + bua)
                .setPositiveButton("Lưu", (d, w) -> {
                    String ten = edtTen.getText().toString();
                    int calo = 0;
                    try { calo = Integer.parseInt(edtCalo.getText().toString()); } catch (Exception e) {}

                    String mon = ten + " (" + calo + " kcal)";
                    if (bua.equals("Sáng")) item.setSang(mon);
                    if (bua.equals("Trưa")) item.setTrua(mon);
                    if (bua.equals("Tối")) item.setToi(mon);
                    item.setTongCalo(tinhTong(item));
                    notifyItemChanged(pos);
                    save_thucan.saveList(context, list);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void xoaBua(thucanngay item, String bua, int pos) {
        new AlertDialog.Builder(context)
                .setTitle("Xóa bữa " + bua)
                .setMessage("Bạn có chắc chắn muốn xóa bữa " + bua + " này không?")
                .setPositiveButton("Có", (d, w) -> {
                    if (bua.equals("Sáng")) item.setSang("(đã xóa)");
                    if (bua.equals("Trưa")) item.setTrua("(đã xóa)");
                    if (bua.equals("Tối")) item.setToi("(đã xóa)");
                    item.setTongCalo(tinhTong(item));
                    notifyItemChanged(pos);
                    save_thucan.saveList(context, list);
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private int tinhTong(thucanngay item) {
        return layCalo(item.getSang()) + layCalo(item.getTrua()) + layCalo(item.getToi());
    }

    private int layCalo(String s) {
        if (s == null) return 0;
        try {
            int start = s.indexOf("(");
            int end = s.indexOf("kcal");
            if (start >= 0 && end > start) {
                String num = s.substring(start + 1, end).trim();
                return Integer.parseInt(num);
            }
        } catch (Exception ignored) {}
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNgay, txtSang, txtTrua, txtToi, txtTong;
        ImageButton btnSuaSang, btnXoaSang, btnSuaTrua, btnXoaTrua, btnSuaToi, btnXoaToi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtSang = itemView.findViewById(R.id.txtSang);
            txtTrua = itemView.findViewById(R.id.txtTrua);
            txtToi = itemView.findViewById(R.id.txtToi);
            txtTong = itemView.findViewById(R.id.txtTongCalo);

            btnSuaSang = itemView.findViewById(R.id.btnSuaSang);
            btnXoaSang = itemView.findViewById(R.id.btnXoaSang);
            btnSuaTrua = itemView.findViewById(R.id.btnSuaTrua);
            btnXoaTrua = itemView.findViewById(R.id.btnXoaTrua);
            btnSuaToi = itemView.findViewById(R.id.btnSuaToi);
            btnXoaToi = itemView.findViewById(R.id.btnXoaToi);
        }
    }
}
