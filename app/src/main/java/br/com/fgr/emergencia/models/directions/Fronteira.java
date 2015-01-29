package br.com.fgr.emergencia.models.directions;

import com.google.gson.annotations.SerializedName;

import br.com.fgr.emergencia.models.general.Coordenada;

public class Fronteira {

    @SerializedName("southwest")
    private Coordenada sudoeste;
    @SerializedName("northeast")
    private Coordenada nordeste;

    public Fronteira(Coordenada sudoeste, Coordenada nordeste) {
        this.sudoeste = sudoeste;
        this.nordeste = nordeste;
    }

    public Coordenada getSudoeste() {
        return sudoeste;
    }

    public void setSudoeste(Coordenada sudoeste) {
        this.sudoeste = sudoeste;
    }

    public Coordenada getNordeste() {
        return nordeste;
    }

    public void setNordeste(Coordenada nordeste) {
        this.nordeste = nordeste;
    }

}