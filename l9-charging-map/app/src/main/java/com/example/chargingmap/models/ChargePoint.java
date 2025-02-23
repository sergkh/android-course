package com.example.chargingmap.models;

import com.google.gson.annotations.SerializedName;

public class ChargePoint {
    @SerializedName("AddressInfo")
    private AddressInfo addressInfo;

    public ChargePoint(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public ChargePoint() {
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }
}
