package br.com.fatec.les.crudsimples.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ValidaParcela {	
	
	public static List<BigDecimal> valorParcelas(BigDecimal valorDeCompra) {
		List<BigDecimal> parcelas = new ArrayList<BigDecimal>();
		for(int i = 1; i <= 10; i++) {		
			BigDecimal valorParcela = valorDeCompra;
			valorParcela = valorParcela.divide(new BigDecimal(i), 2, RoundingMode.HALF_DOWN);
			parcelas.add(valorParcela);
		}
		return parcelas;
	}
}
