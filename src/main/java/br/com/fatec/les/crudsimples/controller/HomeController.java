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

import br.com.fatec.les.crudsimples.dto.RequisicaoCompra;
import br.com.fatec.les.crudsimples.dto.RequisicaoProduto;
import br.com.fatec.les.crudsimples.model.Cidade;
import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.Compra;
import br.com.fatec.les.crudsimples.model.CompraStatus;
import br.com.fatec.les.crudsimples.model.Documento;
import br.com.fatec.les.crudsimples.model.Endereco;
import br.com.fatec.les.crudsimples.model.Estado;
import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.model.StatusProduto;
import br.com.fatec.les.crudsimples.model.Usuario;
import br.com.fatec.les.crudsimples.repository.ClienteRepository;
import br.com.fatec.les.crudsimples.repository.CompraRepository;
import br.com.fatec.les.crudsimples.repository.DocumentoRepository;
import br.com.fatec.les.crudsimples.repository.EnderecoRepository;
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
	@Autowired
	private EnderecoRepository endRepo;
	@Autowired
	private DocumentoRepository docRepo;
	@Autowired
	private CompraRepository compraRepo;
	
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
	public String produto(@PathVariable("id") Long id, Principal principal, RequisicaoProduto requisicao) {
		
//		REMOVENDO PRODUTO DO ESTOQUE
		Produto produto = prodRepo.findById(id).get();
		produto.setProEstoque(produto.getProEstoque() - Integer.parseInt(requisicao.getQtdProduto()));
		prodRepo.save(produto);
		
//		ADICIONANDO PRODUTO AO CLIENTE
		Usuario usuario = userRepo.findByLogin(principal.getName());
		Cliente cliente = usuario.getCliente();	
		produto.setProQtde(Integer.parseInt(requisicao.getQtdProduto()));
		cliente.addProduto(produto);
		cliente.addValorDeCompra(produto.getProValor(), produto.getProQtde());
		clienteRepo.save(cliente);
		
		return "redirect:/carrinho/";
	}
	
	@GetMapping("carrinho")
	public ModelAndView carrinho(Principal principal) {
		Usuario user = userRepo.findByLogin(principal.getName());
		Cliente cliente = user.getCliente();

//		IDENTIFICANDO OS PRODUTOS NO CARRINHO DO CLIENTE
		List<Produto> produtos = new ArrayList<Produto>();		
		for(Produto produto : cliente.getProdutos()) {
			produtos.add(produto);
		}
		
//		IDENTIFICANDO E DELETANDO COMPRAS INTERROMPIDAS
		List<Compra> compras = compraRepo.findByClienteId(cliente.getId());
		for(Compra compra : compras) {
			if(compra.getCompraStatus().equals(CompraStatus.valueOf("INTERROMPIDA"))) {
				compraRepo.delete(compra);
			}
		}
		
		mv = new ModelAndView("carrinho");
//		DEVOLVENDO OS PRODUTOS PARA A PÁGINA
		mv.addObject("cliente", cliente);
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	@PostMapping("/excluir-carrinho")
	public String deleteProduto(Principal principal, RequisicaoProduto requisicao) {
		System.out.println("Removendo item");

		Usuario user = userRepo.findByLogin(principal.getName());
		Cliente cliente = user.getCliente();
		cliente.zeraValorDeCompra();
		
//		DEVOLVENDO AO ESTOQUE
		Produto prod = prodRepo.findById(Long.valueOf(requisicao.getId())).get();
		prod.setProEstoque(prod.getProEstoque() + prod.getProQtde());
		prod.setProQtde(0);
		prodRepo.save(prod);
		
//		RETIRANDO DO CARRINHO DO CLIENTE
		List<Produto> produtos = new ArrayList<Produto>();	
		for(Produto produto : cliente.getProdutos()) {
			if(produto.getId() != Long.valueOf(requisicao.getId())) {
				produtos.add(produto);
				cliente.addValorDeCompra(produto.getProValor(), produto.getProQtde());
			}
		}		
		cliente.setProdutos(produtos);		
		clienteRepo.save(cliente);
		
		return "redirect:/carrinho";
	}
	
	@GetMapping("carrinho-endereco")
	public ModelAndView carrinhoEndereco(Principal principal) {
		Usuario user = userRepo.findByLogin(principal.getName());
		Cliente cliente = user.getCliente();
		
		List<Endereco> enderecos = endRepo.findByCliente(cliente);
		
		List<Produto> produtos = new ArrayList<Produto>();		
		for(Produto produto : cliente.getProdutos()) {
			produtos.add(produto);
		}			
		
		mv = new ModelAndView("carrinho-endereco");
		mv.addObject("enderecos", enderecos);	
		mv.addObject("produtos", produtos);
		mv.addObject("cliente", cliente);		
		
		return mv;
	}
	
	@PostMapping("carrinho-endereco")
	public String carrinhoEndereco(RequisicaoCompra requisicao, Principal principal) {
		Usuario user = userRepo.findByLogin(principal.getName());
		Cliente cliente = user.getCliente();
		
		Endereco end = endRepo.findById(Long.valueOf(requisicao.getEndereco())).get();
		Cidade cidade = end.getCidade();
		Estado estado = cidade.getEstado();
		
		Compra compra = requisicao.toCompra();
		compra.setCliente(cliente);
		compra.setEndereco(end.getLogradouro() + ", " + end.getNumero() + " - " + cidade.getCidade() + "/" + estado.getEstado());
		compra.setCompraStatus(CompraStatus.INTERROMPIDA);
		compraRepo.save(compra);
		
		return "redirect:/carrinho-pgto";
	}
	
	@GetMapping("carrinho-pgto")
	public ModelAndView carrinhoPgto(Principal principal) {
		Usuario user = userRepo.findByLogin(principal.getName());
		Cliente cliente = user.getCliente();		
		
		List<Documento> documentos = docRepo.findByCliente(cliente);
		
		List<Produto> produtos = new ArrayList<Produto>();		
		for(Produto produto : cliente.getProdutos()) {
			produtos.add(produto);
		}
		
		mv = new ModelAndView("carrinho-pgto");
		mv.addObject("documentos", documentos);
		mv.addObject("produtos", produtos);
		mv.addObject("cliente", cliente);	
		
		return mv;
	}
	
	@GetMapping("carrinho-parcelamento")
	public ModelAndView carrinhoParcelamento(Principal principal) {
		Usuario user = userRepo.findByLogin(principal.getName());
		Cliente cliente = user.getCliente();		
		
		List<Documento> documentos = docRepo.findByCliente(cliente);
		
		List<Produto> produtos = new ArrayList<Produto>();		
		for(Produto produto : cliente.getProdutos()) {
			produtos.add(produto);
		}
		mv = new ModelAndView("carrinho-parcelamento");
		mv.addObject("documentos", documentos);
		mv.addObject("produtos", produtos);
		mv.addObject("cliente", cliente);	
		
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
