package com.sepideh.onlinemarket.retrofit;

import com.sepideh.onlinemarket.data.Return;
import com.sepideh.onlinemarket.data.Sms;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by pc on 4/30/2019.
 */

public interface ApiServiceSms {


    @GET("736C574C62505A7172324B4733794D71524F7562484F3167536876714A33714A/verify/lookup.json")
    Single<Sms> SendCode(@Query("template") String template, @Query("receptor") String PhoneNumber, @Query("token") String token);
}
