package br.com.fgr.emergencia.models.directions;

import br.com.fgr.emergencia.models.commons.JsonResponse;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DirectionResponse implements JsonResponse {

    @SerializedName("status") private String statusTransacao;
    @SerializedName("routes") private List<Route> rota;

    public DirectionResponse(String statusTransacao, List<Route> rota) {
        this.statusTransacao = statusTransacao;
        this.rota = rota;
    }

    public String getStatusTransacao() {
        return statusTransacao;
    }

    public List<Route> getRota() {
        return rota;
    }
}