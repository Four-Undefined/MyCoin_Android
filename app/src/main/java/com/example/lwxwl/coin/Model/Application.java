package com.example.lwxwl.coin.Model;

public class Application extends android.app.Application {

    public static String storedUsername;
    public static String storedUserToken;
    public static int storedUserAddBudget;
    public static int storedUserBudget;
    public static int storedUserMonth;
    public static int storedUserDay;


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

    public void getUserBudget(int budget) {
        storedUserBudget = budget;
    }

    public int setUserBudget() {
        return storedUserBudget;
    }

    public void getUserMonth(int month) {
        storedUserMonth = month;
    }

    public int setUserMonth() {
        return storedUserMonth;
    }

    public void getUserDate(int day) {
        storedUserDay = day;
    }

    public int setUserDate() {
        return storedUserDay;
    }
}
