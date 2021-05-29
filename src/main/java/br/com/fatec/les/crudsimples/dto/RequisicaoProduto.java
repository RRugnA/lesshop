package br.com.fatec.les.crudsimples.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.model.StatusProduto;
import br.com.fatec.les.crudsimples.model.TipoProduto;

public class RequisicaoProduto {

	private String produtoId;
	private String nome;
	private String qtd;
	private String tipoProduto;
	private String img;
	private String valor;
	private String descricao;
	private String estoque;
	private String valorCusto;

	public String getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(String produtoId) {
		this.produtoId = produtoId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getQtd() {
		return qtd;
	}

	public void setQtd(String qtd) {
		this.qtd = qtd;
	}

	public String getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(String tipo) {
		this.tipoProduto = tipo;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEstoque() {
		return estoque;
	}

	public void setEstoque(String estoque) {
		this.estoque = estoque;
	}

	public String getValorCusto() {
		return valorCusto;
	}

	public void setValorCusto(String valorCusto) {
		this.valorCusto = valorCusto;
	}

	public Produto toProduto() {
		Produto prod = new Produto();

		prod.setNome(nome);

		if (this.getProdutoId() != null) {
			prod.setProdutoId(Long.valueOf(produtoId));
		}
		if (this.getQtd() != null) {
			prod.setQtde(Integer.parseInt(qtd));
		}
		if (this.getEstoque() != null) {
			prod.setEstoque(Integer.parseInt(estoque));
		}

		prod.setTipoProduto(TipoProduto.valueOf(tipoProduto));
		prod.setImg(img);

		BigDecimal valorBD = new BigDecimal(valorCusto);
		prod.setValorCusto(valorBD);
		
		BigDecimal valorVenda = new BigDecimal(0);
		valorVenda = valorBD.multiply(new BigDecimal(0.2));
		valorVenda = valorVenda.add(valorBD);
		valorVenda.setScale(2, BigDecimal.ROUND_DOWN);
		prod.setValor(valorVenda);
		
		prod.setDescricao(descricao);
		prod.setDataCadastro(LocalDate.now());

		prod.setStatusProduto(StatusProduto.ESTOQUE);

		return prod;
	}

}
