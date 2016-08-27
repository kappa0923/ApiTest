package com.kappa0923.android.app.apitest.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 天気状況分のEntity
 */
public class DescriptionEntity {
    @Expose
    @SerializedName("text")
    private String text;

    @Expose
    @SerializedName("publicTime")
    private String publicTime;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }
}
