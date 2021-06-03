package br.com.fatec.les.crudsimples.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.crudsimples.dto.RequisicaoCliente;
import br.com.fatec.les.crudsimples.dto.RequisicaoCompra;
import br.com.fatec.les.crudsimples.dto.RequisicaoCupom;
import br.com.fatec.les.crudsimples.dto.RequisicaoProduto;

@Controller
@RequestMapping("adm")
public class AdmController {

	@Autowired
	private FacadeAdm facade;
	
	@GetMapping("login-adm")
	public ModelAndView login() {
		return facade.exibirLogin();
	}
	
	@GetMapping("/exibir-clientes")
	public ModelAndView exibir() {
		return facade.exibirClientes();
	}
	
	@PostMapping("/exibir-clientes")
	public String inativarCliente(RequisicaoCliente requisicao) {
		return facade.salvarInativarCliente(requisicao);
	}
	
	@GetMapping("/controle-estoque")
	public ModelAndView painelEstoque() {
		return facade.exibirPainelEstoque();
	}	
	
	@GetMapping("/cadastrar-produto")
	public ModelAndView novoProduto() {
		return facade.exibirCadastrarProduto();
	}
	
	@PostMapping("/cadastrar-produto")
	public String novoProduto(Principal principal, RequisicaoProduto requisicao) {		
		return facade.salvarCadastrarProduto(principal, requisicao);
	}
	
	@GetMapping("/editar-produto/{id}")
	public ModelAndView editarProduto(@PathVariable("id") Long id) {
		return facade.exibirEditarProduto(id);
	}
	
	@PostMapping("/editar-produto/{id}")
	public String editarProduto(RequisicaoProduto requisicao, Principal principal) {	
		return facade.salvarEditarProduto(requisicao, principal);
	}
	
	@GetMapping("/listar-produtos")
	public ModelAndView exibiProdutos() {
		return facade.exibirProdutos();
	}
	
	@PostMapping("/listar-produtos")
	public String inativarProduto(RequisicaoProduto requisicao, Principal principal) {
		return facade.salvarInativarProduto(requisicao, principal);
	}
	
	@GetMapping("/controle-cupom")
	public ModelAndView painelCupom() {
		return facade.exibirPainelCupom();
	}
	
	@GetMapping("/cadastrar-cupom")
	public ModelAndView novoCupom() {
		return facade.exibirNovoCupom();
	}
	
	@PostMapping("/cadastrar-cupom")
	public String novoCupom(RequisicaoCupom requisicao, Principal principal) {
		return facade.salvarNovoCupom(requisicao, principal);
	}
	
	@GetMapping("/listar-cupons")
	public ModelAndView listarCupons() {
		return facade.exibirListarCupom();
	}
	
	@GetMapping("/exibir-vendas")
	public ModelAndView listarVendas() {
		return facade.exibirListarVendas();
	}
	
	@GetMapping("/venda-detalhes/{id}")
	public ModelAndView vendaDetalhes(@PathVariable("id") Long id) {
		return facade.exibirVendaDetalhes(id);
	}
	
	@PostMapping("/venda-detalhes/{id}")
	public String alteraStatusTroca(RequisicaoCompra reqCompra, RequisicaoProduto reqProd, Principal principal) {
		return facade.salvarAlterarStatusTroca(reqCompra, reqProd, principal);
	}
	
	@PostMapping("/exibir-vendas")
	public String alterarStatus(RequisicaoCompra requisicao) {
		return facade.salvarAlterarStatus(requisicao);
	}
	
	@GetMapping("/logs")
	public ModelAndView logs() {
		return facade.exibirLogs();
	}
	
	@GetMapping("/analise")
	public ModelAndView pgGraficos() {
		return facade.exibirGraficoVendas();
	}
}
