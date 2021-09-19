package com.example.ecoffe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ReadyActivity extends AppCompatActivity implements  Serializable {
    TextView tv_ready;
    Intent intent;
    User user;

    protected void onCreate(Bundle savedInstanceStare) {

        intent = getIntent();
        user = (User) intent.getSerializableExtra("user");    //바뀐 User정보 update필요 (스탬프, 잔액, 쿠폰 등)

        super.onCreate(savedInstanceStare);
        setContentView(R.layout.activity_ready);

        updateUserInfo();

        tv_ready = (TextView) findViewById(R.id.tv_ready);  //화면 바뀌는 동작


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_ready.setText("음");
                //딜레이 후 시작할 코드 작성
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_ready.setText("음료");
                //딜레이 후 시작할 코드 작성
            }
        }, 1000);
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
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_ready.setText("음료준비중");
                //딜레이 후 시작할 코드 작성
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_ready.setText("음료준비중.");
                //딜레이 후 시작할 코드 작성
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_ready.setText("음료준비중..");
                //딜레이 후 시작할 코드 작성
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_ready.setText("음료준비중...");
                //딜레이 후 시작할 코드 작성

                Intent intent = new Intent(ReadyActivity.this, MainActivity.class); //화면 전환
                startActivity(intent);
                finish();
            }
        }, 1000);

    }

    private void updateUserInfo() {

        //충전한 금액 서버로 보내기
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 성공한 경우

                    } else {// 실패한 경우
                        Toast.makeText(getApplicationContext(), "실패하였습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        UpdateInfoRequest InfoUpdateRequest = new UpdateInfoRequest(user.getUserID(), user.getUserPassword(),user.getBalance(),user.getStamp(),user.getCoupon(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(ReadyActivity.this);
        queue.add(InfoUpdateRequest);

        Toast.makeText(getApplicationContext(), "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show();


    }


}
