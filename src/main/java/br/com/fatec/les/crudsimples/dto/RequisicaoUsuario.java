package br.com.fatec.les.crudsimples.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.fatec.les.crudsimples.model.Role;
import br.com.fatec.les.crudsimples.model.Usuario;

public class RequisicaoUsuario {

	private String login;
	private String senha;
	private String senha2;

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

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public String validaSenha(String senha, String senha2) {
		
		String error = "As senhas não são iguais!";
		
		if(!senha.equals(senha2)) {
			return error;
		}
		
		return "sucesso";
	}

	public Usuario toUsuario() {

		Usuario user = new Usuario();
		user.setLogin(login);
		user.setSenha(new BCryptPasswordEncoder().encode(senha));
		
		Role role = new Role();
		role.setNomeRole("ROLE_USER");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		
		user.setRoles(roles);
		
		return user;
	}
}
