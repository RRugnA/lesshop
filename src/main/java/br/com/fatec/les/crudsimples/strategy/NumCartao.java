package br.com.fatec.les.crudsimples.strategy;

import java.util.List;

import br.com.fatec.les.crudsimples.model.Documento;

public class NumCartao {

	public static String gerarNumCartao(Documento documento) {		
		String numero = documento.getNumeroCartao().toString();		
		numero = numero.substring(numero.length() - 4, numero.length());		
		numero = "***" + numero;		
		return numero;
	}
	
	public static List<Documento> gerarListaNumCartao(List<Documento> docs) {		
		for(Documento doc : docs) {
			doc.setNumeroCartao(NumCartao.gerarNumCartao(doc));
		}		
		return docs;
	}
}
