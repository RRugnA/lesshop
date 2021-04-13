package br.com.fatec.les.crudsimples.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.crudsimples.business.IStrategy;
import br.com.fatec.les.crudsimples.model.EntidadeDominio;
import br.com.fatec.les.crudsimples.repository.ClienteRepository;

public class Fachada implements IFachada {

	private ClienteRepository clienteRepo;
	private ModelAndView mv;
	
	@Override
	public String cadastrar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String excluir(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String alterar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String validar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

}
