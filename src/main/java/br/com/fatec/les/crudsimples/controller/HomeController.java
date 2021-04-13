package br.com.fatec.les.crudsimples.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.crudsimples.dto.RequisicaoProduto;
import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.model.StatusProduto;
import br.com.fatec.les.crudsimples.model.Usuario;
import br.com.fatec.les.crudsimples.repository.ClienteRepository;
import br.com.fatec.les.crudsimples.repository.ProdutoRepository;
import br.com.fatec.les.crudsimples.repository.UsuarioRepository;

@Controller
public class HomeController {

	ModelAndView mv;
	
	@Autowired
	private ProdutoRepository prodRepo;
	
	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@GetMapping("lesshop")
	public ModelAndView index() {		
		PageRequest paginacao = PageRequest.of(0, 6);		
		List<Produto> produtos = prodRepo.findByProdStatus(StatusProduto.ESTOQUE, paginacao);
		
		mv = new ModelAndView("lesshop");
		mv.addObject("produtos", produtos);		
		
		return mv;
	}
	
	@PostMapping("lesshop")
	public String index(RequisicaoProduto requisicao) {
		
		Produto produto = prodRepo.findById(Long.valueOf(requisicao.getId())).get();
		mv.addObject("produto", produto);
		
		return "redirect:/produto/" + produto.getId().toString();
	}
	
	@GetMapping("produto/{id}")
	public ModelAndView exibirProduto(@PathVariable("id") Long id) {
		mv = new ModelAndView("produto");
		Produto produto = prodRepo.findById(id).get();
		mv.addObject("produto", produto);		
		return mv;
	}
	
	@PostMapping("produto/{id}")
	public String produto(@PathVariable("id") Long id, Principal principal) {
		
		Produto produto = prodRepo.findById(id).get();
//		produto.setProdStatus(StatusProduto.CARRINHO);
		produto.setProQtde(produto.getProQtde() -1);
		prodRepo.save(produto);
		
		System.out.println("Produto: " + produto.getProNome() + " comprado!") ;
		
		Usuario usuario = userRepo.findByLogin(principal.getName());
		Cliente cliente = usuario.getCliente();		
		cliente.addProduto(produto);
		
		clienteRepo.save(cliente);
		
		return "redirect:/carrinho/";
	}
	
	@GetMapping("carrinho")
	public ModelAndView carrinho(Principal principal) {
		Usuario user = userRepo.findByLogin(principal.getName());
		Cliente cliente = user.getCliente();

		List<Produto> produtos = new ArrayList<Produto>();		
		for(Produto produto : cliente.getProdutos()) {
			produtos.add(produto);
		}
		
		mv = new ModelAndView("carrinho");
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	@GetMapping("carrinho-login")
	public ModelAndView carrinhoLogin() {
		mv = new ModelAndView("carrinho-login");
		return mv;
	}
	
	@GetMapping("carrinho-endereco")
	public ModelAndView carrinhoEndereco() {
		mv = new ModelAndView("carrinho-endereco");
		return mv;
	}
	
	@GetMapping("carrinho-pgto")
	public ModelAndView carrinhoPgto() {
		mv = new ModelAndView("carrinho-pgto");
		return mv;
	}
	
	@GetMapping("carrinho-parcelamento")
	public ModelAndView carrinhoParcelamento() {
		mv = new ModelAndView("carrinho-parcelamento");
		return mv;
	}
	
	@GetMapping("carrinho-revisao")
	public ModelAndView carrinhoRevisao() {
		mv = new ModelAndView("carrinho-revisao");
		return mv;
	}
	
	@GetMapping("carrinho-sucesso")
	public ModelAndView carrinhoSucesso() {
		mv = new ModelAndView("carrinho-sucesso");
		return mv;
	}
	
	@GetMapping("home")
	public ModelAndView home() {
		mv = new ModelAndView("home");
		return mv;
	}
}
