package br.com.fatec.les.crudsimples.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Compra extends EntidadeDominio {

	@ManyToMany
	@JoinTable(name = "compra_produto", joinColumns = @JoinColumn(name = "compra_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "produto_id", referencedColumnName = "id"))
	private List<Produto> produtos;

	@OneToOne
	private Cliente cliente;

	private String endereco;

	@Enumerated(EnumType.STRING)
	private FormaPgto formaPgto;

	@Enumerated(EnumType.STRING)
	private CompraStatus compraStatus;

	private String documento;
	private int parcelas;
	private BigDecimal valorTotal;

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
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

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public FormaPgto getFormaPgto() {
		return formaPgto;
	}

	public void setFormaPgto(FormaPgto formaPgto) {
		this.formaPgto = formaPgto;
	}

	public int getParcelas() {
		return parcelas;
	}

	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}

	public CompraStatus getCompraStatus() {
		return compraStatus;
	}

	public void setCompraStatus(CompraStatus compraStatus) {
		this.compraStatus = compraStatus;
	}

}
