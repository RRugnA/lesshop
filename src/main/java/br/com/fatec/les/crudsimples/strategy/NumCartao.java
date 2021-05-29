package br.com.fatec.les.crudsimples.strategy;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.les.crudsimples.model.Documento;

public class NumCartao {

	public static String gerarNumCartao(String documento) {		
//		String numero = documento.getNumeroCartao().toString();		
		documento = documento.substring(documento.length() - 4, documento.length());		
		documento = "***" + documento;		
		return documento;
	}
	
	public static List<String> gerarListaStringNumCartao(List<String> docs) {	
		List<String> docCripto = new ArrayList<String>();
		for(String doc : docs) {
			doc = NumCartao.gerarNumCartao(doc);
			docCripto.add(doc);
		}		
		return docCripto;
	}
	
	public static String gerarNumCartaoDoc(Documento documento) {		
		String numero = documento.getNumeroCartao().toString();		
		numero = numero.substring(numero.length() - 4, numero.length());		
		numero = "***" + numero;		
		return numero;
	}
	
	public static List<Documento> gerarListaDocNumCartao(List<Documento> docs) {		
		for(Documento doc : docs) {
			doc.setNumeroCartao(NumCartao.gerarNumCartaoDoc(doc));
		}		
		return docs;
	}
}
