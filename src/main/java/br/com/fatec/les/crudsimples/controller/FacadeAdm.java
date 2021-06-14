package br.com.fatec.les.crudsimples.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import br.com.fatec.les.crudsimples.model.Dados;
import br.com.fatec.les.crudsimples.model.DadosProduto;
import br.com.fatec.les.crudsimples.model.LogTransacao;
import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.model.StatusTroca;
import br.com.fatec.les.crudsimples.model.Usuario;
import br.com.fatec.les.crudsimples.repository.ClienteRepository;
import br.com.fatec.les.crudsimples.repository.CompraProdutoRepository;
import br.com.fatec.les.crudsimples.repository.CompraRepository;
import br.com.fatec.les.crudsimples.repository.CupomRepository;
import br.com.fatec.les.crudsimples.repository.DadosProdutoRepository;
import br.com.fatec.les.crudsimples.repository.LogTransacaoRepository;
import br.com.fatec.les.crudsimples.repository.ProdutoRepository;
import br.com.fatec.les.crudsimples.repository.UsuarioRepository;
import br.com.fatec.les.crudsimples.strategy.NumCartao;
import br.com.fatec.les.crudsimples.strategy.ValidaCarrinho;
import br.com.fatec.les.crudsimples.strategy.ValidaCliente;
import br.com.fatec.les.crudsimples.strategy.ValidaCupom;
import br.com.fatec.les.crudsimples.strategy.ValidaEstoque;
import br.com.fatec.les.crudsimples.strategy.ValidaProduto;

@Controller
public class FacadeAdm {

	private ModelAndView mv;
	
	@Autowired
	private ClienteRepository clienteRepo;
	@Autowired
	private ProdutoRepository prodRepo;
	@Autowired
	private UsuarioRepository userRepo;
	@Autowired
	private CupomRepository cupomRepo;
	@Autowired
	private CompraRepository compraRepo;
	@Autowired
	private CompraProdutoRepository cpRepo;
	@Autowired
	private DadosProdutoRepository dpRepo;
	@Autowired
	private LogTransacaoRepository ltRepo;
	
	public ModelAndView exibirLogin() {
		mv = new ModelAndView("adm/login-adm");
		return mv;
	}
	
	public ModelAndView exibirClientes() {
		mv = new ModelAndView("adm/adm-clientes");		
		List<Cliente> clientes = clienteRepo.findAll();		
		mv.addObject("clientes", clientes);
		
		return mv;
	}
	
	public String salvarInativarCliente(RequisicaoCliente requisicao) {
		Cliente cliente = clienteRepo.findById(Long.valueOf(requisicao.getClienteId())).get();		
		ValidaCliente.alteraStatus(cliente);		
		clienteRepo.save(cliente);
		
		return "redirect:/adm/exibir-clientes";
	}
	
	public ModelAndView exibirPainelEstoque() {
		mv = new ModelAndView("adm/adm-produtos");
		return mv;
	}
	
	public ModelAndView exibirCadastrarProduto() {
		mv = new ModelAndView("adm/cadastro-produto");
		return mv;
	}
	
	public String salvarCadastrarProduto(Principal principal, RequisicaoProduto requisicao) {
		Produto produto = requisicao.toProduto();
		prodRepo.save(produto);
		
		Usuario usuario = userRepo.findById(principal.getName()).get();
		LogTransacao lt = new LogTransacao(usuario, "INSERT produto " + produto.getNome());
		ltRepo.save(lt);
		
		return "redirect:/adm/controle-estoque";
	}
	
	public ModelAndView exibirEditarProduto(Long id) {
		Produto produto = prodRepo.findById(Long.valueOf(id)).get();		
		mv = new ModelAndView("adm/editar-produto");
		mv.addObject("produto", produto);
		
		return mv;	
	}
	
	public String salvarEditarProduto(RequisicaoProduto requisicao, Principal principal) {
		Produto oldProduto = prodRepo.findById(Long.valueOf(requisicao.getProdutoId())).get();
		Produto produto = requisicao.toProduto();		
		
		Usuario usuario = userRepo.findById(principal.getName()).get();		
		List<String> alteracoes = ValidaProduto.getAlteracao(oldProduto, produto);
		for(String alteracao : alteracoes) {
			LogTransacao lt = new LogTransacao(usuario, "UPDATE produto " + alteracao);
			ltRepo.save(lt);
		}
		
		prodRepo.save(produto);		
		
		return "redirect:/adm/listar-produtos";
	}
	
	public ModelAndView exibirProdutos() {
		List<Produto> produtos = prodRepo.findAll();	
		mv = new ModelAndView("adm/adm-exibir-produtos");		
		mv.addObject("produtos", produtos);		
		return mv;
	}
	
	public String salvarInativarProduto(RequisicaoProduto requisicao, Principal principal) {
		Produto produto = prodRepo.findById(Long.valueOf(requisicao.getProdutoId())).get();	
		Produto oldProduto = new Produto();
		oldProduto.setStatusProduto(produto.getStatusProduto());
		oldProduto.setNome(produto.getNome());
		
		ValidaEstoque.alteraStatus(produto);	
		
		Usuario usuario = userRepo.findById(principal.getName()).get();
		String alteracao = ValidaProduto.getStatusAlteracao(oldProduto, produto);		
		LogTransacao lt = new LogTransacao(usuario, "UPDATE produto " + alteracao);
		ltRepo.save(lt);	
		
		prodRepo.save(produto);
		
		return "redirect:/adm/listar-produtos";
	}
	
	public ModelAndView exibirPainelCupom() {
		mv = new ModelAndView("adm/adm-cupom");
		return mv;
	}
	
	public ModelAndView exibirNovoCupom() {
		mv = new ModelAndView("adm/cadastro-cupom");
		return mv;
	}
	
	public String salvarNovoCupom(RequisicaoCupom requisicao, Principal principal) {
		Cupom cupom = requisicao.toCupom();
		cupomRepo.save(cupom);
		
		Usuario usuario = userRepo.findById(principal.getName()).get();
		LogTransacao lt = new LogTransacao(usuario, "INSERT cupom "	+ cupom.getCodigo() 
																	+ ", valor R$" + cupom.getValorDesconto() 
																	+ ", uso " + cupom.getUsoCupom());
		ltRepo.save(lt);
		
		return "redirect:/adm/listar-cupons";
	}
	
	public ModelAndView exibirListarCupom() {
		List<Cupom> cupons = cupomRepo.findAll();		
		mv = new ModelAndView("adm/adm-exibir-cupons");
		mv.addObject("cupons", cupons);
		return mv;
	}
	
	public ModelAndView exibirListarVendas() {
		List<Compra> compras = compraRepo.findAll();
		mv = new ModelAndView("adm/adm-exibir-vendas");
		mv.addObject("compras", compras);
		return mv;
	}
	
	public ModelAndView exibirVendaDetalhes(Long id) {
		Compra compra = compraRepo.findById(id).get();
		List<CompraProduto> lcp = cpRepo.findByCompraCompraId(compra.getCompraId());
		List<Produto> produtos = ValidaCarrinho.gerarListaCompras(lcp);
		List<String> cartoes = NumCartao.gerarListaStringNumCartao(compra.getCartoes());
		
		mv = new ModelAndView("adm/adm-venda-detalhes");
		mv.addObject("compra", compra);
		mv.addObject("produtos", produtos);
		mv.addObject("cartoes", cartoes);
		mv.addObject("listaCompra", lcp);
		return mv;
	}
	
	public String salvarAlterarStatusTroca(RequisicaoCompra reqCompra, RequisicaoProduto reqProd, Principal principal) {
		Usuario usuario = userRepo.findById(principal.getName()).get();
		Compra compra = compraRepo.findById(Long.valueOf(reqCompra.getCompraId())).get();
		List<CompraProduto> lcp = cpRepo.findByCompraCompraId(compra.getCompraId());
		Produto produto = prodRepo.findById(Long.valueOf(reqProd.getProdutoId())).get();
		
		for(CompraProduto cp : lcp) {
			if(cp.getProduto().getProdutoId() == Long.valueOf(reqProd.getProdutoId())) {
				cp.setStatusTroca(StatusTroca.AUTORIZADO);
				produto.setEstoque(produto.getEstoque() + cp.getQuantidade());
				cpRepo.save(cp);	
				
				LogTransacao lt1 = new LogTransacao(usuario, "UPDATE troca SOLICITADO para " + cp.getStatusTroca() 
															+ " do produto " + produto.getNome() 
															+ " da compra " + cp.getCompra().getCompraId() 
															+ " do cliente " + cp.getCompra().getCliente().getNome());
				ltRepo.save(lt1);
			}
		}
		
		prodRepo.save(produto);
		System.out.println("Produto: " + produto.getNome() + " devolvido ao estoque");
		
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
	
	public String salvarAlterarStatus(RequisicaoCompra requisicao) {
		Compra compra = compraRepo.findById(Long.valueOf(requisicao.getCompraId())).get();		
		compra.setCompraStatus(CompraStatus.valueOf(requisicao.getCompraStatus()));
		compraRepo.save(compra);
		return "redirect:/adm/exibir-vendas";	
	}
	
	public ModelAndView exibirLogs() {
		List<LogTransacao> logs = ltRepo.findAll();		
		mv = new ModelAndView("adm/adm-exibir-logs");
		mv.addObject("logs", logs);
		return mv;
	}
	public ModelAndView exibirGraficoVendas() {

		mv = new ModelAndView("adm/analise");
		
		return mv;
	}
	
//	@RequestMapping("/chart")
//	@ResponseBody
//	public String getDataFromDB() {
//		List<DadosProduto> dadosProdutos = dpRepo.findAll();
//		JsonArray jsonQtde = new JsonArray();
//		JsonArray jsonData = new JsonArray();
//		JsonObject json = new JsonObject();
//		dadosProdutos.forEach(dado->{
//			jsonQtde.add(dado.getQtde());
//			jsonData.add(dado.getDataCadastro().toString());
//		});
//		json.add("qtde", jsonQtde);
//		json.add("data", jsonData);
//		return json.toString();
//	}
//	
//	@RequestMapping("/multichart")
//	@ResponseBody
//	public Map getDataForMulipleLine(){
//		Map<String, List<DadosProduto>> mappedData = new HashMap<>();
//		List<DadosProduto> dps = dpRepo.findAll();
//		
//		for(DadosProduto dp : dps) {
//			
//			if(mappedData.containsKey(dp.getProduto().toString())){
//				mappedData.get(dp.getProduto().toString()).add(dp);
//			} else {
//				List<DadosProduto> tempList = new ArrayList<>();
//				tempList.add(dp);
//				mappedData.put(dp.getProduto().toString(), tempList);
//			}
//		}
//		return mappedData;
//	}
}
