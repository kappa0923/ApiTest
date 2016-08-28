package com.kappa0923.android.app.apitest.storage;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tomka on 2016/08/29.
 */
public class Weather extends RealmObject {
    @PrimaryKey
    private int id;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
