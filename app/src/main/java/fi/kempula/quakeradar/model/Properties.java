package fi.kempula.quakeradar.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Properties {

    @SerializedName("mag") private double magnitude;
    @SerializedName("place") private String place;
    @SerializedName("time") private Date time;
    @SerializedName("type") private String type;
    @SerializedName("title") private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
