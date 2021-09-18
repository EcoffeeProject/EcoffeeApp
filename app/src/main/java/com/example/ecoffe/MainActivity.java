package com.example.ecoffe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,Serializable {

    TextView tv_name,tv_money,tv_stamp, tv_coupon;
    Button order_button,charge_button;

    Intent intent ;
    User user;

    private static String TAG = "phptest_LoadActivity";
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_ID = "userID";
    private static final String TAG_Password = "userPassword";
    private static final String TAG_Balance = "Balance";
    private static final String TAG_Stamp = "Stamp";
    private static final String TAG_Coupon = "Coupon";
    private String mJsonString;


    protected void onCreate(Bundle savedInstanceStare) {
        intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        super.onCreate(savedInstanceStare);
        setContentView(R.layout.activity_main);

        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_name.setText(user.getUserPassword()+ " 님, 환영합니다");




        order_button = (Button) findViewById(R.id.order_button);
        order_button.setOnClickListener(this);

        charge_button= (Button) findViewById(R.id.charge_button);
        charge_button.setOnClickListener(this);

        MainActivity.GetData task = new MainActivity.GetData(); //서버에서 사용자정보 가져오기
        task.execute("http://auddms.ivyro.net/ECO_loadInfo.php");

    }



    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "response  - " + result);


            if (result == null) {


            } else {
                mJsonString = result;
                saveResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = "http://auddms.ivyro.net/ECO_loadInfo.php";


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }

    }


    private void saveResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String password = item.getString(TAG_Password);
                String balance = item.getString(TAG_Balance);
                String stamp = item.getString(TAG_Stamp);
                String coupon = item.getString(TAG_Coupon);

                if (id.equals(user.getUserID())) {// 현재 사용자 ID와 서버에있는 정보중 ID가 같은것의 행 정보만 가져옴
                    user.setInfo(Integer.parseInt(balance),Integer.parseInt(stamp),Integer.parseInt(coupon));

                    tv_money = (TextView)findViewById(R.id.tv_money);
                    tv_money.setText(user.getBalance()+ "원");

                    tv_stamp = (TextView)findViewById(R.id.tv_stamp);
                    tv_stamp.setText(user.getStamp()+ "/8");

                    tv_coupon = (TextView)findViewById(R.id.tv_coupon);
                    tv_coupon.setText("쿠폰 x"+user.getCoupon());

                }
                /*
                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, id);
                hashMap.put(TAG_Password, password);
                hashMap.put(TAG_Balance, balance);
                hashMap.put(TAG_Stamp, stamp);
                hashMap.put(TAG_Coupon, coupon);
*/


            }




        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_button:
                //주문하기 버튼 클릭시 주문 페이지로 이동
                intent = new Intent(getApplicationContext(), OrderActivity.class);
                //intent.putExtra("user", user);
                startActivity(intent);
                break;
            case R.id.charge_button:
                //충전하기 버튼 클릭시 충전 페이지로 이동
                intent = new Intent(getApplicationContext(), DepositActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;

        }
    }
}
