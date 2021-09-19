package com.example.ecoffe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Step3Fragment extends Fragment {

    TextView step3_resultment;
    int step3_result =Infomation.Nothing;

    public Step3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_step3, container, false);
        step3_resultment= view.findViewById(R.id.step3_resultment);

        if (getArguments() != null)
        {
            step3_result = getArguments().getInt("step3_result"); // step1에서 받아온 값 넣기

            if( step3_result ==Infomation.Tumbler){
                step3_resultment.setTextColor(0xFF428681); //초록
                step3_resultment.setText("텀블러 입니다");
            }
            else{
                step3_resultment.setTextColor(0xAAef484a); //빨강
                step3_resultment.setText("종이컵 입니다");
            }
            onResume();

        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(step3_result !=Infomation.Nothing){
            Bundle bundle = new Bundle(); // 번들을 통해 값 전달
            bundle.putInt("step3_result", step3_result);//번들에 넘길 값 저장
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            Step4Fragment step4Fragment = new Step4Fragment();//프래그먼트2 선언
            step4Fragment.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
            transaction.replace(R.id.fragment_step4, step4Fragment);
            transaction.commit();
        }
    }
}