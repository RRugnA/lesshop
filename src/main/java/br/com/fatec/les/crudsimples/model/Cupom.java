package br.com.fatec.les.crudsimples.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Cupom extends EntidadeDominio {

	@Id
	private String codigo;

	@Enumerated(EnumType.STRING)
	private TipoCupom tipoCupom;

	@Enumerated(EnumType.STRING)
	private UsoCupom usoCupom;

	private BigDecimal valorDesconto;
	private LocalDate dataCadastro;

	@ManyToMany
	private List<Compra> compras;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public TipoCupom getTipoCupom() {
		return tipoCupom;
	}

	public void setTipoCupom(TipoCupom tipoCupom) {
		this.tipoCupom = tipoCupom;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public void addCompra(Compra compra) {
		this.compras.add(compra);
	}

	public UsoCupom getUsoCupom() {
		return usoCupom;
	}

	public void setUsoCupom(UsoCupom usoCupom) {
		this.usoCupom = usoCupom;
	}

}
