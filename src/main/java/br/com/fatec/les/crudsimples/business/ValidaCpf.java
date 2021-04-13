package br.com.fatec.les.crudsimples.business;

import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.EntidadeDominio;

public class ValidaCpf implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		
		if(entidade instanceof Cliente){
			Cliente c = (Cliente)entidade;
			
			if(c.getTipoDocumento().toString() == "CPF") {
				
				if(c.getNumeroDocumento().length() < 9){
					return "CPF deve conter 14 digitos!";
				}
				
			}else {
				
				return "Cliente não é pessoa física";
			}				
		}else{
			
			return "CPF não pode ser válidado, pois entidade não é um cliente!";
		}	
		return null;
	}

}
