package br.com.fgr.emergencia.models.events;

import br.com.fgr.emergencia.models.general.Coordenada;
import java.util.List;

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