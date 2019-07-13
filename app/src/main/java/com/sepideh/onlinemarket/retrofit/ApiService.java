package com.sepideh.onlinemarket.retrofit;

import com.sepideh.onlinemarket.data.Category;
import com.sepideh.onlinemarket.data.Comment;
import com.sepideh.onlinemarket.data.ProductDetails;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.Slider;
import com.sepideh.onlinemarket.data.Sms;
import com.sepideh.onlinemarket.data.SuggestedProducts;
import com.sepideh.onlinemarket.data.UserInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by pc on 4/7/2019.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("loginuser.php")
    Single<UserInfo> login(@Field("phone_num") String phoneNumber, @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Single<UserInfo> register(@Field("user_name") String userName,@Field("phone_number") String phoneNumber, @Field("password") String password);


    @GET("getsliders.php")
    Single<List<Slider>> getSliderList();

    @GET("getSuggestionList.php")
    Single<List<ProductInfo>> getSuggestions();

    @GET("getBestList.php")
    Single<List<ProductInfo>> getBestList();

    @GET("getNewList.php")
    Single<List<ProductInfo>> getNewList();

    @FormUrlEncoded
    @POST("getproductdetails.php")
    Single<ProductInfo> getProductDetails(@Field("id") String id);

    @FormUrlEncoded
    @POST("getAllComments.php")
    Single<List<Comment>> getAllComments(@Field("id") String id);

    @FormUrlEncoded
    @POST("vote.php")
    Completable voteToComment(@Field("vote") String vote, @Field("id_comment") String commetnId, @Field("user_id") String userId);

    @FormUrlEncoded
    @POST("sendComment.php")
    Completable sendComment(@Field("product_id") String productId,@Field("user_id") String userId,@Field("star") String star,@Field("description") String description);

    @GET("getCategoryChildern.php")
    Single<List<Category>> getChildern();

    @FormUrlEncoded
    @POST("getChildrenProducts.php")
    Single<List<ProductInfo>> getChildrenProducts(@Field("cat_child") String catChild,@Field("cat_header") int catHeader);

    @FormUrlEncoded
    @POST("completeUserInfo.php")
    Single<UserInfo> updateUserInfo(@Field("phone_num") String phone_num,@Field("name") String name,@Field("family") String family,@Field("tell") String tell,@Field("jensiat") String jensiat);

}

