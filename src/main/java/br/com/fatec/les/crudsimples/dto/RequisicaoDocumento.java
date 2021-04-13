package br.com.fatec.les.crudsimples.dto;

import java.time.LocalDate;

import br.com.fatec.les.crudsimples.model.BandeiraCartao;
import br.com.fatec.les.crudsimples.model.Documento;
import br.com.fatec.les.crudsimples.strategy.ValidaData;

public class RequisicaoDocumento {

	private String clienteId;
	private String bandeiraCartao;
	private String codigoCartao;
	private String numeroCartao;
	private String validadeCartao;
	private String nomeCartao;

	public String getClienteId() {
		return clienteId;
	}

	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}

	public String getBandeiraCartao() {
		return bandeiraCartao;
	}

	public void setBandeiraCartao(String bandeiraCartao) {
		this.bandeiraCartao = bandeiraCartao;
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

	public String getValidadeCartao() {
		return validadeCartao;
	}

	public void setValidadeCartao(String validadeCartao) {
		this.validadeCartao = validadeCartao;
	}

	public String getNomeCartao() {
		return nomeCartao;
	}

	public void setNomeCartao(String nomeCartao) {
		this.nomeCartao = nomeCartao;
	}

	public Documento toDocumento() {
		Documento doc = new Documento();

		doc.setBandeiraCartao(BandeiraCartao.valueOf(bandeiraCartao));
		doc.setCodigoCartao(codigoCartao);
		doc.setNumeroCartao(numeroCartao);
		doc.setNomeCartao(nomeCartao);
		doc.setValidadeCartao(ValidaData.toDate(validadeCartao));

		doc.setDataCadastro(LocalDate.now());

		return doc;
	}

}
