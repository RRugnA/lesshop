package br.com.fatec.les.crudsimples.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.crudsimples.dto.RequisicaoProduto;
import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.repository.ClienteRepository;
import br.com.fatec.les.crudsimples.repository.ProdutoRepository;

@Controller
@RequestMapping("adm")
public class AdmController {

	private ModelAndView mv;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private ProdutoRepository prodRepo;
	
	@GetMapping("login-adm")
	public ModelAndView login() {
		mv = new ModelAndView("adm/login-adm");
		return mv;
	}
	
	@GetMapping("/adm-clientes")
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
}
