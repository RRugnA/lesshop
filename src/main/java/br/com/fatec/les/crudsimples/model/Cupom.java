package br.com.fatec.les.crudsimples.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Cupom {

	@Id
	private String codigo;
	
	@Enumerated(EnumType.STRING)
	private TipoCupom tipoCupom;
	private int valorDesconto;
	private LocalDate dataCadastro;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public TipoCupom getTipoCupom() {
		return tipoCupom;
	}

	public void setTipoCupom(TipoCupom tipoCupom) {
		this.tipoCupom = tipoCupom;
	}

	public int getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(int valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	
}
