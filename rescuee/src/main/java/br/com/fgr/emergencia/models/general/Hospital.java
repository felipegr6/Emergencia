package br.com.fgr.emergencia.models.general;

public class Hospital implements Comparable<Hospital> {

    private int id;
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

    public Hospital(String nome, String distancia, String tempo, long valorTempo, Coordenada coord,
        double espera) {

        this.nome = nome;
        this.distancia = distancia;
        this.tempo = tempo;
        this.valorTempo = valorTempo;
        this.localizacao = coord;
        this.espera = espera;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setEspera(long espera) {
        this.espera = espera;
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

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public long getValorDistancia() {
        return valorDistancia;
    }

    public void setValorDistancia(long valorDistancia) {
        this.valorDistancia = valorDistancia;
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