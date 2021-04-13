package br.com.fatec.les.crudsimples.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

	@GetMapping("novo")
	public ModelAndView novo () {
		ModelAndView mv = new ModelAndView("usuario/novo");
		return mv;
	}
}
