package br.com.fgr.emergencia.models.general;

public enum TransportEnum {

    DRIVING("driving"), WALKING("walking");

    private String modeTransport;

    TransportEnum(String modeTransport) {
        this.modeTransport = modeTransport;
    }

    public String getModeTransport() {
        return this.modeTransport;
    }

}