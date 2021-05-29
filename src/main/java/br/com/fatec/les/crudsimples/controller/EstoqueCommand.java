package br.com.fatec.les.crudsimples.controller;

import org.springframework.stereotype.Controller;

import br.com.fatec.les.crudsimples.dto.RequisicaoProduto;
import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.strategy.ValidaEstoque;

@Controller
public class EstoqueCommand {
	
	ValidaEstoque ve = new ValidaEstoque();
	
	public void executeBaixaEstoque(Produto produto, RequisicaoProduto requisicao) {
		ve.darBaixaEstoque(produto, requisicao);
	}
	
	public void executeControleEstoque(Produto produto) {
		ve.controleDeEstoque(produto);
	}
}
