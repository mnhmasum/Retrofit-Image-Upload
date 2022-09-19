
package com.hinext.software.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Messages {

    @SerializedName("total_rocords")
    @Expose
    private Integer totalRocords;

    public Integer getTotalRocords() {
        return totalRocords;
    }

    public void setTotalRocords(Integer totalRocords) {
        this.totalRocords = totalRocords;
    }

}
