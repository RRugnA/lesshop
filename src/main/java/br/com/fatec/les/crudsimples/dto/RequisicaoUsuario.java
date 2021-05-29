package br.com.fatec.les.crudsimples.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.fatec.les.crudsimples.model.Role;
import br.com.fatec.les.crudsimples.model.Usuario;
import br.com.fatec.les.crudsimples.strategy.ValidaCliente;

public class RequisicaoUsuario {

	private String login;
	private String senha;
	private String senha2;
	private String senhaAtual;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha2() {
		return senha2;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public Usuario toUsuario() {

		Usuario user = new Usuario();
		user.setLogin(login);
		if(ValidaCliente.validaSenha(senha, senha2)) {
			user.setSenha(new BCryptPasswordEncoder().encode(senha));
			Role role = new Role();
			role.setNomeRole("ROLE_USER");
			List<Role> roles = new ArrayList<Role>();
			roles.add(role);
			
			user.setRoles(roles);
			
			return user;
		}
		
		return null;
		
	}
}
