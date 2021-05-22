package br.com.fatec.les.crudsimples.strategy;

import java.util.List;
import br.com.fatec.les.crudsimples.dto.RequisicaoProduto;
import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.model.StatusProduto;

public class ValidaEstoque {

	public static void controleEstoque(Produto produto) {
		if(produto.getProEstoque() == 0) {
			produto.setProdStatus(StatusProduto.INATIVO);
		} else {
			produto.setProdStatus(StatusProduto.ESTOQUE);
		}		
	}
	
	public static void baixaEstoque(Produto produto, RequisicaoProduto requisicao) {
		produto.setProEstoque(produto.getProEstoque() - Integer.parseInt(requisicao.getQtdProduto()));
	}
	
	public static void retornaEstoque(Produto produto) {
		produto.setProEstoque(produto.getProEstoque() + produto.getProQtde());
	}
	
	public static void retornaListaEstoque(List<Produto> produtos) {
		for(Produto produto : produtos) {
			retornaEstoque(produto);
			produto.setProQtde(0);
			controleEstoque(produto);
		}
	}
	
	public static void alteraStatus(Produto produto) {
		if(produto.getProdStatus().equals(StatusProduto.ESTOQUE)) {
			produto.setProdStatus(StatusProduto.INATIVO);
		} else {
			produto.setProdStatus(StatusProduto.ESTOQUE);
		}	
	}
}
