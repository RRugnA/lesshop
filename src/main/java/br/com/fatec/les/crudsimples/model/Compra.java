package br.com.fatec.les.crudsimples.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

@Entity
public class Compra extends EntidadeDominio {

//	@ManyToMany
//	@JoinTable(name = "compra_produto", joinColumns = @JoinColumn(name = "compra_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "prod_id", referencedColumnName = "id"))
//	private List<Produto> listaCompras;

	@OneToMany(mappedBy = "compra")
	private Set<CompraProduto> listaCompras = new HashSet<>();

	@OneToOne
	private Cliente cliente;

	private String endereco;

	@Enumerated(EnumType.STRING)
	private FormaPgto formaPgto;

	@Enumerated(EnumType.STRING)
	@OrderBy("dataCadastro ASCS")
	private CompraStatus compraStatus;

	@ManyToMany
	@JoinTable(name = "compra_cupom", joinColumns = @JoinColumn(name = "compra_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "cupom_id", referencedColumnName = "codigo"))
	private List<Cupom> cupons;

	@ManyToMany
	@JoinTable(name = "compra_documento", joinColumns = @JoinColumn(name = "compra_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "documento_id", referencedColumnName = "id"))
	private List<Documento> documentos = new ArrayList<Documento>();

	private int parcelas;
	private int parcelas2;
	private BigDecimal valorTotal;
	private BigDecimal valorParcela;
	private BigDecimal valorParcela2;
	private int qtde;

//	public List<Produto> getListaCompras() {
//		return listaCompras;
//	}
//
//	public void setListaCompras(List<Produto> listaCompras) {
//		this.listaCompras = listaCompras;
//	}

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

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public void addDocumento(Documento documento) {
		this.documentos.add(documento);
	}

	public void removeDocumento(Documento documento) {
		this.documentos.remove(documento);
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

	public int getParcelas2() {
		return parcelas2;
	}

	public void setParcelas2(int parcelas2) {
		this.parcelas2 = parcelas2;
	}

	public CompraStatus getCompraStatus() {
		return compraStatus;
	}

	public void setCompraStatus(CompraStatus compraStatus) {
		this.compraStatus = compraStatus;
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

	public BigDecimal getValorParcela2() {
		return valorParcela2;
	}

	public void setValorParcela2(BigDecimal valorParcela2) {
		this.valorParcela2 = valorParcela2;
	}

	public List<Cupom> getCupons() {
		return cupons;
	}

	public void setCupons(List<Cupom> cupons) {
		this.cupons = cupons;
	}

	public void addCupom(Cupom cupom) {
		this.cupons.add(cupom);
	}

	public void removeCupom(Cupom cupom) {
		this.cupons.remove(cupom);
	}

	public Compra localizaCompra(CompraStatus situacao, List<Compra> compras) {

		for (Compra compra : compras) {
			if (compra.getCompraStatus().equals(situacao)) {
				return compra;
			}
		}

		return null;
	}

	public BigDecimal toValorParcela(String inputParcela) {
		String[] splitParcela = inputParcela.split(":");
		BigDecimal valor = new BigDecimal(splitParcela[0]);
		return valor;
	}

	public int toQtdeParcela(String inputParcela) {
		String[] splitParcela = inputParcela.split(":");
		return Integer.parseInt(splitParcela[1]);
	}

	public int getQtde() {
		return qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}

	public Set<CompraProduto> getListaCompras() {
		return listaCompras;
	}

	public void setListaCompras(Set<CompraProduto> listaCompras) {
		this.listaCompras = listaCompras;
	}

}
