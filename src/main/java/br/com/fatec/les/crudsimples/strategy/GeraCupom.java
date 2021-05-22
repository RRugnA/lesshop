package br.com.fatec.les.crudsimples.strategy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import br.com.fatec.les.crudsimples.model.Cupom;
import br.com.fatec.les.crudsimples.model.TipoCupom;
import br.com.fatec.les.crudsimples.model.UsoCupom;

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
	
	public static Cupom cupomTroca(BigDecimal valorTroca) {
		Cupom cupom = new Cupom();
		
		cupom.setDataCadastro(LocalDate.now());
		cupom.setTipoCupom(TipoCupom.TROCA);
		cupom.setUsoCupom(UsoCupom.UNICO);
		cupom.setValorDesconto(valorTroca);
		
		return cupom;
	}
}		