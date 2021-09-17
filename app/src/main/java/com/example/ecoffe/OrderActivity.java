package com.example.ecoffe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    private Step1Fragment step1Fragment;
    private Step2Fragment step2Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

   /*     FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

        Step1Fragment step1Fragment = (Step1Fragment) fragmentManager.findFragmentById(R.id.fragment_step1);
  */

    step1Fragment= (Step1Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment_step1);
    step2Fragment =(Step2Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment_step2);

    }
}