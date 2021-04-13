package br.com.fatec.les.crudsimples.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.model.StatusProduto;
import br.com.fatec.les.crudsimples.model.TipoProduto;

public class RequisicaoProduto {

	private String id;
	private String nomeProduto;
	private String qtdProduto;
	private String tipoProduto;
	private String imgProduto;
	private String valorProduto;
	private String descProduto;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getQtdProduto() {
		return qtdProduto;
	}

	public void setQtdProduto(String qtdProduto) {
		this.qtdProduto = qtdProduto;
	}

	public String getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(String tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public String getImgProduto() {
		return imgProduto;
	}

	public void setImgProduto(String imgProduto) {
		this.imgProduto = imgProduto;
	}

	public String getValorProduto() {
		return valorProduto;
	}

	public void setValorProduto(String valorProduto) {
		this.valorProduto = valorProduto;
	}

	public String getDescProduto() {
		return descProduto;
	}

	public void setDescProduto(String descProduto) {
		this.descProduto = descProduto;
	}

	public Produto toProduto() {
		Produto prod = new Produto();

		prod.setProNome(nomeProduto);
		prod.setProQtde(Integer.parseInt(qtdProduto));
		prod.setProdTipo(TipoProduto.valueOf(tipoProduto));
		prod.setProImg(imgProduto);

		BigDecimal valor = new BigDecimal(valorProduto);
		prod.setProValor(valor);

		prod.setProDesc(descProduto);
		prod.setDataCadastro(LocalDate.now());

		prod.setProdStatus(StatusProduto.ESTOQUE);

		return prod;
	}

}
