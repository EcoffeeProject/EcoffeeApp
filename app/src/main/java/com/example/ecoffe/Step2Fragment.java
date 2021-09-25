package com.example.ecoffe;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

public class Step2Fragment extends Fragment {

    TextView step2_waiting, papercup_question, papercup_get,step2;
    int step1_result=Infomation.Nothing;
    ImageView highlight;
    static public CountDownTimer timer;
    BluetoothHelper mBluetooth= Step1Fragment.mBluetooth;

    public Step2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.order_step2, container, false);
        step2_waiting= view.findViewById(R.id.step2_waiting);
        highlight=view.findViewById(R.id.highlight);
        papercup_question= view.findViewById(R.id.papercup_question);
        papercup_get= view.findViewById(R.id.papercup_get);
        step2=view.findViewById(R.id.step2);

        papercup_get.setOnClickListener(new View.OnClickListener() {
            int cnt=0;
            @Override
            public void onClick(View v) {
                if(cnt==0){
                    mBluetooth.SendMessage("Get"); //종이컵 뽑기 메시지 보냄
                    Log.d("tag","Get신호 보냄");
                }
                cnt++;
            }
        });

        if(getArguments()!=null) {

            if (getArguments().getInt("step2_finish") == 0) {

                Log.d("tag","step2_finish"+ getArguments().getInt("step2_finish")) ;
                highlight.setVisibility(View.VISIBLE);
                step1_result = getArguments().getInt("step1_result"); // step1에서 받아온 값 넣기

                if (step1_result == Infomation.ConnectSuccess) {

                    Log.d("tag", "ConnectSuccess in step2");

                    step2_waiting.setText("대기시간: 30초");
                    timer = new CountDownTimer(30000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            step2_waiting.setText("대기시간: " + millisUntilFinished / 1000 + "초");
                        }

                        public void onFinish() {
                            step2_waiting.setText("대기 시간 종료");
                        }
                    }.start();

                    mBluetooth.SendMessage("Check"); //무게 체크 메시지 보냄
                    Log.d("tag", "Check신호 보냄");


                } else  //연결 실패
                {
                    step2_waiting.setText("연결에 실패하였습니다. 다시 시도해 주십시오");
                }

            }else{

                Log.d("tag","step2_finish"+ getArguments().getInt("step2_finish"));
                step2_waiting.setTextColor(0xFF428681); //초록
                step2_waiting.setText("무게가 측정되었습니다");
                highlight.setVisibility(View.INVISIBLE);
                papercup_get.setVisibility(View.INVISIBLE);
                papercup_question.setVisibility(View.INVISIBLE);
            }
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        step2_waiting.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().equals("대기시간: 25초")) {
                    Log.d("tag",s.toString());
                    papercup_get.setVisibility(View.VISIBLE);
                    papercup_question.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}