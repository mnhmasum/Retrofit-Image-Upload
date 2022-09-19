package com.hinext.software.NetworkRelatedClass;

import com.hinext.software.ModelClass.ResponseModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface ApiInterface {
    @Multipart
    @POST("create")
    Call<ResponseModel> fileUpload(
            @Part ("vehicleno") String vehicleno,
            @Part ("office") int office,
            @Part ("user_id") int userId,
            @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("login")
    Call<ResponseModel> login(
            @Part ("username") String vehicleno,
            @Part ("password") String office);

    @FormUrlEncoded
    @POST("view")
    Call<ResponseModel> show(@Path("id") String userId);
}
