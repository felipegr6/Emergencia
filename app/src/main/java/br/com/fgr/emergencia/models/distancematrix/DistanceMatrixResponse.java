package br.com.fgr.emergencia.models.distancematrix;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistanceMatrixResponse {

    @SerializedName("destination_addresses")
    private List<String> destinos;

    @SerializedName("origin_addresses")
    private List<String> origens;

    @SerializedName("rows")
    private List<Destinos> linhas;

    @SerializedName("status")
    private String status;

    public DistanceMatrixResponse(List<String> destinos, List<String> origens,
                                  List<Destinos> linhas, String status) {

        this.destinos = destinos;
        this.origens = origens;
        this.linhas = linhas;
        this.status = status;

    }

    public List<String> getDestinos() {
        return destinos;
    }

    public void setDestinos(List<String> destinos) {
        this.destinos = destinos;
    }

    public List<String> getOrigens() {
        return origens;
    }

    public void setOrigens(List<String> origens) {
        this.origens = origens;
    }

    public List<Destinos> getLinhas() {
        return linhas;
    }

    public void setLinhas(List<Destinos> linhas) {
        this.linhas = linhas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}