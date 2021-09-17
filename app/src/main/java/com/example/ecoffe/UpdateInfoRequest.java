package com.example.ecoffe;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateInfoRequest extends StringRequest {
    // 서버 URL설정 (PHP 파일 연동)
    final static private  String URL = "http://auddms.ivyro.net/ECO_updateInfo.php";
    private Map<String,String> map;

    public UpdateInfoRequest(String userID, String userPassword ,int Balance, int Stamp, int Coupon, Response.Listener<String> listener){
        super(Method.POST,URL,listener, null);

        map = new HashMap<>();
        map.put("userID",userID);
        map.put("userPassword",userPassword);
        map.put("Balance",Balance+"");
        map.put("Stamp",Stamp+"");
        map.put("Coupon",Coupon+"");

    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
