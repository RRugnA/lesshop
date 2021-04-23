package br.com.fatec.les.crudsimples.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;

@Entity
public class Produto extends EntidadeDominio {

	private String proNome;
	private String proImg;
	private int proQtde;
	private String proDesc;
	private BigDecimal proValor;
	private int proEstoque;

	@Enumerated(EnumType.STRING)
	private TipoProduto prodTipo;

	@Enumerated(EnumType.STRING)
	private StatusProduto prodStatus;

	@ManyToMany(mappedBy = "produtos")
	private List<Cliente> clientes;

	@ManyToMany(mappedBy = "produtos")
	private List<Compra> compras;

	public String getProNome() {
		return proNome;
	}

	public void setProNome(String proNome) {
		this.proNome = proNome;
	}

	public String getProImg() {
		return proImg;
	}

	public void setProImg(String proImg) {
		this.proImg = proImg;
	}

	public int getProQtde() {
		return proQtde;
	}

	public void setProQtde(int proQtde) {
		this.proQtde = proQtde;
	}

	public String getProDesc() {
		return proDesc;
	}

	public void setProDesc(String proDesc) {
		this.proDesc = proDesc;
	}

	public BigDecimal getProValor() {
		return proValor;
	}

	public void setProValor(BigDecimal proValor) {
		this.proValor = proValor;
	}

	public TipoProduto getProdTipo() {
		return prodTipo;
	}

	public void setProdTipo(TipoProduto prodTipo) {
		this.prodTipo = prodTipo;
	}

	public StatusProduto getProdStatus() {
		return prodStatus;
	}

	public void setProdStatus(StatusProduto prodStatus) {
		this.prodStatus = prodStatus;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public int getProEstoque() {
		return proEstoque;
	}

	public void setProEstoque(int proEstoque) {
		this.proEstoque = proEstoque;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

}
