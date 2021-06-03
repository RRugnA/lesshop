package br.com.fatec.les.crudsimples.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.crudsimples.dto.RequisicaoCompra;
import br.com.fatec.les.crudsimples.dto.RequisicaoCupom;
import br.com.fatec.les.crudsimples.dto.RequisicaoDocumento;
import br.com.fatec.les.crudsimples.dto.RequisicaoEndereco;
import br.com.fatec.les.crudsimples.dto.RequisicaoProduto;
import br.com.fatec.les.crudsimples.model.Cidade;
import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.Compra;
import br.com.fatec.les.crudsimples.model.CompraProduto;
import br.com.fatec.les.crudsimples.model.CompraStatus;
import br.com.fatec.les.crudsimples.model.Cupom;
import br.com.fatec.les.crudsimples.model.DadosProduto;
import br.com.fatec.les.crudsimples.model.Documento;
import br.com.fatec.les.crudsimples.model.Endereco;
import br.com.fatec.les.crudsimples.model.Estado;
import br.com.fatec.les.crudsimples.model.FormaPgto;
import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.model.StatusProduto;
import br.com.fatec.les.crudsimples.model.StatusTroca;
import br.com.fatec.les.crudsimples.model.TipoCupom;
import br.com.fatec.les.crudsimples.model.UsoCupom;
import br.com.fatec.les.crudsimples.model.Usuario;
import br.com.fatec.les.crudsimples.repository.ClienteRepository;
import br.com.fatec.les.crudsimples.repository.CompraProdutoRepository;
import br.com.fatec.les.crudsimples.repository.CompraRepository;
import br.com.fatec.les.crudsimples.repository.CupomRepository;
import br.com.fatec.les.crudsimples.repository.DadosProdutoRepository;
import br.com.fatec.les.crudsimples.repository.DocumentoRepository;
import br.com.fatec.les.crudsimples.repository.EnderecoRepository;
import br.com.fatec.les.crudsimples.repository.ProdutoRepository;
import br.com.fatec.les.crudsimples.repository.UsuarioRepository;
import br.com.fatec.les.crudsimples.strategy.NumCartao;
import br.com.fatec.les.crudsimples.strategy.ValidaCarrinho;
import br.com.fatec.les.crudsimples.strategy.ValidaCliente;
import br.com.fatec.les.crudsimples.strategy.ValidaCupom;
import br.com.fatec.les.crudsimples.strategy.ValidaEstoque;

@Controller
public class FacadeHome {

	private ModelAndView mv;
	@Autowired
	private EstoqueCommand estoque;	
	@Autowired
	private UsuarioRepository userRepo;
	@Autowired
	private ProdutoRepository prodRepo;
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
	@Autowired
	private CupomRepository cupomRepo;
	@Autowired
	private DadosProdutoRepository dadosRepo;
	
	public ModelAndView home() {
//		EXIBINDO 8 PRODUTOS NA PÁGINA INICIAL
		PageRequest paginacao = PageRequest.of(0, 8);		
		List<Produto> produtos = prodRepo.findByStatusProduto(StatusProduto.ESTOQUE, paginacao);
		
		mv = new ModelAndView("lesshop");
		mv.addObject("produtos", produtos);				
		return mv;
	}
	
	public String infoProduto(RequisicaoProduto requisicao) {
		Produto produto = prodRepo.findById(Long.valueOf(requisicao.getProdutoId())).get();		
		return "redirect:/produto/" + produto.getProdutoId().toString();
	}
	
	public ModelAndView exibirProduto(Long id) {		
		Produto produto = prodRepo.findById(id).get();		
		mv = new ModelAndView("produto");
		mv.addObject("produto", produto);		
		return mv;
	}
	
	public String escolherProduto(Long id, Principal principal, RequisicaoProduto requisicao) {
//		REMOVENDO PRODUTO DO ESTOQUE
		Produto produto = prodRepo.findById(id).get();
		estoque.executeBaixaEstoque(produto, requisicao);
		estoque.executeControleEstoque(produto);
		prodRepo.save(produto);
		
//		ADICIONANDO PRODUTO AO CLIENTE
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		produto.setQtde(Integer.parseInt(requisicao.getQtd()));
		cliente.addProduto(produto);
		cliente.addValorDeCompra(produto.getValor(), produto.getQtde());
		clienteRepo.save(cliente);
		
		return "redirect:/carrinho/";
	}
	
	public ModelAndView exibirCarrinho(Principal principal) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		
//		IDENTIFICANDO OS PRODUTOS NO CARRINHO DO CLIENTE
		List<Produto> produtos = cliente.getProdutos();
		
//		IDENTIFICANDO E DELETANDO COMPRAS NÃO FINALIZADAS
		List<Compra> compras = compraRepo.findByClienteClienteId(cliente.getClienteId());
		for(Compra compra : compras) {
			if(compra.getCompraStatus().equals(CompraStatus.valueOf("ANDAMENTO"))) {
				List<CompraProduto> lcp = cpRepo.findByCompraCompraId(compra.getCompraId());
				for(CompraProduto cp : lcp) {
					cpRepo.delete(cp);
				}				
				compraRepo.delete(compra);				
			}
		}
		
//		DEVOLVENDO OS PRODUTOS PARA A PÁGINA
		mv = new ModelAndView("carrinho");
		mv.addObject("cliente", cliente);
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	public String deletarProduto(Principal principal, RequisicaoProduto requisicao) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		cliente.zeraValorDeCompra();
		
//		DEVOLVENDO AO ESTOQUE
		Produto prod = prodRepo.findById(Long.valueOf(requisicao.getProdutoId())).get();
		ValidaEstoque.retornaEstoque(prod);
		prod.setQtde(0);
		ValidaEstoque.controleEstoque(prod);
		System.out.println("produto: " + prod.getStatusProduto());
		prodRepo.save(prod);
		
//		RETIRANDO DO CARRINHO DO CLIENTE	
		cliente.removeProduto(prod);
		clienteRepo.save(cliente);
		
		return "redirect:/carrinho";
	}
	
	public String salvarCarrinho(Principal principal, RequisicaoCompra requisicao, RequisicaoProduto reqProd) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		
//		INCLUINDO PRODUTO NA COMPRA
		Compra compra = requisicao.toCompra();		
		compra.setCliente(cliente);
		compra.setValorTotal(cliente.getValorDeCompra());
		
//		TESTE COMPRAPRODUTO
		compraRepo.save(compra);	
		List<Produto> produtos = ValidaCarrinho.getProdutos(cliente);
		for(Produto produto : produtos) {
			CompraProduto cp = new CompraProduto(produto, produto.getQtde(), compra, StatusTroca.NAO_SOLICITADO);
			produto.getListaCompras().add(cp);
			compra.getListaCompras().add(cp);
			cpRepo.save(cp);
			
			produto.setQtde(0);
			prodRepo.save(produto);
		}
		
		compraRepo.save(compra);	
		
		return "redirect:/carrinho-endereco";
	}
	
	public ModelAndView exibirCarrinhoEndereco(Principal principal) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		
//		RECUPERAR ENDEREÇO DO CLIENTE
		List<Endereco> enderecos = endRepo.findByCliente(cliente);	
		
//		RECUPERAR LISTA DE PRODUTOS
		List<Produto> produtos = ValidaCarrinho.getProdutos(cliente);			
		
		mv = new ModelAndView("carrinho-endereco");
		mv.addObject("enderecos", enderecos);	
		mv.addObject("produtos", produtos);
		mv.addObject("cliente", cliente);		
		
		return mv;
	}
	
	public String novoEnderecoCarrinhoEndereco(Principal principal, RequisicaoEndereco requisicao) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		Endereco novoEndereco = requisicao.toEndereco();
		novoEndereco.setCliente(cliente);
		endRepo.save(novoEndereco);
		
		return "redirect:/carrinho-endereco";
	}
	
	public String salvarCarrinhoEndereco(RequisicaoCompra requisicao, Principal principal) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		Endereco end = endRepo.findById(Long.valueOf(requisicao.getEndereco())).get();
		Cidade cidade = end.getCidade();
		Estado estado = cidade.getEstado();
		
		List<Compra> compras = compraRepo.findByClienteClienteId(cliente.getClienteId());
		Compra compraAtual = new Compra().localizaCompra(CompraStatus.ANDAMENTO, compras);
		
		compraAtual.setEndereco(end.getLogradouro() + ", " + end.getNumero() + " - " + cidade.getCidade() + "/" + estado.getEstado());
		compraAtual.setValorTotal(cliente.getValorDeCompra());
		compraRepo.save(compraAtual);
		
		return "redirect:/carrinho-pgto";
	}
	
	public ModelAndView exibirCarrinhoPgto(Principal principal) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());		
		List<Produto> produtos = new ArrayList<Produto>();		
		for(Produto produto : cliente.getProdutos()) {
			produtos.add(produto);
		}
		
//		EXIBINDO 4 ÚLTIMOS DÍGITOS DO CARTÃO
		List<Documento> documentos = docRepo.findByCliente(cliente);
		NumCartao.gerarListaDocNumCartao(documentos);
		
		mv = new ModelAndView("carrinho-pgto");
		mv.addObject("documentos", documentos);
		mv.addObject("produtos", produtos);
		mv.addObject("cliente", cliente);	
		
		return mv;
	}
	
	public String novoCartaoCarrinhoPgto(Principal principal, RequisicaoDocumento requisicao) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		Documento novoCartao = requisicao.toDocumento();
		novoCartao.setCliente(cliente);
		docRepo.save(novoCartao);
		
		return "redirect:/carrinho-pgto";
	}
	
	public String salvarCarrinhoPgto(Principal principal, RequisicaoCompra requisicao) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		Compra compraAtual = new Compra();
		
//		IDENTIFICANDO COMPRA ATUAL
		List<Compra> compras = compraRepo.findByClienteClienteId(cliente.getClienteId());
		compraAtual = compraAtual.localizaCompra(CompraStatus.ANDAMENTO, compras);
		compraAtual.zeraCartoes();
		
		List<Documento> docs = new ArrayList<Documento>();
		List<String> ids = requisicao.getDocumento();
		for(String id : ids) {
			Documento doc = docRepo.findById(Long.valueOf(id)).get();
			docs.add(doc);
		}
//		compraAtual.setDocumentos(docs);
		if(docs.size() > 1) {
			compraAtual.setCartao1(docs.get(0).getNumeroCartao());
			System.out.println("cartao 1: " + compraAtual.getCartao1());
			compraAtual.setCartao2(docs.get(1).getNumeroCartao());
			System.out.println("cartao 2: " + compraAtual.getCartao2());
		} 
		if(docs.size() == 1){
			compraAtual.setCartao1(docs.get(0).getNumeroCartao());
			System.out.println("cartao: " + compraAtual.getCartao1());
		}
		compraAtual.setFormaPgto(FormaPgto.CARTAO);
		compraRepo.save(compraAtual);
		
		return "redirect:/carrinho-parcelamento";		
	}
	
	public ModelAndView exibirCarrinhoParcelamento(Principal principal) {
		Usuario user = userRepo.findById(principal.getName()).get();
		Cliente cliente = user.getCliente();	
		
		Compra compraAtual = new Compra();
		List<Compra> compras = compraRepo.findByClienteClienteId(cliente.getClienteId());
		compraAtual = compraAtual.localizaCompra(CompraStatus.ANDAMENTO, compras);
		
		List<Produto> produtos = ValidaCarrinho.getProdutos(cliente);			
		List<Cupom> cupons = compraAtual.getCupons();
		
//		EXIBINDO 4 ÚLTIMOS DÍGITOS DO CARTÃO
		List<String> cartoes = new ArrayList<String>();
		cartoes = NumCartao.gerarListaStringNumCartao(compraAtual.getCartoes());			
		List<Documento> clienteCartoes = cliente.getDocumentos();
		
//		CALCULANDO O VALOR DAS PARCELAS
		List<BigDecimal> parcelas = new ArrayList<BigDecimal>();		
		for(int i = 1; i <= 10; i++) {		
			BigDecimal valorParcela = cliente.getValorDeCompra();
			valorParcela = valorParcela.divide(new BigDecimal(i), 2, RoundingMode.HALF_DOWN);
			parcelas.add(valorParcela);
		}
		
		mv = new ModelAndView("carrinho-parcelamento");
		mv.addObject("compra", compraAtual);
		mv.addObject("produtos", produtos);
		mv.addObject("cliente", cliente);	
		mv.addObject("parcelas", parcelas);
		mv.addObject("cupons", cupons);
		mv.addObject("cartoes", cartoes);
		mv.addObject("clienteCartoes", clienteCartoes);
		
		return mv;
	}
	
	public ModelAndView inserirCupom(Principal principal, RequisicaoCupom requisicao) {
		mv = exibirCarrinhoParcelamento(principal);
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());		
		Compra compraAtual = new Compra();

//		IDENTIFICANDO COMPRA ATUAL
		List<Compra> compras = compraRepo.findByClienteClienteId(cliente.getClienteId());
		compraAtual = compraAtual.localizaCompra(CompraStatus.ANDAMENTO, compras);
		
//		VALIDANDO CÓDIGO DO CUPOM INFORMADO
		try {
			Cupom cupom = cupomRepo.findById(requisicao.getCodigo()).get();
			
			if(!ValidaCupom.cupomValido(cupom.getCodigo(), compraAtual.getCupons())) {
				mv.addObject("aviso", "Código Já utilizado");
				mv.addObject("cupons", compraAtual.getCupons());
				return mv;
			}			
			
//		BARRANDO CUPONS DESNECESSÁRIOS (RN0036)
			BigDecimal somaCupons = ValidaCupom.somaCupons(compraAtual.getCupons());
			if(ValidaCupom.cupomMaior(somaCupons, compraAtual.getValorTotal())){
				mv.addObject("aviso", "Cupom não utilizado. O valor atual é suficiente para realizar a compra!");
				mv.addObject("cupons", compraAtual.getCupons());
				return mv;
			}
			
			compraAtual.addCupom(cupom);
			compraRepo.save(compraAtual);					
			mv.addObject("aviso", "Código Válido");
			mv.addObject("cupons", compraAtual.getCupons());
			
		}catch (NoSuchElementException er){
			mv.addObject("aviso", "Código inválido");
			mv.addObject("cupons", compraAtual.getCupons());
			
		}
		
//		APLICANDO DESCONTO NO VALOR DA COMPRA
		if(compraAtual.getCupons() != null) {
			BigDecimal valorDesconto = new BigDecimal(0);
			for(Cupom cupom : compraAtual.getCupons()) {
				valorDesconto = valorDesconto.add(cupom.getValorDesconto());
			}
			
			System.out.println("Valor desconto: " + valorDesconto);
			
			cliente.setValorDeCompra(cliente.getValorDeCompra().subtract(valorDesconto));
			if(cliente.getValorDeCompra().doubleValue() < 0) {
				cliente.setValorDeCompra(new BigDecimal(0));
			}
			System.out.println("valor com desconto: " + cliente.getValorDeCompra());
			clienteRepo.save(cliente);
		}
		
//		CALCULANDO O VALOR DAS PARCELAS
		List<BigDecimal> parcelas = new ArrayList<BigDecimal>();		
		for(int i = 1; i <= 10; i++) {		
			BigDecimal valorParcela = cliente.getValorDeCompra();
			valorParcela = valorParcela.divide(new BigDecimal(i), 2, RoundingMode.HALF_DOWN);
			parcelas.add(valorParcela);
		}
		
		mv.addObject("parcelas", parcelas);
		return mv;
	}
	
	public ModelAndView removerCupom(String codigo, Principal principal, RequisicaoCupom requisicao) {
		mv = inserirCupom(principal, requisicao);		
		Cupom cupom = cupomRepo.findById(codigo).get();		
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());		
		Compra compraAtual = new Compra();

//		IDENTIFICANDO COMPRA ATUAL
		List<Compra> compras = compraRepo.findByClienteClienteId(cliente.getClienteId());
		compraAtual = compraAtual.localizaCompra(CompraStatus.ANDAMENTO, compras);
		
//		REMOVENDO CUPOM DA COMPRA
		compraAtual.removeCupom(cupom);		
		compraRepo.save(compraAtual);
		
//		REMOVENDO DESCONTO DO VALOR DA COMPRA
		cliente.setValorDeCompra(cliente.getValorDeCompra().add(cupom.getValorDesconto()));
		clienteRepo.save(cliente);
		
		mv.addObject("aviso", "Cupom excluído");
		return mv;
	}
	
	public String removerCartao(Principal principal, RequisicaoDocumento requisicao) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		Compra compraAtual = new Compra();
		
//		IDENTIFICANDO COMPRA ATUAL
		List<Compra> compras = compraRepo.findByClienteClienteId(cliente.getClienteId());
		compraAtual = compraAtual.localizaCompra(CompraStatus.ANDAMENTO, compras);
		
//		Documento doc = docRepo.findById(Long.valueOf(requisicao.getId())).get();
		String numeroCartao = requisicao.getNumeroCartao();
//		compraAtual.removeDocumento(doc);
		compraAtual.removeCartao(numeroCartao);
		compraRepo.save(compraAtual);
		
		return "redirect:/carrinho-parcelamento";
	}
	
	public String salvarCarrinhoParcelamento(Principal principal, RequisicaoCompra requisicao) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		Compra compraAtual = new Compra();
		
//		IDENTIFICANDO COMPRA ATUAL
		List<Compra> compras = compraRepo.findByClienteClienteId(cliente.getClienteId());
		compraAtual = compraAtual.localizaCompra(CompraStatus.ANDAMENTO, compras);
		
		compraAtual.setValorParcela(compraAtual.toValorParcela(requisicao.getValorParcela1()));
		compraAtual.setParcelas(compraAtual.toQtdeParcela(requisicao.getValorParcela1()));
		
		if(requisicao.getValorParcela().size() > 1) {
			compraAtual.setValorParcela2(compraAtual.toValorParcela(requisicao.getValorParcela2()));
			compraAtual.setParcelas2(compraAtual.toQtdeParcela(requisicao.getValorParcela2()));
		}
		
		compraAtual.setValorTotal(cliente.getValorDeCompra());
		compraRepo.save(compraAtual);
		
		return "redirect:/carrinho-revisao";
	}
	
	public ModelAndView exibirCarrinhoRevisao(Principal principal) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());		
		List<Compra> compras = compraRepo.findByClienteClienteId(cliente.getClienteId());
		Compra compraAtual = new Compra().localizaCompra(CompraStatus.ANDAMENTO, compras);
		
		List<Produto> produtos = new ArrayList<Produto>();		
		for(Produto produto : cliente.getProdutos()) {
			produtos.add(produto);
		}
		
		List<String> cartoes = NumCartao.gerarListaStringNumCartao(compraAtual.getCartoes());
		
		mv = new ModelAndView("carrinho-revisao");
		mv.addObject("compra", compraAtual);
		mv.addObject("cliente", cliente);
		mv.addObject("produtos", produtos);
		mv.addObject("cartoes", cartoes);
		return mv;
	}
	
	public String salvarCarrinhoRevisao(Principal principal, RequisicaoCompra requisicao) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());		
		List<Compra> compras = compraRepo.findByClienteClienteId(cliente.getClienteId());
		Compra compraAtual = new Compra().localizaCompra(CompraStatus.ANDAMENTO, compras);
		
		compraAtual.setCompraStatus(CompraStatus.valueOf(requisicao.getCompraStatus()));
		compraRepo.save(compraAtual);
		
		return "redirect:/carrinho-sucesso";
	}
	
	public ModelAndView exibirCarrinhoSucesso(Principal principal) {
		Cliente cliente = ValidaCliente.localizaCliente(userRepo.findById(principal.getName()).get());
		List<Compra> compras = compraRepo.findByClienteClienteId(cliente.getClienteId());
		Compra compraAtual = new Compra().localizaCompra(CompraStatus.PROCESSAMENTO, compras);
		
		BigDecimal valorTotalProdutos = new BigDecimal(0);		
		List<Produto> produtos = new ArrayList<Produto>();
		
		List<CompraProduto> lcp = cpRepo.findByCompraCompraId(compraAtual.getCompraId());
		for(CompraProduto cp : lcp) {
			Produto produto = cp.getProduto();
			produtos.add(produto);
			
			DadosProduto dp = new DadosProduto(produto, cp.getQuantidade());
			dadosRepo.save(dp);
			
			valorTotalProdutos = valorTotalProdutos.add(produto.getValor());
		}
		
		
		List<String> cartoes = NumCartao.gerarListaStringNumCartao(compraAtual.getCartoes());
		
		mv = new ModelAndView("carrinho-sucesso");
		mv.addObject("produtos", produtos);
		mv.addObject("compra", compraAtual);
		mv.addObject("cartoes", cartoes);
		mv.addObject("cliente", cliente);
		
		compraAtual.setCompraStatus(CompraStatus.AGUARDANDO_PGTO);
		
//		INATIVANDO CUPONS UNITÁRIOS
		List<Cupom> cupons = compraAtual.getCupons();
		for(Cupom cupom : cupons) {
			if(cupom.getUsoCupom().equals(UsoCupom.UNICO))
				cupom.setTipoCupom(TipoCupom.INATIVO);
		}
		compraAtual.setCupons(cupons);
		
//		GERANDO CUPOM DE COMPENSAÇÃO
		BigDecimal somaCupons = ValidaCupom.somaCupons(compraAtual.getCupons());
		System.out.println("Soma dos cupons: " + somaCupons);
		if(ValidaCupom.cupomMaior(somaCupons, valorTotalProdutos)) {
			Cupom cupomCompensacao = new Cupom();
			cupomCompensacao.setCodigo(ValidaCupom.gerarCupom(compraAtual.getCupons()));
			cupomCompensacao.setDataCadastro(LocalDate.now());
			cupomCompensacao.setTipoCupom(TipoCupom.COMPENSACAO);
			cupomCompensacao.setUsoCupom(UsoCupom.UNICO);
			cupomCompensacao.setValorDesconto(ValidaCupom.valorCompensado(somaCupons, valorTotalProdutos));
			System.out.println("Cupom de compensação: " + cupomCompensacao.getCodigo() + " - valor: R$ " + cupomCompensacao.getValorDesconto());
			cupomRepo.save(cupomCompensacao);
			compraAtual.addCupom(cupomCompensacao);
		}		
		
		compraRepo.save(compraAtual);
		
		List<Produto> listaVazia = new ArrayList<Produto>();
		cliente.setProdutos(listaVazia);
		cliente.setValorDeCompra(new BigDecimal(0));
		clienteRepo.save(cliente);
		
		return mv;
	}
}
