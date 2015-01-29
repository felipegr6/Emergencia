package br.com.fgr.emergencia.modelos.json;

import com.google.gson.annotations.SerializedName;

public class Duracao {

    @SerializedName("text")
    private String texto;
    @SerializedName("value")
    private long valor;

    public Duracao(String texto, long valor) {
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
