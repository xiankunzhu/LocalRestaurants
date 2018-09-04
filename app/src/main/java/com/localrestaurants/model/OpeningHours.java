package com.localrestaurants.model;

/**
 * @author Xiankun Zhu
 */

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpeningHours {

    @SerializedName("open_now")
    private Boolean openNow;
    @SerializedName("weekday_test")
    private List<Object> weekdayText = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public List<Object> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(List<Object> weekdayText) {
        this.weekdayText = weekdayText;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
