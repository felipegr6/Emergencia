package br.com.fgr.emergencia.models.distancematrix;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Destinos {

    @SerializedName("elements")
    private List<Elementos> elementos;

    public Destinos(List<Elementos> elementos) {
        this.elementos = elementos;
    }

    public List<Elementos> getElementos() {
        return elementos;
    }

    public void setElementos(List<Elementos> elementos) {
        this.elementos = elementos;
    }

}