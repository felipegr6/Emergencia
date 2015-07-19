package br.com.fgr.emergencia.models.general;

public class Configuracao {

    private int raio;
    private int hospitais;
    private String modo;

    public Configuracao(int raio, int hospitais, String modo) {
        this.raio = raio;
        this.hospitais = hospitais;
        this.modo = modo;
    }

    public int getRaio() {
        return raio;
    }

    public int getHospitais() {
        return hospitais;
    }

    public String getModo() {
        return modo;
    }

}