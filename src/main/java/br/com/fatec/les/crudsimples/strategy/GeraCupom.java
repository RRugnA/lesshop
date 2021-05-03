package br.com.fatec.les.crudsimples.strategy;

import java.util.List;
import java.util.Random;

import br.com.fatec.les.crudsimples.model.Cupom;

public class GeraCupom {

	public static String gerarCupom(List<Cupom> cupons) {
		boolean cupomValido = false;
		String novoCupom;
		
		while(!cupomValido) {
			novoCupom = "";
			int leftLimit = 97; // letter 'a'
		    int rightLimit = 122; // letter 'z'
		    int targetStringLength = 3;
		    Random random = new Random();

		    novoCupom = random.ints(leftLimit, rightLimit + 1)
		      .limit(targetStringLength)
		      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		      .toString();
		    
		    novoCupom = novoCupom.toUpperCase() + "2021";
		    
		    for(Cupom cupom : cupons) {
		    	if(!cupom.getCodigo().equals(novoCupom)) {
		    		cupomValido = true;
		    		return novoCupom;
		    	}
		    }
		}
	    
	    return null;
	}
	
}		