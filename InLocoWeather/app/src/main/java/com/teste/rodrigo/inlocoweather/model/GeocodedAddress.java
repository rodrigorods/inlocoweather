package com.teste.rodrigo.inlocoweather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GeocodedAddress implements Parcelable {

    private String formattedAddress;
    private double latitude;
    private double longitude;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public GeocodedAddress(String formattedAddress, double latitude, double longitude) {
        this.setFormattedAddress(formattedAddress);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    @Override
    public String toString() {
        return getFormattedAddress();
    }

    ///////////////////////PARCELABLE CONTENT///////////////////////////

    public GeocodedAddress(Parcel in){
        this.formattedAddress = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.formattedAddress);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    public static final Creator<GeocodedAddress> CREATOR = new Creator<GeocodedAddress>() {
        @Override
        public GeocodedAddress createFromParcel(Parcel in) {
            return new GeocodedAddress(in);
        }
        @Override
        public GeocodedAddress[] newArray(int size) {
            return new GeocodedAddress[size];
        }
    };

}
