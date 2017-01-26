package br.com.fgr.emergencia.models.general;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass public class Coordenada implements RealmModel {

    private double lat;
    private double lng;

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

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String toString() {
        return lat + "," + lng;
    }
}
