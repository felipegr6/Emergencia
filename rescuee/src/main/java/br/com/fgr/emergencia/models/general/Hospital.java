package br.com.fgr.emergencia.models.general;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass public class Hospital implements RealmModel, Comparable<Hospital> {

    @PrimaryKey private String objectId;
    private String nome;
    private String endereco;
    private String distancia;
    private long valorDistancia;
    private long telefone;
    private Coordenada localizacao;
    private String tempo;
    private long valorTempo;
    private double espera;

    public Hospital() {
    }

    public Hospital(String objectId, String nome, String endereco, String distancia,
        long valorDistancia, long telefone, Coordenada localizacao, String tempo, long valorTempo,
        double espera) {
        this.objectId = objectId;
        this.nome = nome;
        this.endereco = endereco;
        this.distancia = distancia;
        this.valorDistancia = valorDistancia;
        this.telefone = telefone;
        this.localizacao = localizacao;
        this.tempo = tempo;
        this.valorTempo = valorTempo;
        this.espera = espera;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public long getValorDistancia() {
        return valorDistancia;
    }

    public void setValorDistancia(long valorDistancia) {
        this.valorDistancia = valorDistancia;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }

    public Coordenada getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Coordenada localizacao) {
        this.localizacao = localizacao;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public long getValorTempo() {
        return valorTempo;
    }

    public void setValorTempo(long valorTempo) {
        this.valorTempo = valorTempo;
    }

    public double getEspera() {
        return espera;
    }

    public void setEspera(double espera) {
        this.espera = espera;
    }

    @Override public String toString() {
        return this.nome + " a " + this.distancia + " em " + this.tempo;
    }

    @Override public int compareTo(Hospital another) {
        if (this.valorTempo < another.valorTempo) {
            return -1;
        } else if (this.valorTempo > another.valorTempo) return 1;

        return 0;
    }
}
