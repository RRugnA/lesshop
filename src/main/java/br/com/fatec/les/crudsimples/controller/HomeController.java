package br.com.fatec.les.crudsimples.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.crudsimples.dto.RequisicaoCompra;
import br.com.fatec.les.crudsimples.dto.RequisicaoCupom;
import br.com.fatec.les.crudsimples.dto.RequisicaoDocumento;
import br.com.fatec.les.crudsimples.dto.RequisicaoEndereco;
import br.com.fatec.les.crudsimples.dto.RequisicaoProduto;

@Controller
@EnableScheduling
public class HomeController {

	@Autowired
	private FacadeHome facade;
	
	@GetMapping("lesshop")
	public ModelAndView index() {		
		return facade.home();
	}
	
	@PostMapping("lesshop")
	public String index(RequisicaoProduto requisicao) {			
		return facade.infoProduto(requisicao);
	}
	
	@GetMapping("produto/{id}")
	public ModelAndView exibirProduto(@PathVariable("id") Long id) {			
		return facade.exibirProduto(id);
	}
	
	@PostMapping("produto/{id}")
	public String produto(@PathVariable("id") Long id, Principal principal, RequisicaoProduto requisicao) {		
		return facade.escolherProduto(id, principal, requisicao);
	}
	
	@GetMapping("carrinho")
	public ModelAndView carrinho(Principal principal) {
		return facade.exibirCarrinho(principal);
	}
	
	@PostMapping("/excluir-carrinho")
	public String deleteProduto(Principal principal, RequisicaoProduto requisicao) {		
		return facade.deletarProduto(principal, requisicao);
	}
	
	@PostMapping("carrinho")
	public String carrinho(Principal principal, RequisicaoCompra requisicao, RequisicaoProduto reqProd) {		
		return facade.salvarCarrinho(principal, requisicao, reqProd);
	}
	
	@GetMapping("carrinho-endereco")
	public ModelAndView carrinhoEndereco(Principal principal) {
		return facade.exibirCarrinhoEndereco(principal);
	}
	
	@PostMapping("carrinho-endereco/novo-endereco")
	public String carrinhoEndereco(Principal principal, RequisicaoEndereco requisicao) {
		return facade.novoEnderecoCarrinhoEndereco(principal, requisicao);
	}
	
	@PostMapping("carrinho-endereco")
	public String carrinhoEndereco(RequisicaoCompra requisicao, Principal principal) {		
		return facade.salvarCarrinhoEndereco(requisicao, principal);
	}
	
	@GetMapping("carrinho-pgto")
	public ModelAndView carrinhoPgto(Principal principal) {
		return facade.exibirCarrinhoPgto(principal);
	}
	
	@PostMapping("carrinho-pgto/novo-cartao")
	public String carrinhoPgto(Principal principal, RequisicaoDocumento requisicao) {
		return facade.novoCartaoCarrinhoPgto(principal, requisicao);
	}
	
	@PostMapping("carrinho-pgto")
	public String carrinhoPgto(Principal principal, RequisicaoCompra requisicao) {
		return facade.salvarCarrinhoPgto(principal, requisicao);		
	}
	
	@GetMapping("carrinho-parcelamento")
	public ModelAndView carrinhoParcelamento(Principal principal) {
		return facade.exibirCarrinhoParcelamento(principal);
	}
	
	@PostMapping("/carrinho-parcelamento/cupom")
	public ModelAndView verificarCupom(Principal principal, RequisicaoCupom requisicao) {
		return facade.inserirCupom(principal, requisicao);
	}
	
	@PostMapping("carrinho-parcelamento/remover-cartao")
	public String carrinhoParcelamentoRemoverCartao(Principal principal, RequisicaoDocumento requisicao) {		
		return facade.removerCartao(principal, requisicao);
	}
	
	@PostMapping("carrinho-parcelamento")
	public String carrinhoParcelamento(Principal principal, RequisicaoCompra requisicao) {
		return facade.salvarCarrinhoParcelamento(principal, requisicao);
	}
	
	@GetMapping("carrinho-parcelamento/{codigo}-removido")
	public ModelAndView removerCupom(@PathVariable("codigo")String codigo, Principal principal, RequisicaoCupom requisicao) {
		return facade.removerCupom(codigo, principal, requisicao);
	}
	
	
	@GetMapping("carrinho-revisao")
	public ModelAndView carrinhoRevisao(Principal principal) {
		return facade.exibirCarrinhoRevisao(principal);
	}
	
	@PostMapping("carrinho-revisao")
	public String carrinhoRevisao(Principal principal, RequisicaoCompra requisicao) {	
		return facade.salvarCarrinhoRevisao(principal, requisicao);
	}
	
	@GetMapping("carrinho-sucesso")
	public ModelAndView carrinhoSucesso(Principal principal) {	
		return facade.exibirCarrinhoSucesso(principal);
	}
}
