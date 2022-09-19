
package com.hinext.software.ModelClass;

import com.google.gson.annotations.SerializedName;

public class ResponseModel
{
    @SerializedName("status")
    public int status;
    @SerializedName("messages")
    public EventModel messages;

    @SerializedName("data")
    public Data data;
}
