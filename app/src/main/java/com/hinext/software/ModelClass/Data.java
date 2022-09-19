package com.hinext.software.ModelClass;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("user_id")
    public int userId;
    @SerializedName("username")
    public String userName;

    @SerializedName("office")
    public int office;
}
