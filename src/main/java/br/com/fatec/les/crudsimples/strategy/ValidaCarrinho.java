package br.com.fatec.les.crudsimples.strategy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.Compra;
import br.com.fatec.les.crudsimples.model.CompraProduto;
import br.com.fatec.les.crudsimples.model.Produto;

@EnableScheduling
public class ValidaCarrinho {
	
	public static void validar(Compra compra) {
		if(!compra.getListaCompras().isEmpty()) {
			someJob();
		}
	}
	
	@Scheduled(fixedRate = 2000)
	static void someJob() {
		System.out.println("Há itens no carrinho...");		
	}
	
	public static List<Produto> getProdutos(Cliente cliente){
		List<Produto> produtos = new ArrayList<Produto>();
		for(Produto produto : cliente.getProdutos()) {
			produtos.add(produto);
		}
		return produtos;
	}
	
	public static void removerListaProdutos(List<Produto> produtos, Cliente cliente) {
		for(Produto produto : cliente.getProdutos()) {
			cliente.removeProduto(produto);
		}
	}
	
	public static List<Produto> gerarListaCompras(List<CompraProduto> lcp) {
		List<Produto> produtos = new ArrayList<Produto>();
		for(CompraProduto cp : lcp) {
			Produto produto = cp.getProduto();
			produtos.add(produto);
		}
		return produtos;
	}
	
//	@Scheduled(fixedRate = 10000)
//	public static void temporizadorCarrinho(Principal principal) {
//		System.out.println("Tempo de espera do carrinho finalizado...");
//		System.out.println("Removendo item");
//
//		Cliente cliente = localizaCliente(principal);
//		
////		DEVOLVENDO AO ESTOQUE
//		List<Produto> produtos = ValidaCarrinho.getProdutos(cliente);	
//		ValidaEstoque.retornaListaEstoque(produtos);
//		for(Produto prod : produtos) {
//			prodRepo.save(prod);
//		}
//		
////		RETIRANDO DO CARRINHO DO CLIENTE	
//		ValidaCarrinho.removerListaProdutos(produtos, cliente);
//		clienteRepo.save(cliente);
//	}
}
