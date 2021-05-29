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

import br.com.fatec.les.crudsimples.dto.RequisicaoCliente;
import br.com.fatec.les.crudsimples.dto.RequisicaoCompra;
import br.com.fatec.les.crudsimples.dto.RequisicaoCupom;
import br.com.fatec.les.crudsimples.dto.RequisicaoProduto;
import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.Compra;
import br.com.fatec.les.crudsimples.model.CompraProduto;
import br.com.fatec.les.crudsimples.model.CompraStatus;
import br.com.fatec.les.crudsimples.model.Cupom;
import br.com.fatec.les.crudsimples.model.Documento;
//import br.com.fatec.les.crudsimples.model.LogTransacao;
import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.model.StatusTroca;
import br.com.fatec.les.crudsimples.model.Usuario;
import br.com.fatec.les.crudsimples.repository.ClienteRepository;
import br.com.fatec.les.crudsimples.repository.CompraProdutoRepository;
import br.com.fatec.les.crudsimples.repository.CompraRepository;
import br.com.fatec.les.crudsimples.repository.CupomRepository;
//import br.com.fatec.les.crudsimples.repository.LogTransacaoRepository;
import br.com.fatec.les.crudsimples.repository.ProdutoRepository;
import br.com.fatec.les.crudsimples.repository.UsuarioRepository;
import br.com.fatec.les.crudsimples.strategy.ValidaCarrinho;
import br.com.fatec.les.crudsimples.strategy.ValidaCliente;
import br.com.fatec.les.crudsimples.strategy.ValidaCupom;
import br.com.fatec.les.crudsimples.strategy.ValidaEstoque;

@Controller
@RequestMapping("adm")
public class AdmController {

	private ModelAndView mv;
	
	@Autowired
	private ClienteRepository clienteRepo;
	@Autowired
	private ProdutoRepository prodRepo;
	@Autowired
	private CupomRepository cupomRepo;
	@Autowired
	private CompraRepository compraRepo;
	@Autowired
	private CompraProdutoRepository cpRepo;
	@Autowired
	private UsuarioRepository userRepo;
//	@Autowired
//	private LogTransacaoRepository ltRepo;
	
	@GetMapping("login-adm")
	public ModelAndView login() {
		mv = new ModelAndView("adm/login-adm");
		return mv;
	}
	
	@GetMapping("/exibir-clientes")
	public ModelAndView exibir() {
		mv = new ModelAndView("adm/adm-clientes");
		
		List<Cliente> clientes = clienteRepo.findAll();
		
		mv.addObject("clientes", clientes);
		
		return mv;
	}
	
	@PostMapping("/exibir-clientes")
	public String inativarCliente(RequisicaoCliente requisicao) {
		Cliente cliente = clienteRepo.findById(Long.valueOf(requisicao.getClienteId())).get();
		
		ValidaCliente.alteraStatus(cliente);		
		clienteRepo.save(cliente);
		
		return "redirect:/adm/exibir-clientes";
	}
	
	@GetMapping("/controle-estoque")
	public ModelAndView painelEstoque() {
		mv = new ModelAndView("adm/adm-produtos");
		return mv;
	}	
	
	@GetMapping("/cadastrar-produto")
	public ModelAndView novoProduto() {
		mv = new ModelAndView("adm/cadastro-produto");
		return mv;
	}
	
	@PostMapping("/cadastrar-produto")
	public String novoProduto(Principal principal, RequisicaoProduto requisicao) {		
		Produto produto = requisicao.toProduto();
		prodRepo.save(produto);
		
		Usuario usuario = userRepo.findById(principal.getName()).get();
//		LogTransacao lt = new LogTransacao(usuario, "INSERT produto " + produto.getId());
//		ltRepo.save(lt);
		
		return "redirect:/adm/controle-estoque";
	}
	
	@GetMapping("/editar-produto/{id}")
	public ModelAndView editarProduto(@PathVariable("id") Long id) {
		Produto produto = prodRepo.findById(Long.valueOf(id)).get();
		
		mv = new ModelAndView("adm/editar-produto");
		mv.addObject("produto", produto);
		
		return mv;
	}
	
	@PostMapping("/editar-produto/{id}")
	public String editarProduto(RequisicaoProduto requisicao, Principal principal) {	
		Produto produto = requisicao.toProduto();		
		prodRepo.save(produto);
		
		Usuario usuario = userRepo.findById(principal.getName()).get();
//		LogTransacao lt = new LogTransacao(usuario, "UPDATE produto " + produto.getId());
//		ltRepo.save(lt);
		
		return "redirect:/adm/listar-produtos";
	}
	
	@GetMapping("/listar-produtos")
	public ModelAndView exibiProdutos() {
		List<Produto> produtos = prodRepo.findAll();	
		mv = new ModelAndView("adm/adm-exibir-produtos");		
		mv.addObject("produtos", produtos);		
		return mv;
	}
	
	@PostMapping("/listar-produtos")
	public String inativarProduto(RequisicaoProduto requisicao, Principal principal) {
		Produto produto = prodRepo.findById(Long.valueOf(requisicao.getProdutoId())).get();		
		ValidaEstoque.alteraStatus(produto);		
		prodRepo.save(produto);
		
		Usuario usuario = userRepo.findById(principal.getName()).get();
//		LogTransacao lt = new LogTransacao(usuario, "UPDATE produto " + produto.getId());
//		ltRepo.save(lt);
		
		return "redirect:/adm/listar-produtos";
	}
	
	@GetMapping("/controle-cupom")
	public ModelAndView painelCupom() {
		mv = new ModelAndView("adm/adm-cupom");
		return mv;
	}
	
	@GetMapping("/cadastrar-cupom")
	public ModelAndView novoCupom() {
		mv = new ModelAndView("adm/cadastro-cupom");
		return mv;
	}
	
	@PostMapping("/cadastrar-cupom")
	public String novoCupom(RequisicaoCupom requisicao, Principal principal) {
		Cupom cupom = requisicao.toCupom();
		cupomRepo.save(cupom);
		
		Usuario usuario = userRepo.findById(principal.getName()).get();
//		LogTransacao lt = new LogTransacao(usuario, "INSERT cupom " + cupom.getCodigo());
//		ltRepo.save(lt);
		
		return "redirect:/adm/listar-cupons";
	}
	
	@GetMapping("/listar-cupons")
	public ModelAndView listarCupons() {
		List<Cupom> cupons = cupomRepo.findAll();
		
		mv = new ModelAndView("adm/adm-exibir-cupons");
		mv.addObject("cupons", cupons);
		return mv;
	}
	
	@GetMapping("/exibir-vendas")
	public ModelAndView listarVendas() {
		List<Compra> compras = compraRepo.findAll();
		mv = new ModelAndView("adm/adm-exibir-vendas");
		mv.addObject("compras", compras);
		return mv;
	}
	
	@GetMapping("/venda-detalhes/{id}")
	public ModelAndView vendaDetalhes(@PathVariable("id") Long id) {
		Compra compra = compraRepo.findById(id).get();
		List<CompraProduto> lcp = cpRepo.findByCompraCompraId(compra.getCompraId());
		List<Produto> produtos = ValidaCarrinho.gerarListaCompras(lcp);
//		List<Documento> cartoes = compra.getDocumentos();
		List<String> cartoes = compra.getCartoes();
		
		mv = new ModelAndView("adm/adm-venda-detalhes");
		mv.addObject("compra", compra);
		mv.addObject("produtos", produtos);
		mv.addObject("cartoes", cartoes);
		return mv;
	}
	
	@PostMapping("/venda-detalhes/{id}")
	public String alteraStatusTroca(RequisicaoCompra reqCompra, RequisicaoProduto reqProd, Principal principal) {
		Usuario usuario = userRepo.findById(principal.getName()).get();
		Compra compra = compraRepo.findById(Long.valueOf(reqCompra.getCompraId())).get();
		List<CompraProduto> lcp = cpRepo.findByCompraCompraId(compra.getCompraId());
		Produto produto = prodRepo.findById(Long.valueOf(reqProd.getProdutoId())).get();
		
		for(CompraProduto cp : lcp) {
			if(cp.getProduto().getProdutoId() == Long.valueOf(reqProd.getProdutoId())) {
				cp.setStatusTroca(StatusTroca.AUTORIZADO);
				produto.setEstoque(produto.getEstoque() + cp.getQuantidade());
				cpRepo.save(cp);	
				
//				LogTransacao lt1 = new LogTransacao(usuario, "UPDATE troca " + produto.getId() + " da compra " + cp.getCompra());
//				ltRepo.save(lt1);
			}
		}
		
		prodRepo.save(produto);
		System.out.println("Produto: " + produto.getNome() + " devolvido ao estoque");
		
//		LogTransacao lt2 = new LogTransacao(usuario, "UPDATE produto " + produto.getId());
//		ltRepo.save(lt2);
		
//		CupomRepo.findAll não pode ser nulo
		List<Cupom> cupons = cupomRepo.findAll();
		Cupom cupomTroca = new Cupom();				
		cupomTroca = ValidaCupom.cupomTroca(produto.getValor());			
		cupomTroca.setCodigo(ValidaCupom.gerarCupom(cupons));
		System.out.println("Código: " + cupomTroca.getCodigo());			
		cupomRepo.save(cupomTroca);
		
		compra.addCupom(cupomTroca);
		compraRepo.save(compra);
		
		return "redirect:/adm/exibir-vendas";
	}
	
	@PostMapping("/exibir-vendas")
	public String alterarStatus(RequisicaoCompra requisicao) {
		Compra compra = compraRepo.findById(Long.valueOf(requisicao.getCompraId())).get();		
		compra.setCompraStatus(CompraStatus.valueOf(requisicao.getCompraStatus()));
		compraRepo.save(compra);
		return "redirect:/adm/exibir-vendas";		
		
	}
}
