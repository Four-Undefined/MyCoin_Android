package com.example.lwxwl.coin.Model;

public class LoginUser {

    public String username;
    public String password;
    public int date;
    public int month;

    public LoginUser(String username, String password, int month, int date){
        this.username = username;
        this.password = password;
        this.date = date;
        this.month = month;
    }
}
