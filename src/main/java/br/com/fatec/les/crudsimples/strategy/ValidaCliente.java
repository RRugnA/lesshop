package br.com.fatec.les.crudsimples.strategy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.fatec.les.crudsimples.dto.RequisicaoUsuario;
import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.TipoStatus;
import br.com.fatec.les.crudsimples.model.Usuario;

public class ValidaCliente {

	public static void alteraStatus(Cliente cliente) {
		if(cliente.getTipoStatus().equals(TipoStatus.ATIVO)) {
			cliente.setTipoStatus(TipoStatus.INATIVO);
		} else {
			cliente.setTipoStatus(TipoStatus.ATIVO);
		}	
	}
	
	public static boolean validaSenha(String senha, String senha2) {		
		if(!senha.equals(senha2)) {
			return false;
		}		
		return true;
	}
	
	public static void setSenha(String senha, Usuario usuario) {
		usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
	}
	
	public static boolean validaSenhaAtual(String senha, Usuario usuario) {
		if(new BCryptPasswordEncoder().matches(senha, usuario.getSenha())){
			return true;
		}
		return false;
	}
	
	public static boolean alteraSenha(RequisicaoUsuario requisicao, Usuario usuario) {
		if(ValidaCliente.validaSenhaAtual(requisicao.getSenhaAtual(), usuario)) {
			System.out.println("senhaAtual identica");
			
			if(ValidaCliente.validaSenha(requisicao.getSenha(), requisicao.getSenha2())) {
				System.out.println("novas senhas são idênticas");
				
				ValidaCliente.setSenha(requisicao.getSenha(), usuario);
				
				System.out.println("Senha alterada com sucesso");
				return true;
				
			} else {
				System.out.println("novas senhas não batem");
			}
			
		} else {
			System.out.println("senhaAtual não confere");
		}
		
		return false;
	}
}
