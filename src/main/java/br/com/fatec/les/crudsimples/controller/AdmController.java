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
import br.com.fatec.les.crudsimples.repository.ClienteRepository;
import br.com.fatec.les.crudsimples.repository.CompraRepository;
import br.com.fatec.les.crudsimples.repository.CupomRepository;
import br.com.fatec.les.crudsimples.repository.ProdutoRepository;

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
		compra.setCompraStatus(CompraStatus.valueOf(requisicao.getCompraStatus()));
		compraRepo.save(compra);
		
		return "redirect:/adm/exibir-vendas";
	}
}
