package br.com.fgr.emergencia.models.general;

public class Configuracao {

    private int raio;
    private int hospitais;

    public Configuracao(int raio, int hospitais) {
        this.raio = raio;
        this.hospitais = hospitais;
    }

    public int getRaio() {
        return raio;
    }

    public void setRaio(int raio) {
        this.raio = raio;
    }

    public int getHospitais() {
        return hospitais;
    }

    public void setHospitais(int hospitais) {
        this.hospitais = hospitais;
    }

}
