package com.archerswet.test08.request;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestInterface {

    @GET("goods/query")
    Call<ResponseBody> getgoods();

    @POST("goods/add")
    @FormUrlEncoded
    Call<ResponseBody> addToCar(@Field("uid") Integer uid,@Field("gid") Integer gid,@Field("shopnumber") Integer shopnumber);

    @GET("goods/car")
    Call<ResponseBody> getFromCar(@Query("uid") Integer uid,@Query("oid") Integer oid);

    @GET("goods/car")
    Call<ResponseBody> getFromCar(@Query("uid") Integer uid);

    @POST("goods/increase")
    @FormUrlEncoded
    Call<ResponseBody> increase(@Field("uid") Integer uid,@Field("gid") Integer gid);

    @POST("goods/decrease")
    @FormUrlEncoded
    Call<ResponseBody> decrease(@Field("uid") Integer uid,@Field("gid") Integer gid);

    @POST("goods/order")
    @FormUrlEncoded
    Call<ResponseBody> takeOrder(@Field("uid") Integer uid, @Field("onumber") Integer onumber, @Field("oprice") Double oprice, @Field("gids")List<Integer> gids);

}
