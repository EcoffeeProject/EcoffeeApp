package com.example.ecoffe;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

public class Step2Fragment extends Fragment {

    TextView step2_waiting;
    SharedViewModel sharedViewModel;

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

        sharedViewModel = new ViewModelProvider(requireActivity()).get(Step1Fragment.sharedViewModel.getClass());

        sharedViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Log.d("tag","변화 감지됨"+s);
                if (s.equals("ConnectSuccess")) {

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
                    Log.d("tag","Check신호 보냄");

                }else  //블루투스 연결에 실패했을 경우  블루투스 연결 재시도 혹은 그냥 안된다는 알림 필요
                {

                }

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onResume() {
        super.onResume();

  //      sharedViewModel_tp = new ViewModelProvider(requireActivity()).get(SharedViewModel_TP.class);

}}