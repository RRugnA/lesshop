package br.com.fatec.les.crudsimples.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.crudsimples.model.Produto;
import br.com.fatec.les.crudsimples.repository.ProdutoRepository;

@Controller
@RequestMapping("adm")
public class ProdutoController {
	
	private ModelAndView mv;

	@Autowired
	private ProdutoRepository prodRepo;
	
	@GetMapping("/listar-produtos")
	public ModelAndView exibir() {
		mv = new ModelAndView("adm/adm-exibir-produtos");
		
		List<Produto> produtos = prodRepo.findAll();
		
		mv.addObject("produtos", produtos);
		
		return mv;
	}
}
