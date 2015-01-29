package br.com.fgr.emergencia.models.directions;

import com.google.gson.annotations.SerializedName;

import br.com.fgr.emergencia.models.commons.JsonResponse;

public class DirectionResponse implements JsonResponse {

    @SerializedName("status")
    private String statusTransacao;
    @SerializedName("routes")
    private Route rota;

    public DirectionResponse(String statusTransacao, Route rota) {
        this.statusTransacao = statusTransacao;
        this.rota = rota;
    }

    public String getStatusTransacao() {
        return statusTransacao;
    }

    public void setStatusTransacao(String statusTransacao) {
        this.statusTransacao = statusTransacao;
    }

    public Route getRota() {
        return rota;
    }

    public void setRota(Route rota) {
        this.rota = rota;
    }

}