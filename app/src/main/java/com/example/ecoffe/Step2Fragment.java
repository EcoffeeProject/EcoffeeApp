package com.example.ecoffe;

import android.os.Bundle;
import android.os.CountDownTimer;
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
    private SharedViewModel sharedViewModel;
    static int count=0;
    BluetoothHelper mBluetooth= Step1Fragment.mBluetooth;

    public Step2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_step2, container, false);
        step2_waiting= view.findViewById(R.id.step2_waiting);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if(s.equals("success")){

                    if(mBluetooth.isConnected()){
                        Log.d("tag",mBluetooth.ReceiveMessage());
                    }

                    step2_waiting.setText("대기시간: 30초");
                    new CountDownTimer(30000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            step2_waiting.setText("대기 시간: " + millisUntilFinished / 1000 + "초");
                        }
                        public void onFinish() {
                            step2_waiting.setText("대기 시간 종료");
                        }
                    }.start();


                }
                else{ //블루투스 연결에 실패했을 경우  블루투스 연결 재시도 혹은 그냥 안된다는 알림 필요

                }

            }
        });
    }

   /* @Override
    public void onStart() {

        super.onStart();
        count++;



            mBluetooth.setBluetoothHelperListener(new BluetoothHelper.BluetoothHelperListener() {

                @Override
                public void onBluetoothHelperMessageReceived(BluetoothHelper bluetoothhelper, String message) {
                    Log.d("tag","메시지 들어옴");
                    // Do your stuff with the message received !!!
                    if(message.equals("Pass"))  //무게 통과, 텀블러 메시지
                    {

                    }
                    else if (message.equals("Fail")) //종이컵 메시지
                    {

                    }

                }
                @Override
                public void onBluetoothHelperConnectionStateChanged(BluetoothHelper bluetoothhelper, boolean isConnected) {

                }
            });
        }
    }*/
}