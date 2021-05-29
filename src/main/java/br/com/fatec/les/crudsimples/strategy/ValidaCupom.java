package br.com.fatec.les.crudsimples.strategy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import br.com.fatec.les.crudsimples.model.Cupom;
import br.com.fatec.les.crudsimples.model.TipoCupom;
import br.com.fatec.les.crudsimples.model.UsoCupom;

public class ValidaCupom {

	public static String cupomAtivo(Cupom cupom) {
		if(cupom.getTipoCupom().equals(TipoCupom.INATIVO)) {
			return "Cupom inválido";
		}
		return "Cupom válido";
	}
	
	public static boolean cupomValido(String codigo, List<Cupom> cupons) {
		for(Cupom cup : cupons) {
			if(cup.getCodigo().equals(codigo)) {
				return false;
			}
		}
		return true;
	}
	
	public static BigDecimal somaCupons(List<Cupom> cupons) {
		BigDecimal valorTotal = new BigDecimal(0);
		
		for(Cupom cupom : cupons) {
			valorTotal = valorTotal.add(cupom.getValorDesconto());
		}
		
		return valorTotal;
	}
	
	public static boolean cupomMaior(BigDecimal somaCupons, BigDecimal valorCompra) {
		if(somaCupons.doubleValue() <= valorCompra.doubleValue()) {
			return false;
		}
		return true;
	}
	
	public static BigDecimal valorCompensado(BigDecimal somaCupons, BigDecimal valorProduto) {
		BigDecimal compensacao = new BigDecimal(0);
		
		if(somaCupons.doubleValue() > valorProduto.doubleValue()) {
			compensacao = somaCupons.subtract(valorProduto);
		}
		
		return compensacao;
	}
	
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