package br.com.fatec.les.crudsimples.controller;

import java.util.List;

import br.com.fatec.les.crudsimples.model.EntidadeDominio;

public interface IFachada {

	public String cadastrar(EntidadeDominio entidade);
	public String excluir(EntidadeDominio entidade);
	public String alterar(EntidadeDominio entidade);
	public List<EntidadeDominio> consultar(EntidadeDominio entidade);
	public String validar(EntidadeDominio entidade);
}
