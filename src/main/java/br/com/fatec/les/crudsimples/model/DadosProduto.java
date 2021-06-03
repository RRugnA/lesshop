package br.com.fatec.les.crudsimples.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DadosProduto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dadosId;

	@ManyToOne
	private Produto produto;

	private int qtde;

	private LocalDate dataCadastro;
	
	public DadosProduto(Produto produto, int qtde) {
		this.produto = produto;
		this.qtde = qtde;
		this.dataCadastro = LocalDate.now();
	}
	
	public DadosProduto() {
		
	}

	public Long getDadosId() {
		return dadosId;
	}

	public void setDadosId(Long dadosId) {
		this.dadosId = dadosId;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQtde() {
		return qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Override
	public String toString() {
		return "DadosProduto [dadosId=" + dadosId + ", produtos=" + produto + ", qtde=" + qtde + ", dataCadastro="
				+ dataCadastro + "]";
	}

}
