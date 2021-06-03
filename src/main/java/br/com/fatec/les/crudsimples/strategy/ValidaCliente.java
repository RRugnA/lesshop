package br.com.fatec.les.crudsimples.strategy;

import java.security.Principal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.fatec.les.crudsimples.dto.RequisicaoUsuario;
import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.TipoStatus;
import br.com.fatec.les.crudsimples.model.Usuario;
import ch.qos.logback.core.net.SyslogOutputStream;

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
	
	public static Cliente localizaCliente(Usuario usuario) {
		Cliente cliente = usuario.getCliente();	
		return cliente;
	}	
	
	public static boolean validaSenha(String senha) {
		boolean validador = true;
		int verificador;	
		
		char[] caracteres = senha.toCharArray();
		
		String maiusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] lms = maiusculas.toCharArray();
		
		String numeros = "0123456789";
		char[] lns = numeros.toCharArray();
		
//		VALIDA SENHA COM 8 CARACTERES
		if(senha.length() < 8) {
			System.out.println("Possui menos de 8 caracteres");
			validador = false;
			return validador;
		}	
		
		for(char letra : caracteres) {
			
//			VALIDA SENHA COM LETRA MAIÚSCULA
			verificador = 0;
			for(char lm : lms) {
				if(letra == lm){
					System.out.println("Possui letra maiúscula");
					verificador = 1;
				}
				if(verificador < 1) {
					validador = false;
					return validador;
				}
			}
			
//			VALIDA SENHA COM NÚMERO
			verificador = 0;
			for(char ln : lns) {
				if(letra == ln) {
					System.out.println("Possui número");
					verificador = 1;
				}
				if(verificador < 1) {
					validador = false;
					return validador;
				}
			}
		}
		
		return validador;
	}
}
