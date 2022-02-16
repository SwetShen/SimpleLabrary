package com.archerswet.test07.request;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitTool {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            return new Retrofit.Builder()
                    .baseUrl(RequestData.BASEURL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void getRetrofitResult(Call<ResponseBody> call, Handler handler){
        StringBuffer buffer = new StringBuffer();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String result = response.body().string();
                    buffer.append(result);
                    Message message = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("data",result);
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("data_","ERROR:" + t.getMessage());
            }
        });
    }


    public static void getRetrofitResult(Call<ResponseBody> call, Handler handler,int what){
        StringBuffer buffer = new StringBuffer();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String result = response.body().string();
                    buffer.append(result);
                    Message message = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("data",result);
                    message.what = what;
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("data_","ERROR:" + t.getMessage());
            }
        });
    }
}
