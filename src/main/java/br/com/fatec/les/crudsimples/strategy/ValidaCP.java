package br.com.fatec.les.crudsimples.strategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.fatec.les.crudsimples.model.CompraProduto;
import br.com.fatec.les.crudsimples.model.Produto;

public class ValidaCP {

	public static BigDecimal getValorTotal(List<CompraProduto> lcp) {
		BigDecimal valorTotalProdutos = new BigDecimal(0);
		for(CompraProduto cp : lcp) {
			Produto produto = cp.getProduto();		
			valorTotalProdutos = valorTotalProdutos.add(produto.getValor());
		}		
		return valorTotalProdutos;
	}
	
	public static List<Produto> getProdutos(List<CompraProduto> lcp) {
		List<Produto> produtos = new ArrayList<Produto>();
		for(CompraProduto cp : lcp) {
			Produto produto = cp.getProduto();
			produtos.add(produto);			
		}		
		return produtos;
	}
}
