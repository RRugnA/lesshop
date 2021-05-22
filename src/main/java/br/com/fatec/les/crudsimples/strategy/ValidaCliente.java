package br.com.fatec.les.crudsimples.strategy;

import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.TipoStatus;

public class ValidaCliente {

	public static void alteraStatus(Cliente cliente) {
		if(cliente.getTipoStatus().equals(TipoStatus.ATIVO)) {
			cliente.setTipoStatus(TipoStatus.INATIVO);
		} else {
			cliente.setTipoStatus(TipoStatus.ATIVO);
		}	
	}
}
