package br.com.fgr.emergencia.models.directions;

import br.com.fgr.emergencia.models.commons.Distancia;
import br.com.fgr.emergencia.models.commons.Duracao;
import br.com.fgr.emergencia.models.general.Coordenada;
import com.google.gson.annotations.SerializedName;

public class Etapa {

    @SerializedName("html_instructions") private String instrucoes;
    @SerializedName("distance") private Distancia distancia;
    @SerializedName("duration") private Duracao duracao;
    @SerializedName("start_location") private Coordenada localidadeInicial;
    @SerializedName("end_location") private Coordenada localidadeFinal;

    public String getInstrucoes() {
        return instrucoes;
    }

    public Distancia getDistancia() {
        return distancia;
    }

    public Duracao getDuracao() {
        return duracao;
    }

    public Coordenada getLocalidadeInicial() {
        return localidadeInicial;
    }

    public Coordenada getLocalidadeFinal() {
        return localidadeFinal;
    }
}