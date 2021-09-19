package com.example.ecoffe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class OrderActivity extends AppCompatActivity {

    private Step1Fragment step1Fragment;
    private Step2Fragment step2Fragment;
    private Step3Fragment step3Fragment;
    private Step4Fragment step4Fragment;
    private Step5Fragment step5Fragment;
    Intent intent;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        Bundle bundle = new Bundle(); // 번들을 통해 값 전달
        bundle.putSerializable("user",user);

        step5Fragment = new Step5Fragment(); //requestActivity에 fragment1을 띄워줌
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_step5,step5Fragment).commit(); //번들객체 생성, text값 저장 Bundle bundle = new Bundle(); bundle.putString("text",text); //fragment1로 번들 전달 fragment1.setArguments(bundle);
        step5Fragment.setArguments(bundle);


        step1Fragment= (Step1Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment_step1);
        step2Fragment =(Step2Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment_step2);
        step3Fragment =(Step3Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment_step3);
        step4Fragment =(Step4Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment_step4);

        setContentView(R.layout.activity_order);
        
    }

    @Override
    public void onBackPressed(){ //뒤로가기 버튼 누르면 종료
        
        if(Step1Fragment.mBluetooth.isConnected())
        Step1Fragment.mBluetooth.Disconnect();   //연결되어있다면 종료시킨다
    }

}