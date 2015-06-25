package br.com.fgr.emergencia.models.distancematrix;

import java.util.List;

import br.com.fgr.emergencia.models.general.Coordenada;

public class DistanceMatrixRequest {

    private Coordenada origem;
    private List<Coordenada> destinos;
    private String modo;

    public DistanceMatrixRequest(Coordenada origem, List<Coordenada> destinos, String modo) {
        this.origem = origem;
        this.destinos = destinos;
        this.modo = modo;
    }

    public String getOrigem() {
        return origem.getLat() + "," + origem.getLgn();
    }

    public String getDestinos() {

        StringBuilder sb = new StringBuilder();

        for (Coordenada c : destinos) {

            sb.append(c.getLat());
            sb.append(",");
            sb.append(c.getLgn());
            sb.append("|");

        }

        return sb.toString();

    }

    public String getModo() {
        return modo;
    }

}