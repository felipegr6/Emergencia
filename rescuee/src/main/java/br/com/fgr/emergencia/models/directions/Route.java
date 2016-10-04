package br.com.fgr.emergencia.models.directions;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Route {

    @SerializedName("summary") private String descricao;
    @SerializedName("legs") private List<Trecho> trechos;
    @SerializedName("waypoint_order") private List<Integer> ordemPontos;
    @SerializedName("overview_polyline") private Polilinha polilinha;
    @SerializedName("bounds") private Fronteira fronteira;
    @SerializedName("copyrights") private String direitosReservados;
    @SerializedName("warnings") private List<String> pontosAtencao;

    public String getDescricao() {
        return descricao;
    }

    public List<Trecho> getTrechos() {
        return trechos;
    }

    public List<Integer> getOrdemPontos() {
        return ordemPontos;
    }

    public Polilinha getPolilinha() {
        return polilinha;
    }

    public Fronteira getFronteira() {
        return fronteira;
    }

    public String getDireitosReservados() {
        return direitosReservados;
    }

    public List<String> getPontosAtencao() {
        return pontosAtencao;
    }
}