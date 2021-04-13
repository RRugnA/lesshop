package br.com.fatec.les.crudsimples.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.les.crudsimples.model.Cliente;
import br.com.fatec.les.crudsimples.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>  {
	List<Endereco> findByCliente(Cliente cliente);
}
