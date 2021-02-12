package com.hoaitu.qlntusers.model;

public class Motel {
    private  String name;
    private String age;
    private String sex;
    private String phone;
    private String tinh;
    private String huyen;
    private String address;
    private int price;
    private Long datest;
    private Long datelt;
    private String title;
    private String loaitin;
    private String img;
    private boolean view;

    public Motel() {
    }

    public Motel(String name, String age, String sex, String phone,String tinh,String huyen, String address, int price, Long datest, Long datelt, String title, String loaitin, String img,boolean view) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.phone = phone;
        this.tinh = tinh;
        this.huyen = huyen;
        this.address = address;
        this.price = price;
        this.datest = datest;
        this.datelt = datelt;
        this.title = title;
        this.loaitin = loaitin;
        this.img = img;
        this.view = view;
    }

    public Motel(String img, String title, String address) {
        this.img = img;
        this.title = title;
        this.address = address;
    }

    public String getTinh() {
        return tinh;
    }

    public void setTinh(String tinh) {
        this.tinh = tinh;
    }

    public String getHuyen() {
        return huyen;
    }

    public void setHuyen(String huyen) {
        this.huyen = huyen;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getDatest() {
        return datest;
    }

    public void setDatest(Long datest) {
        this.datest = datest;
    }

    public Long getDatelt() {
        return datelt;
    }

    public void setDatelt(Long datelt) {
        this.datelt = datelt;
    }

    public String getLoaitin() {
        return loaitin;
    }

    public void setLoaitin(String loaitin) {
        this.loaitin = loaitin;
    }

    public boolean isView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }

    @Override
    public String toString(){
        return title+" "+address;
    }
}

