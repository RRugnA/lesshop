package br.com.fatec.les.crudsimples.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
public class Documento extends EntidadeDominio {

	private String codigoCartao;
	private String numeroCartao;
	private String nomeCartao;
	private LocalDate validadeCartao;
	private boolean preferencial;

	@Enumerated(EnumType.STRING)
	private BandeiraCartao bandeiraCartao;

	@ManyToOne
	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getCodigoCartao() {
		return codigoCartao;
	}

	public void setCodigoCartao(String codigoCartao) {
		this.codigoCartao = codigoCartao;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public LocalDate getValidadeCartao() {
		return validadeCartao;
	}

	public void setValidadeCartao(LocalDate validadeCartao) {
		this.validadeCartao = validadeCartao;
	}

	public BandeiraCartao getBandeiraCartao() {
		return bandeiraCartao;
	}

	public void setBandeiraCartao(BandeiraCartao bandeiraCartao) {
		this.bandeiraCartao = bandeiraCartao;
	}

	public String getNomeCartao() {
		return nomeCartao;
	}

	public void setNomeCartao(String nomeCartao) {
		this.nomeCartao = nomeCartao;
	}

	public boolean isPreferencial() {
		return preferencial;
	}

	public void setPreferencial(boolean preferencial) {
		this.preferencial = preferencial;
	}

}
