package com.example.ecoffe;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class Step1Fragment extends Fragment{

    static public BluetoothAdapter mBluetoothAdapter = null;
    static public BluetoothHelper mBluetooth = new BluetoothHelper();
    int step3_result =Infomation.Nothing;

    String DEVICE_NAME = "vendingMachine";  //블르투스 이름
    TextView step1_resultment;
    static public  SharedViewModel sharedViewModel;

    public Step1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


        View view = inflater.inflate(R.layout.order_step1, container, false);
        step1_resultment = view.findViewById(R.id.step1_resultment);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.enable();

        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            onStop();
        }

        if( mBluetooth.isConnected()==false)
            mBluetooth.Connect(DEVICE_NAME);
        Log.d("tag","블루투스 연결좀 해보자");

        mBluetooth.setBluetoothHelperListener(new BluetoothHelper.BluetoothHelperListener() {

            @Override
            public void onBluetoothHelperMessageReceived(BluetoothHelper bluetoothhelper, String message) {

                Log.d("tag", "블루투스 메시지 들어옴:" + message);
                if (message.equals("T"))    //Tumbler
                {
                    Log.d("tag", "p들어와서 tumbler인식");
                    Step2Fragment.timer.cancel();
                    step3_result = Infomation.Tumbler;
                    onResume();
                }
                else if (message.equals("P"))    //Paper
                {
                    Step2Fragment.timer.cancel();
                    step3_result = Infomation.PaperCup;
                    onResume();
                }
                else if(message.equals("R")) //Ready
                {
                    Log.d("tag", "R들어와서 음료 준비중");
                }
                else if(message.equals("C"))  //Complete
                {
                    Log.d("tag", "C들어와서 음료뽑기 완료");
                    mBluetooth.Disconnect();  //이젠 블루투스 연결 종료하기
                 }
            }

            @Override
            public void onBluetoothHelperConnectionStateChanged(BluetoothHelper bluetoothhelper, boolean isConnected) {
                Log.d("tag","블루투스 연결 상태 체크 함수 속");
                if (isConnected) {
                    step1_resultment.setTextColor(0xFF428681); //초록
                    step1_resultment.setText("연결되었습니다");
                    sharedViewModel.setLiveData("ConnectSuccess");

                } else {
                    Log.d("tag","연결끊김");
                    step1_resultment.setTextColor(0xAAef484a); //빨강
                    step1_resultment.setText("연결을 실패하였습니다");
                }
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull @org.jetbrains.annotations.NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onResume() {
        super.onResume();

        if(step3_result !=Infomation.Nothing) {

            Bundle bundle = new Bundle(); // 번들을 통해 값 전달
            bundle.putInt("step3_result", step3_result);//번들에 넘길 값 저장

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            Step3Fragment step3Fragment = new Step3Fragment();//프래그먼트2 선언
            step3Fragment.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
            transaction.replace(R.id.fragment_step3, step3Fragment);
            transaction.commit();
        }
    }
}