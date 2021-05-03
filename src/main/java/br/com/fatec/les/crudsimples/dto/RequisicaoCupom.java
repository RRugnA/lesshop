package br.com.fatec.les.crudsimples.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.fatec.les.crudsimples.model.Cupom;
import br.com.fatec.les.crudsimples.model.TipoCupom;
import br.com.fatec.les.crudsimples.model.UsoCupom;

public class RequisicaoCupom {

	private String codigo;
	private String tipoCupom;
	private String valorDesconto;
	private String usoCupom;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo.toUpperCase();
	}

	public String getTipoCupom() {
		return tipoCupom;
	}

	public void setTipoCupom(String tipoCupom) {
		this.tipoCupom = tipoCupom;
	}

	public String getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(String valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	
	public String getUsoCupom() {
		return usoCupom;
	}

	public void setUsoCupom(String usoCupom) {
		this.usoCupom = usoCupom;
	}

	public Cupom toCupom() {
		Cupom cupom = new Cupom();
		
		cupom.setDataCadastro(LocalDate.now());
		cupom.setCodigo(codigo);
		cupom.setTipoCupom(TipoCupom.valueOf(tipoCupom));
		cupom.setValorDesconto(new BigDecimal(valorDesconto));
		cupom.setUsoCupom(UsoCupom.valueOf(usoCupom));
		
		return cupom;
	}
	
	public Cupom cupomTroca(BigDecimal valorTroca) {
		Cupom cupom = new Cupom();
		
		cupom.setDataCadastro(LocalDate.now());
		cupom.setTipoCupom(TipoCupom.TROCA);
		cupom.setUsoCupom(UsoCupom.UNICO);
		cupom.setValorDesconto(valorTroca);
		
		return cupom;
	}
}
