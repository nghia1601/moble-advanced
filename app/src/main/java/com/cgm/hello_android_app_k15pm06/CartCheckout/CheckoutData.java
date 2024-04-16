package com.cgm.hello_android_app_k15pm06.CartCheckout;

import com.cgm.hello_android_app_k15pm06.Cart.CartItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckoutData {

    @SerializedName("hoTenKH")
    private String hoTenKH;

    @SerializedName("sdt")
    private String sdt;

    @SerializedName("diaChi")
    private String diaChi;

    @SerializedName("pTTT")
    private String pTTT;

    @SerializedName("tongTien")
    private double tongTien;

    @SerializedName("chitiethoadon")
    private List<CartItemInfo> chitiethoadon;

    public CheckoutData(String hoTenKH, String sdt, String diaChi, String pTTT, double tongTien, List<CartItemInfo> chitiethoadon) {
        this.hoTenKH = hoTenKH;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.pTTT = pTTT;
        this.tongTien = tongTien;
        this.chitiethoadon = chitiethoadon;
    }


    //checkout cart
    public void setCartItems(List<CartItemInfo> cartItems) {
        this.chitiethoadon = cartItems;
    }

    public String getHoTenKH() {
        return hoTenKH;
    }

    public void setHoTenKH(String hoTenKH) {
        this.hoTenKH = hoTenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getpTTT() {
        return pTTT;
    }

    public void setpTTT(String pTTT) {
        this.pTTT = pTTT;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public List<CartItemInfo> getChitiethoadon() {
        return chitiethoadon;
    }

    public void setChitiethoadon(List<CartItemInfo> chitiethoadon) {
        this.chitiethoadon = chitiethoadon;
    }
}
