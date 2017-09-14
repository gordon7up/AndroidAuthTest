package com.haptix.authtest.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.haptix.authtest.MainActivity;
import com.haptix.authtest.Utilities;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.Interceptor;

/**
 * Created by gordonwallace on Sept 2017.
 * Retrofit 2 API singleton class.
 */

public class Api {
    private static Api instance = null;
    ApiService apiService;
    OkHttpClient defaultHttpClient;

    public static Api getInstance(){
        if(instance == null){
            instance = new Api();
        }
        return instance;
    }



    private Api(){
        buildRetrofit();
    }

    private void buildRetrofit(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(buildInterceptorClient(defaultHttpClient))
                .build();

        this.apiService = retrofit.create(ApiService.class);
    }

    private OkHttpClient buildInterceptorClient(OkHttpClient client){
        client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        //getAccessToken is your own accessToken(retrieve it by saving in shared preference or any other option )
                        if(Utilities.getToken(MainActivity.appContext).isEmpty()){
                            //PrintLog.error("retrofit 2","Authorization header is already present or token is empty....");
                            return chain.proceed(chain.request());
                        }
                        Request authorisedRequest = chain.request().newBuilder()
                                .addHeader("Authorization", Utilities.getToken(MainActivity.appContext)).build();
                        //PrintLog.error("retrofit 2","Authorization header is added to the url....");
                        return chain.proceed(authorisedRequest);
                    }}).build();

        return client;
    }

    public ApiService getApiService(){
        return this.apiService;
    }

}
