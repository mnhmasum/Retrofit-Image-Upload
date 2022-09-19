
package com.hinext.software.ModelClass;

import com.google.gson.annotations.SerializedName;

public class ResponseModel
{
    @SerializedName("status")
    public int success;
    @SerializedName("messages")
    public EventModel messages;
}
