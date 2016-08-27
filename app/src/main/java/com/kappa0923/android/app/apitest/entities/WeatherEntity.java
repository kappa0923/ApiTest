package com.kappa0923.android.app.apitest.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 天気情報を受け取るEntity
 */
public class WeatherEntity {
    @Expose
    @SerializedName("description")
    private DescriptionEntity descriptionEntities;

    @Expose
    @SerializedName("link")
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public DescriptionEntity getDescription() {
        return descriptionEntities;
    }

    public void setDescriptionEntities(DescriptionEntity descriptionEntities) {
        this.descriptionEntities = descriptionEntities;
    }
}
