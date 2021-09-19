package com.example.ecoffe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    private Step1Fragment step1Fragment;
    private Step2Fragment step2Fragment;
    private Step3Fragment step3Fragment;
    private Step4Fragment step4Fragment;
    private Step5Fragment step5Fragment;
    Intent intent;
    static public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = new User("지연","1234");
  //      intent = getIntent();
  //      user = (User) intent.getSerializableExtra("user");
//        user.setInfo(0,0,10);

        setContentView(R.layout.activity_order);
        step1Fragment= (Step1Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment_step1);
        step2Fragment =(Step2Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment_step2);
        step3Fragment =(Step3Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment_step3);
        step4Fragment =(Step4Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment_step4);
        step5Fragment =(Step5Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment_step5);

        if(step5Fragment.isDetached()){
            Log.d("tag","OrderActivity finish()");
            finish();
        }
    }
}