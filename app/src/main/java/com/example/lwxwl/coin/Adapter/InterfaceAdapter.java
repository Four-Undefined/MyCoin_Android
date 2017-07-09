package com.example.lwxwl.coin.Adapter;

import com.example.lwxwl.coin.Model.Accounting;
import com.example.lwxwl.coin.Model.AccountingUser;
import com.example.lwxwl.coin.Model.AddBudget;
import com.example.lwxwl.coin.Model.AddBudgetUser;
import com.example.lwxwl.coin.Model.Budget;
import com.example.lwxwl.coin.Model.BudgetUser;
import com.example.lwxwl.coin.Model.Cost;
import com.example.lwxwl.coin.Model.Login;
import com.example.lwxwl.coin.Model.LoginUser;
import com.example.lwxwl.coin.Model.Profile;
import com.example.lwxwl.coin.Model.Register;
import com.example.lwxwl.coin.Model.RegisterUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface InterfaceAdapter {


    @POST("api/signup/")
    Call<Register> getRegisterInfo(@Body RegisterUser user);

    @GET("api/show_profile/")
    Call<Profile> getProfile(@Header("token")String token);

    @POST("api/signin/")
    Call<Login> getUserToken(@Body LoginUser user);

    @POST("api/add_account/")
    Call<Accounting> getAccounting(@Body AccountingUser accountingUser, @Header("token")String token);

    @POST("api/budget/")
    Call<AddBudget> getAddBudget(@Body AddBudgetUser addBudgetUser, @Header("token")String token);

    @POST("api/view_budget/")
    Call<Budget> getUserBudget(@Body BudgetUser budgetUser, @Header("token")String token);

    @GET("api/get_one/{month}/")
    Call<Cost> getCost(@Path("month")int month, @Header("token")String token);

}

/*
注册:
        URL； api/signup/
        Post data : {
             “username” : string
             “password”  string
        }
        Return data {
             “create” : id
        }
*/

/*
登陆:
        Url :  api/signin/
        Post data  : {
             “username” :
             “password” :
             “day” : int
	         “month” : int
        }
        Return data : {
             “token” : string
        }
*/

/*记账 ：
        Url : api/add_account/       post
        Headers : token
        Post data {
             “edu” : int
             “trip” : int
             “diet” : int
             “normal” : int
             “clothes” : int
             “enter” : int
             “month” : int
             “day” : int
        }
        Return data  {
             “id” : int
             “sum” : int
             “date” : string
        } ,200
*/

/*
加预算 ：
        URL ：api/budget/       post
        Headers  : token
        Post data {
             “month” :  int
             “budget” : int
        }
        return data {
	         “month” : int ,
	         “budget” : int ,
	         “user_id” : int
        } 200
*/

/*
查看预算:
        URL： api/view_budget/      post
        Headers : token
        Post data {
	         "month" : int
        }
        return data {
	         “month” : int ,
	         “budget” : int ,
	         “user_id” : int
        } , 200
*/

/*
查看当月支出:
        Url : api/get_one/<int:month>/    get
        Headers : token
        Return data {
	         "sum" : int
        }
*/