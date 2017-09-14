package com.haptix.authtest.api;

/**
 * Created by gordonwallace on Sept 2017.
 */


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.Call;
import retrofit2.http.Query;

public interface ApiService {
    String BASE_URL = "http://34.252.111.58:8000/";

    @FormUrlEncoded
    @POST("users/")
    Call<ResponseBody> addUser(
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("password") String pass);

//    @GET("markets/")
//    Call<List<Market>> getMarkets();

}