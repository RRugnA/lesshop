package br.com.fatec.les.crudsimples.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.crudsimples.dto.RequisicaoProduto;
import br.com.fatec.les.crudsimples.model.DadosProduto;
import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.repository.DadosProdutoRepository;
import br.com.fatec.les.crudsimples.repository.ProdutoRepository;

@Controller
@RequestMapping("adm")
public class GraphController {
	
	@Autowired
	private DadosProdutoRepository dpRepo;
	@Autowired
	private ProdutoRepository prodRepo;
	
	@GetMapping("/analiseGrafica")
	public ModelAndView graph() {
		List<Produto> produtos = prodRepo.findAll();
		Map<String, Integer> surveyMap = new LinkedHashMap<>();
		String nome = "";
		
		ModelAndView mv = new ModelAndView("adm/analiseGrafica");
		mv.addObject("produtos", produtos);
		mv.addObject("surveyMap", surveyMap);
		mv.addObject("nomeProduto", nome);
		return mv;
	}

	@PostMapping("/analiseGrafica")
	public ModelAndView lineGraph(RequisicaoProduto requisicao, Model model) {
		List<Produto> produtos = prodRepo.findAll();
		List<DadosProduto> ldp = dpRepo.findByProdutoProdutoId(Long.valueOf(requisicao.getProdutoId()));		
		Map<String, Integer> surveyMap = new LinkedHashMap<>();
		String nome = "";
		
		for(DadosProduto dp : ldp) {
			surveyMap.put(dp.getDataCadastro().getMonth().toString(), dp.getQtde());
			nome = dp.getProduto().getNome();
		}
		ModelAndView mv = new ModelAndView("adm/analiseGrafica");
		mv.addObject("produtos", produtos);
		mv.addObject("surveyMap", surveyMap);
		mv.addObject("nomeProduto", nome);
		return mv;
	}

}
