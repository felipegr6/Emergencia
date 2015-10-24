package br.com.fgr.emergencia.models.events;

import java.util.List;

import br.com.fgr.emergencia.models.general.Coordenada;

public class LoadListEvent {

    private Coordenada coordenadaAtual;
    private List<Coordenada> coordenadas;

    public LoadListEvent(Coordenada coordenadaAtual, List<Coordenada> coordenadas) {
        this.coordenadaAtual = coordenadaAtual;
        this.coordenadas = coordenadas;
    }

    public Coordenada getCoordenadaAtual() {
        return coordenadaAtual;
    }

    public List<Coordenada> getCoordenadas() {
        return coordenadas;
    }

}