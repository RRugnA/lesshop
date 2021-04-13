package br.com.fatec.les.crudsimples.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import br.com.fatec.les.crudsimples.model.Cidade;
import br.com.fatec.les.crudsimples.model.Endereco;
import br.com.fatec.les.crudsimples.model.Estado;
import br.com.fatec.les.crudsimples.model.TipoEndereco;

public class RequisicaoEndereco {

	private String clienteId;
	private String logradouro;
	private String num;
	private String cep;
	private String complemento;
	private String cidade;
	private String estado;
	private String tipoEndereco;

	public String getClienteId() {
		return clienteId;
	}

	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(String tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}

	public Date toValidade(String data) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			Date date = formatter.parse(data);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	public Endereco toEndereco() {
		Endereco end = new Endereco();
		end.setLogradouro(logradouro);
		end.setNumero(num);
		end.setCep(cep);
		end.setComplemento(complemento);

		end.setTipoEndereco(TipoEndereco.valueOf(tipoEndereco));
		end.setDataCadastro(LocalDate.now());

		Cidade cid = new Cidade();
		cid.setCidade(cidade);

		Estado est = new Estado();
		est.setEstado(estado);

		cid.setEstado(est);

		end.setCidade(cid);

		return end;
	}

}
