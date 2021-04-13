package br.com.fatec.les.crudsimples.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.crudsimples.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{

	Usuario findByLogin(String login);
}
