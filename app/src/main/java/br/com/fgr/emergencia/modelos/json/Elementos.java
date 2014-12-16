package br.com.fgr.emergencia.modelos.json;

import com.google.gson.annotations.SerializedName;

public class Elementos {

    @SerializedName("distance")
    private Distancia distancia;

    @SerializedName("duration")
    private Duracao duracao;

    @SerializedName("status")
    private String status;

    public Elementos(Distancia distancia, Duracao duracao, String status) {

        this.distancia = distancia;
        this.duracao = duracao;
        this.status = status;

    }

    public Distancia getDistancia() {
        return distancia;
    }

    public void setDistancia(Distancia distancia) {
        this.distancia = distancia;
    }

    public Duracao getDuracao() {
        return duracao;
    }

    public void setDuracao(Duracao duracao) {
        this.duracao = duracao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
