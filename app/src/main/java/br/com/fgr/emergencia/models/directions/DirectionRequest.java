package br.com.fgr.emergencia.models.directions;

import br.com.fgr.emergencia.models.general.Coordenada;

public class DirectionRequest {

    private Coordenada origem;
    private Coordenada destino;
    private boolean sensor;

    public DirectionRequest(Coordenada origem, Coordenada destino, boolean sensor) {

        this.origem = origem;
        this.destino = destino;
        this.sensor = sensor;

    }

    public Coordenada getOrigem() {
        return origem;
    }

    public void setOrigem(Coordenada origem) {
        this.origem = origem;
    }

    public Coordenada getDestino() {
        return destino;
    }

    public void setDestino(Coordenada destino) {
        this.destino = destino;
    }

    public boolean isSensor() {
        return sensor;
    }

    public void setSensor(boolean sensor) {
        this.sensor = sensor;
    }

}