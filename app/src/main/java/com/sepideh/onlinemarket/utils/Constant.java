package com.sepideh.onlinemarket.utils;

import android.graphics.Typeface;

import com.sepideh.onlinemarket.retrofit.ApiClient;
import com.sepideh.onlinemarket.retrofit.ApiService;
import com.sepideh.onlinemarket.retrofit.ApiServiceSms;
import com.sepideh.onlinemarket.retrofit.SmsClient;

/**
 * Created by pc on 4/7/2019.
 */

public class Constant {

    public static String BASE_URL="http://spidstore.ir/onlinemarket/";
    public static String Base_Url_SMS="https://api.kavenegar.com/v1/";
    public static String TEMPLATE="sendcode";
    public static ApiService apiService= ApiClient.createService(ApiService.class);
    public static ApiServiceSms apiServiceSms= SmsClient.createService(ApiServiceSms.class);
}
