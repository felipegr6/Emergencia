package br.com.fgr.emergencia.models.directions;

import br.com.fgr.emergencia.models.general.Coordenada;

public class DirectionRequest {

    private Coordenada origem;
    private Coordenada destino;

    public DirectionRequest(Coordenada origem, Coordenada destino) {

        this.origem = origem;
        this.destino = destino;
    }

    public String getOrigem() {
        return origem.getLat() + "," + origem.getLng();
    }

    public String getDestino() {
        return destino.getLat() + "," + destino.getLng();
    }
}