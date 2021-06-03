package br.com.fatec.les.crudsimples.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import br.com.fatec.les.crudsimples.strategy.NumCartao;

@Entity
public class Compra extends EntidadeDominio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long compraId;

	@OneToMany(mappedBy = "compra")
	private Set<CompraProduto> listaCompras = new HashSet<>();

	@OneToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	private String endereco;

	@Enumerated(EnumType.STRING)
	private FormaPgto formaPgto;

	@Enumerated(EnumType.STRING)
	@OrderBy("dataCadastro ASCS")
	private CompraStatus compraStatus;

	@ManyToMany
	@JoinTable(name = "compra_cupom", joinColumns = @JoinColumn(name = "compra_id", referencedColumnName = "compraId"), inverseJoinColumns = @JoinColumn(name = "cupom_id", referencedColumnName = "codigo"))
	private List<Cupom> cupons;

	private String cartao1;
	private String cartao2;
	private int parcelas;
	private int parcelas2;
	private BigDecimal valorTotal;
	private BigDecimal valorParcela;
	private BigDecimal valorParcela2;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getCompraId() {
		return compraId;
	}

	public void setCompraId(Long compraId) {
		this.compraId = compraId;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

//	public List<Documento> getDocumentos() {
//		return documentos;
//	}
//
//	public void setDocumentos(List<Documento> documentos) {
//		this.documentos = documentos;
//	}
//
//	public void addDocumento(Documento documento) {
//		this.documentos.add(documento);
//	}
//
//	public void removeDocumento(Documento documento) {
//		this.documentos.remove(documento);
//	}

	public String getCartao1() {
		return cartao1;
	}

	public void setCartao1(String cartao1) {
		this.cartao1 = cartao1;
	}

	public List<String> getCartoes() {
		List<String> cartoes = new ArrayList<>();
		if (this.cartao1 != null) {
			cartoes.add(this.cartao1);
		}
		if (this.cartao2 != null) {
			cartoes.add(this.cartao2);
		}
		return cartoes;
	}

	public void removeCartao(String cartao) {
		String verificador1 = NumCartao.gerarNumCartao(this.cartao1);
		String verificador2 = NumCartao.gerarNumCartao(this.cartao2);
		if (cartao.equals(verificador1)) {
			cartao1 = null;
		}
		if (cartao.equals(verificador2)) {
			cartao2 = null;
		}
	}

	public void zeraCartoes() {
		this.cartao1 = null;
		this.cartao2 = null;
	}

	public String getCartao2() {
		return cartao2;
	}

	public void setCartao2(String cartao2) {
		this.cartao2 = cartao2;
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

	public Set<CompraProduto> getListaCompras() {
		return listaCompras;
	}

	public void setListaCompras(Set<CompraProduto> listaCompras) {
		this.listaCompras = listaCompras;
	}

}
