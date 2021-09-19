package com.example.ecoffe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class ReadyActivity extends AppCompatActivity implements  Serializable {
TextView tv_ready;
    Intent intent;
    User user;

    protected void onCreate(Bundle savedInstanceStare) {
        super.onCreate(savedInstanceStare);
        setContentView(R.layout.activity_ready);


        tv_ready = (TextView) findViewById(R.id.tv_ready);
        //tv_ready.setText(String.valueOf(user.getBalance())+"원");





                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv_ready.setText("음");

                            //딜레이 후 시작할 코드 작성
                        }
                    }, 1000);// 0.5초 정도 딜레이를 준 후 시작
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv_ready.setText("음료");
                            //딜레이 후 시작할 코드 작성
                        }
                    }, 1000);// 0.5초 정도 딜레이를 준 후 시작
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv_ready.setText("음료준");
                            //딜레이 후 시작할 코드 작성
                        }
                    }, 1000);// 0.5초 정도 딜레이를 준 후 시작
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv_ready.setText("음료준비");
                            //딜레이 후 시작할 코드 작성
                        }
                    }, 1000);// 0.5초 정도 딜레이를 준 후 시작
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv_ready.setText("음료준비중");
                            //딜레이 후 시작할 코드 작성
                        }
                    }, 1000);// 0.5초 정도 딜레이를 준 후 시작

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv_ready.setText("음료준비중.");
                            //딜레이 후 시작할 코드 작성
                        }
                    }, 1000);// 0.5초 정도 딜레이를 준 후 시작

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv_ready.setText("음료준비중..");
                            //딜레이 후 시작할 코드 작성
                        }
                    }, 500);// 0.5초 정도 딜레이를 준 후 시작

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv_ready.setText("음료준비중...");
                            //딜레이 후 시작할 코드 작성
                        }
                    }, 1000);// 0.5초 정도 딜레이를 준 후 시작

                //음료가 다 준비되었다면 메인으로 화면전환?
                //Intent intent = new Intent(ReadyActivity.this, MainActivity.class); //화면 전환
               // startActivity(intent);
               // finish();
            }




}
