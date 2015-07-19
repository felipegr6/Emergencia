package br.com.fgr.emergencia.models.directions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.fgr.emergencia.models.commons.Distancia;
import br.com.fgr.emergencia.models.commons.Duracao;
import br.com.fgr.emergencia.models.general.Coordenada;

public class Trecho {

    @SerializedName("steps")
    private List<Etapa> etapas;
    @SerializedName("distance")
    private Distancia distancia;
    @SerializedName("duration")
    private Duracao duracao;
    @SerializedName("arrival_time")
    private TempoEstimado tempoEstimado;
    @SerializedName("start_location")
    private Coordenada localidadeInicial;
    @SerializedName("end_location")
    private Coordenada localidadeFinal;
    @SerializedName("start_address")
    private String enderecoInicial;
    @SerializedName("end_address")
    private String enderecoFinal;

    public List<Etapa> getEtapas() {
        return etapas;
    }

    public Distancia getDistancia() {
        return distancia;
    }

    public Duracao getDuracao() {
        return duracao;
    }

    public TempoEstimado getTempoEstimado() {
        return tempoEstimado;
    }

    public Coordenada getLocalidadeInicial() {
        return localidadeInicial;
    }

    public Coordenada getLocalidadeFinal() {
        return localidadeFinal;
    }

    public String getEnderecoInicial() {
        return enderecoInicial;
    }

    public String getEnderecoFinal() {
        return enderecoFinal;
    }

}