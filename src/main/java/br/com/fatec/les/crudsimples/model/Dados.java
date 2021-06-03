package br.com.fatec.les.crudsimples.model;

import java.util.List;

public class Dados {

	private List<Integer> qtde;
	private List<String> data;

	public Dados(List<Integer> qtde, List<String> data) {
		this.qtde = qtde;
		this.data = data;
	}

	public List<Integer> getQtde() {
		return qtde;
	}

	public void setQtde(List<Integer> qtde) {
		this.qtde = qtde;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

}
