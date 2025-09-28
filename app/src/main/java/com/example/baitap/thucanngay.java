package com.example.baitap;

import java.io.Serializable;
import java.io.Serializable;

public class thucanngay implements Serializable {
    private String ngay;
    private String sang;
    private String trua;
    private String toi;
    private int tongCalo;

    public thucanngay(String ngay, String sang, String trua, String toi, int tongCalo) {
        this.ngay = ngay;
        this.sang = sang;
        this.trua = trua;
        this.toi = toi;
        this.tongCalo = tongCalo;
    }

    // Getter
    public String getNgay() {
        return ngay;
    }

    public String getSang() {
        return sang;
    }

    public String getTrua() {
        return trua;
    }

    public String getToi() {
        return toi;
    }

    public int getTongCalo() {
        return tongCalo;
    }

    // Setter nếu cần
    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public void setSang(String sang) {
        this.sang = sang;
    }

    public void setTrua(String trua) {
        this.trua = trua;
    }

    public void setToi(String toi) {
        this.toi = toi;
    }

    public void setTongCalo(int tongCalo) {
        this.tongCalo = tongCalo;
    }
}
