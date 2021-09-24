package com.example.ecoffe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    TextView tv_ready,tv_ready2,tv_ready3,tv_ready4,tv_ready5,tv_ready6,tv_ready7,tv_ready8;
    Intent intent;
    User user;

    protected void onCreate(Bundle savedInstanceStare) {

        intent = getIntent();
        user = (User) intent.getSerializableExtra("user");    //바뀐 User정보 update필요 (스탬프, 잔액, 쿠폰 등)


        super.onCreate(savedInstanceStare);
        setContentView(R.layout.activity_ready);

        updateUserInfo();

        tv_ready = (TextView) findViewById(R.id.tv_ready);  //화면 바뀌는 동작
        tv_ready2 = (TextView) findViewById(R.id.tv_ready2);
        tv_ready3 = (TextView) findViewById(R.id.tv_ready3);
        tv_ready4 = (TextView) findViewById(R.id.tv_ready4);
        tv_ready5 = (TextView) findViewById(R.id.tv_ready5);
        tv_ready6 = (TextView) findViewById(R.id.tv_ready6);
        tv_ready7 = (TextView) findViewById(R.id.tv_ready7);
        tv_ready8 = (TextView) findViewById(R.id.tv_ready8);


        AlphaAnimation anim8 = new AlphaAnimation(0, 1);
        anim8.setDuration(500);    // 에니메이션이 0.5초동안 작동
        anim8.setStartOffset(4000);//4초 뒤에 애니메이션 실행
        tv_ready8.startAnimation(anim8);

        //tv_ready2.setVisibility(View.INVISIBLE);
        AlphaAnimation anim7 = new AlphaAnimation(0, 1);
        anim7.setDuration(500);
        anim7.setStartOffset(3500);
        tv_ready7.startAnimation(anim7);

        AlphaAnimation anim6 = new AlphaAnimation(0, 1);
        anim6.setDuration(500);
        anim6.setStartOffset(3000);
        tv_ready6.startAnimation(anim6);

        AlphaAnimation anim5 = new AlphaAnimation(0, 1);
        anim5.setDuration(500);
        anim5.setStartOffset(2500);
        tv_ready5.startAnimation(anim5);

        AlphaAnimation anim4 = new AlphaAnimation(0, 1);
        anim4.setDuration(500);
        anim4.setStartOffset(2000);
        tv_ready4.startAnimation(anim4);

        AlphaAnimation anim3 = new AlphaAnimation(0, 1);
        anim3.setDuration(500);
        anim3.setStartOffset(1500);
        tv_ready3.startAnimation(anim3);

        AlphaAnimation anim2 = new AlphaAnimation(0, 1);
        anim2.setDuration(500);
        anim2.setStartOffset(1000);
        tv_ready2.startAnimation(anim2);

        AlphaAnimation anim = new AlphaAnimation(0, 1);
        anim.setDuration(500);
        anim.setStartOffset(500);
        tv_ready.startAnimation(anim);






        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_ready8.startAnimation(anim8);
                tv_ready7.startAnimation(anim7);
                tv_ready6.startAnimation(anim6);
                tv_ready5.startAnimation(anim5);
                tv_ready4.startAnimation(anim4);
                tv_ready3.startAnimation(anim3);
                tv_ready2.startAnimation(anim2);
                tv_ready.startAnimation(anim);

            }
        }, 5000); //5초뒤에 실행되는 쓰레드

//총 애니메이션 소요시간 9~10초 정도




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(ReadyActivity.this, MainActivity.class); //화면 전환
                startActivity(intent);
                finish();
            }
        }, 10000);

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
