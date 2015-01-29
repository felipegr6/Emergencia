package br.com.fgr.emergencia.models.directions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {

    @SerializedName("summary")
    private String descricao;
    @SerializedName("legs")
    private List<Trecho> trechos;
    @SerializedName("waypoint_order")
    private List<Integer> ordemPontos;
    @SerializedName("overview_polyline")
    private Polilinha polilinha;
    @SerializedName("bounds")
    private Fronteira fronteira;
    @SerializedName("copyrights")
    private String direitosReservados;
    @SerializedName("warnings")
    private List<String> pontosAtencao;

    public Route(String descricao, List<Trecho> trechos, List<Integer> ordemPontos, Polilinha polilinha, Fronteira fronteira, String direitosReservados, List<String> pontosAtencao) {
        this.descricao = descricao;
        this.trechos = trechos;
        this.ordemPontos = ordemPontos;
        this.polilinha = polilinha;
        this.fronteira = fronteira;
        this.direitosReservados = direitosReservados;
        this.pontosAtencao = pontosAtencao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Trecho> getTrechos() {
        return trechos;
    }

    public void setTrechos(List<Trecho> trechos) {
        this.trechos = trechos;
    }

    public List<Integer> getOrdemPontos() {
        return ordemPontos;
    }

    public void setOrdemPontos(List<Integer> ordemPontos) {
        this.ordemPontos = ordemPontos;
    }

    public Polilinha getPolilinha() {
        return polilinha;
    }

    public void setPolilinha(Polilinha polilinha) {
        this.polilinha = polilinha;
    }

    public Fronteira getFronteira() {
        return fronteira;
    }

    public void setFronteira(Fronteira fronteira) {
        this.fronteira = fronteira;
    }

    public String getDireitosReservados() {
        return direitosReservados;
    }

    public void setDireitosReservados(String direitosReservados) {
        this.direitosReservados = direitosReservados;
    }

    public List<String> getPontosAtencao() {
        return pontosAtencao;
    }

    public void setPontosAtencao(List<String> pontosAtencao) {
        this.pontosAtencao = pontosAtencao;
    }

}