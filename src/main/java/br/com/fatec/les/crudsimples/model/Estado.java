package br.com.fatec.les.crudsimples.model;

import javax.persistence.Embeddable;

@Embeddable
public class Estado {

	private String estado;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	

}
