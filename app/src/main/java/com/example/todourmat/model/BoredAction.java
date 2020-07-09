package com.example.todourmat.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "bored_action")
public class BoredAction {
    @SerializedName("key")
    @ColumnInfo(name = "key")
    private String key;

    @SerializedName("activity")
    @ColumnInfo(name = "activity")
    private String activity;

    @SerializedName("type")
    @ColumnInfo(name = "type")
    private String type;

    @SerializedName("link")
    @ColumnInfo(name = "link")
    private String link;

    @SerializedName("accessibility")
    @ColumnInfo(name = "accessibility")
    private Float accessibility;

    @SerializedName("price")
    @ColumnInfo(name = "price")
    private Float price;

    @SerializedName("participants")
    @ColumnInfo(name = "participants")
    private Integer participants;

    public BoredAction(String key, String activity, String type, String link, Float accessibility, Float price, Integer participants) {
        this.key = key;
        this.activity = activity;
        this.type = type;
        this.link = link;
        this.accessibility = accessibility;
        this.price = price;
        this.participants = participants;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Float getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Float accessibility) {
        this.accessibility = accessibility;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "BoredAction{" +
                "key='" + key + '\'' +
                ", activity='" + activity + '\'' +
                ", type='" + type + '\'' +
                ", link='" + link + '\'' +
                ", accessibility=" + accessibility +
                ", price=" + price +
                ", participants=" + participants +
                '}';
    }
}
