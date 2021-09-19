package com.example.ecoffe;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Step5Fragment extends Fragment {

    User user;
    int coupon ;
    int step4_result= Infomation.Nothing;
    int step3_result= Infomation.Nothing;
    int step5_result=Infomation.Nothing;
    static int cnt=0;

    TextView step5_coupon_ment,productmoney_won,discount_won,coupon_aply_won,paymoney_won,coupon_use;
    Button step5_coupon_btn, pay_btn;
    int paymoney=Infomation.Nothing;

    BluetoothHelper mBluetooth= Step1Fragment.mBluetooth;

    public Step5Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.order_step5, container, false);
        step5_coupon_ment= view.findViewById(R.id.step5_coupon_ment);
        productmoney_won = view.findViewById(R.id.productmoney_won);
        discount_won= view.findViewById(R.id.discount_won);
        coupon_aply_won= view.findViewById(R.id.coupon_aply_won);
        paymoney_won= view.findViewById(R.id.paymoney_won);
        coupon_use=view.findViewById(R.id.coupon_use);
        step5_coupon_btn = view.findViewById(R.id.step5_coupon_btn);
        pay_btn= view.findViewById(R.id.pay_btn);


        if(getArguments()!=null) {

            if (cnt == 0) {

                Log.d("tag", "초기화시 들어온 번들임");
                this.user = (User) getArguments().getSerializable("user");

                coupon = user.getCoupon();
                step5_coupon_ment.setText("(보유: " + coupon + "장)");  //보유 쿠폰개수만 보여줌
                cnt++;
            } else {

                Log.d("tag", "step4 이후로 들어온 번들임");
                step3_result = getArguments().getInt("step3_result"); // step3에서 받아온 값 텀블러 or 종이컵
                step4_result = getArguments().getInt("step4_result"); // step4에서 받아온 값 선택 음료 가격

                productmoney_won.setText(step4_result + "원");
                productmoney_won.setVisibility(View.VISIBLE);
                paymoney = step4_result;
                paymoney_won.setText(paymoney + "원");
                paymoney_won.setVisibility(View.VISIBLE);

                //텀블러 할인, 쿠폰사용 유무
                if (step3_result == Infomation.Tumbler) {
                    Log.d("tag", "step4이후 번들 값");

                    step5_coupon_ment.setText("(사용가능: " + coupon + "장|보유: " + coupon + "장)");
                    discount_won.setText("-1000원");
                    discount_won.setVisibility(View.VISIBLE);

                    paymoney -= 1000;

                    step5_coupon_btn.setOnClickListener(new View.OnClickListener() {
                        int cnt = 0;

                        @Override
                        public void onClick(View v) {

                            if (cnt == 0) {
                                coupon_use.setTextColor(0xFF428681); //초록
                                coupon_use.setText("쿠폰이 적용되었습니다");
                                coupon_aply_won.setText("-1000원");
                                paymoney -= 1000;
                                paymoney_won.setText(paymoney + "원");
                            } else {
                                coupon_use.setTextColor(0xAAef484a); //빨강
                                coupon_use.setText("쿠폰이 이미 적용되었습니다");
                            }
                            coupon_use.setVisibility(View.VISIBLE);
                            coupon_aply_won.setVisibility(View.VISIBLE);
                            cnt++;
                        }
                    });

                } else //PaperCup
                {
                    Log.d("tag", "step4이후 번들 값");
                    step5_coupon_ment.setText("(사용가능: 0장|보유: " + coupon + "장)");
                    discount_won.setVisibility(View.VISIBLE);

                    step5_coupon_btn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            coupon_use.setTextColor(0xAAef484a); //빨강
                            coupon_use.setText("종이컵 사용자는 쿠폰 적용이 불가합니다");
                            coupon_use.setVisibility(View.VISIBLE);
                        }
                    });
                }

                //전체 코드
                paymoney_won.setText(paymoney + "원");
                coupon_aply_won.setVisibility(View.VISIBLE);
                paymoney_won.setVisibility(View.VISIBLE);

                pay_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (user.getBalance() >= paymoney) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("결제하기")        // 제목 설정
                                    .setMessage("결제 하시겠습니까?")        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            if(coupon_aply_won.getText().equals("-1000원"))
                                                step5_result= Infomation.PayUseCoupon;
                                            else
                                                step5_result = Infomation.PaySuccess;
                                            onResume();
                                        }
                                    })
                                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {

                                        }
                                    });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("결제하기")        // 제목 설정
                                    .setMessage("충전된 금액이 부족합니다.\n충전된 금액: " + user.getBalance() + "원\n결제 금액: " + paymoney_won.getText())        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            step5_result = Infomation.PayFail;
                                            onResume();
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });

            }
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(step5_result>=Infomation.PaySuccess){

            mBluetooth.SendMessage("Pay"); //결제 메시지 보냄
            user.addStamp();
            user.subBalance(paymoney);

            if(step5_result==Infomation.PayUseCoupon)
                user.useCoupon();

            //Intent intent = new Intnet(getActivity())

        }else if (step5_result==Infomation.PayFail){

            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("user",user);
            getActivity().startActivity(intent);

            mBluetooth.Disconnect();
            onDetach();
        }

    }



}

