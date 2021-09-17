package com.example.ecoffe;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;

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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

public class Step1Fragment extends Fragment{

    public BluetoothAdapter mBluetoothAdapter = null;
    static public BluetoothHelper mBluetooth = new BluetoothHelper();

    String DEVICE_NAME = "vendingMachine";  //블르투스 이름
    TextView step1_resultment;
    private SharedViewModel sharedViewModel;
    static int cnt=0;

    public Step1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("tag",cnt+"번째 step1 onCreateView()");
        cnt++;
        View view = inflater.inflate(R.layout.order_step1, container, false);
        step1_resultment = view.findViewById(R.id.step1_resultment);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.enable();

        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            onStop();
        }
        mBluetooth.setBluetoothHelperListener(new BluetoothHelper.BluetoothHelperListener() {

            @Override
            public void onBluetoothHelperMessageReceived(BluetoothHelper bluetoothhelper, String message) {
                Log.d("tag","블루투스 메시지 들어옴"+message);
                // Do your stuff with the message received !!!

            }

            @Override
            public void onBluetoothHelperConnectionStateChanged(BluetoothHelper bluetoothhelper, boolean isConnected) {
                Log.d("tag","블루투스 연결 상태 체크 함수 속");
                if (isConnected) {
                    step1_resultment.setTextColor(0xFF428681); //초록
                    step1_resultment.setText("연결되었습니다");
                    mBluetooth.SendMessage("Check");

                } else {
                    step1_resultment.setTextColor(0xAAef484a); //빨강
                    step1_resultment.setText("연결을 실패하였습니다");
                }
            }
        });

        mBluetooth.Connect(DEVICE_NAME);
        Log.d("tag","블루투스 연결좀 해보자");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @org.jetbrains.annotations.NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        step1_resultment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("tag","변화되었다구요 "+s);

                if(sharedViewModel!=null){

                    if(s.toString().equals("연결되었습니다"))
                        sharedViewModel.setLiveData("success");
                    else
                        sharedViewModel.setLiveData("fail");
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /* @Override
    public void onStart() {

        super.onStart();
        Log.d("tag",cnt+"번째 step1start()");
        cnt++;



    }*/


}