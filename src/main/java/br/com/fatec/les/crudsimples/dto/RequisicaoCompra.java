package br.com.fatec.les.crudsimples.dto;

import java.time.LocalDate;
import java.util.List;

import br.com.fatec.les.crudsimples.model.Compra;
import br.com.fatec.les.crudsimples.model.CompraStatus;

public class RequisicaoCompra {

	private String compraId;
	private String cliente;
	private String endereco;
	private List<String> documento;
	private List<String> valorParcela;
	private String valorTotal;
	private String nomeProduto;
	private String compraStatus;
	private String qtde;

	public String getCompraId() {
		return compraId;
	}

	public void setCompraId(String compraId) {
		this.compraId = compraId;
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

	public List<String> getDocumento() {
		return documento;
	}

	public void setDocumento(List<String> documento) {
		this.documento = documento;
	}

	public void addDocumento(String documento) {
		this.documento.add(documento);
	}

	public List<String> getValorParcela() {
		return valorParcela;
	}

	public String getValorParcela1() {
		return valorParcela.get(0);
	}

	public String getValorParcela2() {
		return valorParcela.get(1);
	}

	public void setValorParcela(List<String> parcela) {
		this.valorParcela = parcela;
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

	public String getQtde() {
		return qtde;
	}

	public void setQtde(String qtde) {
		this.qtde = qtde;
	}

	public Compra toCompra() {
		Compra compra = new Compra();

		compra.setDataCadastro(LocalDate.now());
		compra.setCompraStatus(CompraStatus.ANDAMENTO);

		return compra;
	}

}
