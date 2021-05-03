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
	@JoinTable(
			name = "compra_produto", joinColumns = @JoinColumn(
			name = "compra_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(
			name = "prod_id", referencedColumnName = "id"))
	private List<Produto> listaCompras;

	@OneToOne
	private Cliente cliente;

	private String endereco;

	@Enumerated(EnumType.STRING)
	private FormaPgto formaPgto;

	@Enumerated(EnumType.STRING)
	private CompraStatus compraStatus;

	@ManyToMany
	@JoinTable(
			name = "compra_cupom", joinColumns = @JoinColumn(
			name = "compra_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(
			name = "cupom_id", referencedColumnName = "codigo"))
	private List<Cupom> cupons;
	
	private String documento;
	private int parcelas;
	private BigDecimal valorTotal;
	private BigDecimal valorParcela;

	public List<Produto> getListaCompras() {
		return listaCompras;
	}

	public void setListaCompras(List<Produto> listaCompras) {
		this.listaCompras = listaCompras;
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

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
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
		
		for(Compra compra : compras) {
			if(compra.getCompraStatus().equals(situacao)) {
				return compra;
			}
		}
		
				
//		for (Compra compra : compras) {
//			if (compra.getCompraStatus().equals(CompraStatus.valueOf("ANDAMENTO"))) {
//				return compra;
//			}
//		}
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
	
	
}
