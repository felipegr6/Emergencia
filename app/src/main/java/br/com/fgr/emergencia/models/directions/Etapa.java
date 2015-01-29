package br.com.fgr.emergencia.models.directions;

import com.google.gson.annotations.SerializedName;

import br.com.fgr.emergencia.models.commons.Distancia;
import br.com.fgr.emergencia.models.commons.Duracao;
import br.com.fgr.emergencia.models.general.Coordenada;

public class Etapa {

    @SerializedName("html_instructions")
    private String instrucoes;
    @SerializedName("distance")
    private Distancia distancia;
    @SerializedName("duration")
    private Duracao duracao;
    @SerializedName("start_location")
    private Coordenada localidadeInicial;
    @SerializedName("end_location")
    private Coordenada localidadeFinal;

    public Etapa(String instrucoes, Distancia distancia, Duracao duracao, Coordenada localidadeInicial, Coordenada localidadeFinal) {
        this.instrucoes = instrucoes;
        this.distancia = distancia;
        this.duracao = duracao;
        this.localidadeInicial = localidadeInicial;
        this.localidadeFinal = localidadeFinal;
    }

    public String getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
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

}