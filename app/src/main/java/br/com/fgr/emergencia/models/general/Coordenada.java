package br.com.fgr.emergencia.models.general;

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

    public String toString() {
        return lat + "," + lgn;
    }

}