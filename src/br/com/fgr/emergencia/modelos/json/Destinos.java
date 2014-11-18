package br.com.fgr.emergencia.modelos.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Destinos {

	@SerializedName("elements")
	private List<Elementos> elementos;

	public Destinos(List<Elementos> elementos) {
		this.elementos = elementos;
	}

	public List<Elementos> getElementos() {
		return elementos;
	}

	public void setElementos(List<Elementos> elementos) {
		this.elementos = elementos;
	}

}
