
    package com.example.baitap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

    public class ThucAn_adapter extends BaseAdapter {

        private Context context;
        private List<ThucAn> list;
        private database dbHelper;

        public ThucAn_adapter(Context context, List<ThucAn> list) {
            this.context = context;
            this.list = list;
            this.dbHelper = new database(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_thucan, parent, false);
            }

            ThucAn thucAn = list.get(position);

            TextView tvTen = view.findViewById(R.id.tvTen);
            TextView tvBuaAn = view.findViewById(R.id.tvBuaAn);
            TextView tvCalo = view.findViewById(R.id.tvCalo);
            Button btnSua = view.findViewById(R.id.btnSua);

            tvTen.setText(thucAn.getTenThucAn());
            tvBuaAn.setText(thucAn.getBuaAn());
            tvCalo.setText(thucAn.getCaloHapThu() + " kcal");

            // Khi nhấn nút sửa
            btnSua.setOnClickListener(v -> {
                Intent intent = new Intent(context, ThucAn_sua.class);
                intent.putExtra("id", thucAn.getId());
                intent.putExtra("ten", thucAn.getTenThucAn());
                intent.putExtra("buaan", thucAn.getBuaAn());
                intent.putExtra("calo", thucAn.getCaloHapThu());
                context.startActivity(intent);
            });

            // Khi nhấn giữ để xóa
            view.setOnLongClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("Xóa món ăn")
                        .setMessage("Bạn có chắc muốn xóa món ăn này không?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            db.delete("thucan", "id=?", new String[]{String.valueOf(thucAn.getId())});
                            list.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Đã xóa món ăn", Toast.LENGTH_SHORT).show();

                            // 🟩 Gọi cập nhật tổng calo
                            if (context instanceof ThucAn_activity) {
                                ((ThucAn_activity) context).updateTongCalo();
                            }
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
                return true;
            });


            return view;
        }
    }


