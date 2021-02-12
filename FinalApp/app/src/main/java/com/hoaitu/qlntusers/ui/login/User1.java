package com.hoaitu.qlntusers.ui.login;

public class User1 {

    public String name , age , email;
    public int level = 1;

    public User1(String name, String email, int level) {
        this.name = name;
        this.email = email;
        this.level = level;
    }

    public User1(){
    }

    public User1(String name, String age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
    public User1(String name,  String email) {
        this.name = name;
        this.email = email;
    }
//
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
