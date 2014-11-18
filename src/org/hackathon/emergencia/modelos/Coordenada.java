package org.hackathon.emergencia.modelos;

public class Coordenada {

    private double lat;
    private double lgn;

    public Coordenada() {

    }

    public Coordenada(double lat, double lgn) {

        this.lat = lat;
        this.lgn = lgn;

    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLgn() {
        return lgn;
    }

    public void setLgn(double lgn) {
        this.lgn = lgn;
    }

}