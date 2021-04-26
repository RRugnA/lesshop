package br.com.fatec.les.crudsimples.dto;

import java.time.LocalDate;

import br.com.fatec.les.crudsimples.model.Compra;
import br.com.fatec.les.crudsimples.model.CompraStatus;

public class RequisicaoCompra {

	private String id;
	private String cliente;
	private String endereco;
	private String documento;
	private String parcela;
	private String valorTotal;
	private String nomeProduto;
	private String compraStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getParcela() {
		return parcela;
	}

	public void setParcela(String parcela) {
		this.parcela = parcela;
	}

	public String getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getCompraStatus() {
		return compraStatus;
	}

	public void setCompraStatus(String compraStatus) {
		this.compraStatus = compraStatus;
	}

	public Compra toCompra() {
		Compra compra = new Compra();

		compra.setDataCadastro(LocalDate.now());
		compra.setCompraStatus(CompraStatus.ANDAMENTO);

		return compra;
	}

}
