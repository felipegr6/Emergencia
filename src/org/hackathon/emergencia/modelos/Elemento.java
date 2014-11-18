package org.hackathon.emergencia.modelos;

public class Elemento {

    private Valor distancia;
    private Valor duracao;
    private String status;

    public Elemento() {

    }

    public Elemento(Valor distancia, Valor duracao, String status) {

        this.distancia = distancia;
        this.duracao = duracao;
        this.status = status;

    }

    public Valor getDistancia() {

        return distancia;

    }

    public void setDistancia(Valor distancia) {

        this.distancia = distancia;

    }

    public Valor getDuracao() {

        return duracao;

    }

    public void setDuracao(Valor duracao) {

        this.duracao = duracao;

    }

    public String getStatus() {

        return status;

    }

    public void setStatus(String status) {

        this.status = status;

    }

}
