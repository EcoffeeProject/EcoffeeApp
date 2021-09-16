package com.example.ecoffe;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private  String userID;
    private  String userPassword;
    private int balacne;
    private int stamp;
    private int coupon;

    User(String userID,String userPassword){
        this.userID = userID;
        this.userPassword = userPassword;
     }

     void setInfo(int balacne, int stamp, int coupon){
        this.balacne = balacne;
        this.stamp = stamp;
        this.coupon = coupon;

     }

    void addStamp(){
        if(this.stamp == 8){
            this.stamp = 0;
            addCoupon();
        }

        this.stamp +=1;


    }
    void addCoupon(){
        this.coupon +=1;

    }
    boolean useCoupon(){

        if(this.coupon >0) {
            this.coupon -= 1;
            return true; //쿠폰사용 완료
        }
        else  return false; //쿠폰사용 안됨

    }
    void deposit(int money){
        this.balacne += money;
    }

    boolean pay(int money){
        if(this.balacne >= money){
        this.balacne -= money;
        return true; //돈 무사히 결제됨
        }
else return false; //돈없어서 결제안됨
    }



    String getUserID(){
        return this.userID;
    }
    String getUserPassword(){
        return this.userPassword;
    }
    int getBalance(){
        return  this.balacne;
    }
    int getStamp(){
        return  this.stamp;
    }
    int getCoupon(){
        return  this.coupon;
    }



}
