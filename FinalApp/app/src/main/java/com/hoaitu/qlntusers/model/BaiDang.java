package com.hoaitu.qlntusers.model;

public class BaiDang {
    private String tieude;
    private String dientich;
    private String mota;
    private String sex;
    private String phone;
    private String tinh;
    private String huyen;
    private String address;
    private String price;
    private String datest;
    private String datelt;
    private String title;
    private String loaitin;
    private String img;
    private boolean view = true;

    public BaiDang() {
    }

    public BaiDang(String mtieude, String mmota, String msdt, String mdiachi, String mgia, String mdientich, String mngay, String st_gioitinh, String st_huyen, String st_tinh, String st_ngay, String st_hinhthuc, String st_loaitin, String mimage) {
        this.tieude = mtieude;
        this.dientich = mdientich;
        this.mota = mmota;
        this.sex = st_gioitinh;
        this.phone = msdt;
        this.tinh = st_tinh;
        this.huyen = st_huyen;
        this.address = mdiachi;
        this.price = mgia;
        this.datest = mngay;
        this.datelt = st_ngay;
        this.title = st_hinhthuc;
        this.loaitin = st_loaitin;
        this.img = mimage;
    }

    public BaiDang(String mtieude, String mmota, String msdt, String mdiachi, String mgia, String mdientich, String st_huyen, String st_tinh, String st_hinhthuc, String mimage) {
        this.tieude = mtieude;
        this.dientich = mdientich;
        this.mota = mmota;
        this.phone = msdt;
        this.tinh = st_tinh;
        this.huyen = st_huyen;
        this.address = mdiachi;
        this.price = mgia;
        this.title = st_hinhthuc;
        this.img = mimage;
    }

//    public BaiDang(String tieude, String dientich, String mota, String sex, String phone, String tinh, String huyen, String address, String price, String datest, String datelt, String title, String loaitin, String img) {
//        this.tieude = tieude;
//        this.dientich = dientich;
//        this.mota = mota;
//        this.sex = sex;
//        this.phone = phone;
//        this.tinh = tinh;
//        this.huyen = huyen;
//        this.address = address;
//        this.price = price;
//        this.datest = datest;
//        this.datelt = datelt;
//        this.title = title;
//        this.loaitin = loaitin;
//        this.img = img;
//    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTieude() {
        return tieude;
    }

    public String getDientich() {
        return dientich;
    }

    public String getMota() {
        return mota;
    }

    public String getPhone() {
        return phone;
    }

    public String getTinh() {
        return tinh;
    }

    public String getHuyen() {
        return huyen;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String  getDatest() {
        return datest;
    }

    public String  getDatelt() {
        return datelt;
    }

    public String getTitle() {
        return title;
    }

    public String getLoaitin() {
        return loaitin;
    }

    public String getImg() {
        return img;
    }

    public boolean isView() {
        return view;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public void setDientich(String dientich) {
        this.dientich = dientich;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTinh(String tinh) {
        this.tinh = tinh;
    }

    public void setHuyen(String huyen) {
        this.huyen = huyen;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDatest(String datest) {
        this.datest = datest;
    }

    public void setDatelt(String datelt) {
        this.datelt = datelt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLoaitin(String loaitin) {
        this.loaitin = loaitin;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setView(boolean view) {
        this.view = view;
    }
}
