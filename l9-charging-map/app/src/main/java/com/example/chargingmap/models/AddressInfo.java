package com.example.chargingmap.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

public class AddressInfo {
    @SerializedName("Title")
    private String title;

    @SerializedName("Latitude")
    private double latitude;

    @SerializedName("Longitude")
    private double longitude;

    public AddressInfo() {
    }

    public AddressInfo(String title, double latitude, double longitude) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LatLng getPoint() {
        return new LatLng(getLatitude(), getLongitude());
    }
}
