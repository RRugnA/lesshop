package br.com.fatec.les.crudsimples.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.crudsimples.dto.RequisicaoCompra;
import br.com.fatec.les.crudsimples.dto.RequisicaoCupom;
import br.com.fatec.les.crudsimples.dto.RequisicaoProduto;
import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.Compra;
import br.com.fatec.les.crudsimples.model.CompraStatus;
import br.com.fatec.les.crudsimples.model.Cupom;
import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.model.StatusProduto;
import br.com.fatec.les.crudsimples.repository.ClienteRepository;
import br.com.fatec.les.crudsimples.repository.CompraRepository;
import br.com.fatec.les.crudsimples.repository.CupomRepository;
import br.com.fatec.les.crudsimples.repository.ProdutoRepository;
import br.com.fatec.les.crudsimples.strategy.GeraCupom;

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
	public String novoProduto(RequisicaoProduto requisicao) {
		
		Produto prod = requisicao.toProduto();
		prodRepo.save(prod);
		
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
	public String editarProduto(RequisicaoProduto requisicao) {
		Produto produto = requisicao.toProduto();		
		prodRepo.save(produto);
		
		return "redirect:/adm/listar-produtos";
	}
	
	@GetMapping("/listar-produtos")
	public ModelAndView exibiProdutos() {
		mv = new ModelAndView("adm/adm-exibir-produtos");
		
		List<Produto> produtos = prodRepo.findAll();
		
		mv.addObject("produtos", produtos);
		
		return mv;
	}
	
	@PostMapping("/listar-produtos")
	public String inativarProduto(RequisicaoProduto requisicao) {
		Produto produto = prodRepo.findById(Long.valueOf(requisicao.getId())).get();
		if(produto.getProdStatus().equals(StatusProduto.ESTOQUE)) {
			produto.setProdStatus(StatusProduto.INATIVO);
		} else {
			produto.setProdStatus(StatusProduto.ESTOQUE);
		}		
		
		prodRepo.save(produto);
		
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
	public String novoCupom(RequisicaoCupom requisicao) {
		Cupom cupom = requisicao.toCupom();
		cupomRepo.save(cupom);
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
		List<Produto> produtos = compra.getListaCompras();
		mv = new ModelAndView("adm/adm-venda-detalhes");
		mv.addObject("compra", compra);
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	@PostMapping("/exibir-vendas")
	public String alterarStatus(RequisicaoCompra requisicao) {
		Compra compra = compraRepo.findById(Long.valueOf(requisicao.getId())).get();
		
		if(compra.getCompraStatus().equals(CompraStatus.TROCA_SOLICITADA)){
			List<Cupom> cupons = cupomRepo.findAll();
			
			Cupom cupomTroca = new Cupom();				
			cupomTroca = new RequisicaoCupom().cupomTroca(compra.getValorTotal());			
			cupomTroca.setCodigo(GeraCupom.gerarCupom(cupons));
			System.out.println("Código: " + cupomTroca.getCodigo());			
			cupomRepo.save(cupomTroca);
			
			compra.addCupom(cupomTroca);
			compra.setCompraStatus(CompraStatus.TROCA_AUTORIZADA);
			compraRepo.save(compra);
			
			return "redirect:/adm/exibir-vendas";
		} 
		
		compra.setCompraStatus(CompraStatus.valueOf(requisicao.getCompraStatus()));
		compraRepo.save(compra);
		return "redirect:/adm/exibir-vendas";		
		
	}
}
