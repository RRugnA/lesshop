package br.com.fatec.les.crudsimples.controller;

import java.security.Principal;
import java.util.List;

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
import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.Compra;
import br.com.fatec.les.crudsimples.model.CompraProduto;
import br.com.fatec.les.crudsimples.model.Cupom;
import br.com.fatec.les.crudsimples.model.Documento;
import br.com.fatec.les.crudsimples.model.Endereco;
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

@Controller
@RequestMapping("cliente")
public class ClienteController {

	private ModelAndView mv;
	
	@Autowired
	private ClienteRepository clienteRepo;
	@Autowired
	private EnderecoRepository endRepo;
	@Autowired
	private DocumentoRepository docRepo;
	@Autowired
	private UsuarioRepository userRepo;
	@Autowired
	private CompraRepository compraRepo;
	@Autowired
	private CompraProdutoRepository cpRepo;
	
	public Cliente localizaCliente(Principal principal) {
		Usuario usuario = userRepo.findByLogin(principal.getName());
		Cliente cliente = usuario.getCliente();	
		return cliente;
	}	
	
	@GetMapping("cadastro-login")
	public ModelAndView cadastrarLogin() {
		mv = new ModelAndView("cliente/cadastro-login");
		return mv;
	}
	
	@PostMapping("cadastro-login")
	public String novoLogin(RequisicaoUsuario requisicao, RedirectAttributes redirectAttributes) {
		
		Usuario usuario = requisicao.toUsuario();
		userRepo.save(usuario);
		
		return "redirect:/login-home";
	}
	
	@GetMapping("cadastro-pessoal")
	public ModelAndView cadastrar() {
		mv = new ModelAndView("cliente/cadastro-pessoal");
		return mv;
	}
	
	@PostMapping("cadastro-pessoal")
	public String novo(Principal principal, RequisicaoCliente requisicao) {
		
		Cliente cliente = requisicao.toCliente();
		Usuario usuario = userRepo.findByLogin(principal.getName());
		cliente.setUsuario(usuario);
		
		clienteRepo.save(cliente);
		
		usuario.setCliente(cliente);
		userRepo.save(usuario);
		
		return "redirect:/cliente/exibir-dados-pessoais";
	}
	
	@GetMapping("cadastrar-endereco")
	public ModelAndView novoEnderco() {
		mv = new ModelAndView("cliente/cadastro-endereco");
		return mv;
	}
	
	@PostMapping("cadastrar-endereco")
	public String novoEndereco(Principal principal, RequisicaoEndereco requisicao) {		
		Cliente cliente = localizaCliente(principal);
		Endereco endereco = requisicao.toEndereco();		
		endereco.setCliente(cliente);
		endRepo.save(endereco);
		
		return "redirect:/cliente/exibir-enderecos";
	}
	
	@GetMapping("cadastrar-documento")
	public ModelAndView novoDocumento() {
		mv = new ModelAndView("cliente/cadastro-documento");
		return mv;
	}
	
	@PostMapping("cadastrar-documento")
	public String novoDocumento(Principal principal, RequisicaoDocumento requisicao) {
		Cliente cliente = localizaCliente(principal);
		Documento documento = requisicao.toDocumento();		
		documento.setCliente(cliente);
		docRepo.save(documento);
		
		return "redirect:/cliente/exibir-opcoes-pagamento";
	}
	
	@GetMapping("/exibir-dados-pessoais")
	public ModelAndView exibirPessoal(Principal principal) {
		
		Usuario user = userRepo.findByLogin(principal.getName());	
		if(user.getCliente() == null) {
			mv = new ModelAndView("cliente/exibir");
		} else {
			mv = new ModelAndView("cliente/exibir");
			Cliente cliente = user.getCliente();
			mv.addObject("cliente", cliente);
		}				
		
		return mv;		
	}
	
	@GetMapping("/exibir-enderecos")
	public ModelAndView exibirEnderecos(Principal principal) {
		Cliente cliente = localizaCliente(principal);
		List<Endereco> enderecos = endRepo.findByCliente(cliente);
		mv = new ModelAndView("cliente/exibir-endereco");
		mv.addObject("enderecos", enderecos);	
		
		return mv;
	}
	
	@GetMapping("/exibir-opcoes-pagamento")
	public ModelAndView exibirDocumentos(Principal principal) {		
		Cliente cliente = localizaCliente(principal);	
		List<Documento> documentos = docRepo.findByCliente(cliente);
		
//		EXIBINDO 4 ÚLTIMOS DÍGITOS DO CARTÃO
		NumCartao.gerarListaNumCartao(documentos);
		
		mv = new ModelAndView("cliente/exibir-documento");
		mv.addObject("documentos", documentos);		
		return mv;
	}
	
	@GetMapping("editar-dados-pessoais")
	public ModelAndView editarPessoal(Principal principal) {
		mv = new ModelAndView("cliente/editar-cliente");
		
		Usuario user = userRepo.findByLogin(principal.getName());
		Cliente cliente = user.getCliente();
		
		mv.addObject("cliente", cliente);
		
		return mv;
	}
	
	@PostMapping("editar-cliente/{id}")
	public String editarCliente(@PathVariable("id") Long id, RequisicaoCliente requisicao, Principal principal) {
		Cliente cliente = clienteRepo.findById(id).get();
		
		List<Endereco> ends = endRepo.findByCliente(cliente);
		List<Documento> docs = docRepo.findByCliente(cliente);
		
		cliente = requisicao.toCliente();
		cliente.setId(id);
		
		cliente.setDocumentos(docs);
		cliente.setEndereço(ends);
		
		Usuario usuario = userRepo.findByLogin(principal.getName());
		cliente.setUsuario(usuario);
		
		System.out.println("id = " + cliente.getId());
		clienteRepo.save(cliente);
		
		return "redirect:/cliente/exibir-dados-pessoais";
	}
	
	@GetMapping("cadastrar-endereco/{id}")
	public ModelAndView incluirEndereco(@PathVariable("id") Long id) {
		mv = new ModelAndView("cliente/cadastro-endereco");
		
		Cliente cliente = clienteRepo.findById(id).get();
		mv.addObject("cliente",cliente);
		
		return mv;
	}
	
	@PostMapping("cadastrar-endereco/{id}")
	public String incluirEndereco(@PathVariable("id") Long id, RequisicaoEndereco requisicao) {
		
		Endereco endereco = requisicao.toEndereco();
		Cliente cliente = clienteRepo.findById(id).get();
		
		endereco.setCliente(cliente);
		endRepo.save(endereco);
		
		return "redirect:/cliente/exibir";
	}
	
	@GetMapping("editar-endereco/{id}")
	public ModelAndView editarEndereco(@PathVariable("id") Long id) {
		mv = new ModelAndView("cliente/editar-endereco");
		
		Endereco endereco = endRepo.findById(id).get();

		mv.addObject("endereco", endereco);
		
		return mv;
	}
	
	@PostMapping("editar-endereco/{id}")
	public String editarEndereco(@PathVariable("id") Long id, RequisicaoEndereco requisicao) {
		Endereco endereco = endRepo.findById(id).get();
		Cliente cliente = clienteRepo.findById(endereco.getCliente().getId()).get();
		
		endereco = requisicao.toEndereco();
		endereco.setCliente(cliente);
		endereco.setId(id);
		
		System.out.println("id = " + endereco.getId());
		endRepo.save(endereco);
		
		return "redirect:/cliente/enderecos-cadastrados/" + cliente.getId().toString();
	}
	
	@GetMapping("cadastrar-documento/{id}")
	public ModelAndView incluirDocumento() {
		mv = new ModelAndView("cliente/cadastro-documento");
		
		return mv;
	}
	
	@PostMapping("cadastrar-documento/{id}")
	public String incluirDocumento(@PathVariable("id") Long id, RequisicaoDocumento requisicao) {
		
		Documento documento = requisicao.toDocumento();
		Cliente cliente = clienteRepo.findById(id).get();
		
		documento.setCliente(cliente);
		docRepo.save(documento);
		
		return "redirect:/cliente/exibir";
	}
	
	@GetMapping("editar-documento/{id}")
	public ModelAndView editarDocumento(@PathVariable("id") Long id) {
		mv = new ModelAndView("cliente/editar-documento");
		
		Documento documento = docRepo.findById(id).get();

		mv.addObject("documento", documento);
		
		return mv;
	}
	
	@PostMapping("editar-documento/{id}")
	public String editarDocumento(@PathVariable("id") Long id, RequisicaoDocumento requisicao) {
		Documento documento = docRepo.findById(id).get();
		Cliente cliente = clienteRepo.findById(documento.getCliente().getId()).get();
		
		documento = requisicao.toDocumento();
		documento.setCliente(cliente);
		documento.setId(id);
		
		System.out.println("id = " + documento.getId());
		docRepo.save(documento);
		
		return "redirect:/cliente/documentos-cadastrados/" + cliente.getId().toString();
	}
	
	@GetMapping("enderecos-cadastrados/{id}")
	public ModelAndView exibirEndereco(@PathVariable("id") Long id) {
		mv = new ModelAndView("cliente/exibir-endereco");
		
		Cliente cliente = clienteRepo.findById(id).get();
		mv.addObject("cliente", cliente);
		
		List<Endereco> enderecos = endRepo.findByCliente(cliente);		
		mv.addObject("enderecos", enderecos);	
		
		return mv;
	}
	
	@GetMapping("documentos-cadastrados/{id}")
	public ModelAndView exibirDocumento(@PathVariable("id") Long id) {
		mv = new ModelAndView("cliente/exibir-documento");
		
		Cliente cliente = clienteRepo.findById(id).get();
		mv.addObject("cliente", cliente);
		
		List<Documento> documentos = docRepo.findByCliente(cliente);
		mv.addObject("documentos", documentos);

		return mv;
	}
	
	@GetMapping("/excluir-cliente/{id}")
	public ModelAndView excluirCliente(@PathVariable("id") Long id) {
		mv = new ModelAndView("cliente/sucesso");
		
		Cliente cliente = clienteRepo.findById(id).get();
		clienteRepo.delete(cliente);
		
		String sucesso = "Cliente excluído";
		mv.addObject("sucesso", sucesso);
		return mv;
	}
	
	@GetMapping("excluir-endereco/{id}")
	public ModelAndView excluirEndereco(@PathVariable("id") Long id) {
		mv = new ModelAndView("cliente/sucesso");
		
		Endereco endereco = endRepo.findById(id).get();
		endRepo.delete(endereco);
		
		String sucesso = "Endereço excluído";
		mv.addObject("sucesso", sucesso);
		
		return mv;
	}
	
	@GetMapping("excluir-documento/{id}")
	public ModelAndView excluirDocumento(@PathVariable("id") Long id) {
		mv = new ModelAndView("cliente/sucesso");
		
		Documento documento = docRepo.findById(id).get();
		docRepo.delete(documento);
		
		String sucesso = "Documento excluído";
		mv.addObject("sucesso", sucesso);
		
		return mv;
	}
	
	@GetMapping("historico-de-compras")
	public ModelAndView historico(Principal principal) {
		Cliente cliente = localizaCliente(principal);
		List<Compra> compras = compraRepo.findByClienteId(cliente.getId());
		
		mv = new ModelAndView("cliente/historico");
		mv.addObject("compras", compras);
		return mv;
	}
	
	@GetMapping("detalhes-historico/{id}")
	public ModelAndView detalhesHistorico(@PathVariable("id") Long id) {
		Compra compra = compraRepo.findById(id).get();
		List<CompraProduto> lcp = cpRepo.findByCompraId(compra.getId());
		List<Produto> produtos = ValidaCarrinho.gerarListaCompras(lcp);
//		List<Produto> produtos = compra.getListaCompras();
		List<Cupom> cupons = compra.getCupons();
		List<Documento> cartoes = compra.getDocumentos();
		
		mv = new ModelAndView("cliente/detalhes-historico");
		mv.addObject("compra", compra);
		mv.addObject("produtos", produtos);
		mv.addObject("cupons", cupons);
		mv.addObject("cartoes", cartoes);
		return mv;
	}
	
	@PostMapping("detalhes-historico/{id}")
	public String solicitarTroca(RequisicaoCompra reqCompra, RequisicaoProduto reqProd) {
		Compra compra = compraRepo.findById(Long.valueOf(reqCompra.getCompraId())).get();
		List<CompraProduto> lcp = cpRepo.findByCompraId(compra.getId());
		for(CompraProduto cp : lcp) {
			if(cp.getProduto().getId() == Long.valueOf(reqProd.getProdutoId())) {
				cp.setStatusTroca(StatusTroca.SOLICITADO);
				cpRepo.save(cp);
			}
		}	
		
		return "redirect:/cliente/detalhes-historico/{id}";
	}

}
