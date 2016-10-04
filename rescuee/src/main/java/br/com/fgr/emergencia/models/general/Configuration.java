package br.com.fgr.emergencia.models.general;

public class Configuration {

    private int radius;
    private int hospitals;
    private String mode;

    public Configuration(int radius, int hospitals, String mode) {

        this.radius = radius;
        this.hospitals = hospitals;
        this.mode = mode;
    }

    public int getRadius() {
        return radius;
    }

    public int getHospitals() {
        return hospitals;
    }

    public String getMode() {
        return mode;
    }
}