package br.com.fatec.les.crudsimples.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.TipoCliente;
import br.com.fatec.les.crudsimples.model.TipoDocumento;
import br.com.fatec.les.crudsimples.model.TipoStatus;

public class RequisicaoCliente {

	private String clienteId;
	private String nome;
	private String tipoCliente;
	private String tipoDocumento;
	private String codigo;
	private String tipoStatus;
	private String valorDeCompra;

	public String getClienteId() {
		return clienteId;
	}

	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipoStatus() {
		return tipoStatus;
	}

	public void setTipoStatus(String tipoStatus) {
		this.tipoStatus = tipoStatus;
	}

	public String getValorDeCompra() {
		return valorDeCompra;
	}

	public void setValorDeCompra(String valorDeCompra) {
		this.valorDeCompra = valorDeCompra;
	}

	public BigDecimal toValorDeCompra(String valorDeCompra) {
		BigDecimal valor = new BigDecimal(valorDeCompra);
		return valor;
	}
	
	public Cliente toCliente() {

		Cliente cliente = new Cliente();

		cliente.setNome(nome);

		if (clienteId != null) {
			cliente.setId(Long.valueOf(clienteId));
		}

		cliente.setDataCadastro(LocalDate.now());
		cliente.setTipoCliente(TipoCliente.valueOf(tipoCliente));
		cliente.setTipoDocumento(TipoDocumento.valueOf(tipoDocumento));
		cliente.setNumeroDocumento(codigo);
		cliente.setTipoStatus(TipoStatus.ATIVO);

		return cliente;
	}

}
