package br.com.fatec.les.crudsimples.strategy;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.les.crudsimples.model.Produto;

public class ValidaProduto {

	public static List<String> getAlteracao(Produto antes, Produto depois) {
		List<String> alteracoes = new ArrayList<String>();
		
		if(!antes.getNome().equals(depois.getNome())) {
			System.out.println("nome diferente");
			alteracoes.add("nome " + antes.getNome() + " para " + depois.getNome());
		}
		if(!antes.getImg().equals(depois.getImg())) {
			alteracoes.add(antes.getNome() + " img " + antes.getImg() + " para " + depois.getImg());
		}
		if(!antes.getValorCusto().equals(depois.getValorCusto())) {
			alteracoes.add(antes.getNome() + " valor de custo " + antes.getValorCusto().toString() + " para " + depois.getValorCusto().toString());
		}
		if(!antes.getDescricao().equals(depois.getDescricao())) {
			alteracoes.add(antes.getNome() + " descrição " + antes.getDescricao() + " para " + depois.getDescricao());
		}
		if(antes.getEstoque() != depois.getEstoque()) {
			alteracoes.add(antes.getNome() + " estoque " + String.valueOf(antes.getEstoque()) + " para " + String.valueOf(depois.getEstoque()));
		}
		if(!antes.getTipoProduto().equals(depois.getTipoProduto())) {
			alteracoes.add(antes.getNome() + " tipo " + antes.getTipoProduto().toString() + " para " + depois.getTipoProduto().toString());
		}
		
		for(String alteracao : alteracoes ) {
			System.out.println(alteracao);
		}
		
		return alteracoes;		
	}
	
	public static String getStatusAlteracao(Produto antes, Produto depois) {
		String alteracao = "";
		if(!antes.getStatusProduto().equals(depois.getStatusProduto())) {
			alteracao = antes.getNome() + " status " + antes.getStatusProduto().toString() + " para " + depois.getStatusProduto().toString();
		}
		
		System.out.println(alteracao);
		
		return alteracao;
	}
}
