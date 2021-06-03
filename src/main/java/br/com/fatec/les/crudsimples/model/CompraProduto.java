package br.com.fatec.les.crudsimples.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class CompraProduto {

	@EmbeddedId
	private CompraProdutoId id = new CompraProdutoId();

	@ManyToOne
	@MapsId("compraId")
	private Compra compra;

	@ManyToOne
	@MapsId("produtoId")
	private Produto produto;

	@Enumerated(EnumType.STRING)
	private StatusTroca statusTroca;

	private int quantidade;
	
	public CompraProduto() {
		
	}

	public CompraProduto(Produto produto, int quantidade, Compra compra, StatusTroca status) {
		this.produto = produto;
		this.quantidade = quantidade;
		this.compra = compra;
		this.statusTroca = status;
	}
	
	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public StatusTroca getStatusTroca() {
		return statusTroca;
	}

	public void setStatusTroca(StatusTroca statusTroca) {
		this.statusTroca = statusTroca;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
