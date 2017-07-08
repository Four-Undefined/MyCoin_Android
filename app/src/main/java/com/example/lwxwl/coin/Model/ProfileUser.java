package com.example.lwxwl.coin.Model;

/**
 * Created by lwxwl on 2017/7/7.
 */

public class ProfileUser {

    public String username;
    public int userAvatar;

    public ProfileUser(String username, int userAvatar){
        this.username = username;
        this.userAvatar = userAvatar;
    }
    public String getUsername(){
        return  username;
    }
    public int getUserAvatar(){
        return userAvatar;
    }
}
