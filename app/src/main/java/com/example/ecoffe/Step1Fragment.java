package com.example.ecoffe;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class Step1Fragment extends Fragment{

    static public BluetoothAdapter mBluetoothAdapter = null;
    static public BluetoothHelper mBluetooth = new BluetoothHelper();
    int step3_result =Infomation.Nothing;
    int step1_result = Infomation.Nothing;
    String DEVICE_NAME = "vendingMachine";  //블르투스 이름
    TextView step1_resultment;
    ImageView highlight;

    public Step1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.order_step1, container, false);
        step1_resultment = view.findViewById(R.id.step1_resultment);
        highlight= view.findViewById(R.id.highlight);
        highlight.setVisibility(View.VISIBLE);

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
                    Log.d("tag", "R 들어와서 음료 준비중");
                }
                else if(message.equals("C"))  //Complete
                {
                    Log.d("tag", "C 들어와서 음료뽑기 완료");
                    mBluetooth.Disconnect();  //이젠 블루투스 연결 종료하기
                }
            }

            @Override
            public void onBluetoothHelperConnectionStateChanged(BluetoothHelper bluetoothhelper, boolean isConnected) {
                Log.d("tag","블루투스 연결 상태 체크 함수 속");
                if (isConnected) {
                    step1_resultment.setTextColor(0xFF428681); //초록
                    step1_resultment.setText("연결되었습니다");
                    step1_result=Infomation.ConnectSuccess;
                    onResume();

                } else {
                    Log.d("tag","연결끊김");
                    step1_resultment.setTextColor(0xAAef484a); //빨강
                    step1_resultment.setText("연결을 실패하였습니다 다시 시도해주십시오");
                    step1_result=Infomation.ConnectFail;
                    onResume();
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

        if(step1_result!=Infomation.Nothing){

            if(step1_result==Infomation.ConnectFail){
                return;
            }

            Step2Fragment step2Fragment = new Step2Fragment();//프래그먼트2 선언
            Bundle bundle = new Bundle();

            if(step3_result!=Infomation.Nothing)   //텀블러,종이컵 인식후 불려졌을 때 step2 invisble로 바꾸러 감
            {
                bundle.putInt("step2_finish",Infomation.Finish);
                Log.d("tag","step2다시 부른다") ;

            }
            else  //블루투스 연결 후 처음 step2로 넘김
            {
                bundle.putInt("step1_result", step1_result);//번들에 넘길 값 저장
                Log.d("tag","step2처음 넘김");
                highlight.setVisibility(View.INVISIBLE);

            }

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            step2Fragment.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
            transaction.replace(R.id.fragment_step2, step2Fragment);
            transaction.commit();

        }
        if(step3_result !=Infomation.Nothing) {

            Bundle bundle = new Bundle(); // 번들을 통해 값 전달
            bundle.putInt("step3_result", step3_result);//번들에 넘길 값 저장

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            Step3Fragment step3Fragment = new Step3Fragment();//프래그먼트3 선언
            step3Fragment.setArguments(bundle);//번들을 프래그먼트3으로 보낼 준비
            transaction.replace(R.id.fragment_step3, step3Fragment);
            transaction.commit();

            step1_result=Infomation.Nothing;
            step3_result=Infomation.Nothing;
        }
    }
}