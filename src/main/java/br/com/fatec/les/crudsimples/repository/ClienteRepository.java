package br.com.fatec.les.crudsimples.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.crudsimples.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>  {
	
}
