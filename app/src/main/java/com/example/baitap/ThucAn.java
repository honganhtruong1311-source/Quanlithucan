package com.example.baitap;


    public class ThucAn {
        private int id;
        private String tenThucAn;
        private String buaAn;
        private int caloHapThu;
        private String ngay;

        public ThucAn(int id, String tenThucAn, String buaAn, int caloHapThu, String ngay) {
            this.id = id;
            this.tenThucAn = tenThucAn;
            this.buaAn = buaAn;
            this.caloHapThu = caloHapThu;
            this.ngay = ngay;
        }

        public int getId() { return id; }
        public String getTenThucAn() { return tenThucAn; }
        public String getBuaAn() { return buaAn; }
        public int getCaloHapThu() { return caloHapThu; }
        public String getNgay() { return ngay; }

        public void setTenThucAn(String tenThucAn) { this.tenThucAn = tenThucAn; }
        public void setBuaAn(String buaAn) { this.buaAn = buaAn; }
        public void setCaloHapThu(int caloHapThu) { this.caloHapThu = caloHapThu; }
    }


