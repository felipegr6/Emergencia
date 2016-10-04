package br.com.fgr.emergencia.models.general;

import com.google.gson.annotations.SerializedName;

public class Coordenada {

    @SerializedName("lat") private double lat;
    @SerializedName("lng") private double lng;

    public Coordenada() {

    }

    public Coordenada(double lat, double lng) {

        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLgn() {
        return lng;
    }

    public void setLgn(double lng) {
        this.lng = lng;
    }

    public String toString() {
        return lat + "," + lng;
    }
}