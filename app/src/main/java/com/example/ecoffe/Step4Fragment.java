package com.example.ecoffe;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Step4Fragment extends Fragment {

    int step3_result = Infomation.Nothing;
    int step4_result =Infomation.Nothing;
    TextView americano, caffelatte, greenlatte, choice_info, choice_finish;
    ImageView americano_img, caffelatte_img, greenlatte_img;


    public Step4Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_step4, container, false);

        americano = view.findViewById(R.id.americano);
        caffelatte = view.findViewById(R.id.caffelatte);
        greenlatte = view.findViewById(R.id.greenlatte);
        americano_img = view.findViewById(R.id.americano_img);
        caffelatte_img = view.findViewById(R.id.caffelatte_img);
        greenlatte_img = view.findViewById(R.id.greenlatte_img);
        choice_info= view.findViewById(R.id.choice_info);
        choice_finish=view.findViewById(R.id.choice_finish);

        if (getArguments() != null) {

            step3_result = getArguments().getInt("step3_result"); // step1에서 받아온 값 넣기

            americano_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    americano.setTextColor(0xFF9B7A63);  //brown
                    caffelatte.setTextColor(0xFF7F7F7F); //black
                    greenlatte.setTextColor(0xFF7F7F7F); //black
                    choice_info.setText("아메리카노 2000원");
                    choice_info.setVisibility(View.VISIBLE);
                    choice_finish.setVisibility(View.VISIBLE);
                    step4_result=Infomation.Americano;
                }
            });

            caffelatte_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    americano.setTextColor(0xFF7F7F7F);  //black
                    caffelatte.setTextColor(0xFFE4C066); //Yellow
                    greenlatte.setTextColor(0xFF7F7F7F); //black
                    choice_info.setText("카페라떼 2500원");
                    choice_info.setVisibility(View.VISIBLE);
                    choice_finish.setVisibility(View.VISIBLE);
                    step4_result=Infomation.CaffeLatte;
                }
            });

            greenlatte_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    americano.setTextColor(0xFF7F7F7F);  //black
                    caffelatte.setTextColor(0xFF7F7F7F); //black
                    greenlatte.setTextColor(0xE63B7600); //green
                    choice_info.setText("녹차라떼 3000원");
                    choice_info.setVisibility(View.VISIBLE);
                    choice_finish.setVisibility(View.VISIBLE);
                    step4_result=Infomation.GreenLatte;
                }
            });

            choice_finish.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onResume();
                }
            });

        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(step3_result!=Infomation.Nothing && step4_result!=Infomation.Nothing){

            americano_img.setClickable(false);
            caffelatte_img.setClickable(false);
            greenlatte_img .setClickable(false);


            Bundle bundle = new Bundle(); // 번들을 통해 값 전달
            bundle.putInt("step3_result",step3_result);//번들에 넘길 값 저장
            bundle.putInt("step4_result",step4_result);//번들에 넘길 값 저장

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            Step5Fragment step5Fragment = new Step5Fragment();//프래그먼트2 선언
            step5Fragment.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
            transaction.replace(R.id.fragment_step5, step5Fragment);
            transaction.commit();

        }
    }


}