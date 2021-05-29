package br.com.fatec.les.crudsimples.strategy;

import java.util.List;
import br.com.fatec.les.crudsimples.dto.RequisicaoProduto;
import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.model.StatusProduto;

public class ValidaEstoque {

	public static void controleEstoque(Produto produto) {
		if(produto.getEstoque() == 0) {
			produto.setStatusProduto(StatusProduto.INATIVO);
		} else {
			produto.setStatusProduto(StatusProduto.ESTOQUE);
		}		
	}
	
	public static void baixaEstoque(Produto produto, RequisicaoProduto requisicao) {
		produto.setEstoque(produto.getEstoque() - Integer.parseInt(requisicao.getQtd()));
	}
	
	public static void retornaEstoque(Produto produto) {
		produto.setEstoque(produto.getEstoque() + produto.getQtde());
	}
	
	public static void retornaListaEstoque(List<Produto> produtos) {
		for(Produto produto : produtos) {
			retornaEstoque(produto);
			produto.setQtde(0);
			controleEstoque(produto);
		}
	}
	
	public static void alteraStatus(Produto produto) {
		if(produto.getStatusProduto().equals(StatusProduto.ESTOQUE)) {
			produto.setStatusProduto(StatusProduto.INATIVO);
		} else {
			produto.setStatusProduto(StatusProduto.ESTOQUE);
		}	
	}
	
	public void darBaixaEstoque(Produto produto, RequisicaoProduto requisicao) {
		produto.setEstoque(produto.getEstoque() - Integer.parseInt(requisicao.getQtd()));
	}
	
	public void controleDeEstoque(Produto produto) {
		if(produto.getEstoque() == 0) {
			produto.setStatusProduto(StatusProduto.INATIVO);
		} else {
			produto.setStatusProduto(StatusProduto.ESTOQUE);
		}		
	}
}
