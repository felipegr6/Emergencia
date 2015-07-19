package br.com.fgr.emergencia.models.directions;

import com.google.gson.annotations.SerializedName;

public class Polilinha {

    @SerializedName("points")
    private String pontos;

    public Polilinha(String pontos) {
        this.pontos = pontos;
    }

    public String getPontos() {
        return pontos;
    }

}