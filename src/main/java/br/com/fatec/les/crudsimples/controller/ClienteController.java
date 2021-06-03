package br.com.fatec.les.crudsimples.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fatec.les.crudsimples.dto.RequisicaoCliente;
import br.com.fatec.les.crudsimples.dto.RequisicaoCompra;
import br.com.fatec.les.crudsimples.dto.RequisicaoDocumento;
import br.com.fatec.les.crudsimples.dto.RequisicaoEndereco;
import br.com.fatec.les.crudsimples.dto.RequisicaoProduto;
import br.com.fatec.les.crudsimples.dto.RequisicaoUsuario;
import br.com.fatec.les.crudsimples.model.MensagemErros;

@Controller
@RequestMapping("cliente")
public class ClienteController {
	
	@Autowired
	private FacadeCliente facade;
	
	@GetMapping("cadastro-login")
	public ModelAndView cadastrarLogin() {
		return facade.exibirCadastroLogin();
	}
	
	@PostMapping("cadastro-login")
	public String novoLogin(RequisicaoUsuario requisicao, RedirectAttributes redirectAttributes) {
		return facade.salvarLogin(requisicao, redirectAttributes);
	}
	
	@GetMapping("alterar-senha")
	public ModelAndView alterarSenha() {
		return facade.exibirAlterarSenha();
	}
	
	@PostMapping("alterar-senha")
	public ModelAndView alterarSenha(Principal principal, RequisicaoUsuario requisicao, MensagemErros mensagem) {
		return facade.salvarAlterarSenha(principal, requisicao, mensagem);	
	}
	
	@GetMapping("cadastro-pessoal")
	public ModelAndView cadastrar() {
		return facade.exibirCadastroPessoal();
	}
	
	@PostMapping("cadastro-pessoal")
	public String novo(Principal principal, RequisicaoCliente requisicao) {		
		return facade.salvarCadastroPessoal(principal, requisicao);
	}
	
	@GetMapping("cadastrar-endereco")
	public ModelAndView novoEnderco() {
		return facade.exibirCadastroEndereco();
	}
	
	@PostMapping("cadastrar-endereco")
	public String novoEndereco(Principal principal, RequisicaoEndereco requisicao) {		
		return facade.salvarCadastroEndereco(principal, requisicao);
	}
	
	@GetMapping("cadastrar-documento")
	public ModelAndView novoDocumento() {
		return facade.exibirCadastroDocumento();
	}
	
	@PostMapping("cadastrar-documento")
	public String novoDocumento(Principal principal, RequisicaoDocumento requisicao) {
		return facade.salvarCadastroDocumento(principal, requisicao);
	}
	
	@GetMapping("/exibir-dados-pessoais")
	public ModelAndView exibirPessoal(Principal principal) {
		return facade.exibirDadosPessoais(principal);	
	}
	
	@GetMapping("/exibir-enderecos")
	public ModelAndView exibirEnderecos(Principal principal) {
		return facade.exibirEnderecos(principal);
	}
	
	@GetMapping("/exibir-opcoes-pagamento")
	public ModelAndView exibirDocumentos(Principal principal) {		
		return facade.exibirDocumentos(principal);
	}
	
	@GetMapping("editar-dados-pessoais")
	public ModelAndView editarPessoal(Principal principal) {
		return facade.exibirEditarDadosPessoais(principal);
	}
	
	@PostMapping("editar-cliente/{id}")
	public String editarCliente(@PathVariable("id") Long id, RequisicaoCliente requisicao, Principal principal) {
		return facade.salvarEditarDadosPessoais(id, requisicao, principal);
	}
	
	@GetMapping("cadastrar-endereco/{id}")
	public ModelAndView incluirEndereco(@PathVariable("id") Long id) {
		return facade.incluirEndereco(id);
	}
	
	@PostMapping("cadastrar-endereco/{id}")
	public String incluirEndereco(@PathVariable("id") Long id, RequisicaoEndereco requisicao) {		
		return facade.salvarNovoEndereco(id, requisicao);
	}
	
	@GetMapping("editar-endereco/{id}")
	public ModelAndView editarEndereco(@PathVariable("id") Long id) {
		return facade.exibirEditarEndereco(id);
	}
	
	@PostMapping("editar-endereco/{id}")
	public String editarEndereco(@PathVariable("id") Long id, RequisicaoEndereco requisicao) {
		return facade.salvarEditarEndereco(id, requisicao);
	}
	
	@GetMapping("cadastrar-documento/{id}")
	public ModelAndView incluirDocumento() {
		return facade.exibirIncluirDocumento();
	}
	
	@PostMapping("cadastrar-documento/{id}")
	public String incluirDocumento(@PathVariable("id") Long id, RequisicaoDocumento requisicao) {
		return facade.salvarIncluirDocumento(id, requisicao);
	}
	
	@GetMapping("editar-documento/{id}")
	public ModelAndView editarDocumento(@PathVariable("id") Long id) {
		return facade.exibirEditarDocumento(id);
	}
	
	@PostMapping("editar-documento/{id}")
	public String editarDocumento(@PathVariable("id") Long id, RequisicaoDocumento requisicao) {
		return facade.salvarEditarDocumento(id, requisicao);
	}
	
	@GetMapping("enderecos-cadastrados/{id}")
	public ModelAndView exibirEndereco(@PathVariable("id") Long id) {
		return facade.exibirEndereco(id);
	}
	
	@GetMapping("documentos-cadastrados/{id}")
	public ModelAndView exibirDocumento(@PathVariable("id") Long id) {
		return facade.exibirDocumento(id);
	}
	
	@GetMapping("/excluir-cliente/{id}")
	public ModelAndView excluirCliente(@PathVariable("id") Long id) {
		return facade.excluirCliente(id);
	}
	
	@GetMapping("excluir-endereco/{id}")
	public ModelAndView excluirEndereco(@PathVariable("id") Long id) {
		return facade.excluirEndereco(id);
	}
	
	@GetMapping("excluir-documento/{id}")
	public ModelAndView excluirDocumento(@PathVariable("id") Long id) {
		return facade.excluirDocumento(id);
	}
	
	@GetMapping("historico-de-compras")
	public ModelAndView historico(Principal principal) {
		return facade.exibirHistorico(principal);
	}
	
	@GetMapping("detalhes-historico/{id}")
	public ModelAndView detalhesHistorico(@PathVariable("id") Long id) {
		return facade.exibirDetalhesHistorico(id);
	}
	
	@PostMapping("detalhes-historico/{id}")
	public String solicitarTroca(RequisicaoCompra reqCompra, RequisicaoProduto reqProd) {
		return facade.salvarSolicitarTroca(reqCompra, reqProd);
	}

}
