package com.example.ecoffe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DepositActivity extends AppCompatActivity implements View.OnClickListener,Serializable {
    Intent intent;
    User user;

    EditText et_money;
    TextView tv_money;
    Button btn_deposit;
    int depositMoney = 0;

    protected void onCreate(Bundle savedInstanceStare) {
        intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        super.onCreate(savedInstanceStare);
        setContentView(R.layout.activity_deposit);

        et_money = (EditText) findViewById(R.id.et_money);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_money.setText(String.valueOf(user.getBalance())+"원");

        btn_deposit = (Button)findViewById(R.id.btn_deposit);
        btn_deposit.setOnClickListener(this);

        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //입력하기 전

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //EditText에 변화가 있을 때
                depositMoney = Integer.parseInt(et_money.getText().toString());
                tv_money.setText(String.valueOf(user.getBalance() + depositMoney)+"원");

            }

            @Override
            public void afterTextChanged(Editable s) {
                //입력이 끝났을 때

            }
        });








    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_deposit:
                //사용자가 작성한 금액 충전하기

                user.deposit(depositMoney);



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
                RequestQueue queue = Volley.newRequestQueue(DepositActivity.this);
                queue.add(InfoUpdateRequest);


                Toast.makeText(getApplicationContext(), "충전이 완료되었습니다.", Toast.LENGTH_SHORT).show();


                //user.addCar(carNum);
                //intent.putExtra("user",user);
                //결제하기 버튼 클릭시 메인 페이지로 이동
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);

                break;
        }
    }



    }
