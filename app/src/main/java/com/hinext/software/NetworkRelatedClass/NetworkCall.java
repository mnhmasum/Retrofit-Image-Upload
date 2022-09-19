package com.hinext.software.NetworkRelatedClass;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.hinext.software.ModelClass.Datum;
import com.hinext.software.ModelClass.Example;
import com.hinext.software.ModelClass.ResponseModel;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCall {

    static CallBack callBack;

    public interface CallBack {
        void onSuccess(ResponseModel responseModel);
        void onSuccess(Example item);
        void onFailed(String message);
    }

    public static void setListener(CallBack callBack) {
        NetworkCall.callBack = callBack;
    }

    public static void fileUpload(String filePath, String vehicleNo, int office, int userId) {

        ApiInterface apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);
        Logger.addLogAdapter(new AndroidLogAdapter());

        File file = new File(filePath);
        //create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file); //allow image and any other file

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("image1", file.getName(), requestFile);

        Gson gson = new Gson();
        //String patientData = gson.toJson(imageSenderInfo);

        //RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, patientData);

        // finally, execute the request
        Call<ResponseModel> call = apiInterface.fileUpload(vehicleNo, office, userId, body);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {

                ResponseModel responseModel = response.body();
                if (responseModel != null) {
                    Logger.d("Response:=> " + responseModel.messages.success);
                    callBack.onSuccess(responseModel);
                } else {
                    callBack.onFailed("Server response null");
                }

                /*if(responseModel != null){
                    EventBus.getDefault().post(new EventModel("response", responseModel.success));
                    Logger.d("Response code " + response.code() +
                            " Response Message: " + responseModel.getMessage());
                } else
                    EventBus.getDefault().post(new EventModel("response", "ResponseModel is NULL"));*/
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Logger.d("Exception: " + t);
                callBack.onFailed(t.getMessage());
                //EventBus.getDefault().post(new EventModel("response", t.getMessage()));
            }
        });
    }

    public static void login(String filePath, String vehicleNo) {

        ApiInterface apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);
        Logger.addLogAdapter(new AndroidLogAdapter());

        /*File file = new File(filePath);
        //create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file); //allow image and any other file

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("image1", file.getName(), requestFile);
*/
        Gson gson = new Gson();
        //String patientData = gson.toJson(imageSenderInfo);

        //RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, patientData);

        // finally, execute the request
        Call<ResponseModel> call = apiInterface.login(filePath, vehicleNo);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {

                ResponseModel responseModel = response.body();
                Logger.d("Response:=> " + responseModel.messages.success);
                callBack.onSuccess(responseModel);
                /*if(responseModel != null){
                    EventBus.getDefault().post(new EventModel("response", responseModel.success));
                    Logger.d("Response code " + response.code() +
                            " Response Message: " + responseModel.getMessage());
                } else
                    EventBus.getDefault().post(new EventModel("response", "ResponseModel is NULL"));*/
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Logger.d("Exception: " + t);
                callBack.onFailed(t.getMessage());
                //EventBus.getDefault().post(new EventModel("response", t.getMessage()));
            }
        });
    }

    public static void show(int userId) {
        ApiInterface apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);
        Logger.addLogAdapter(new AndroidLogAdapter());
        Call<Example> call = apiInterface.show(userId);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(@NonNull Call<Example> call, @NonNull Response<Example> response) {

                Example responseModel = response.body();

                if (responseModel != null) {
                    Logger.d("Response:=> " + responseModel.getData().size());
                    Logger.d("Response code " + response.code() + " Response Message: " + responseModel);
                    callBack.onSuccess(responseModel);
                } else
                    callBack.onFailed("No Data found");
            }

            @Override
            public void onFailure(@NonNull Call<Example> call, @NonNull Throwable t) {
                Logger.d("Exception: " + t);
                callBack.onFailed(t.getMessage());
                //EventBus.getDefault().post(new EventModel("response", t.getMessage()));
            }
        });
    }
}
