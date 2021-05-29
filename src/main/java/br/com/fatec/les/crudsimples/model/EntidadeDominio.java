package br.com.fatec.les.crudsimples.model;

import java.time.LocalDate;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntidadeDominio {

	private LocalDate dataCadastro;

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

}
