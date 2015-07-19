package br.com.fgr.emergencia.models.directions;

import com.google.gson.annotations.SerializedName;

import br.com.fgr.emergencia.models.general.Coordenada;

public class Fronteira {

    @SerializedName("southwest")
    private Coordenada sudoeste;
    @SerializedName("northeast")
    private Coordenada nordeste;

    public Coordenada getSudoeste() {
        return sudoeste;
    }

    public Coordenada getNordeste() {
        return nordeste;
    }

}