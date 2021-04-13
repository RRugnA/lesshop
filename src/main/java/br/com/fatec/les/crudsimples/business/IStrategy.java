package br.com.fatec.les.crudsimples.business;

import br.com.fatec.les.crudsimples.model.EntidadeDominio;

public interface IStrategy {

	public String processar(EntidadeDominio entidade);
}
