package br.com.fgr.emergencia.models.commons;

import com.google.gson.annotations.SerializedName;

public class Distancia {

    @SerializedName("text")
    private String texto;
    @SerializedName("value")
    private long valor;

    public Distancia(String texto, long valor) {

        this.texto = texto;
        this.valor = valor;

    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
    }

}