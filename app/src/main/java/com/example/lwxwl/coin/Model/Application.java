package com.example.lwxwl.coin.Model;

public class Application extends android.app.Application {

    public static String storedUsername;
    public static String storedUserToken;
    public static int storedUserAddBudget;
    public static int storedUserMonth;
    public static int storedUserDate;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getUserText(String call) {
        storedUsername = call;
    }
    public String setUserText(){
        return storedUsername;
    }
    public void getUserToken(String response){
        storedUserToken = response;
    }

    public String setUserToken(){
        return  storedUserToken;
    }

    public void getUserAddBudget(int budget) {
        storedUserAddBudget = budget;
    }

    public int setUserAddBudget() {
        return storedUserAddBudget;
    }

    public void getUserMonth(int month) {
        storedUserMonth = month;
    }

    public int setUserMonth() {
        return storedUserMonth;
    }

    public void getUserDate(int date) {
        storedUserDate = date;
    }

    public int setUserDate() {
        return storedUserDate;
    }
}
