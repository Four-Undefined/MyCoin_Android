package com.example.lwxwl.coin.Model;

public class LoginUser {

    public String username;
    public String password;
    public int day;
    public int month;

    public LoginUser(String username, String password, int month, int day){
        this.username = username;
        this.password = password;
        this.day = day;
        this.month = month;
    }
}
