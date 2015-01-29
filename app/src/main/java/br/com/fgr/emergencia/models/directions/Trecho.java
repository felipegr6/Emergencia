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

    public Trecho(List<Etapa> etapas, Distancia distancia, Duracao duracao, TempoEstimado tempoEstimado, Coordenada localidadeInicial, Coordenada localidadeFinal, String enderecoInicial, String enderecoFinal) {

        this.etapas = etapas;
        this.distancia = distancia;
        this.duracao = duracao;
        this.tempoEstimado = tempoEstimado;
        this.localidadeInicial = localidadeInicial;
        this.localidadeFinal = localidadeFinal;
        this.enderecoInicial = enderecoInicial;
        this.enderecoFinal = enderecoFinal;

    }

    public List<Etapa> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<Etapa> etapas) {
        this.etapas = etapas;
    }

    public Distancia getDistancia() {
        return distancia;
    }

    public void setDistancia(Distancia distancia) {
        this.distancia = distancia;
    }

    public Duracao getDuracao() {
        return duracao;
    }

    public void setDuracao(Duracao duracao) {
        this.duracao = duracao;
    }

    public TempoEstimado getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(TempoEstimado tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }

    public Coordenada getLocalidadeInicial() {
        return localidadeInicial;
    }

    public void setLocalidadeInicial(Coordenada localidadeInicial) {
        this.localidadeInicial = localidadeInicial;
    }

    public Coordenada getLocalidadeFinal() {
        return localidadeFinal;
    }

    public void setLocalidadeFinal(Coordenada localidadeFinal) {
        this.localidadeFinal = localidadeFinal;
    }

    public String getEnderecoInicial() {
        return enderecoInicial;
    }

    public void setEnderecoInicial(String enderecoInicial) {
        this.enderecoInicial = enderecoInicial;
    }

    public String getEnderecoFinal() {
        return enderecoFinal;
    }

    public void setEnderecoFinal(String enderecoFinal) {
        this.enderecoFinal = enderecoFinal;
    }
}
