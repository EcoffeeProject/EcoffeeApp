package com.example.ecoffe;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private  String userID;
    private  String userPassword;
    private int balacne;
    private int stamp;
    private int coupon=10;

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
        this.stamp +=1;

        if(this.stamp == 8){
            this.stamp = 0;
            addCoupon();
        }


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
    }  //충전하기

    void subBalance(int money){ //결제후 잔액 줄여주기
        this.balacne+=money;
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
