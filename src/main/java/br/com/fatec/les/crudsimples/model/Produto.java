package br.com.fatec.les.crudsimples.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Produto extends EntidadeDominio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long produtoId;

	private String nome;
	private String img;
	private int qtde;
	private String descricao;
	private BigDecimal valor;
	private int estoque;
	private BigDecimal valorCusto;

	@Enumerated(EnumType.STRING)
	private TipoProduto tipoProduto;

	@Enumerated(EnumType.STRING)
	private StatusProduto statusProduto;

	@ManyToMany(mappedBy = "produtos")
	private List<Cliente> clientes;

	@OneToMany(mappedBy = "produto")
	private Set<CompraProduto> listaCompras = new HashSet<>();
	
	@OneToMany(mappedBy = "produto")
	private List<DadosProduto> dados;

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getQtde() {
		return qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public BigDecimal getValorCusto() {
		return valorCusto;
	}

	public void setValorCusto(BigDecimal valorCusto) {
		this.valorCusto = valorCusto;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public StatusProduto getStatusProduto() {
		return statusProduto;
	}

	public void setStatusProduto(StatusProduto statusProduto) {
		this.statusProduto = statusProduto;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Set<CompraProduto> getListaCompras() {
		return listaCompras;
	}

	public void setListaCompras(Set<CompraProduto> listaCompras) {
		this.listaCompras = listaCompras;
	}

	@Override
	public String toString() {
		return "Produto [produtoId=" + produtoId + ", nome=" + nome + ", img=" + img + ", qtde=" + qtde + ", descricao="
				+ descricao + ", valor=" + valor + ", estoque=" + estoque + ", valorCusto=" + valorCusto
				+ ", tipoProduto=" + tipoProduto + ", statusProduto=" + statusProduto + ", clientes=" + clientes
				+ ", listaCompras=" + listaCompras + "]";
	}
}
