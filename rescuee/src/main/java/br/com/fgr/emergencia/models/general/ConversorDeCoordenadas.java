package br.com.fgr.emergencia.models.general;

import android.support.annotation.NonNull;
import br.com.fgr.emergencia.models.events.LoadListEvent;
import java.util.ArrayList;
import java.util.List;

public class ConversorDeCoordenadas {

    private int zoom;
    private Coordenada coordenadaCentral;
    private List<Coordenada> coordenadas;

    public ConversorDeCoordenadas(int zoom, @NonNull LoadListEvent event) {

        this.zoom = zoom;
        this.coordenadaCentral = event.getCoordenadaAtual();
        this.coordenadas = event.getCoordenadas();
    }

    public List<Coordenada> getCoordenadasPadronizadas() {

        List<Coordenada> novasCoordenadas = new ArrayList<>();

        for (Coordenada c : coordenadas) {

            double xPadronizado = zoom * (coordenadaCentral.getLat() - c.getLat());
            double yPadronizado = zoom * (coordenadaCentral.getLng() - c.getLng());

            novasCoordenadas.add(new Coordenada(xPadronizado, yPadronizado));
        }

        return novasCoordenadas;
    }
}