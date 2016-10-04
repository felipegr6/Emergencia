package br.com.fgr.emergencia.models.distancematrix;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DistanceMatrixResponse {

    @SerializedName("destination_addresses") private List<String> destinos;

    @SerializedName("origin_addresses") private List<String> origens;

    @SerializedName("rows") private List<Destinos> linhas;

    @SerializedName("status") private String status;

    public List<String> getDestinos() {
        return destinos;
    }

    public List<String> getOrigens() {
        return origens;
    }

    public List<Destinos> getLinhas() {
        return linhas;
    }

    public String getStatus() {
        return status;
    }
}