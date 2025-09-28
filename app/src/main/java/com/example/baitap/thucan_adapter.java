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

public class thucan_adapter extends RecyclerView.Adapter<thucan_adapter.ThucAnViewHolder> {
    private Context context;
    private ArrayList<thucanngay> list;

    public thucan_adapter(Context context, ArrayList<thucanngay> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ThucAnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_thucan, parent, false);
        return new ThucAnViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ThucAnViewHolder holder, int position) {
        thucanngay item = list.get(position);

        holder.txtNgay.setText("Ngày: " + item.getNgay());
        holder.txtSang.setText("Sáng: " + item.getSang());
        holder.txtTrua.setText("Trưa: " + item.getTrua());
        holder.txtToi.setText("Tối: " + item.getToi());
        holder.txtTongCalo.setText("Tổng: " + item.getTongCalo() + " kcal");
        // 🔹 Nhấn giữ để xóa item
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa ngày " + item.getNgay())
                    .setMessage("Bạn có chắc chắn muốn xóa toàn bộ ngày này?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        list.remove(position);
                        notifyItemRemoved(position);

                        // lưu lại sau khi xóa
                        save_thucan.saveList(context, list);
                    })
                    .setNegativeButton("Không", null)
                    .show();
            return true; // trả về true để xác nhận đã xử lý long click
        });

        // XÓA bữa sáng
        holder.btnXoaSang.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa bữa sáng")
                    .setMessage("Bạn có chắc chắn muốn xóa bữa sáng này?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        item.setSang("(đã xóa)");
                        item.setTongCalo(tinhTong(item));
                        notifyItemChanged(position);

                        // 🔹 Lưu lại thay đổi
                        save_thucan.saveList(context, list);
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });

        // XÓA bữa trưa
        holder.btnXoaTrua.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa bữa trưa")
                    .setMessage("Bạn có chắc chắn muốn xóa bữa trưa này?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        item.setTrua("(đã xóa)");
                        item.setTongCalo(tinhTong(item));
                        notifyItemChanged(position);

                        save_thucan.saveList(context, list);
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });

        // XÓA bữa tối
        holder.btnXoaToi.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa bữa tối")
                    .setMessage("Bạn có chắc chắn muốn xóa bữa tối này?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        item.setToi("(đã xóa)");
                        item.setTongCalo(tinhTong(item));
                        notifyItemChanged(position);

                        save_thucan.saveList(context, list);
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });

        // SỬA bữa sáng
        holder.btnSuaSang.setOnClickListener(v -> showEditDialog(item, "Sáng", position));
        holder.btnSuaTrua.setOnClickListener(v -> showEditDialog(item, "Trưa", position));
        holder.btnSuaToi.setOnClickListener(v -> showEditDialog(item, "Tối", position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void showEditDialog(thucanngay item, String buoi, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sửa bữa " + buoi);

        View viewInflated = LayoutInflater.from(context).inflate(R.layout.sua_bua, null);
        EditText edtMon = viewInflated.findViewById(R.id.edtMon);
        EditText edtCalo = viewInflated.findViewById(R.id.edtCalo);

        builder.setView(viewInflated);

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String mon = edtMon.getText().toString();
            int calo = 0;
            try {
                calo = Integer.parseInt(edtCalo.getText().toString());
            } catch (NumberFormatException e) {
                calo = 0;
            }
            String text = mon + " (" + calo + " kcal)";

            if (buoi.equals("Sáng")) {
                item.setSang(text);
            } else if (buoi.equals("Trưa")) {
                item.setTrua(text);
            } else if (buoi.equals("Tối")) {
                item.setToi(text);
            }

            item.setTongCalo(tinhTong(item));
            notifyItemChanged(position);

            // 🔹 Lưu lại thay đổi
            save_thucan.saveList(context, list);
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private int tinhTong(thucanngay item) {
        return layCalo(item.getSang()) + layCalo(item.getTrua()) + layCalo(item.getToi());
    }

    private int layCalo(String text) {
        if (text == null) return 0;
        try {
            int start = text.indexOf("(");
            int end = text.indexOf("kcal");
            if (start >= 0 && end > start) {
                String number = text.substring(start + 1, end).trim();
                return Integer.parseInt(number);
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    public static class ThucAnViewHolder extends RecyclerView.ViewHolder {
        TextView txtNgay, txtSang, txtTrua, txtToi, txtTongCalo;
        ImageButton btnSuaSang, btnXoaSang, btnSuaTrua, btnXoaTrua, btnSuaToi, btnXoaToi;

        public ThucAnViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtSang = itemView.findViewById(R.id.txtSang);
            txtTrua = itemView.findViewById(R.id.txtTrua);
            txtToi = itemView.findViewById(R.id.txtToi);
            txtTongCalo = itemView.findViewById(R.id.txtTongCalo);

            btnSuaSang = itemView.findViewById(R.id.btnSuaSang);
            btnXoaSang = itemView.findViewById(R.id.btnXoaSang);
            btnSuaTrua = itemView.findViewById(R.id.btnSuaTrua);
            btnXoaTrua = itemView.findViewById(R.id.btnXoaTrua);
            btnSuaToi = itemView.findViewById(R.id.btnSuaToi);
            btnXoaToi = itemView.findViewById(R.id.btnXoaToi);
        }
    }
}
