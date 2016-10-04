package br.com.fgr.emergencia.models.directions;

import br.com.fgr.emergencia.models.general.Coordenada;
import com.google.gson.annotations.SerializedName;

public class Fronteira {

    @SerializedName("southwest") private Coordenada sudoeste;
    @SerializedName("northeast") private Coordenada nordeste;

    public Coordenada getSudoeste() {
        return sudoeste;
    }

    public Coordenada getNordeste() {
        return nordeste;
    }
}