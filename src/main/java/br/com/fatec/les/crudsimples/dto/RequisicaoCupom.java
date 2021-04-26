package br.com.fatec.les.crudsimples.dto;

import java.time.LocalDate;

import br.com.fatec.les.crudsimples.model.Cupom;
import br.com.fatec.les.crudsimples.model.TipoCupom;

public class RequisicaoCupom {

	private String codigo;
	private String tipoCupom;
	private String valorDesconto;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public Cupom toCupom() {
		Cupom cupom = new Cupom();
		
		cupom.setDataCadastro(LocalDate.now());
		cupom.setCodigo(codigo);
		cupom.setTipoCupom(TipoCupom.valueOf(tipoCupom));
		cupom.setValorDesconto(Integer.parseInt(valorDesconto));
		
		return cupom;
	}
}
