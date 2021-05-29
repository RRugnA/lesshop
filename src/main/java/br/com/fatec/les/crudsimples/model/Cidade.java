package br.com.fatec.les.crudsimples.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Cidade {

	private String cidade;
	
	@Embedded
	private Estado estado;

	
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
