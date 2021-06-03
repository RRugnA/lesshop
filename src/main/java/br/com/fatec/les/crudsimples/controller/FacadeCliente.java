package br.com.fatec.les.crudsimples.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fatec.les.crudsimples.dto.RequisicaoCliente;
import br.com.fatec.les.crudsimples.dto.RequisicaoCompra;
import br.com.fatec.les.crudsimples.dto.RequisicaoDocumento;
import br.com.fatec.les.crudsimples.dto.RequisicaoEndereco;
import br.com.fatec.les.crudsimples.dto.RequisicaoProduto;
import br.com.fatec.les.crudsimples.dto.RequisicaoUsuario;
import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.Compra;
import br.com.fatec.les.crudsimples.model.CompraProduto;
import br.com.fatec.les.crudsimples.model.Cupom;
import br.com.fatec.les.crudsimples.model.Documento;
import br.com.fatec.les.crudsimples.model.Endereco;
import br.com.fatec.les.crudsimples.model.MensagemErros;
import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.model.StatusTroca;
import br.com.fatec.les.crudsimples.model.Usuario;
import br.com.fatec.les.crudsimples.repository.ClienteRepository;
import br.com.fatec.les.crudsimples.repository.CompraProdutoRepository;
import br.com.fatec.les.crudsimples.repository.CompraRepository;
import br.com.fatec.les.crudsimples.repository.DocumentoRepository;
import br.com.fatec.les.crudsimples.repository.EnderecoRepository;
import br.com.fatec.les.crudsimples.repository.UsuarioRepository;
import br.com.fatec.les.crudsimples.strategy.NumCartao;
import br.com.fatec.les.crudsimples.strategy.ValidaCarrinho;
import br.com.fatec.les.crudsimples.strategy.ValidaCliente;

@Controller
public class FacadeCliente {
	
	private ModelAndView mv;
	
	@Autowired
	private UsuarioRepository userRepo;
	@Autowired
	private ClienteRepository clienteRepo;
	@Autowired
	private EnderecoRepository endRepo;
	@Autowired
	private DocumentoRepository docRepo;
	@Autowired
	private CompraRepository compraRepo;
	@Autowired
	private CompraProdutoRepository cpRepo;
	
	
	public ModelAndView exibirCadastroLogin() {
		mv = new ModelAndView("cliente/cadastro-login");
		return mv;
	}
	
	public String salvarLogin(RequisicaoUsuario requisicao, RedirectAttributes redirectAttributes) {
		Usuario usuario = requisicao.toUsuario();
		userRepo.save(usuario);
		
		return "redirect:/login-home";
	}
	
	public ModelAndView exibirAlterarSenha() {
		mv = new ModelAndView("cliente/alterar-senha");
		return mv;
	}
	
	public ModelAndView salvarAlterarSenha(Principal principal, RequisicaoUsuario requisicao, MensagemErros mensagem) {
		Usuario usuario = userRepo.findById(principal.getName()).get();
		
		if(ValidaCliente.alteraSenha(requisicao, usuario)) {
			userRepo.save(usuario);
			mensagem.setMensagem("Senha alterada com sucesso!");
			mv = new ModelAndView("cliente/alterar-senha");
			mv.addObject("mensagem", mensagem);
			return mv;
		} 
		
		mensagem.setMensagem("Senha Atual incorreta ou campos Nova Senha não são idênticos");
		mv = new ModelAndView("cliente/alterar-senha");
		mv.addObject("mensagem", mensagem);
		return mv;
	}
	
	public ModelAndView exibirCadastroPessoal() {
		mv = new ModelAndView("cliente/cadastro-pessoal");
		return mv;
	}
	
	public String salvarCadastroPessoal(Principal principal, RequisicaoCliente requisicao) {
		Cliente cliente = requisicao.toCliente();
		Usuario usuario = userRepo.findById(principal.getName()).get();
		cliente.setUsuario(usuario);
		System.out.println("cadastrando cliente...");
		clienteRepo.save(cliente);
		
		usuario.setCliente(cliente);
		userRepo.save(usuario);
		
		return "redirect:/cliente/exibir-dados-pessoais";
	}
	
	public ModelAndView exibirCadastroEndereco() {
		mv = new ModelAndView("cliente/cadastro-endereco");
		return mv;
	}
	
	public String salvarCadastroEndereco(Principal principal, RequisicaoEndereco requisicao) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		Endereco endereco = requisicao.toEndereco();		
		endereco.setCliente(cliente);
		endRepo.save(endereco);
		
		return "redirect:/cliente/exibir-enderecos";
	}
	
	public ModelAndView exibirCadastroDocumento() {
		mv = new ModelAndView("cliente/cadastro-documento");
		return mv;
	}
	
	public String salvarCadastroDocumento(Principal principal, RequisicaoDocumento requisicao) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		Documento documento = requisicao.toDocumento();		
		documento.setCliente(cliente);
		docRepo.save(documento);
		
		return "redirect:/cliente/exibir-opcoes-pagamento";
	}
	
	public ModelAndView exibirDadosPessoais(Principal principal) {
		Usuario user = userRepo.findById(principal.getName()).get();	
		if(user.getCliente() == null) {
			mv = new ModelAndView("cliente/exibir");
		} else {
			mv = new ModelAndView("cliente/exibir");
			Cliente cliente = user.getCliente();
			mv.addObject("cliente", cliente);
		}				
		
		return mv;	
	}
	
	public ModelAndView exibirEnderecos(Principal principal) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		List<Endereco> enderecos = endRepo.findByCliente(cliente);
		mv = new ModelAndView("cliente/exibir-endereco");
		mv.addObject("enderecos", enderecos);	
		
		return mv;
	}
	
	public ModelAndView exibirDocumentos(Principal principal) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		List<Documento> documentos = docRepo.findByCliente(cliente);
		
//		EXIBINDO 4 ÚLTIMOS DÍGITOS DO CARTÃO
		NumCartao.gerarListaDocNumCartao(documentos);
		
		mv = new ModelAndView("cliente/exibir-documento");
		mv.addObject("documentos", documentos);		
		return mv;
	}
	
	public ModelAndView exibirEditarDadosPessoais(Principal principal) {
		mv = new ModelAndView("cliente/editar-cliente");		
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		mv.addObject("cliente", cliente);
		
		return mv;
	}
	
	public String salvarEditarDadosPessoais(Long id, RequisicaoCliente requisicao, Principal principal) {
		Cliente cliente = clienteRepo.findById(id).get();
		
		List<Endereco> ends = endRepo.findByCliente(cliente);
		List<Documento> docs = docRepo.findByCliente(cliente);
		
		cliente = requisicao.toCliente();
		cliente.setClienteId(id);
		cliente.setDataCadastro(LocalDate.now());
		cliente.setDocumentos(docs);
		cliente.setEndereço(ends);
		
		Usuario usuario = userRepo.findById(principal.getName()).get();
		cliente.setUsuario(usuario);
		
		System.out.println("id = " + cliente.getClienteId());
		clienteRepo.save(cliente);
		
		return "redirect:/cliente/exibir-dados-pessoais";
	}
	
	public ModelAndView incluirEndereco(Long id) {
		mv = new ModelAndView("cliente/cadastro-endereco");		
		Cliente cliente = clienteRepo.findById(id).get();
		mv.addObject("cliente",cliente);
		
		return mv;
	}
	
	public String salvarNovoEndereco(Long id, RequisicaoEndereco requisicao) {
		Endereco endereco = requisicao.toEndereco();
		Cliente cliente = clienteRepo.findById(id).get();		
		endereco.setCliente(cliente);
		endRepo.save(endereco);
		
		return "redirect:/cliente/exibir";
	}
	
	public ModelAndView exibirEditarEndereco(Long id) {
		mv = new ModelAndView("cliente/editar-endereco");		
		Endereco endereco = endRepo.findById(id).get();
		mv.addObject("endereco", endereco);
		
		return mv;
	}
	
	public String salvarEditarEndereco(Long id, RequisicaoEndereco requisicao) {
		Endereco endereco = endRepo.findById(id).get();
		Cliente cliente = clienteRepo.findById(endereco.getCliente().getClienteId()).get();
		
		endereco = requisicao.toEndereco();
		endereco.setCliente(cliente);
		endereco.setEnderecoId(id);
		
		System.out.println("id = " + endereco.getEnderecoId());
		endRepo.save(endereco);
		
		return "redirect:/cliente/enderecos-cadastrados/" + cliente.getClienteId().toString();
	}
	
	public ModelAndView exibirIncluirDocumento() {
		mv = new ModelAndView("cliente/cadastro-documento");		
		return mv;
	}
	
	public String salvarIncluirDocumento(Long id, RequisicaoDocumento requisicao) {
		Documento documento = requisicao.toDocumento();
		Cliente cliente = clienteRepo.findById(id).get();
		
		documento.setCliente(cliente);
		docRepo.save(documento);
		
		return "redirect:/cliente/exibir";
	}
	
	public ModelAndView exibirEditarDocumento(Long id) {
		mv = new ModelAndView("cliente/editar-documento");		
		Documento documento = docRepo.findById(id).get();
		mv.addObject("documento", documento);
		
		return mv;
	}
	
	public String salvarEditarDocumento(Long id, RequisicaoDocumento requisicao) {
		Documento documento = docRepo.findById(id).get();
		Cliente cliente = clienteRepo.findById(documento.getCliente().getClienteId()).get();
		
		documento = requisicao.toDocumento();
		documento.setCliente(cliente);
		documento.setDocumentoId(id);
		
		System.out.println("id = " + documento.getDocumentoId());
		docRepo.save(documento);
		
		return "redirect:/cliente/documentos-cadastrados/" + cliente.getClienteId().toString();
	}
	
	public ModelAndView exibirEndereco(Long id) {
		mv = new ModelAndView("cliente/exibir-endereco");		
		Cliente cliente = clienteRepo.findById(id).get();
		mv.addObject("cliente", cliente);		
		List<Endereco> enderecos = endRepo.findByCliente(cliente);		
		mv.addObject("enderecos", enderecos);	
		
		return mv;
	}
	
	public ModelAndView exibirDocumento(Long id) {
		mv = new ModelAndView("cliente/exibir-documento");		
		Cliente cliente = clienteRepo.findById(id).get();
		mv.addObject("cliente", cliente);		
		List<Documento> documentos = docRepo.findByCliente(cliente);
		mv.addObject("documentos", documentos);

		return mv;
	}
	
	public ModelAndView excluirCliente(Long id) {
		mv = new ModelAndView("cliente/sucesso");		
		Cliente cliente = clienteRepo.findById(id).get();
		clienteRepo.delete(cliente);		
		String sucesso = "Cliente excluído";
		mv.addObject("sucesso", sucesso);
		
		return mv;
	}
	
	public ModelAndView excluirEndereco(Long id) {
		mv = new ModelAndView("cliente/sucesso");		
		Endereco endereco = endRepo.findById(id).get();
		endRepo.delete(endereco);		
		String sucesso = "Endereço excluído";
		mv.addObject("sucesso", sucesso);
		
		return mv;
	}
	
	public ModelAndView excluirDocumento(Long id) {
		mv = new ModelAndView("cliente/sucesso");		
		Documento documento = docRepo.findById(id).get();
		docRepo.delete(documento);		
		String sucesso = "Documento excluído";
		mv.addObject("sucesso", sucesso);
		
		return mv;
	}
	
	public ModelAndView exibirHistorico(Principal principal) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		List<Compra> compras = compraRepo.findByClienteClienteId(cliente.getClienteId());
		
		mv = new ModelAndView("cliente/historico");
		mv.addObject("compras", compras);
		return mv;
	}
	
	public ModelAndView exibirDetalhesHistorico(Long id) {
		Compra compra = compraRepo.findById(id).get();
		List<CompraProduto> lcp = cpRepo.findByCompraCompraId(compra.getCompraId());
		List<Produto> produtos = ValidaCarrinho.gerarListaCompras(lcp);
		List<Cupom> cupons = compra.getCupons();
		List<String> cartoes = NumCartao.gerarListaStringNumCartao(compra.getCartoes());
		
		mv = new ModelAndView("cliente/detalhes-historico");
		mv.addObject("compra", compra);
		mv.addObject("produtos", produtos);
		mv.addObject("cupons", cupons);
		mv.addObject("cartoes", cartoes);
		mv.addObject("listaCompra", lcp);
		return mv;
	}
	
	public String salvarSolicitarTroca(RequisicaoCompra reqCompra, RequisicaoProduto reqProd) {
		Compra compra = compraRepo.findById(Long.valueOf(reqCompra.getCompraId())).get();
		List<CompraProduto> lcp = cpRepo.findByCompraCompraId(compra.getCompraId());
		for(CompraProduto cp : lcp) {
			if(cp.getProduto().getProdutoId() == Long.valueOf(reqProd.getProdutoId())) {
				cp.setStatusTroca(StatusTroca.SOLICITADO);
				cpRepo.save(cp);
			}
		}	
		
		return "redirect:/cliente/detalhes-historico/{id}";
	}
}
