package br.com.fatec.les.crudsimples.dto;

import br.com.fatec.les.crudsimples.business.ValidaData;
import br.com.fatec.les.crudsimples.model.DadosProduto;

public class RequisicaoDadosProduto {

	private String dataInicial;
	private String dataFinal;

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public DadosProduto toDPInicial() {
		DadosProduto dp = new DadosProduto();
		dp.setDataCadastro(ValidaData.toDate(dataInicial));
		return dp;
	}
	
	public DadosProduto toDPFinal() {
		DadosProduto dp = new DadosProduto();
		dp.setDataCadastro(ValidaData.toDate(dataFinal));
		return dp;
	}
}
