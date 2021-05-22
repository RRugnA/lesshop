package br.com.fatec.les.crudsimples.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Cliente extends EntidadeDominio {

	private String nome;
	private String numeroDocumento;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	private Usuario usuario;

	@Enumerated(EnumType.STRING)
	private TipoCliente tipoCliente;

	@Enumerated(EnumType.STRING)
	private TipoDocumento tipoDocumento;

	@Enumerated(EnumType.STRING)
	private TipoStatus tipoStatus;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Endereco> endereço;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Documento> documentos;

	@ManyToMany
	@JoinTable(name = "cliente_produto", joinColumns = @JoinColumn(name = "cliente_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "produto_id", referencedColumnName = "id"))
	private List<Produto> produtos;

	private BigDecimal valorDeCompra;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	private Compra compra;
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipo) {
		this.tipoCliente = tipo;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<Endereco> getEndereço() {
		return endereço;
	}

	public void setEndereço(List<Endereco> endereço) {
		this.endereço = endereço;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public TipoStatus getTipoStatus() {
		return tipoStatus;
	}

	public void setTipoStatus(TipoStatus tipoStatus) {
		this.tipoStatus = tipoStatus;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public void addProduto(Produto produto) {
		this.produtos.add(produto);
	}
	
	public void removeProduto(Produto produto) {
		this.produtos.remove(produto);
	}

	public Produto getProduto(int index) {
		return this.produtos.get(index);
	}

	public BigDecimal getValorDeCompra() {
		return valorDeCompra;
	}

	public void setValorDeCompra(BigDecimal valorDoProduto) {
		this.valorDeCompra = valorDoProduto;
	}
	
	public void addValorDeCompra(BigDecimal valorDoProduto, int quantidade) {
		
		BigDecimal valor = new BigDecimal("0");
		valor = valor.add(valorDoProduto);
		valor = valor.multiply(new BigDecimal(quantidade));
		
		if(this.valorDeCompra != null) {
			valor = valor.add(this.valorDeCompra);
		} 
		
		this.valorDeCompra = valor;
	}
	
	public void zeraValorDeCompra() {
		this.valorDeCompra = new BigDecimal(0);
	}

}
